# tinder
Ejercitacion Git con proyecto Java Spring "tinder de mascotas".
Segundo push, creo clases servicios, y agrego entidad Foto, FotoServicio, FotoRepositorio, y las Relaciones de Mascota y Usuario con Foto.
Agrego package configuraciones Clase ConfiguracionesSeguridad, para la seguridad del proyecto.
Conectamos UsuarioServicio con la seguridad de Spring, para poder autentificar usuarios mediante el UsuarioServicio, primero la clase implements la interfaz de Spring Security UserDetailsService, que nos obliga a implementar Override de un metodo  abstracto LoadByUsername.
