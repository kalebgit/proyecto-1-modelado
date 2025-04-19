package adaptadores;

import componentes.*;

/**
 * Clase abstracta que implementa el patrón Adapter para hacer compatibles componentes incompatibles.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public abstract class ComponenteAdapter extends Componente {
    protected Componente componenteOriginal;
    
    /**
     * Constructor de la clase ComponenteAdapter.
     * 
     * @param componenteOriginal El componente original que se adaptará.
     * @param nombreAdapter El nombre del adaptador.
     * @param precioExtra El precio extra del adaptador.
     */
    public ComponenteAdapter(Componente componenteOriginal, String nombreAdapter, double precioExtra) {
        super(componenteOriginal.getNombre() + " con " + nombreAdapter, 
              componenteOriginal.getPrecio() + precioExtra, 
              componenteOriginal.getMarca(), 
              componenteOriginal.getTipoComponente());
        this.componenteOriginal = componenteOriginal;
    }
    
    /**
     * Obtiene el componente original que está siendo adaptado.
     * 
     * @return El componente original.
     */
    public Componente getComponenteOriginal() {
        return componenteOriginal;
    }
}
