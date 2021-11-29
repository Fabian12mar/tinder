package tinder.controladores;
//@autor FABIAN C

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tinder.entidades.Usuario;
import tinder.errores.ErrorServicio;
import tinder.servicios.UsuarioServicio;

@Controller
@RequestMapping("/foto") //URL
public class FotoControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/usuario") //URL
 //ver opcion profe Adri @GetMapping("/usuario/{id}")   
 //ver opcion profe Adri fotoUsuario(@PathVariable String id)
    public ResponseEntity<byte[]> fotoUsuario(@RequestParam String id) {

        try {
            Usuario usuario = usuarioServicio.buscarPorId(id);
            if (usuario.getFoto() == null) {
                throw new ErrorServicio("El usuario no tiene una foto asignada. ");

            }
            byte[] foto = usuario.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (ErrorServicio ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

}
