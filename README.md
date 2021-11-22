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
En el controlador de registro probamos el formulario con System.out.print, y
recibe los datos y los envia a la CONSOLA. Una vez probado, borramos los sout, 
para conectar el controlador,instanciamos UsuarioServicio en controlador para
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
condicion y pasamos la variable "error" si es distinta a null, muestro el
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
Si los datos ingresados estan correctos, podriamos enviar algun parametro al
index, o mejor aun creamos otro template exito.html, poniendo otros mensajes
de exito, agregamos en <h2 una variable titulo th:text= y agregamos en <p
una variable descripcion th:text=, y en controlador mediante el metodo put de
la clase modelo, enviamos los mensajes de exito a las variables del html.
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
GUARDAR IMAGEN
En el controlador estabamos enviando null el primer parametro al servicio, 
porque el primer parametro que recibe el servicio es el archivo foto, para 
ello en el html agregamos al <form el pedido de la foto enctype="multipart/form-
data, porque enviaremos un archivo en el formulario; y agregamos el input de la
foto. Agregamos al controlador el parametro MultipartFile archivo que recibira,
y cambiamos en el controlador el parametro null que enviaba al servicio por el 
parametro archivo.
El controlador de Registro recibe del html un MultipartFile, el controlador
envia este al servicio de Registro, y el servicio registrar lo pasa al servicio
de Foto que instancia foto, setea tipo Mime, nombre y contenido lo pasa a Bytes,
y retorna a fotoRepositorio.save.
--------------------------------------------------------------------------------
VALIDO QUE SEGUNDA CLAVE INGRESADA SEA IGUAL A CLAVE 1
En controlador Registro, agregamos parametro clave2 a pasar al servicio Usuario,
en servicio Usuario en registrar agregamos parametro clave 2 que recibimos de 
controlador, en metodo modificar tambien agregamos parametro clave2 y lo que
recibe de validar.
Metodo validar tambien agregamos recibe clave 2 y agregamos validacion !=
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
Ingresamos a la base de datos, listamos zona, select * from zona;, y en la tabla
zona agregamos unas zonas a mano.
En el html en el form agregamos un <select que lista las zonas, y un <option
dinamico de Thymeleaf ,un EACH para que busque en la base de datos y nos traiga,
e itere un arreglo de zonas y para cada zona genere una opcion en el combo.
th:each="zona :${zonas}" th:text="${zona.id}" th:text="${zona.nombre}"
Primero a cada una de las zonas le doy nombre variable zona: e itero la List
{zonas}, y uso la variable zona.id para setear, y en el nombre de la opcion
ponemos el atributo .nombre de la zona. El dato List{zonas} lo saca del ModelMap
del controlador registro, que el template registro.html le envia al controlador,
para lo que en el controlador Registro instanciamos zona Repositorio, 
agregamos como parametro el objeto Modelmap modelo, que permite enviar
variables y collecciones al html, y cuando se ingrese a la url /registro, se van
a buscar todas las zonas de la base de datos, y se guarda lista en modelo, y
cuando se llame a el html registro este llama la lista combo de zonas en el
select.
-------------------------------------------------------------------------------
Agrego el idZona como parametro al controlador Registro al metodo registrar, 
y lo pasamos como parametro a enviar al metodo registrar del Servicio Usuario 
(APARECE COMO ERROR HASTA NO AGREGAR EL PARAMETRO EN Servicio Usuario). 
Agrego en el Servicio Usuario el atributo ZonaRepositorio y el parametro 
idZona a recibir por el metodo registrar



continuar video 4 min240
