package componentes;

/**
 * Clase que representa un procesador (CPU).
 * Extiende la clase Componente con atributos específicos para CPUs.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class CPU extends Componente {
    private int cantidadNucleos;
    private String socket;
    
    /**
     * Constructor de la clase CPU.
     * 
     * @param nombre El nombre del CPU.
     * @param precio El precio del CPU.
     * @param marca La marca del CPU (Intel o AMD).
     * @param cantidadNucleos La cantidad de núcleos del procesador.
     * @param socket El tipo de socket del procesador.
     */
    public CPU(String nombre, double precio, String marca, int cantidadNucleos, String socket) {
        super(nombre, precio, marca, "CPU");
        this.cantidadNucleos = cantidadNucleos;
        this.socket = socket;
    }
    
    /**
     * Obtiene la cantidad de núcleos del procesador.
     * 
     * @return La cantidad de núcleos.
     */
    public int getCantidadNucleos() {
        return cantidadNucleos;
    }
    
    /**
     * Obtiene el socket del procesador.
     * 
     * @return El socket del procesador.
     */
    public String getSocket() {
        return socket;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * Un CPU es compatible con un Motherboard si tienen el mismo socket.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof Motherboard) {
            Motherboard mb = (Motherboard) componente;
            return this.socket.equals(mb.getSocket()) && 
                  (mb.getChipset().contains(this.marca) || mb.getChipset().contains("Compatible"));
        }
        return true; // Con otros componentes no hay incompatibilidad directa
    }
    
    /**
     * Representación en texto del CPU.
     * 
     * @return Una cadena que representa al CPU.
     */
    @Override
    public String toString() {
        return super.toString() + " - Núcleos: " + cantidadNucleos + " - Socket: " + socket;
    }
}
