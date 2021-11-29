
package tinder.controladores;
//@autor FABIAN C

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Usuario;
import tinder.entidades.Zona;
import tinder.errores.ErrorServicio;
import tinder.repositorios.ZonaRepositorio;
import tinder.servicios.UsuarioServicio;


@Controller
@RequestMapping("/usuario") //URL
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ZonaRepositorio zonaRepositorio;
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editar-perfil") //URL
//recibe session para validar que usuario logueado sea el mismo de la session    
    public String editarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {
        
        List<Zona> zonas = zonaRepositorio.findAll();
        model.put("zonas", zonas);
        
        Usuario login = (Usuario) session.getAttribute("usuariosession");
//si el usuario logueado es !distinto al que recibo por parametro
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/inicio";
            
        }
       
        try {
            Usuario usuario = usuarioServicio.buscarPorId(id);
            model.addAttribute("perfil", usuario);
        } catch(ErrorServicio e) {
            model.addAttribute("error", e.getMessage());
        }
        
        return "perfil.html";
        
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-perfil") //URL
/*para cuando modifique el usuario tambien se modifique el usuario de la sesion
agrego parametro HttpSession para tener acceso y luego llamar a session y 
 colocar atributo usuario nuevo*/
    public String registrar(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {
     
        Usuario usuario = null;
 
        try {
            usuario = usuarioServicio.buscarPorId(id);
            usuarioServicio.modificar(archivo, id, nombre, apellido, mail, clave1, clave2, idZona);
//pisa el usuario logueado con los datos nuevos            
            session.setAttribute("usuariosession", usuario);
            
            return "redirect:/inicio";
            
        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
            modelo.put("zonas", zonas);
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);
            
            return "perfil.html";
            
        }
        
    }
    

}
