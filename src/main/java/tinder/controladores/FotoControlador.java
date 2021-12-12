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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tinder.entidades.Mascota;
import tinder.entidades.Usuario;
import tinder.errores.ErrorServicio;
import tinder.servicios.MascotaServicio;
import tinder.servicios.UsuarioServicio;

@Controller
@RequestMapping("/foto") //URL
public class FotoControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private MascotaServicio mascotaServicio;

/*agregando el id entre llaves indicamos que el id pasa como parte de la URL, y
cambiando el parametro del metodo de RequestParam a PathVariable indica que el
id lo va a sacar de lo que venga en la ruta {} */
    @GetMapping("/usuario/{id}") //URL
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) {

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

    /*agregando el id entre llaves indicamos que el id pasa como parte de la URL, y
cambiando el parametro del metodo de RequestParam a PathVariable indica que el
id lo va a sacar de lo que venga en la ruta {} */
    @GetMapping("/mascota/{id}") //URL
    public ResponseEntity<byte[]> fotoMascota(@PathVariable String id) {

        try {
            Mascota mascota = mascotaServicio.buscarPorId(id);
            if (mascota.getFoto() == null) {
                throw new ErrorServicio("El usuario no tiene una foto asignada. ");

            }
            byte[] foto = mascota.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (ErrorServicio ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }
    
}
