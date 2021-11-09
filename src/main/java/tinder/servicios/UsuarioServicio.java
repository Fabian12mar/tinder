package tinder.servicios;
//@autor FABIAN C

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Foto;
import tinder.entidades.Usuario;
import tinder.errores.ErrorServicio;
import tinder.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    /* como atributo de esta class ponemos el repositorio y con Autowired la 
    variable la inicializa el servidor de app, no hace falta 
    UsuarioRepositorio = new UsuarioRepositorio*/
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;

//metodo registrar o CREAR  
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave) throws ErrorServicio {

        validar(nombre, apellido, mail, clave);

//si se da alguno de los errores de arriba, no se ejecuta lo siguiente
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setClave(clave);
        usuario.setAlta(new Date());

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        
        usuarioRepositorio.save(usuario);

    }

    public void modificar(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave) throws ErrorServicio {

        validar(nombre, apellido, mail, clave);

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
            usuario.setClave(clave);
            
            String idFoto = null;
            if(usuario.getFoto() != null){
                idFoto = usuario.getFoto().getId();
            } 

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            usuario.setFoto(foto);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado. ");
        }

    }

    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            
  usuarioRepositorio.save(usuario);
            
        }else {
            throw new ErrorServicio("No se encontro el usuario solicitado. ");
        }
    }

    public void habilitar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
/* a diferencia del metodo deshabilitar aqui borramos la fecha de baja y 
   lo guardamos */
            usuario.setBaja(null);
            
  usuarioRepositorio.save(usuario);
            
        }else {
            throw new ErrorServicio("No se encontro el usuario solicitado. ");
        }
    }
    
    /* creamos un metodo validar para no repetir la logica de validar de arriba,
 traemos el codigo de arriba aqui y desde registrar llamamos a este metodo*/
    private void validar(String nombre, String apellido, String mail, String clave) throws ErrorServicio {

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

    }

}
