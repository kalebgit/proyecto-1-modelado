package observer;

/**
 * Interfaz para el patrón Observer.
 * Define el método que será llamado cuando haya cambios en un sujeto observado.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public interface Observer {
    
    /**
     * Método llamado cuando ocurre un cambio en el sujeto observado.
     * 
     * @param mensaje El mensaje con información sobre el cambio.
     */
    void actualizar(String mensaje);
}
