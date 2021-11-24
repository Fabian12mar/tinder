# tinder
Ejercitacion Git con proyecto Java Spring "tinder de mascotas".
Segundo push, creo clases servicios, y agrego entidad Foto, FotoServicio,
FotoRepositorio, y las Relaciones de Mascota y Usuario con Foto.
Agrego package configuraciones Clase ConfiguracionesSeguridad, para la seguridad
del proyecto.
Creo rama DEVELOP (git checkout -b DEVELOP). Si hize modificaciones en README
u otra en remoto tengo que traer cambios a local para poder subir nueva rama a
remoto, traigo cambios con: git pull --rebase origin develop. Una vez traidos
los cambios a local, subo nueva rama a remoto: git push https://TOKEN@github.com/Fabian12mar/tinder.git develop

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

Trabajo proyecto local en rama DEVELOP
Conecto UsuarioServicio con la seguridad de Spring, para poder autentificar
usuarios mediante el UsuarioServicio, primero la clase implements la interfaz de
Spring Security UserDetailsService, que nos obliga a implementar Override de un
metodo  abstracto LoadByUsername, este metodo recibe el nombre del usuario,
lo busca en el repositorio y lo transforma en un usuario de Spring Security.
"SI TUVIERAMOS MAS DE UN ROL POR EJ UN USUARIO ADMINISTRADOR QUE PUDIESE POR EJ
CREAR LAS ZONAS, AQUI A TRAVES DE ALGUN ATRIBUTO DE USUARIO DETERMINAR QUE TIPO
DE USUARIO ES Y DEPENDIENDO DEL TIPO ES QUE PERMISOS LE ASIGNAMOS, POR AHORA
TENEMOS UN SOLO USUARIO QUE ES EL DUEÑO DE LA MASCOTA". Una vez que defino el
Servicio de Usuario que va a hacer la validacion o autentificacion de su Usuario,
en el MAIN hago el metodo ConfigureGlobal, que le dice a la configuracion de
Spring Security cual es el sercicio que vamos a utilizar para autentificar el
usuario y setea un encriptador de contraseñas al Servicio de Usuario, para que
cada vez que se chequea una clave usa ese encriptador (BCryptPasswordEncoder()).
Luego vuelvo al ServicioUsuario y hay que setear el mismo encriptador de
contraseñas al momento de que el usuario guarde su clave al registrarse,
generamos una variable de nombre encriptada, que sera el metodo encriptador,
y es la que voy a setear, y lo mismo hago en el metodo modificar usuario.
--------------------------------------------------------------------------------
PRIMER PUSH A RAMA DEVELOP
spring-boot run OK, Y YA NO ME PIDIO LA CLAVE PARA ACCEDER Y MOSTRAR EL HTML
INDEX EN EL localhost:8080, por la clase ConfiguracionesSeguridad antes agregada.
Creo una clase NotificacionServicio para que envie las notificaciones por mail,
despues este servicio la vinculo en el UsuarioServicio, para que una vez que se
registre le damos la bienvenida, despues tambien vinculo con las acciones de
VotoServicio para que enviemos notificacion cuando mascota fue votada y cuando
se le devuelve o corresponde un voto.
Por ultimo marco los metodos QUE GENERAN MODIFICACIONES en la base de datos,
como TRANSACCIONALES, de esta manera decimos que si el metodo se ejecuta SIN
LANZAR EXCEPCIONES entonces se hace un comit a la base de datos, si el metodo,
o un metodo llamado dentro del metodo, lanza una excepcion y no es atrapada se
vuelve atras (rollback) con la transaccion y no se aplica nada en la base de
datos. (metodos validar y de notificacion no se marcan, no generan modificaciones
en la base de datos)

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

Finalizo backend.
SEGUNDO PUSH A DEVELOP
ERROR por mailSender en Servicio de Notificaciones,  modifico archivo 
application.properties en other sources.
Creo rama LOGIN, subo rama a remoto.

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

