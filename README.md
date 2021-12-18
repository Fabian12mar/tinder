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

MODULO DE MASCOTAS 1 (v12)

ESCRIBIR COMPONENTES Y VISTAS NECESARIAS PARA QUE UN USUARIO PUEDA AÑADIR n 
CANTIDAD DE MASCOTAS

HAGO UNA VISTA mascota.html PARA CARGAR LA INFORMACION DE UNA MASCOTA (hago una
copia de la vista perfil.html de Usuario para modificarlo), la cual va a servir
tanto para crear una Mascota como para modificarla.
HAGO UNA Controlador Mascota (hago una copia del Controlador Usuario para
modificarlo), 
La notacion @PreAuthorize la escribo a nivel Controlador, porque todos los
metodos que tengamos aca los va tener quer usar un Usuario Registrado, con el
mismo rol que el de usuario,
El objeto zona no es necesario.
Metodo editar-perfil puede recibir el id de mascota por lo que al RequestParam
agrego(required = false), el metodo recibe los parametros, añade la mascota en
blanco, con el nombre "perfil", y devuelve return "mascota.html";
EN LA PAGINA inicio.html agrego un nuevo boton <a ademas del de Editar Perfil,
que sea para CREAR UNA MASCOTA:
 <a th:href="@{/mascota/editar-perfil}" class="btn btn-primary btn-xl rounded-pill mt-5">¡Agrega una Mascota!</a>
Si ahora me logueo, en la pagina de inicio sale nuevo boton Agrega una Mascota,
si toco me muestra "error 500 Ocurrio un error interno", esto dice porque la
propiedad foto no puede estar en null, este ERROR porque en Controlador Mascota
en el metodo editarPerfil en el model solo estaba recibiendo el parametro
ModelMap model, pero no estaba añadiendo la mascota al model:
Añado una mascota en blanco con el nombre "perfil":
        Mascota mascota = new Mascota();
        model.put("perfil", mascota);
        return "mascota.html";
AHORA CARGA EL FORMULARIO PARA CREAR O AGREGAR UNA MASCOTA CON UN h1 "MASCOTA".
Al titulo h1 mascota lo hago dinamico, agregando un tag <span dentro del h1:
<span th:text="${perfil.id == null ? 'Crear' : 'Actualizar'}"></span> Mascota</h1>
Con un th condicional que modifica el h1 segun si no vienen id h1 dira "CREAR",
y si viene id dira "ACTUALIZAR".

En paquete enumeraciones agrago una nueva enum Tipo, en la cual defino PERRO, 
GATO, CONEJO;
Y en la Entidad Mascota agrego el atributo:
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
Y AGREGAR el get y set de este atributo.
y en Controlador Mascota, metodo editarPerfil, agrego los model.put para las
enumeraciones Tipo y Sexo, enumeraciones que traen un metodo .values() que 
devolvera un array de todas las enumeraciones,
        model.put("sexos", Sexo.values());
        model.put("tipos", Tipo.values());
el metodo .put ENVIA A LA VISTA LOS value, a la key " " asociada,
en la VISTA mascota.html creo 2 select para Sexo y Tipo,
th:selected="${perfil.tipo != null && tipo == perfil.tipo}"></option> 
si el perfil.tipo != null y el TIPO es igual perfil.tipo, select lo marca como
seleccionado.
--------------------------------------------------------------------------------
PARA BOTON "ACTUALIZAR MASCOTA", en Controlador Mascota escribo METODO
"ACTUALIZAR", con parametros de datos que en teoria recibe.

Llamamos al metodo "modificar" de Servicio Mascota, en el cual recibe entre
otros el id del usuario que va a Actualizar la mascota (login.getId()), que lo
puedo sacar de la sesion, para lo cual aqui lo pido:
        Usuario login = (Usuario) session.getAttribute("usuariosession");
