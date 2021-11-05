
package tinder.repositorios;
//@autor FABIAN C

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tinder.entidades.Usuario;


@Repository
/* REPOSITORIOS son INTERFACES y EXTIENDEN de JpaRepository, indicamos 
 entre <> la Clase y el tipo de Id que tiene la Clase*/
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")    
public Usuario buscarPorMail(@Param("mail") String mail);

}
