package tinder.servicios;
//@autor FABIAN C

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Foto;
import tinder.errores.ErrorServicio;
import tinder.repositorios.FotoRepositorio;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
 /*creamos un try an catch por el metodo getBytes me marca que puede lanzar una
 excepcion, por si la lectura del archivo falla, imprimimos el error en la consola*/
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
  
 //si carga una foto por primera vez, o ya tiene y la quiere cambiar
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio{
        if(archivo != null){
         try {
                Foto foto = new Foto();
                
                if(idFoto != null){
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if(respuesta.isPresent()){
                        foto = respuesta.get();
                    }
                }
                
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
                  
  
    
    
}
