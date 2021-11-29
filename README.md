# tinder
Ejercitacion Git con proyecto Java Spring "tinder de mascotas".
Segundo push, creo clases servicios, y agrego entidad Foto, FotoServicio,
FotoRepositorio, y las Relaciones de Mascota y Usuario con Foto.
Agrego package configuraciones Clase ConfiguracionesSeguridad, para la seguridad
del proyecto. Eesta configuracion esta relacionada a como viajan los datos
cuando se loguea el usuario, no hace referencia al httpSession configurado en
Servicio Usuario que lo vamos a tomar en el html.
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
session.setAttribute("usuariosession", usuario);
LA ANTERIOR SENTENCIA, GUARDA AL USUARIO EN EL NOMBRE DE VARIABLE
"usuariosession".
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

REGISTRACION DE USUARIO COMPLETA (V9)
FUNCIONALIDAD DE USUARIO REGISTRADO "CONFIGURA TU PERFIL"

Saco informacion que tenemos guardada en la sesion, del usuario que esta
logueado, puntualmente saco el ID, para que cuando presione el boton "Configura
tu perfil" se abra una INTERFACE que contenga todos los datos, los pueda cambiar
y los pueda persistir en la base de datos.
Creo un CONTROLADOR USUARIO RequestMapping("/usuario"), con 2 metodos, 
@GetMapping "/editar-perfil" y @PostMapping "/actualizar-perfil".
Cuando se llame a /USUARIO/editar-perfil se llama el metodo METODO editarPerfil! 
El metodo List<Zona> zonas es similar al de registrar usuarios, va a abrir un
formulario (return "perfil.html") para EDITAR MIS DATOS DE PERFIL O DE USUARIO.
Busca todas las zonas, las mete en el modelo, ese modelo lo va usar la vista
perfil.html "zonas", y guardo las zonas para poder iterarlas y generar opciones
dentro del Select de zonas.
Recibe el id del usuario que esta logueado, y despues de listar las zonas, busca
en la base de datos los datos del usuario logueado y lo envia al model con un
nombre de variable "perfil", si ocurrio algun error, catch guarda el "error" en
el model, para mostrarle al usuario lo que ocurrio.
A esa variable "perfil" se le asigno un usuario, y en la vista perfil.html los
th:value="${perfil.....} los toma de la variable "perfil", para los input
nombre, apellido, mail y zona. No asi las clave1 ni clave2 que no las saca del
"perfil" usuario (th:value="${clave1}". Guardo clave1 y clave2 por si vuelve 
por si algun error cuando se esta editando perfil.
LA VISTA "perfil.html" SE ESTARIA MANEJANDO CON EL Controlador Usuario 
("/USUARIO")("/EDITAR-PERFIL") metodo editarPerfil, Y NO DESDE REGISTRO
CONTROLADOR ("/")("/REGISTRO") que se maneja con la vista registro.html.
Y retorna la vista "perfil.html", esta vista tiene la misma informacion del form
de registro, pero llama a una action distinta /USUARIO/actualizar-perfil METODO
registrar, a traves del metodo POST, el form tiene enctype="multipart, porque
puede recibir imagenes, el form traslada el id y demas datos del usuario logueado
th:value"${perfil.id, el "id" A TRAVES DE UN CAMPO type="hidden, NO SE MUESTRA
EN PANTALLA VIAJA OCULTO.
En la pagina inicio.html esta la frase "Usuario logueado" y en un <h2 saca de la
sesion al usuario logueado y muestra eL nombre y el apellido, <h2 class=
"masthead-subheading mb-0" th:if="${session.usuariosession != null}" th:text="$
{session.usuariosession.nombre + ' ' + session.usuariosession.apellido}"></h2>.
En la pagina de inicio, luego del usuario logueado, habia un boton 
"¡Configura tu perfil!", que hasta ahora no funcionaba, configuro con una
etiqueta th:href="@{/usuario/editar-perfil(id=__${session.usuariosession.id}__)}"
, a la que tambien esta url puede recibir parametros de th 
((id=__${session.usuariosession.id}__)}), en este caso el "id", que sacamos de
la session.usuariosession.
En las vistas o html o paginas, al posicionarnos sobre albun boton etc, en la
parte inferior izquierda de la ventana nos muestra la ruta del link que nos va 
a abrir ese boton, la ruta y despues del signo de pregunta "?" envia las 
variables o parametros que abrira, en este caso el id igualado al id del 
usuario de la sesion.
Ahora al hacer click en boton "Configura tu perfil" nos lleva a la vista 
perfil.html (/usuario/editar-perfil?id=...) , con los datos cargados en los 
inputs, menos las claves, salvo si elegimos guardarla por navegador, carga la
clave1.
Cuando se acciona este formulario lleva al action=/usuario/ACTUALIZAR-PERFIL 
METODO registrar en el Controlador Usuario, el segundo metodo del Controlador
Usuario, que recibe un Model, un archivo, y los demas datos, Y A DIFERENCIA DEL
FORMULARIO DE REGISTRO RECIBE EL id.
Despues llama al metodo modificar de Servicio Usuario y le pasa todos los datos, 
y si no hubo ningun error va a REDIRIGIR A INICIO "redirect:/inicio"; y si hubo
algun error, CATCH va a volver a cargar los datos necesarios en la vista, y
a cargar una variable "error" con el error ocurrido, y en lugar de ir al inicio,
va a volver a perfil.html.
Para que no se produsca error, cuando modifique el usuario se modifique tambien
el usuario de la sesion, en el Controlador Usuario "/actualizar-perfil en metodo
registrar agrego parametro, HttpSession session, para tener acceso y luego
llamar a session y colocar atributo usuario nuevo, pisando asi el usuario viejo
con los datos nuevos,  session.setAttribute("usuariosession", usuario);

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

