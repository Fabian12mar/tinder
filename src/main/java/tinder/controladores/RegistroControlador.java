package tinder.controladores;
//@autor FABIAN C

import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    podemos indicarlo como parametro opcional. ModelMap mostramos errores en navegador */
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2) {

        //agregamos try catch porque las validaciones del servicio manejaban errores
        try {
            usuarioServicio.registrar(null, nombre, apellido, mail, clave1);
        } catch (ErrorServicio ex) {
            /* esto muestra los errores por consola, por ej si clave tiene menos de 6 digitos 
o si no se completa formulario */
            // java.util.logging.Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
/* cambiamos metodo de arriba por metodo put de modelo, para ver errores
por navegador. "error" es nombre de variable */
            modelo.put("error", ex.getMessage());

            /* enviamos las variables nuevamente a la vista para que en caso de error 
 no se pierda lo que habia ingresado el usuario, en el form se agrega un 
            th:value*/
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);

            return "registro.html";
        }
        /* si datos ingresados estan correctos enviamos a exito en vez de index, y
 mediante metdo put de la clase modelo enviamos al html los mensajes de 
 exito a traves de las variables*/
        modelo.put("titulo", "Bienvenido a Tinder de Mascotas. ");
        modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "exito.html";
    }

}
