package adaptadores;

import componentes.*;

/**
 * Adaptador para hacer compatibles CPUs AMD con placas base Intel y viceversa.
 * Implementa el patrón Adapter.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class CPUAdapter extends ComponenteAdapter {
    private String socketAdaptado;
    
    /**
     * Constructor de la clase CPUAdapter.
     * 
     * @param cpu El CPU original que se adaptará.
     * @param socketAdaptado El socket al que se adaptará.
     */
    public CPUAdapter(CPU cpu, String socketAdaptado) {
        super(cpu, "Adaptador de Socket " + cpu.getSocket() + " a " + socketAdaptado, 499.99);
        this.socketAdaptado = socketAdaptado;
    }
    
    /**
     * Verifica la compatibilidad con otro componente.
     * El adaptador hace que el CPU sea compatible con placas base que tengan el socket adaptado.
     * 
     * @param componente El componente con el que se verifica la compatibilidad.
     * @return true si son compatibles, false en caso contrario.
     */
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof Motherboard) {
            Motherboard mb = (Motherboard) componente;
            return socketAdaptado.equals(mb.getSocket());
        }
        return true; // Con otros componentes no hay incompatibilidad directa
    }
    
    /**
     * Representación en texto del adaptador de CPU.
     * 
     * @return Una cadena que representa al adaptador.
     */
    @Override
    public String toString() {
        CPU cpu = (CPU) componenteOriginal;
        return super.toString() + " - Núcleos: " + cpu.getCantidadNucleos() + 
               " - Socket Original: " + cpu.getSocket() + " - Socket Adaptado: " + socketAdaptado;
    }
}
