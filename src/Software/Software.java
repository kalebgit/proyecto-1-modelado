package software;

/**
 * Clase que representa un software que puede instalarse en una PC.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Software {
    private String nombre;
    private String version;
    private double precio;
    
    /**
     * Constructor de la clase Software.
     * 
     * @param nombre El nombre del software.
     * @param version La versión del software.
     * @param precio El precio del software.
     */
    public Software(String nombre, String version, double precio) {
        this.nombre = nombre;
        this.version = version;
        this.precio = precio;
    }
    
    /**
     * Obtiene el nombre del software.
     * 
     * @return El nombre del software.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene la versión del software.
     * 
     * @return La versión del software.
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * Obtiene el precio del software.
     * 
     * @return El precio del software.
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Verifica si este software es igual a otro.
     * Dos softwares son iguales si tienen el mismo nombre.
     * 
     * @param obj El objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Software software = (Software) obj;
        return nombre.equals(software.nombre);
    }
    
    /**
     * Representación en texto del software.
     * 
     * @return Una cadena que representa al software.
     */
    @Override
    public String toString() {
        return "Software: " + nombre + " - Versión: " + version + " - Precio: $" + precio;
    }
}