Trabajo en local en rama LOGIN. 
Comienzo modelo Vista Controlador, INICIO, LOGIN Y REGISTRO.
Copio los html en la carpeta templates y css en carpeta static. 
HAGO UN CONTROLADOR POR CADA VISTA HTML O TEMPLATE.
Modifico las vistas html: agrego en los botones en los href las url de los
controladores. Con Thymeleaf se puede hacer segmentos de la pagina para que las
PARTES REPETITIVAS DE LAS PAGINAS no haiga que agregarlas en todas las plantillas.
Arreglo conflictos MERGE LOGIN A DEVELOP, con Visual Studio Code, borro rama
LOGIN local y remota. 
Por ahora seguire trabajando solo en rama develop sin ramas.
Modifico README.md desde netbeans, los cambios del README Visual Studio Code
los esta tomando automaticos. 

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

GUARDAR FORMULARIO DE REGISTRO
En el controlador de registro pruebo el formulario con System.out.print, y
recibe los datos y los envia a la CONSOLA. Una vez probado, borro los sout, 
para conectar el controlador,agrego atributo UsuarioServicio en controlador para
que envie esos datos a el Servicio de Usuario, que tiene los metodos para
registrar al usuario, grabarlo en la base de datos y mostrarlo en el navegador. 
En UsuarioServicio comentamos linea de notificacion por mail hasta que la 
creemos, .

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

VALIDAR FORMULARIOS
Mostramos los errores en navegador, ya no por consola, creamos en controlador
1 objeto ModelMap Modelo, la clase Modelo en Spring sirve para insertar
en ese modelo toda la informacion que vamos a mostrar en pantalla o van a
necesitar las interfaces de usuario. Despues en el registro Html agrego el
mensaje de error un parrafo <p th:if de Thymeleaf, if si se cumple esta
condicion y paso la variable "error" si es distinta a null, muestro el
parrafo el tag de th:text la variable error, en al etiqueta HTML debemos poner
esta sentencia xmlns:th="http://www.thymeleaf.org", indicamos que vamos a 
usar los tag de Thymeleaf.
El servicio valida, si hay un error lo pasa al controlador catch,en la 
variable "error" en el modelo.put, y pide que se vuelva a abrir el mismo html
, mostrando el error que pusimos en ese template o html.
Cuando se genera un error y el controlador devuelve la misma vista, los campos
no nos cargan lo que habia cargado el usuario, para que esa informacion no se
pierda, en el controlador enviamos a la vista de nuevo las variables que
habia llenado el usuario con modelo.put, y agregando en el form en los campos
un th:value=.
Si los datos ingresados estan correctos, puedo enviar algun parametro al
index, o mejor aun crear otro template exito.html, poniendo otros mensajes
de exito, agregamos en <h2 una variable titulo th:text= y agregamos en <p
una variable descripcion th:text=, y en controlador mediante el metodo put de
la clase modelo, enviamos los mensajes de exito a las variables del html.
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
GUARDAR IMAGEN
En el controlador estaba enviando null el primer parametro al servicio, 
porque el primer parametro que recibe el servicio es el archivo foto, para 
ello en el html agregamos al <form el pedido de la foto enctype="multipart/form-
data, porque enviaremos un archivo en el formulario; y agregamos el input de la
foto. Agrego al controlador el parametro MultipartFile archivo que recibira,
y cambio en el controlador el parametro null que enviaba al servicio por el 
parametro archivo.
El controlador de Registro recibe del html un MultipartFile, el controlador
envia este al servicio de Registro, y el servicio registrar lo pasa al servicio
de Foto que instancia foto, setea tipo Mime, nombre y contenido lo pasa a Bytes,
y retorna a fotoRepositorio.save.
--------------------------------------------------------------------------------
VALIDO QUE SEGUNDA CLAVE INGRESADA SEA IGUAL A CLAVE 1
En controlador Registro, agrego parametro clave2 a pasar al servicio Usuario,
en servicio Usuario en registrar agrego parametro clave 2 que recibimos de 
controlador, en metodo modificar tambien agrego parametro clave2 y lo que
recibe de validar.
Metodo validar tambien agrego recibe clave 2 y agrego validacion !=
clave1,
/* LA VALIDACION CON EQUALS DISTINTA LO ESPECIFICO CON EL SIGNO ! DELANTE DE 
PRIMER CLAVE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< */
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales. ");
        }
