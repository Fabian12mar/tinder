package tinder.controladores;
//@autor FABIAN C

import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tinder.errores.ErrorServicio;
import tinder.servicios.UsuarioServicio;

@Controller
//configura cual es la url que va escuchar este controlador, a partir de la barra /
@RequestMapping("/")
public class RegistroControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registro")
//metodo regitstro, responde a traves de metodo Get de https, a partir de la barra /
    public String registro() {
//abre vista
        return "registro.html";
    }

    //segun lo que pusimos en form html en action
    @PostMapping("/registrar")
    /* anotamos RequestParam que son parametros de la solicitud http, tambien
    podemos indicarlo como parametro opcional */
    public String registrar(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2) {

        //agregamos try catch porque las validaciones del servicio manejaban errores
        try {
            usuarioServicio.registrar(null, nombre, apellido, mail, clave1);
        } catch (ErrorServicio ex) {
            java.util.logging.Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "registro.html";
        }

        return "index.html";
    }

}
