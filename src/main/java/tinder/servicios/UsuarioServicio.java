package tinder.servicios;
//@autor FABIAN C

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Foto;
import tinder.entidades.Usuario;
import tinder.entidades.Zona;
import tinder.errores.ErrorServicio;
import tinder.repositorios.UsuarioRepositorio;
import tinder.repositorios.ZonaRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

    /* como atributo de esta class ponemos el repositorio y con Autowired la 
    variable la inicializa el servidor de app, no hace falta 
    UsuarioRepositorio = new UsuarioRepositorio*/
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private FotoServicio fotoServicio;

    @Autowired
    private ZonaRepositorio zonaRepositorio;

//metodo registrar o CREAR 
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave, String clave2, String idZona) throws ErrorServicio {

        Zona zona = zonaRepositorio.getOne(idZona);

        validar(nombre, apellido, mail, clave, clave2, zona);

//si se da alguno de los errores de arriba, no se ejecuta lo siguiente
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setZona(zona);

//generamos variable 
        String encriptada = new BCryptPasswordEncoder().encode(clave);
//al usuario lo persistimos con la clave encriptada
        usuario.setClave(encriptada);

        usuario.setAlta(new Date());

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);

        usuarioRepositorio.save(usuario);

        //le damos bienvenida al usuario recien registrado
        //cuerpo de mens, titulo y mail 
        //notificacionServicio.enviar("Bienvenidos al tinder de mascotas! ", "Tinder de Mascotas", usuario.getMail());
    }

    @Transactional
    public void modificar(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave, String clave2, String idZona) throws ErrorServicio {

                Zona zona = zonaRepositorio.getOne(idZona);
        
        validar(nombre, apellido, mail, clave, clave2, zona);

        /* validamos que si id no existe, con el findById que nos devuelve una
 clase Optional, y reemplazamos con estas 3 lineas la cuarta, con la clase Optional 
 puedo ver si  como respuesta al id me devuelve un usuario, entonces lo busco
 y modifico, sino devuelve una excepcion*/
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            //buscamos usuario
            // Usuario usuario = usuarioRepositorio.findById(id).get();
            /* encontramos y seteamos nuevos datos, como ya tenia id lo actualiza, metodo
  registrar lo crea, porque cuando se mapeo la entidad pusimos id generar uuid*/
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setMail(mail);
            usuario.setZona(zona);

            //generamos variable 
            String encriptada = new BCryptPasswordEncoder().encode(clave);
//al usuario lo persistimos con la clave encriptada
            usuario.setClave(encriptada);

            String idFoto = null;
            if (usuario.getFoto() != null) {
                idFoto = usuario.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            usuario.setFoto(foto);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado. ");
        }

    }

    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario);

        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado. ");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            /* a diferencia del metodo deshabilitar aqui borramos la fecha de baja y 
   lo guardamos */
            usuario.setBaja(null);

            usuarioRepositorio.save(usuario);

        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado. ");
        }
    }

    /* creamos un metodo validar para no repetir la logica de validar de arriba,
 traemos el codigo de arriba aqui y desde registrar llamamos a este metodo*/
    private void validar(String nombre, String apellido, String mail, String clave, String clave2, Zona zona) throws ErrorServicio {

        //validamos ingreso datos, creamos package clase e
        if (nombre == null || nombre.isEmpty()) {
            /* creamos un package creamos excepcion nuestra a cambio que nos lanze excepcion generica  */
            throw new ErrorServicio("El nombre del usuario no puede ser nulo. ");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo. ");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail del usuario no puede ser nulo. ");
        }
        if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener mas de 6 digitos");
        }
/* LA VALIDACION CON EQUALS DISTINTA LO ESPECIFICO CON EL SIGNO ! DELANTE DE 
PRIMER CLAVE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< */
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales. ");
        }
        if (zona == null) {
            throw new ErrorServicio("No se encontro la zona solicitada. ");
        }


    }

    /* este metodo se llama cuando el ususario quiere autentificarse en la plataforma,
 Spring Security llama a este metodo, si existe un usuario con el mail
 que ingreso en el formulario le crea estos 3 permisos o accesos, y los pasa
  al constructor de usuario de spring*/
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            
 //recupera los atributos del Request o solicitud http          
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
 //solicita los datos de sesion de esa solicitud         
            HttpSession session = attr.getRequest().getSession(true);
/*guardo los datos de sesion, como el objeto usuario de la solicitud, en una
variable de sesion "usuariosession*/
            session.setAttribute("usuariosession", usuario);

    /*        GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
            permisos.add(p2);

            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
            permisos.add(p3);                                                    */

            /* pasamos los datos del usuario y la lista de permisos al constructor
            de usuario de Spring Security */
            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;

        } else {
            // si no existe retornamos un usuario vacio
            return null;
        }
    }

}