OTRA OPCION DE SEGURIDAD ES AGREGAR EL MOSTRAR CLAVE A LOS CAMPOS DE LAS CLAVES.

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

MOSTRAR COMBO EN PANTALLA
Para que el usuario elija la ZONA A LA QUE PERTENECE, dato util para que
listemos las mascotas de su zona.
Ingreso a la base de datos, listamos zona, select * from zona;, y en la tabla
zona agregamos unas zonas a mano.
En el html en el form agrego un <select que lista las zonas, y un <option
dinamico de Thymeleaf ,un EACH para que busque en la base de datos y nos traiga,
e itere un arreglo de zonas y para cada zona genere una opcion en el combo.
th:each="zona :${zonas}" th:text="${zona.id}" th:text="${zona.nombre}"
Primero a cada una de las zonas le doy nombre variable zona: e itero la List
{zonas}, y uso la variable zona.id para setear, y en el nombre de la opcion
ponemos el atributo .nombre de la zona. El dato List{zonas} lo saca del ModelMap
del controlador registro, que el template registro.html le envia al controlador,
para lo que en el controlador Registro agrego atributo zona Repositorio, 
agrego como parametro el objeto Modelmap modelo, que permite enviar
variables y collecciones al html, y cuando se ingrese a la url /registro, se van
a buscar todas las zonas de la base de datos, y se guarda lista en modelo, y
cuando se llame a el html registro este llama la lista combo de zonas en el
select.
-------------------------------------------------------------------------------
Agrego el idZona como parametro al controlador Registro al metodo registrar, 
y lo paso como parametro a enviar al metodo registrar del Servicio Usuario 
(APARECE COMO ERROR HASTA NO AGREGAR EL PARAMETRO EN Servicio Usuario). 
Agrego en el Servicio Usuario el atributo ZonaRepositorio y el parametro 
idZona a recibir por el metodo registrar, y en el metodo registrar creo 
variable zona del tipo Zona con la que busco en Repositorio Zona la zona por
id con metodo .getOne (https://qastack.mx/programming/24482117/when-use-getone
-and-findone-methods-spring-data-jpa), y seteamos la zona al Usuario. Agrego el
parametro zona en el llamado del metodo validar, y en metodo validar agrego 
parametro Zona zona, y agrego un if por si la zona es null agrego un 
ErrorServicio indicando que no se encontro la zona solicitada.
Lo mismo anterior hago en metodo modificar.
En controlador de Registro en metodo registrar en la parte catch de error, 
agrego el mismo modelo que habia puesto en metodo registro de controlador, 
el que busca lista zonas en repositorio, para que en caso de error que redirige
a al misma vista, los datos ingresados de zona tampoco se pierda.
Al registrar un usuario ahora tiene vinculada una zona.

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 

