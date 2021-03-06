package tinder.servicios;
//@autor FABIAN C

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tinder.entidades.Foto;
import tinder.entidades.Mascota;
import tinder.entidades.Usuario;
import tinder.enumeraciones.Sexo;
import tinder.errores.ErrorServicio;
import tinder.repositorios.MascotaRepositorio;
import tinder.repositorios.UsuarioRepositorio;

@Service
public class MascotaServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;

    public void agregarMascota(MultipartFile archivo, String idUsuario, String nombre, Sexo sexo) throws ErrorServicio {

        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();

        validar(nombre, sexo);

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setSexo(sexo);
        mascota.setAlta(new Date());
        
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);

        mascotaRepositorio.save(mascota);

    }

    public void modificar(MultipartFile archivo, String idUsuario, String idMascota, String nombre, Sexo sexo) throws ErrorServicio {

        validar(nombre, sexo);

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        
        if (respuesta.isPresent()) {

            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);
                
                String idFoto = null;
                if(mascota.getFoto()!= null){
                    idFoto = mascota.getFoto().getId();
                    
                }
                
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                mascota.setFoto(foto);
                
                mascotaRepositorio.save(mascota);
            } else {
                throw new ErrorServicio("No tiene permisos suficientes para realizar la operacion. ");
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el identificador solicitado. ");
        }
    }

    public void elimintar(String idUsuario, String idMascota) throws ErrorServicio {

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId().equals(idUsuario)){
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el identificador solicitado. ");

        }
    }

    public void validar(String nombre, Sexo sexo) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la mascota no puede ser nulo o vacio. ");
        }
        if (sexo == null) {
            throw new ErrorServicio("El sexo de la mascota no puede ser nulo. ");
        }
    }

}
