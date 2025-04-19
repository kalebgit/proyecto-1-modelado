package componentes;

/**
 * Clase que representa un dispositivo de almacenamiento (HDD o SSD).
 * Extiende la clase Componente con atributos específicos para almacenamiento.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Almacenamiento extends Componente {
    private int capacidadAlmacenamiento; // en GB
    private String tipoAlmacenamiento; // HDD o SSD
    private String interfaz; // SATA, M.2, etc.
    
    /**
     * Constructor de la clase Almacenamiento.
     * 
     * @param nombre El nombre del dispositivo.
     * @param precio El precio del dispositivo.
     * @param marca La marca del dispositivo.
     * @param capacidadAlmacenamiento La capacidad en GB.
     * @param tipoAlmacenamiento El tipo de almacenamiento (HDD o SSD).
     * @param interfaz La interfaz de conexión (SATA, M.2, etc.).
     */
    public Almacenamiento(String nombre, double precio, String marca, int capacidadAlmacenamiento, 
                          String tipoAlmacenamiento, String interfaz) {
        super(nombre, precio, marca, "Almacenamiento");
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.tipoAlmacenamiento = tipoAlmacenamiento;
        this.interfaz = interfaz;
    }
    
    /**
     * Obtiene la capacidad de almacenamiento.
     * 
     * @return La capacidad en GB.
     */
    public int getCapacidadAlmacenamiento() {
        return capacidadAlmacenamiento;
    }
    
    /**
     * Obtiene el tipo de almacenamiento.
     * 
     * @return El tipo (HDD o SSD).
     */
    public String getTipoAlmacenamiento() {
        return tipoAlmacenamiento;
    }
    
    /**
     * Obtiene la interfaz del dispositivo.
     * 
     * @return La interfaz (SATA, M.2, etc.).
     */
    public String getInterfaz() {
        return interfaz;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * Un dispositivo de almacenamiento es compatible con una placa base si ésta tiene la interfaz adecuada.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        // Por simplicidad, asumimos que todas las placas base tienen interfaces SATA y M.2
        return true;
    }
    
    /**
     * Representación en texto del dispositivo de almacenamiento.
     * 
     * @return Una cadena que representa al dispositivo.
     */
    @Override
    public String toString() {
        return super.toString() + " - Capacidad: " + capacidadAlmacenamiento + "GB - Tipo: " + 
               tipoAlmacenamiento + " - Interfaz: " + interfaz;
    }
}
