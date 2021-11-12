package tinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tinder.servicios.UsuarioServicio;

@SpringBootApplication
public class TinderApplication {
    
    @Autowired
    private UsuarioServicio usuarioServicio;

	public static void main(String[] args) {
		SpringApplication.run(TinderApplication.class, args);
	}
        
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
        }
}
