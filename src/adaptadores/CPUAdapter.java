package adaptadores;

import componentes.*;

public class CPUAdapter extends CPU {
    private String socketAdaptado;
    private CPU cpuOriginal;
    
    public CPUAdapter(CPU cpu, String socketAdaptado) {
        // Llama al constructor de CPU
        super(cpu.getNombre() + " con Adaptador", 
              cpu.getPrecio() + 499.99, 
              cpu.getMarca(), 
              cpu.getCantidadNucleos(),
              socketAdaptado);
        this.socketAdaptado = socketAdaptado;
        this.cpuOriginal = cpu;
    }
    
    @Override
    public boolean esCompatibleCon(Componente componente) {
        if (componente instanceof Motherboard) {
            Motherboard mb = (Motherboard) componente;
            return this.getSocket().equals(mb.getSocket());
        }
        return true;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Adaptado de " + cpuOriginal.getSocket() + ")";
    }
}
