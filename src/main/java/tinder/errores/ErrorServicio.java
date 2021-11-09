
package tinder.errores;
//@autor FABIAN C

/* creamos esta clase para diferenciar ls errores de nuestra logica de los 
que ocurren en el sistema */
public class ErrorServicio extends Exception{

    //constructor
public ErrorServicio(String msn) {
    super(msn);
}

}
