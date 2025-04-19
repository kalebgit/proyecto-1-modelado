package componentes;

/**
 * Clase abstracta base para todos los componentes de computadora.
 * Define los atributos y métodos comunes a todos los componentes.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public abstract class Componente {
    protected String nombre;
    protected double precio;
    protected String marca;
    protected String tipoComponente;
    
    /**
     * Constructor de la clase Componente.
     * 
     * @param nombre El nombre del componente.
     * @param precio El precio del componente.
     * @param marca La marca del componente.
     * @param tipoComponente El tipo de componente (CPU, RAM, GPU, etc.).
     */
    public Componente(String nombre, double precio, String marca, String tipoComponente) {
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.tipoComponente = tipoComponente;
    }
    
    /**
     * Obtiene el nombre del componente.
     * 
     * @return El nombre del componente.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el precio del componente.
     * 
     * @return El precio del componente.
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Obtiene la marca del componente.
     * 
     * @return La marca del componente.
     */
    public String getMarca() {
        return marca;
    }
    
    /**
     * Obtiene el tipo de componente.
     * 
     * @return El tipo de componente.
     */
    public String getTipoComponente() {
        return tipoComponente;
    }
    
    /**
     * Representación en texto del componente.
     * 
     * @return Una cadena que representa al componente.
     */
    @Override
    public String toString() {
        return tipoComponente + ": " + nombre + " - Marca: " + marca + " - Precio: $" + precio;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    public abstract boolean esCompatibleCon(Componente componente);
}
