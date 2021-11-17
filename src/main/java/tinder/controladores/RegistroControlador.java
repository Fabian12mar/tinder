package tinder.controladores;
//@autor FABIAN C

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//configura cual es la url que va escuchar este controlador, a partir de la barra /
@RequestMapping("/")
public class RegistroControlador {

    @GetMapping("/registro")
//metodo regitstro, responde a traves de metodo Get de https, a partir de la barra /
    public String registro() {

        return "registro.html";

    }

}