CONFIGURACIONES DE SEGURIDAD, CONFIGURAR SPRING SECURITY EN EL LOGIN (V6)
Habilita la seguridad web a traves de la libreria de Spring Security, y 
configurar algunos aspectos de la seguridad web y de la seguridad a traves del
protocolo http.
AGREGO UNA INSTANCIA O ATRIBUTO DEL SERVICIO DE USUARIO, que vamos a utilizar
para buscar en la base de datos usuarios por nombre de usuario.
AGREGO UNA CONFIGURACION DEL MANEJADOR DE SEGURIDAD DE SPRING SECURITY, METODO
ConfigureGlobal, en el cual indicamos cual es el servicio que debe utilizar para 
autentificar un usuario (auth.userDetailsService(usuariServicio)), y tambien
indicamos, cual es el encoder que va a utilizar para comparar las contraseñas
una vez que encuentre usuarios(auth.paswordEncoder(new BCryptPasswordEncoder()).
(esto es cuando registramos un usuario a traves de nuestro Servicio, la
contraseña que el usuario lleno en el form de registro, la encriptamos a traves
de un encoder, que es el que estamos pasando en esta configuracion al manejador
de seguridad de Spring, y cuando un usuario va a logearse o ingresar al llenar
en el formulario de logueo su usuario y su clave en texto plano, Spring Security
va a tomar esa clave en texto plano y la va a encriptar a traves del encoder que 
nosotros pasamos aca, para este fin los 2 encoder deben ser identicos, para que
cuando se comparen o transformen las contraseñas sean iguales.
Lo siguiente son configuraciones del http:
AGREGO .headers().frameOptions().sameOrigin().and()
.authorizeRequests().antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
Indicamos que los recursos que esten dentro de las carpetas css, js o img, los
pueda acceder cualquier usuario sin necesidad de estar logueado o tener algun
permiso especifico.
CONFIGURACION METODO LOGIN
.and().formLogin()
.loginPage("/login")
En que url se va encontrar formulario de login, indicado en GetMapping de html
.loginProcessingUrl("/logincheck")
La url que va usar Spring Security para validar o autenticar algun usuario, 
indicada en el FORM de login de la pagina login.html
.usernameParameter("username")
.passwordParameter("password")
Con que nombre viajan los nombres de usuario y password, podemos elegir cualquier
nombre, indicado en el FORM DE login de la pagina login.html
.defaultSuccessUrl("/inicio")
A que url va ingresar si el usuario se autentifico con exito
.permitAll()
.and().logout()
.logoutUrl("/logout")
.logoutSuccessUrl("/")
Cuando el usuario toque o ingrese a /logout, a que url va a redirigir al usuario
/ esto seria a la index
.permitAll().and().csrf().disable();                
--------------------------------------------------------------------------------
EN VISTA login.html:
anotamos en <section <form action="/logincheck"
anotamos en <input email name="username"
anotamos en <input password name="password"
--------------------------------------------------------------------------------
Si hay un error en el logueo, lo redirige a pagina login.html, el error lo
muestra en la barra de direccion, pero NO EN LA VISTA.
Para mostrar el error EN LA VISTA, en el Controlador de Login, agregamos
parametro RequestParam(required = false) String error, como que puede recibir
un error, pero false le indica que es un parametro opcional, osea que no siempre
puede venir un "error". Tambien instanciamos un ModelMap, y escribimos una
sentencia IF el error es distinto a null que ejecute el model, que tiene como
atributo ("error", y "el  mensaje de error "Usuario o clave incorrectos"). 
En el login.html agregamos un th:if si en el model viene el mensaje de error,
que imprima el MENSAJE DE ERROR EN LOS INPUT.
--------------------------------------------------------------------------------
AL LOGUEARSE el usuario, accede a la pagina inicio.html, esto lo vemos en la
barra de direccion, pero aun no he configurado la pagina, para eso vamos a crear
un Controlador Inicio, este recurso "/inicio" deberia abrir la vista inicio.html
solo si estoy logueado. Creamos la vista inicio.html con un <h1 Usuario logueado.
Al boton salir ponemos href="/logout", Y EN ConfiguracionesSeguridad AGREGO EN 
.logoutSuccessUrl("/LOGIN?LOGOUT")PARA LA SALIDA DE LA VISTA inicio.html DESPUES
DE LOGUEADO, DE VUELTA A LA VISTA login.html, PERO CON UN AGREGADO DE MENSAJE 
"ha salido correctamente", para lograr este mensaje de exito logout, en el
Controlador de login agrego un parametro @RequestParam(required = false String
logout, y agrego un if si logout != null { model.put("logout", "Ha salido
 correctamente. ") }, y en la vista login.html junto al mensaje de error de
login, agrego el mensaje de salida correctamente de logout, haciendo uso de
th:if EL MODELO DE LA VISTA PEDIRA EL MENSAJE AL CONTROLADOR PARA IMPRIMIRLO.

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

AGREGAR SEGURIDAD A LOS ACCESOS A LA APLICACION (V7)
Con Spring Security hacemos seguros los accesos a LAS URL.
Hasta aqui si entro a la BARRA DE NAVEGACION y pongo localhost:8080/INICIO,
ENTRA A LA VISTA, sin que haiga ningun usuario logueado.
En el Controlador Inicio anotamos @PreAuthorize("hasAnyRole('ROLE_ROL')"), que
va a hacer que para ingresar a la url tiene que estar autorizado, y la regla es
que tenga algun rol de los que pasemos por parametro, estos parametros los sabe
de un metodo que habia @Override en el Servicio Usuario, que busca si el usuario
EXISTE, le asigna algun PERMISO y ROL.
Ahora si escribo localhost:8080/INICIO solo me lleva a la vista login.html y NO
a inicio.html

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

RECUPERAR Y UTILIZAR INFORMACION DE LA SESION (V8)
Para mostrar datos del usuario logueado en las vistas.
En el Servicio Usuario, en el metodo @Override LoadUserByUsername, cuando ya
se que el usuario ingreso el username (mail) y la contraseña correcta, inserto
una llamada para guardar ese usuario que traigo de la base de datos, que se que
ya esta autenticado, para meterlo en la SESION WEB.
En inicio.html abajo del mensaje "Usuario logueado", uso un tag de thymeleaf 
th:if si en la variable sesion hay un atributo usuariosession != null, que
muestre de este <h2 el th:text= el .nombre + '' espacio + .apellido del usuario
dela sesion.
Ahora cuando me logueo me MUESTRA en la pagina de inicio.html NOMBRE y APELLIDO.
--------------------------------------------------------------------------------
CONFIGURAR PAGINA DE ERRORES EN FORMATO APLICACION, NO FORMATO DE SERVIDOR WEB.
Creo pagina error.html que tendria el mismo estilo de toda la plataforma, en la
que en un <h1 voy a MOSTRAR EL CODIGO DEL ERROR, y en un <h2 MOSTRAR EL MENSAJE
DE ERROR asociado a ese codigo. Y creo un boton para volver y en href="/" para
que vuelva a vista inicio.html.
Creo Controlador Errores, especificamos todo lo que sea /error va entrar en un
method, que recupera el codigo de error que viene del servidor, y en switch case 
establezco un mensaje particular para ese codigo de error y redirecciono los 
errores a la vista error.html (ModelAndView errorPage = new ModelAndView("error");)
Ahora si en la barra de direcciones escribo un recurso o direccion que no 
existe, ej http://localhost:8080/A, ME MUESTRA "ERROR 404 EL RECURSO SOLICITADO
NO FUE ENCONTRADO", EN EL ESTILO DE LA VISTA QUE DEFINO.
Si borro la anotacion @Controller y a la vista error.html le cambion el nombre,
y llamo una direccion inexistente, ahora me da el error al estilo del servidor, 
en este caso de Apache Tomcat.
GRACIAS AL CONTROLADOR, CAPTURO EL FLUJO DE ERROR, Y MUESTRO UN MENSAJE 
PERZONALIZADO, QUE TAMBIEN PODRIA INCLUIR UNA IMAGEN RELACIONADA A LA PAGINA o
algo customizado, y devuelve la vista, que toma los datos del modelo que
inyecto en la vista con th: text""${.
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





continuar V8 min327
