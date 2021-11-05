
package tinder.repositorios;
//@autor FABIAN C

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tinder.entidades.Mascota;


@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{

@Query("SELECT C FROM Mascota c WHERE c.usuario.id = :id")
public List<Mascota> buscarMascotasPorUsuario(@Param("id") String id);

}
