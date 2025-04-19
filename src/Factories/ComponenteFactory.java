package factories;

import componentes.Componente;

/**
 * Interfaz que define el método para crear un componente.
 * Patrón Factory Method para la creación de componentes.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public interface ComponenteFactory {
    
    /**
     * Crea un componente específico.
     * 
     * @return El componente creado.
     */
    Componente crearComponente();
}
