package componentes;

/**
 * Clase que representa una fuente de alimentación.
 * Extiende la clase Componente con atributos específicos para fuentes de alimentación.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class FuenteDeAlimentacion extends Componente {
    private int potenciaMaxima; // en watts
    private String certificacion; // 80+ Bronze, 80+ Gold, etc.
    
    /**
     * Constructor de la clase FuenteDeAlimentacion.
     * 
     * @param nombre El nombre de la fuente.
     * @param precio El precio de la fuente.
     * @param marca La marca de la fuente.
     * @param potenciaMaxima La potencia máxima en watts.
     * @param certificacion La certificación de eficiencia.
     */
    public FuenteDeAlimentacion(String nombre, double precio, String marca, int potenciaMaxima, String certificacion) {
        super(nombre, precio, marca, "Fuente de Alimentacion");
        this.potenciaMaxima = potenciaMaxima;
        this.certificacion = certificacion;
    }
    
    /**
     * Obtiene la potencia máxima de la fuente.
     * 
     * @return La potencia máxima en watts.
     */
    public int getPotenciaMaxima() {
        return potenciaMaxima;
    }
    
    /**
     * Obtiene la certificación de eficiencia.
     * 
     * @return La certificación.
     */
    public String getCertificacion() {
        return certificacion;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * Una fuente es compatible con una GPU si proporciona suficiente potencia.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof GPU) {
            GPU gpu = (GPU) componente;
            return this.potenciaMaxima >= gpu.getPotenciaRequerida();
        }
        return true; // Con otros componentes no hay incompatibilidad directa
    }
    
    /**
     * Representación en texto de la fuente de alimentación.
     * 
     * @return Una cadena que representa a la fuente.
     */
    @Override
    public String toString() {
        return super.toString() + " - Potencia: " + potenciaMaxima + "W - Certificación: " + certificacion;
    }
}