Y un condicional: if(id == null || id.isEmpty()) si el id es nulo o es vacio, 
quiere decir que la mascota es nueva y llamo al Servicio "agregarMascota" para
crearla, y si el id es distinto a nulo quiere decir que la mascota existe y
llamo al servicio "modificar":
mascotaServicio._______________(archivo, login.getId(), nombre, sexo, tipo);
En Servicio Mascota agrego el metodo "buscarPorId".
Si hay algun error, en el CATCH seteo nuevamente los datos, para cargar 
nuevamente los datos que habia llenado el usuario, y con los modelos vuelvo a
llenar los datos, y el mensaje de error en la vista.
            Mascota mascota = new Mascota();
            mascota.setId(id);
            mascota.setNombre(nombre);
            mascota.setSexo(sexo);
            mascota.setTipo(tipo);

            modelo.put("sexos", Sexo.values());
            modelo.put("tipos", Tipo.values());
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", mascota);

AHORA EL METODO "agregarMascota" DEL SERVICIO CREA UNA MASCOTA.
TAMBIEN EN CONSULTA SQL DE MASCOTA FIGURA UN foto_id AUNQUE NO HAIGA SIDO
CARGADA, Y EN CONSULTA SQL DE FOTO SE VE EL id COMO QUE LLEGA EL Multipart, PERO
NO TIENE CONTENIDO, LLEGA VACIO, para ello en Servicio Foto agrego una
validacion en el if, que ademas de que el archivo NO sea nulo, tiene que NO ser
VACIO, para que se ejecute el try del metodo guardar del Servicio Foto:
            if (archivo != null && !archivo.isEmpty()) {
AHORA SI CREO UNA MASCOTA SIN AGREGAR FOTO, EN LA CONSULTA SQL DE FOTO NO 
APARECE NINGUN id DE FOTO NUEVA, Y EN LA CONSULTA SQL DE MASCOTA, AHORA LE
FIGURA LOS CAMPOS foto_id COMO [NULL], ESTO POR EL if "NO NULO" Y "NO VACIO".
--------------------------------------------------------------------------------
Para probar la EDICION de una mascota, NO la creacion, aun sin haber creado la 
vista para ello, en la barra de URL agrego un id de Mascota existente, para que 
el controlador lo tome como id existente y llame al Servicio "modificar" !!!!!!!
Agregando desde el ?:
http://localhost:8080/mascota/editar-perfil?id=f6a7ecaf-6731-4157-98be-57c134058b91
Aun asi el formulario no me carga los datos de la mascota indicada, esto porque
en el Controlador el metodo "editarPerfil" recibe el id, pero no estaba haciendo
nada con ese id, solo siempre crea una nueva mascota:
 Mascota mascota = new Mascota()
Para que el metodo tome el id, agrego un if:
            if (id != null && !id.isEmpty()) {
            try {
            mascota = mascotaServicio.buscarPorId(id);
De esta manera ahora el model.put("perfil", mascota); que tenia, va a pasar a la
vista, con el nombre "perfil", el valor de la mascota del try, que es el id
buscado en el Servicio.
--------------------------------------------------------------------------------
Por ultimo para SOLUCIONAR UN ERROR O BUG 500 error interno,
NullPointerException: null, PORQUE SE PIERDE LA SESION CUANDO SE REINICIA EL
SERVIDOR Y EL USUARIO DA "NULL", HAGO redirect a "inicio"
        Usuario login = (Usuario)session.getAttribute("usuariosession");
        if(login == null) {
        return "redirect:/inicio";
        }
.............

--------------------------------------------------------------------------------
PARA QUE SE MUESTRE LA FOTO DE UNA MASCOTA,
En Controlador Foto agrego el @GetMapping("/mascota/{id}") que es llamado o 
consumido desde la vista mascota.html, 
el metodo fotoMascota de este Controlador, llama al metodo buscarPorId(id) del 
Servicio Mascota

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

MODULO DE MASCOTAS 2 (V13)

AGREGO UNA VISTA PARA CONSULTAR, TODAS LAS MASCOTAS DEL USUARIO, AGREGAR,
MODIFICAR Y ELIMINAR.

Copio como base la vista mascota.html, y procedo a modifical el html, primero
renombro a mascotaS.html, y elimino el span th:text que cambiaba el comienzo del
titulo segun si existia o no id, a Crear o Actualizar Mascota, y cambio la
segunda parte del titulo por Mis Mascotas.
El div de section lo hago de todo el ancho, de 6 a 2 a 12 12, y elimino las
siguientes lineas de codigo hasta el footer, que serian las parte de mostrar
foto y la parte del form de error y nombre, y los select tipo, sexo y input 
archivo foto.
Creo una <table, un<thead como ENCABEZADO, un <tr para las columnas y un <th
como nombre del encabezado cada columna(nombre, sexo y tipo). Un <body, y un
<tr th:each="mascota : ${mascotas}"> QUE VA A MOSTRAR UNA MASCOTA POR CADA FILA,
un <td que indica en CADA COLUMNA que atributos mostrar (nombre, sexo y tipo).
--------------------------------------------------------------------------------
En Controlador Mascota creo metodo misMascotas, @GetMapping("/mis-mascotas"), el
cual va recibir la session y el modelo de la vista. Modelo en vista mascotas:
                        <tr th:each="mascota : ${mascotas}">
                        <td th:text="${mascota.nombre}"></td>
                        <td th:text="${mascota.sexo}"></td>
                        <td th:text="${mascota.tipo}"></td>
                        </tr>
el <td es el modelo de th que llama al atributo de cada columna.
Recupera el usuario logueado, y si es null vuelve a "login":
               Usuario login = (Usuario) session.getAttribute("usuariosession");
               if(login == null) {
               return "redirect:/login";
               }
ESTE REDIRECT LO CORRIJO EN METODO editarerfil DE redirect:usuario a "login".
Si el usuario no es null llamo al metodo buscarMascotasPorUsuario del Servicio
mascota que guardo en un List:
List<Mascota> mascotas = mascotaServicio.buscarMascotasPorUsuario(login.getId());
y a ese List lo pongo en el model de la vista:
                                     model.put("mascotas", mascotas);
Vista mascotas que va a recorrer la List:
                    <tr th:each="mascota : ${mascotas}">
Y return "mascotas";
--------------------------------------------------------------------------------
En Servicio Mascota escribo el metodo buscarMascotasPorUsuario, que llama el
Controlador, el metodo devuelve un List de mascotas, recibe el id del usuario,
y retorna de la interface Repositorio Mascota del metodo buscarMascotasPorUsuario
el cual recibe el id del usuario:
@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
@Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id")
public List<Mascota> buscarMascotasPorUsuario(@Param("id") String id);
--------------------------------------------------------------------------------
Ahora al loguearme, y en la barra de URL al colocar
http://localhost:8080/mascota/mis-mascotas
me da la tabla con la lista de Mis Mascotas.
Voy a la vista mascotas.html para darle estilo a esta tabla:
            <table class="table table-light table-hover">
hover hace que al pasar el cursor por encima de una fila la encienda.
Doy estilo a los encabezados <tr:
                    <tr class="bg-black  text-white">
                        <th>Nombre</th>
Agrego a la tabla una columna mas que nos de botones para accionar sobre los 
registros (modificar, eliminar):
                        <th>Acciones</th>
y un <td th: para esta nueva columna, con UN LINK Editar y href a URL:
(esta URL parecida a la de la vista inicio.html)
(ESTA URL RECIBE EL id DE LA MASCOTA QUE ESTAMOS ITERANDO EN ESTE MOMENTO)
<td><a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__}}">Editar</a></td>
Este boton Editar nos lleva a la pagina de "Actualizar Mascota", esto seria
porque esta recibiendo un id.
..................

Agrego otro <td th: para el boton Eliminar, EN LA MISMA COLUMNA <th Acciones:
<td><a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__)}">Editar</a></td>
<td><a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__)}">Eliminar</a></td>-->
ESTAS URL RECIBE EL id DE LA MASCOTA QUE ESTAMOS ITERANDO EN ESTE MOMENTO)
MANERA CORRECTA!!!!: (LA ANTERIOR ME GENERA DOS COLUMNAS PARA LA COLUMNA
 <th Acciones-->)
