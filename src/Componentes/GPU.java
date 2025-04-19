package componentes;

/**
 * Clase que representa una tarjeta gráfica (GPU).
 * Extiende la clase Componente con atributos específicos para GPUs.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class GPU extends Componente {
    private String tipoMemoriaGPU;
    private int potenciaRequerida; // en watts
    
    /**
     * Constructor de la clase GPU.
     * 
     * @param nombre El nombre de la GPU.
     * @param precio El precio de la GPU.
     * @param marca La marca de la GPU.
     * @param tipoMemoriaGPU El tipo de memoria de la GPU.
     * @param potenciaRequerida La potencia requerida en watts.
     */
    public GPU(String nombre, double precio, String marca, String tipoMemoriaGPU, int potenciaRequerida) {
        super(nombre, precio, marca, "GPU");
        this.tipoMemoriaGPU = tipoMemoriaGPU;
        this.potenciaRequerida = potenciaRequerida;
    }
    
    /**
     * Obtiene el tipo de memoria de la GPU.
     * 
     * @return El tipo de memoria.
     */
    public String getTipoMemoriaGPU() {
        return tipoMemoriaGPU;
    }
    
    /**
     * Obtiene la potencia requerida por la GPU.
     * 
     * @return La potencia requerida en watts.
     */
    public int getPotenciaRequerida() {
        return potenciaRequerida;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * Una GPU es compatible con una fuente de alimentación si esta proporciona suficiente potencia.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof FuenteDeAlimentacion) {
            FuenteDeAlimentacion fuente = (FuenteDeAlimentacion) componente;
            return fuente.getPotenciaMaxima() >= this.potenciaRequerida;
        }
        return true; // Con otros componentes no hay incompatibilidad directa
    }
    
    /**
     * Representación en texto de la GPU.
     * 
     * @return Una cadena que representa a la GPU.
     */
    @Override
    public String toString() {
        return super.toString() + " - Memoria: " + tipoMemoriaGPU + " - Potencia: " + potenciaRequerida + "W";
    }
}
