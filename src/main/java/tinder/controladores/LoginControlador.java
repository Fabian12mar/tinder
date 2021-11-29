
package tinder.controladores;
//@autor FABIAN C

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
//configura cual es la url que va escuchar este controlador, a partir de la barra /
@RequestMapping("/") //URL
public class LoginControlador {

//ESTOS METODOS /login logout ESTAN RELACIONADOS A LA Configuracion Seguridad
@GetMapping("/login") //URL
//metodo login, responde a traves de metodo Get de https, a partir de la barra /
public String login(@RequestParam(required = false) String error, 
        @RequestParam(required = false) String logout, ModelMap model){
    
    if (error != null) {
        model.put("error", "Nombre de usuario o clave incorrectos. ");
    }
    if(logout != null){
        model.put("logout", "Ha salido correctamente de la plataforma. ");
    }
   
return "login.html";

}
    
    

}