<td>
<a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__)}">Editar</a> -
<a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__)}">Eliminar</a>
</td>
Agrego otra columna mas para la foto:
                        <th>Foto</th>
Y un <td th: para el contenido del atributo de esa columna, con la URL a la
foto, este <td lo copio de la vista mascota por ser similar:
(solo cambio perfil por mascota. y mascota.id, esto quiere decir SI LA 
MASCOTA QUE ESTAMOS ITERANDO TIENE FOTO LA MUESTRO, SINO EN LA SEGUNDA
LINEA DIGO QUE SI NO TIENE FOTO MUESTRE LA FOTO GENERICA)
(Para que imagenes no rompan tabla agrego un tamaño, y un estilo de borde
solo para dar estilo redondo)
<td>
<img th:if="${mascota.foto != null}" th:src="${'/foto/mascota/' + mascota.id}" alt="" style="max-height:150px; border-radius: 100%">
<img th:if="${mascota.foto == null}" src="/img/m1.jpeg" alt="" style="max-height: 150px; border-radius: 100%"> 
</td>
--------------------------------------------------------------------------------
En mascotA html hago cambio en:
<header>
      <h1 style="margin-top:80px; text-align: center;"><span th:text="${perfil.
id == null ? 'Crear' : 'Actualizar'}"></span> Mascota</h1>
  </header>
