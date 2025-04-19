package componentes;

/**
 * Clase que representa una memoria RAM.
 * Extiende la clase Componente con atributos específicos para memorias RAM.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class RAM extends Componente {
    private int capacidad; // en GB
    private String tipo; // DDR4, DDR5, etc.
    
    /**
     * Constructor de la clase RAM.
     * 
     * @param nombre El nombre de la RAM.
     * @param precio El precio de la RAM.
     * @param marca La marca de la RAM.
     * @param capacidad La capacidad en GB de la RAM.
     * @param tipo El tipo de memoria RAM (DDR4, DDR5, etc.).
     */
    public RAM(String nombre, double precio, String marca, int capacidad, String tipo) {
        super(nombre, precio, marca, "RAM");
        this.capacidad = capacidad;
        this.tipo = tipo;
    }
    
    /**
     * Obtiene la capacidad de la RAM.
     * 
     * @return La capacidad en GB.
     */
    public int getCapacidad() {
        return capacidad;
    }
    
    /**
     * Obtiene el tipo de memoria RAM.
     * 
     * @return El tipo de memoria (DDR4, DDR5, etc.).
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * Una RAM es compatible con un Motherboard si tienen el mismo tipo de memoria.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof Motherboard) {
            Motherboard mb = (Motherboard) componente;
            return this.tipo.equals(mb.getTipoMemoria());
        }
        return true; // Con otros componentes no hay incompatibilidad directa
    }
    
    /**
     * Representación en texto de la RAM.
     * 
     * @return Una cadena que representa a la RAM.
     */
    @Override
    public String toString() {
        return super.toString() + " - Capacidad: " + capacidad + "GB - Tipo: " + tipo;
    }
}
