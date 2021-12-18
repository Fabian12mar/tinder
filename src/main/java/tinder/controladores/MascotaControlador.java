package tinder.controladores;
//@autor FABIAN C

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Mascota;
import tinder.entidades.Usuario;
import tinder.entidades.Zona;
import tinder.enumeraciones.Sexo;
import tinder.enumeraciones.Tipo;
import tinder.errores.ErrorServicio;
import tinder.repositorios.ZonaRepositorio;
import tinder.servicios.MascotaServicio;
import tinder.servicios.UsuarioServicio;

@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
@Controller
@RequestMapping("/mascota") //URL
public class MascotaControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private MascotaServicio mascotaServicio;
    
//    @Autowired
//    private ZonaRepositorio zonaRepositorio;
    
//metodo Eliminar MAPEADO CON DELETEmapping    
    @PostMapping("/eliminar-perfil")
    public String eliminar(HttpSession session, @RequestParam String id){
        try {
            Usuario login = (Usuario)session.getAttribute("usuariosession");
            mascotaServicio.eliminar(login.getId(), id);
        } catch (ErrorServicio ex) {
            Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/mascota/mis-mascotas";
        
    }
    
    
    @GetMapping("/mis-mascotas") //URL
    public String misMascotas(HttpSession session, ModelMap model) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if(login == null) {
            return "redirect:/login";
        }
        
        List<Mascota> mascotas = mascotaServicio.buscarMascotasPorUsuario(login.
                getId());
        model.put("mascotas", mascotas);
        
        return "mascotas";
        
    }
    
    
    @GetMapping("/editar-perfil") //URL
//recibe session para validar que login logueado sea el mismo de la session    
    public String editarPerfil(HttpSession session, @RequestParam(required = false) String id, @RequestParam(required = false) String accion, ModelMap model) {
        
        if (accion == null) {
            accion = "Crear";
        }
        
        Usuario login = (Usuario)session.getAttribute("usuariosession");
        if(login == null) {
            return "redirect:/login";
        }

        //añade al model una mascota, con el nombre "perfil"
        Mascota mascota = new Mascota();
        if (id != null && !id.isEmpty()) {
            try {
                mascota = mascotaServicio.buscarPorId(id);
            } catch (ErrorServicio ex) {
                Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        model.put("perfil", mascota);
        model.put("accion", accion);

        model.put("sexos", Sexo.values());
        model.put("tipos", Tipo.values());

        return "mascota.html";

//        Usuario login = (Usuario) session.getAttribute("usuariosession");
////si el login logueado es !distinto al que recibo por parametro
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/inicio";
//
//        }
//
//        try {
//            Usuario login = usuarioServicio.buscarPorId(id);
//            model.addAttribute("perfil", login);
//        } catch (ErrorServicio e) {
//            model.addAttribute("error", e.getMessage());
//        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-perfil") //URL
/*para cuando modifique el login tambien se modifique el login de la sesion
agrego parametro HttpSession para tener acceso y luego llamar a session y 
 colocar atributo login nuevo*/
    public String actualizar(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam Sexo sexo, @RequestParam Tipo tipo) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if(login == null) {
            return "redirect:/inicio";
        }

        try {
            if (id == null || id.isEmpty()) {
                mascotaServicio.agregarMascota(archivo, login.getId(), nombre, sexo, tipo);
            } else {
                mascotaServicio.modificar(archivo, login.getId(), id, nombre, sexo, tipo);

            }

            return "redirect:/inicio";

        } catch (ErrorServicio ex) {
            Mascota mascota = new Mascota();
            mascota.setId(id);
            mascota.setNombre(nombre);
            mascota.setSexo(sexo);
            mascota.setTipo(tipo);

            modelo.put("accion", "Actualizar");
            modelo.put("sexos", Sexo.values());
            modelo.put("tipos", Tipo.values());
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", mascota);

            return "mascota.html";

        }
    }

}

//    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
//    @PostMapping("/actualizar-perfil") //URL
///*para cuando modifique el login tambien se modifique el login de la sesion
//agrego parametro HttpSession para tener acceso y luego llamar a session y 
// colocar atributo login nuevo*/
//    public String registrar(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {
//
//        Usuario login = null;
//
//        try {
//            Usuario login = (Usuario) session.getAttribute("usuariosession");
////si el login logueado es !distinto al que recibo por parametro
//            if (login == null || !login.getId().equals(id)) {
//                return "redirect:/inicio";
//            }
//
//            login = usuarioServicio.buscarPorId(id);
//            usuarioServicio.modificar(archivo, id, nombre, apellido, mail, clave1, clave2, idZona);
////pisa el login logueado con los datos nuevos            
//            session.setAttribute("usuariosession", login);
//
//            return "redirect:/inicio";
//
//        } catch (ErrorServicio ex) {
//           // List<Zona> zonas = zonaRepositorio.findAll();
//           // modelo.put("zonas", zonas);
//            modelo.put("error", ex.getMessage());
//            modelo.put("perfil", login);
//
//            return "perfil.html";
//
//        }
//
//    }

