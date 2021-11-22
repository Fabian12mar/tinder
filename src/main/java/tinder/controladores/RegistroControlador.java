package tinder.controladores;
//@autor FABIAN C

import java.util.List;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Zona;
import tinder.errores.ErrorServicio;
import tinder.repositorios.ZonaRepositorio;
import tinder.servicios.UsuarioServicio;

@Controller
//configura cual es la url que va escuchar este controlador, a partir de la barra /
@RequestMapping("/")
public class RegistroControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @GetMapping("/registro")
//metodo regitstro, responde a traves de metodo Get de https, a partir de la barra /
    public String registro(ModelMap modelo) {
     
/* busca todas las zonas del Repositorio en base de datos y guarda lista en
variable "zonas" en modelo, cuando ingresemos a url registro, el html registro
 llama desde el select a la lista "zonas"*/
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        
//abre vista
        return "registro.html";
    }

    //segun lo que pusimos en form html en action
    @PostMapping("/registrar")
    /* anotamos RequestParam que son parametros de la solicitud http, tambien
    podemos indicarlo como parametro opcional. ModelMap mostramos errores en navegador */
    public String registrar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String mail, 
            @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {

        //agregamos try catch porque las validaciones del servicio manejaban errores
        try {
/* ENVIAMOS ESTOS ATRIBUTOS AL SERVICIO, EL PRIMER PARAMETRO AQUI LO 
 ENVIAVAMOS NULL, PORQUE EL PRIMER PARAMETRO DEL SERVICIO RECIBE EL ARCHIVO DE
FOTO, QUE NO ESTABAMOS RECIBIENDO POR AHORA */
            usuarioServicio.registrar(archivo, nombre, apellido, mail, clave1, clave2, idZona);
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
