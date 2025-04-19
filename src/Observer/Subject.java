package observer;

/**
 * Interfaz para el patrón Observer.
 * Define los métodos para agregar, eliminar y notificar observadores.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public interface Subject {
    
    /**
     * Agrega un observador al sujeto.
     * 
     * @param observer El observador a agregar.
     */
    void agregarObservador(Observer observer);
    
    /**
     * Elimina un observador del sujeto.
     * 
     * @param observer El observador a eliminar.
     */
    void eliminarObservador(Observer observer);
    
    /**
     * Notifica a todos los observadores sobre un cambio.
     * 
     * @param mensaje El mensaje que se enviará a los observadores.
     */
    void notificarObservadores(String mensaje);
}
