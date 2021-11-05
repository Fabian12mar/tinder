
package tinder.repositorios;
//@autor FABIAN C

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tinder.entidades.Voto;


@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String> {

//donde yo fui votante. De la fecha mas nueva a la mas vieja
@Query("SELECT c FROM Voto c WHERE c.mascota1.id = :id ORDER BY c.fecha DESC")
public List<Voto> buscarVotosPropios(@Param("id") String id);

//donde yo fui votado.
@Query("SELECT c FROM Voto c WHERE c.mascota2.id = :id ORDER BY c.fecha DESC")
public List<Voto> buscarVotosRecibidos(@Param("id") String id);


}