Cambio el modelo perfil que segun si hay o no id cambia el titulo de Crear a
Actualizar, por un modelo accion {accion:
      <h1 style="margin-top:80px; text-align: center;"><span th:text="${accion}"
></span> Mascota</h1>
--------------------------------------------------------------------------------
Y en Controlador Mascota en metodo editar-perfil, agrego a recibir parametro
String accion, y este parametro lo voy a meter en el modelo, y con este modelo
voy a determinar el titulo y ..........

        if (accion == null) {
            accion = "Crear";
        }
Este modelo llama la accion del if anterior.
        model.put("accion", accion);
Y en el metodo Actualizar agrego el modelo:
            modelo.put("accion", "Actualizar");
--------------------------------------------------------------------------------
En mascotA html modifico la siguiente linea:
<br/><button type="submit" class="btn btn-primary">Actualizar Mascota</button>
agrego th:if en 3 modelos que si el modelo accion es igual a esos valores,
muestre el correspondiente boton submit, Crear, Actualizar o Eliminar; al boton
Eliminar le cambio el estilo a btn-danger:
<br/><button th:if="${accion == 'Crear'}" type="submit" class="btn btn-primary">Crear Mascota</button>
<br/><button th:if="${accion == 'Actualizar'}" type="submit" class="btn btn-primary">Actualizar Mascota</button>
<br/><button th:if="${accion =='Eliminar'}" type="submit" class="btn btn-danger">Eliminar Mascota</button>
En etiqueta de <form saco el action:
<form action="/mascota/actualizar-perfil" method="POST" enctype="multipart/form-data">
y lo llevo a los 3 botones submit PERO CON EL NOMBRE DE "formaction", formaction
que dice que si estoy Creando o Acualizando ME LLEVE a /mascota/ACTUALIZAR-perfil,
y que si estoy Eliminando ME LLEVE a /mascota/ELIMINAR-perfil,
tambien de etiqueta de <form saco method="POST" y lo llevo a nivel button, a los
3 submit PERO CON EL NOMBRE DE "formmethod", formmethod que en el caso de
Eliminar es method "DELETE":
<br/><button th:if="${accion == 'Crear'}" type="submit" class="btn btn-primary" formaction="/mascota/actualizar-perfil" formmethod="POST">Crear Mascota</button>
<br/><button th:if="${accion == 'Actualizar'}" type="submit" class="btn btn-primary" formaction="/mascota/actualizar-perfil" formmethod="POST">Actualizar Mascota</button>
<br/><button th:if="${accion =='Eliminar'}" type="submit" class="btn btn-danger" formaction="/mascota/eliminar-perfil" formmethod="DELETE">Eliminar Mascota</button>
--------------------------------------------------------------------------------
En Controlador Mascota creo el metodo eliminar:
Recibe id de la mascota, llama al metodo eliminar del Servicio Mascota que
recibe el id del usuario logueado y el id de la mascota que quiero eliminar, 
el metodo del Servicio marca que puede tirar una Excepcion por lo que lo 
encierro en un try catch, y haiga o no haiga error hago un return a misma URL: 
//metodo Eliminar MAPEADO CON DELETEmapping !!!!!!!!!!!!!  
    @DeleteMapping("/eliminar-perfil")
    public String eliminar(HttpSession session, @RequestParam String id){
        try {
            Usuario login = (Usuario)session.getAttribute("usuariosession");
            mascotaServicio.eliminar(login.getId(), id);
        } catch (ErrorServicio ex) {
Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/mascota/mis-mascotas";
        
    }
--------------------------------------------------------------------------------
Por lo anterior modifico vista mascotaS,
en estos Links "Editar" y "Eliminar" agrego un accion a Actualizar y Eliminar:
<td>
<a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__,accion=Actualizar)}">Editar</a> -
<a th:href="@{/mascota/editar-perfil(id=__${mascota.id}__),accion=Eliminar)}">Eliminar</a>
</td>
--------------------------------------------------------------------------------
Para cuando la ACCION sea eliminar:
En la vista mascota, agrego en el form en el input foto un condicional para
eliminar el campo o input de la foto: th:if="${accion != 'Eliminar'}"
Y el mismo if lo agrego en el label foto, para no se muestre la palabra Foto.
Y el mismo if tambien agrego a los option de select tipo y sexo, y a input de
nombre, para mostrar los campos pero desabilitados para cambiar los datos antes
ingresados: th:disabled="${accion == 'Eliminar'}"
Ahora cuando toque boton Eliminar Mascota el unico campo o input del form que va
a viajar son los que no esten disable desabilitados, en este caso va a ser el
input tipo hidden con el ID !!!
                      <input type="hidden" name="id" th:value="${perfil.id}"/>

