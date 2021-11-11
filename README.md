# tinder
Ejercitacion Git con proyecto Java Spring "tinder de mascotas".
Segundo push, creo clases servicios, y agrego entidad Foto, FotoServicio, FotoRepositorio, y las Relaciones de Mascota y Usuario con Foto.
Agrego package configuraciones Clase ConfiguracionesSeguridad, para la seguridad del proyecto.
CREO RAMA DEVELOP
Conectamos UsuarioServicio con la seguridad de Spring, para poder autentificar usuarios mediante el UsuarioServicio, primero la clase implements la interfaz de Spring Security UserDetailsService, que nos obliga a implementar Override de un metodo  abstracto LoadByUsername, este metodo recibe el nombre del usuario, lo busca en el repositorio y lo transforma en un usuario de Spring Security. "SI TUVIERAMOS MAS DE UN ROL POR EJ UN USUARIO ADMINISTRADOR QUE PUDIESE POR EJ CREAR LAS ZONAS, AQUI A TRAVES DE ALGUN ATRIBUTO DE USUARIO DETERMINAR QUE TIPO DE USUARIO ES Y DEPENDIENDO DEL TIPO ES QUE PERMISOS LE ASIGNAMOS, POR AHORA TENEMOS UN SOLO TIPO QUE ES EL USUARIO DUEÑO DE LA MASCOTA"