SECURIZACION (V9)(v7)
HACER QUE DETERMINADOS SERVICIOS O CONTROLADORES SEAN ACCEDIDOS POR USUARIOS QUE
ADEMAS DE ESTAR LOGUEADOS, TENGAN UN ROL PARTICULAR.

En el Controlador Inicio, en el que redireccionamos al usuario una vez que esta
logueado, DETERMINAMOS QUE ESTA URL @GetMapping("/inicio) debe ser accedida 
solamente por usuarios con el ROL 'USUARIOS REGISTRADOS', agregando anotacion:
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
esto estaba definido en Servicio Usuario en metodo @Override UserDetails 
loadUserByUsername, el que se usa Spring Security para autenticar o loguear
al usuario.
Si tuviera un solo controlador para todas las URL, la anotacion @PreAuthorize
la podria poner a nivel controlador o clase, public class PortalControlador,
pero la anotacion va a ser tomada por todos los metodos de la clase, pero las 
URL index, login, registro, DEBEN SER ACCEDIDAS POR TODOS LOS USUARIOS.
Ahora si en la barra de direcciones anoto la URL localhost:8080/INICIO me
redirige a la URL /LOGIN.
Si al @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')") le cambio nombre de
rol, ej ROLE_USUARIO_REGISTRADO2, e intento acceder loguado correctamente, me da
error 403 "No tiene permisos para acceder al recurso. ", PORQUE CON EL USUARIO
QUE ME LOGUIE TIENE ROL 'ROLE_USUARIO_REGISTRADO', y para consumir este servicio
puse que se necesita'ROLE_USUARIO_REGISTRADO 2'.
--------------------------------------------------------------------------------
CUANDO SE INGRESA A "Configurar perfil" en la URL SE PASA EL id DEL USUARIO
COMO PARAMETRO:
http://localhost:8080/usuario/editar-perfil?id=f52d5919-345b-4511-96b0-6bd96cd7c79e
si ingreso a la base de datos, y copio el id de otro usuario y lo pongo en esta
URL, me va dejar editar el perfil de ese usuario. Esto por 2 motivos, primero 
porque el Controlador Usuario no esta securizado (@PreAuthorize) (solo habia 
securizado la URL /inicio),
Y SEGUNDO, NO HE HECHO NINGUNA VALIDACION ADICIONAL PARA CORROBORAR QUE EL
USUARIO QUE ESTA EDITANDO ESE PERFIL SEA EL USUARIO DUEÑO DE ESE PERFIL.
Para ello primero securizar los 2 metodos de Controlador Usuario,
@GetMapping("/editar-perfil") y @PostMapping("/actualizar-perfil"), agregandoles
la anotacion @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')"),
Con esto me aseguro que solamente que ademas de que sean usuarios que esten
logueados, sean usuarios con ese rol.
Como SEGUNDA MEDIDA no debo permitir que el editar-perfil lo edite un usuario
distinto del que esta logueado, para esto, en Servicio Usuario guardabamos al
usuario en la sesion (session.setAttribute("usuariosession", usuario);), 
Para eso en el metodo GET editarPerfil("/editar-perfil) pondremos que reciba
parametro HttpSession session, de esta manera podremos usar el objeto session,
con el cual haremos la validacion: pido el atributo getAttribute("usuariosession"),
lo casteo = (Usuario) session.getAttribute("usuariosession"); y lo guardo en una
variable "login" Usuario login = (Usuario) session.getAttribute("usuariosession");
y valido si el login es null, quiere decir que en la sesion no hay ningun
usuario, con lo que con esa sentencia ya no deberia estar ahi, y si no es null
pero el id del usuario logeado no es igual al id del usuario que recibo por 
parametro y estoy queriendo modificar ( !login.getId().equals(id)),
lo redirecciono a return "redirect:/inicio";
Ahora cuando me logueo e intento editar el perfil de otro usuario copiando su
id y reemplazandolo por el mio en la URL, me redirecciona a la pagina
URL inicio.
Y en el metodo POST registrar ("/actualizar-perfil) hacemos lo mismo que en el 
anterior, evitando ya no el acceso al formulario, sino la orden de actualizar
el perfil.

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

CONTROLADOR PARA DEVOLVER UNA IMAGEN (V11)
METODOS EN CONTROLADORES QUE DEVUELVEN ALGUN OBJETO DISTINTO A UNA VISTA HTML

Hasta ahora los metodos nos han devuelto vistas html o una redireccion a un 
servicio que terminaba devolviendo una vista html.
Escribo un Controlador Foto, que devuelve la foto de perfil de un usuario
especifico. En el registro y en la edicion de perfil podemos ADJUNTAR FOTO AL 
USUARIO.
El proceso que haciamos con la foto era en Servicio Usuario, metodo modificar, 
generabamos una foto, y se la asignabamos al usuario, llamamos a Servicio Foto, 
metodo guardar, el cual tomaba los bytes del contenido de ese archivo , lo
guarda en una entidad, esa entidad la enviamos a la base de datos y queda
almacenada la foto del usuario en la base de datos.
CREO Controlador Foto, y un metodo fotoUsuario:
public ResponseEntity<byte[]> fotoUsuario(@RequestParam String id) {
que nos devuelve un ResponseEntity<byte[]> de un arreglo de bytes, porque las
fotos las persistimos como un arreglo de bytes, recibe el id del usuario, 
busca el usuario por id, lo asigna a variable usuario, y pide al usuario la foto
y el contenido(arreglo de bytes) y lo guardamos en una VARIABLE byte[] foto 
byte[] foto = usuario.getFoto().getContenido();
(LAS FOTOS EN EL HTML SE CONSUMEN COMO UNA URL)
Ahora si se accede a /foto/usuario en el id del usuario DEBE DESCARGARSE LA FOTO.
Y devolvemos un Return new ResponseEntity con el arreglo de byte[] y 3
parametros:
return new ResponseEntity<>(foto, headers, HttpStatus.OK);
el primer parametro es el contenido, el segundo las cabeceras que le dicen al 
navegador que lo que estoy devolviendo es una imagen, y el tercero es 
el codigo con el que vamos a devolver el pedido, en este caso 200.
Por los headers o cabeceras, agregamos antes del return una headers de la clase
HttpHeaders, en el que manifiesta el ContentType de ese headers del tipo
IMAGE_JPEG:
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.IMAGE_JPEG);
EL RETURN MARCA ERROR PORQUE EN CASO DE QUE EXISTA UNA EXCEPCION, EN EL CATCH
hay que devolver un ResponseEntity<> con un codigo de estado, en este caso un 
NOT_FOUND, sea porque no se encontro un usuario con ese id, o porque el usuario
no tenga una foto:
return new ResponseEntity<>(foto, headers, HttpStatus.OK);
Y agrego al try el if por si el usuario no tiene una foto:
if (usuario.getFoto() == null) {
seguido de la excepcion que va a capturar el catch y devolvera un NOT_FOUND
throw new ErrorServicio("El usuario no tiene una foto asignada. ");
AHORA SI EN BARRA DE DIRECCIONES COLOCAMOS localhost:8080/foto/usuario?id=....
Y EL id DEL USUARIO, EL NAVEGADOR NOS DEVUELVE LA FOTO, salvo que el ContenType
o mime este erroneo.
--------------------------------------------------------------------------------
AHORA USAMOS LA FOTOS
Desde vista perfil.html, antes de la imagen del perrito, agrego un <img th:if,
si el usuario al que le estoy intentando cambiar el perfil tiene una foto
asignada, voy a mostrar la foto del usuario, usando la URL del Controlador Foto
y CONCATENANDO AL FINAL DE LA URL EL id DEL PERFIL QUE ESTAMOS EDITANDO:
<img th:if="${perfil.foto != null}" class="img-fluid rounded-circle" th:src="${'/foto/usuario?id=' + perfil.id}" alt="">
Y la foto del perrito la vamos a mostrar siempre y cuando el usuario que estoy
intentando modificar el perfil, no tenga una foto de perfil:
<img th:if="${perfil.foto == null}"  class="img-fluid rounded-circle" src="/img/m1.jpeg" alt="">
--------------------------------------------------------------------------------
PASAR LA RUTA DE LA IMAGEN COMO UNA RUTA Y NO CON PARAMETRO DE id
A veces por cuestiones de cache, de navegador, de comportamiento de los
navegadores, para las busquedas para google, google no guarda los parametros de
peticion, guarda las url completas, y como para hacer url amigables, es que no
conviene en las imagenes usar el id como parametro sino pasarlo como una ruta
completa y distinta para cada usuario, PASAR EL PARAMETRO COMO PARTE DE LA URL.
Para ello en el Controlador Foto en el metodo ResponseEntity en la anotacion de 
GetMapping("/usuario/{id}") agrego entre llaves el id, INDICANDO QUE SERA
PARTE DE LA URL. Y cambio el parametro del metodo (@PathVariable String id) de
@RequestParam a @PathVariable, con esto decimos que este id lo va a sacar de lo
que venga en el @GetMapping.
Y en la vista perfil.html: 
"${'/foto/usuario?id=' + perfil.id}"  CAMBIAMOS LA RUTA POR:
"${'/foto/usuario/' + perfil.id}"
Ahora en la barra de direcciones para llamar al recurso tenemos que implementar:
localhost:8080/foto/usuario?id=f52d5919-345b-4511-96b0-6bd96cd7c79e
CAMBIAMOS POR: (RUTA SIN ?id=)
localhost:8080/foto/usuario/f52d5919-345b-4511-96b0-6bd96cd7c79e

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<










continuar v11 min2020