Al tocar Eliminar Mascota da un error 405, visto en pila errores de output:
org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR:
org.springframework.web.HttpRequestMethodNotSupportedException: Request method
'GET' not supported
Consultado en base de datos la mascota no se elimino, no tiene fecha de baja,
esta NULL. Para corregir esto en Controlador Mascota, en metodo eliminar, el 
@DeleteMapping("/eliminar-perfil) lo cambio por @PostMapping, al igual como
tiene el de metodo actualizar,
Ahora el eliminar funciona, en la base de datos en campo baja le aparece fecha
de baja, PERO LA VISTA ME LO SIGUE MOSTRANDO, PARA CORREGIR ESTO,
Desde Repositorio Mascota, el metodo buscarMascotasPorUsuario esta trayendo 
todas las mascotas de un usuario, falto una condicion en la Query SELECT:
AND c.baja IS NULL, esto quiere decir que no esta eliminada la mascota.
Ahora la vista no muestra el registro que tiene baja. Tambien puedo desde 
Netbeans desde el llamado de la base de datos, boton derecho sobre el campo-
registro "Baja" - Set to NULL, y guardo desde cuadro Commit Record(s), ahora
en el navegador la vista me vuelve a mostrar la mascota que habia eliminado.
--------------------------------------------------------------------------------
AGREGO EL BOTON AGREGAR MASCOTA EN LA VISTA mascotas:
De la vista inicio copio este boton "¡Agrega una Mascota!", y lo copio en vista
mascotaS arriba de la tabla, y agrego un <br/> salto de linea:
<a th:href="@{/mascota/editar-perfil}" class="btn btn-primary btn-xl rounded-pill mt-5">¡Agrega una Mascota!</a>
<br/>
Y en el boton de la vista inicio cambio la ruta de mascota/editar-perfil a
mascota/mis-mascotas, y cambio el texto del boton de Agrega una Mascota a
"¡Mis Mascotas!.
En la vista mascotaS modifico el boton "¡Agrega una Mascota"! agregado antes,
desde el inspector, Estilos, agrego float, margin y padding, y lo agregado en
Elementos copio style=... y pego en Netbeans en boton "¡Agrega una Mascota"!
Ahora desde la vista mascotaS, puedo agregar mascotas.
--------------------------------------------------------------------------------
En la vista mascota AGREGO UN BOTON VOLVER, para no seguir volviendo desde el
navegador:










  
    





continuar v13 min2710
