package componentes;

/**
 * Clase que representa un gabinete para PC.
 * Extiende la clase Componente con atributos específicos para gabinetes.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Gabinete extends Componente {
    private String factorForma; // ATX, Micro-ATX, Mini-ITX, etc.
    private String color;
    private boolean RGB;
    
    /**
     * Constructor de la clase Gabinete.
     * 
     * @param nombre El nombre del gabinete.
     * @param precio El precio del gabinete.
     * @param marca La marca del gabinete.
     * @param factorForma El factor de forma soportado.
     * @param color El color del gabinete.
     * @param RGB Si tiene iluminación RGB.
     */
    public Gabinete(String nombre, double precio, String marca, String factorForma, String color, boolean RGB) {
        super(nombre, precio, marca, "Gabinete");
        this.factorForma = factorForma;
        this.color = color;
        this.RGB = RGB;
    }
    
    /**
     * Obtiene el factor de forma del gabinete.
     * 
     * @return El factor de forma.
     */
    public String getFactorForma() {
        return factorForma;
    }
    
    /**
     * Obtiene el color del gabinete.
     * 
     * @return El color.
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Verifica si el gabinete tiene iluminación RGB.
     * 
     * @return true si tiene RGB, false en caso contrario.
     */
    public boolean tieneRGB() {
        return RGB;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * Un gabinete es compatible con una placa base si soporta su factor de forma.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        // Por simplicidad, asumimos compatibilidad con todos los componentes
        return true;
    }
    
    /**
     * Representación en texto del gabinete.
     * 
     * @return Una cadena que representa al gabinete.
     */
    @Override
    public String toString() {
        return super.toString() + " - Factor de forma: " + factorForma + " - Color: " + color + 
               " - RGB: " + (RGB ? "Sí" : "No");
    }
}
