# tinder
Ejercitacion Git con proyecto Java Spring "tinder de mascotas".
Segundo push, creo clases servicios, y agrego entidad Foto, FotoServicio, FotoRepositorio, y las Relaciones de Mascota y Usuario con Foto.
Agrego package configuraciones Clase ConfiguracionesSeguridad, para la seguridad del proyecto.
CREO RAMA DEVELOP
Conectamos UsuarioServicio con la seguridad de Spring, para poder autentificar usuarios mediante el UsuarioServicio, primero la clase implements la interfaz de Spring Security UserDetailsService, que nos obliga a implementar Override de un metodo  abstracto LoadByUsername, este metodo recibe el nombre del usuario, lo busca en el repositorio y lo transforma en un usuario de Spring Security. "SI TUVIERAMOS MAS DE UN ROL POR EJ UN USUARIO ADMINISTRADOR QUE PUDIESE POR EJ CREAR LAS ZONAS, AQUI A TRAVES DE ALGUN ATRIBUTO DE USUARIO DETERMINAR QUE TIPO DE USUARIO ES Y DEPENDIENDO DEL TIPO ES QUE PERMISOS LE ASIGNAMOS, POR AHORA TENEMOS UN SOLO USUARIO QUE ES EL DUEÑO DE LA MASCOTA". Una vez que definimos el Servicio de Usuario que va a hacer la validacion o autentificacion de su Usuario, en el MAIN haremos el metodo ConfigureGlobal, que le dice a la configuracion de Spring Security cual es el sercicio que vamos a utilizar para autentificar el usuario y setea un encriptador de contraseñas al Servicio de Usuario, para que cada vez que se chequea una clave usa ese encriptador (BCryptPasswordEncoder()). Luego volvemos al ServicioUsuario y tenemos que setear el mismo encriptador de contraseñas al momento de que el usuario guarde su clave al registrarse, generamos una variable de nombre encriptada, que sera el metodo encriptador, y es la que vamos a setear, y lo mismo hacemos en el metodo modificar usuario.
PRIMER PUSH A RAMA DEVELOP
spring-boot run OK, Y YA NO ME PIDIO LA CLAVE PARA ACCEDER Y MOSTRAR EL HTML INDEX EN EL localhost:8080
Creamos una clase NotificacionServicio para que envie las notificaciones por mail
continuar video 9 min 
