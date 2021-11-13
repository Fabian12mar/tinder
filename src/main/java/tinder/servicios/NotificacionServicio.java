
package tinder.servicios;
//@autor FABIAN C

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class NotificacionServicio {

@Autowired
private JavaMailSender mailSender;

/* anotamos como ASINCRONO, para que el hilo de ejecucion no espera a que se 
termine de enviar el mail, lo ejecuta en un hilo paralelo , por eso el usuario
tiene respuesta inmediata, no tiene que esperar que se termine de enviarl el mail*/
@Async
public void enviar(String cuerpo, String titulo, String mail){
//clase de JavaMailSender para enviar mensaje
    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(mail);
    mensaje.setFrom("noreply@tinder-mascota.com");
    mensaje.setSubject(titulo);
    mensaje.setText(cuerpo);
    
    mailSender.send(mensaje);
}

}
