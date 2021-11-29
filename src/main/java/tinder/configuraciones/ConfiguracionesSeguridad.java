package tinder.configuraciones;
//@autor FABIAN C

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tinder.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class ConfiguracionesSeguridad extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
/* LA FORMA DISTINTA DE ESCRIBIR EL CODIGO, LA SIGUIENTE LINEA NO FINALIZA
 EN PUNTO Y COMA PORQUE CONTINUA, CON LA DE ABAJO, Y EL COMIENZO IGUAL auth*/               
                .userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
        
    }
/*esta configuracion esta relacionada a como viajan los datos cuando se loguea
el usuario, no hace referencia al httpSession configurado en Servicio Usuario 
que lo vamos a tomar en el html*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
                .and().formLogin()
                .loginPage("/login") // Que formulario esta mi login
          //cuando se loguea de forma exitosa      
                .loginProcessingUrl("/logincheck")
                .usernameParameter("username") // Como viajan los datos del logueo
                .passwordParameter("password")// Como viajan los datos del logueo
                .defaultSuccessUrl("/inicio") // A que URL viaja ACCESO
                .permitAll()
                .and().logout() // Aca configuro la salida
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
/*asi como enviamos al ingresar a una ruta, a la salida tambien podemos darle 
 una salida, pero hay que hacer otra configuracion */                
               // .defaultSuccessUrl("/")
                .permitAll()
                .and().csrf().disable();
        
    }
}
