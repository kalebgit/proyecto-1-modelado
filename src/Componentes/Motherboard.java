package componentes;

/**
 * Clase que representa una placa base (Motherboard).
 * Extiende la clase Componente con atributos específicos para placas base.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Motherboard extends Componente {
    private String chipset;
    private String socket;
    private String tipoMemoria; // DDR4, DDR5, etc.
    private int ranurasPCIe;
    
    /**
     * Constructor de la clase Motherboard.
     * 
     * @param nombre El nombre de la placa base.
     * @param precio El precio de la placa base.
     * @param marca La marca de la placa base.
     * @param chipset El chipset de la placa base.
     * @param socket El socket de la placa base.
     * @param tipoMemoria El tipo de memoria soportado.
     * @param ranurasPCIe Número de ranuras PCIe disponibles.
     */
    public Motherboard(String nombre, double precio, String marca, String chipset, String socket, String tipoMemoria, int ranurasPCIe) {
        super(nombre, precio, marca, "Motherboard");
        this.chipset = chipset;
        this.socket = socket;
        this.tipoMemoria = tipoMemoria;
        this.ranurasPCIe = ranurasPCIe;
    }
    
    /**
     * Obtiene el chipset de la placa base.
     * 
     * @return El chipset.
     */
    public String getChipset() {
        return chipset;
    }
    
    /**
     * Obtiene el socket de la placa base.
     * 
     * @return El socket.
     */
    public String getSocket() {
        return socket;
    }
    
    /**
     * Obtiene el tipo de memoria soportado.
     * 
     * @return El tipo de memoria.
     */
    public String getTipoMemoria() {
        return tipoMemoria;
    }
    
    /**
     * Obtiene el número de ranuras PCIe.
     * 
     * @return Número de ranuras PCIe.
     */
    public int getRanurasPCIe() {
        return ranurasPCIe;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof CPU) {
            CPU cpu = (CPU) componente;
            return this.socket.equals(cpu.getSocket()) &&
                  (this.chipset.contains(cpu.getMarca()) || this.chipset.contains("Compatible"));
        } else if (componente instanceof RAM) {
            RAM ram = (RAM) componente;
            return this.tipoMemoria.equals(ram.getTipo());
        } else if (componente instanceof GPU) {
            return this.ranurasPCIe > 0; // Debe tener al menos una ranura PCIe
        }
        return true; // Con otros componentes no hay incompatibilidad directa
    }
    
    /**
     * Representación en texto de la placa base.
     * 
     * @return Una cadena que representa a la placa base.
     */
    @Override
    public String toString() {
        return super.toString() + " - Chipset: " + chipset + " - Socket: " + socket + 
               " - Memoria: " + tipoMemoria + " - Ranuras PCIe: " + ranurasPCIe;
    }
}
