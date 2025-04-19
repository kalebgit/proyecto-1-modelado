package pc;

import componentes.*;
import software.Software;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una PC ensamblada.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class PC {
    private CPU cpu;
    private Motherboard motherboard;
    private List<RAM> ramsInstaladas;
    private List<Almacenamiento> almacenamientosInstalados;
    private GPU gpu;
    private FuenteDeAlimentacion fuenteDeAlimentacion;
    private Gabinete gabinete;
    private List<Software> softwaresInstalados;
    private double precioTotal;
    
    /**
     * Constructor de la clase PC.
     * 
     * @param cpu El procesador.
     * @param motherboard La placa base.
     * @param ram La memoria RAM.
     * @param almacenamiento El almacenamiento.
     * @param gpu La tarjeta gráfica.
     * @param fuenteDeAlimentacion La fuente de alimentación.
     * @param gabinete El gabinete.
     */
    public PC(CPU cpu, Motherboard motherboard, RAM ram, Almacenamiento almacenamiento,
              GPU gpu, FuenteDeAlimentacion fuenteDeAlimentacion, Gabinete gabinete) {
        this.cpu = cpu;
        this.motherboard = motherboard;
        this.ramsInstaladas = new ArrayList<>();
        this.ramsInstaladas.add(ram);
        this.almacenamientosInstalados = new ArrayList<>();
        this.almacenamientosInstalados.add(almacenamiento);
        this.gpu = gpu;
        this.fuenteDeAlimentacion = fuenteDeAlimentacion;
        this.gabinete = gabinete;
        this.softwaresInstalados = new ArrayList<>();
        
        calcularPrecioTotal();
    }
    
    /**
     * Constructor privado para ser usado por el Builder.
     */
    private PC() {
        this.ramsInstaladas = new ArrayList<>();
        this.almacenamientosInstalados = new ArrayList<>();
        this.softwaresInstalados = new ArrayList<>();
    }
    
    /**
     * Agrega una memoria RAM a la PC.
     * 
     * @param ram La memoria RAM a agregar.
     */
    public void agregarRAM(RAM ram) {
        if (ramsInstaladas.size() < 4) { // Máximo 4 slots de RAM
            ramsInstaladas.add(ram);
            calcularPrecioTotal();
        }
    }
    
    /**
     * Agrega un dispositivo de almacenamiento a la PC.
     * 
     * @param almacenamiento El dispositivo de almacenamiento a agregar.
     */
    public void agregarAlmacenamiento(Almacenamiento almacenamiento) {
        almacenamientosInstalados.add(almacenamiento);
        calcularPrecioTotal();
    }
    
    /**
     * Instala software en la PC.
     * 
     * @param software El software a instalar.
     * @return true si se instaló correctamente, false si ya estaba instalado.
     */
    public boolean instalarSoftware(Software software) {
        if (!softwareInstalado(software)) {
            softwaresInstalados.add(software);
            calcularPrecioTotal();
            return true;
        }
        return false;
    }
    
    /**
     * Verifica si un software ya está instalado.
     * 
     * @param software El software a verificar.
     * @return true si ya está instalado, false en caso contrario.
     */
    public boolean softwareInstalado(Software software) {
        return softwaresInstalados.contains(software);
    }
    
    /**
     * Calcula el precio total de la PC.
     */
    private void calcularPrecioTotal() {
        precioTotal = 0;
        
        if (cpu != null) precioTotal += cpu.getPrecio();
        if (motherboard != null) precioTotal += motherboard.getPrecio();
        if (gpu != null) precioTotal += gpu.getPrecio();
        if (fuenteDeAlimentacion != null) precioTotal += fuenteDeAlimentacion.getPrecio();
        if (gabinete != null) precioTotal += gabinete.getPrecio();
        
        for (RAM ram : ramsInstaladas) {
            precioTotal += ram.getPrecio();
        }
        
        for (Almacenamiento almacenamiento : almacenamientosInstalados) {
            precioTotal += almacenamiento.getPrecio();
        }
        
        for (Software software : softwaresInstalados) {
            precioTotal += software.getPrecio();
        }
    }
    
    /**
     * Obtiene el precio total de la PC.
     * 
     * @return El precio total.
     */
    public double getPrecioTotal() {
        return precioTotal;
    }
    
    /**
     * Obtiene el procesador de la PC.
     * 
     * @return El procesador.
     */
    public CPU getCpu() {
        return cpu;
    }
    
    /**
     * Obtiene la placa base de la PC.
     * 
     * @return La placa base.
     */
    public Motherboard getMotherboard() {
        return motherboard;
    }
    
    /**
     * Obtiene la lista de RAMs instaladas.
     * 
     * @return La lista de RAMs.
     */
    public List<RAM> getRamsInstaladas() {
        return ramsInstaladas;
    }
    
    /**
     * Obtiene la lista de almacenamientos instalados.
     * 
     * @return La lista de almacenamientos.
     */
    public List<Almacenamiento> getAlmacenamientosInstalados() {
        return almacenamientosInstalados;
    }
    
    /**
     * Obtiene la tarjeta gráfica de la PC.
     * 
     * @return La tarjeta gráfica.
     */
    public GPU getGpu() {
        return gpu;
    }
    
    /**
     * Obtiene la fuente de alimentación de la PC.
     * 
     * @return La fuente de alimentación.
     */
    public FuenteDeAlimentacion getFuenteDeAlimentacion() {
        return fuenteDeAlimentacion;
    }
    
    /**
     * Obtiene el gabinete de la PC.
     * 
     * @return El gabinete.
     */
    public Gabinete getGabinete() {
        return gabinete;
    }
    
    /**
     * Obtiene la lista de software instalado.
     * 
     * @return La lista de software.
     */
    public List<Software> getSoftwaresInstalados() {
        return softwaresInstalados;
    }
    
    /**
     * Crea un nuevo constructor de PC (Builder).
     * 
     * @return Un nuevo builder de PC.
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Clase Builder para construir PCs.
     * Implementa el patrón Builder.
     */
    public static class Builder {
        private PC pc;
        
        /**
         * Constructor del Builder.
         */
        public Builder() {
            pc = new PC();
        }
        
        /**
         * Establece el procesador de la PC.
         * 
         * @param cpu El procesador.
         * @return Este builder.
         */
        public Builder setCPU(CPU cpu) {
            pc.cpu = cpu;
            return this;
        }
        
        /**
         * Establece la placa base de la PC.
         * 
         * @param motherboard La placa base.
         * @return Este builder.
         */
        public Builder setMotherboard(Motherboard motherboard) {
            pc.motherboard = motherboard;
            return this;
        }
        
        /**
         * Añade una memoria RAM a la PC.
         * 
         * @param ram La memoria RAM a añadir.
         * @return Este builder.
         */
        public Builder addRAM(RAM ram) {
            pc.ramsInstaladas.add(ram);
            return this;
        }
        
        /**
         * Añade un dispositivo de almacenamiento a la PC.
         * 
         * @param almacenamiento El almacenamiento a añadir.
         * @return Este builder.
         */
        public Builder addAlmacenamiento(Almacenamiento almacenamiento) {
            pc.almacenamientosInstalados.add(almacenamiento);
            return this;
        }
        
        /**
         * Establece la tarjeta gráfica de la PC.
         * 
         * @param gpu La tarjeta gráfica.
         * @return Este builder.
         */
        public Builder setGPU(GPU gpu) {
            pc.gpu = gpu;
            return this;
        }
        
        /**
         * Establece la fuente de alimentación de la PC.
         * 
         * @param fuente La fuente de alimentación.
         * @return Este builder.
         */
        public Builder setFuenteDeAlimentacion(FuenteDeAlimentacion fuente) {
            pc.fuenteDeAlimentacion = fuente;
            return this;
        }
        
        /**
         * Establece el gabinete de la PC.
         * 
         * @param gabinete El gabinete.
         * @return Este builder.
         */
        public Builder setGabinete(Gabinete gabinete) {
            pc.gabinete = gabinete;
            return this;
        }
        
        /**
         * Instala software en la PC.
         * 
         * @param software El software a instalar.
         * @return Este builder.
         */
        public Builder addSoftware(Software software) {
            if (!pc.softwareInstalado(software)) {
                pc.softwaresInstalados.add(software);
            }
            return this;
        }
        
        /**
         * Construye la PC con los componentes configurados.
         * 
         * @return La PC construida.
         * @throws IllegalStateException Si faltan componentes obligatorios.
         */
        public PC build() {
            // Verificar componentes obligatorios
            if (pc.cpu == null || pc.motherboard == null || pc.ramsInstaladas.isEmpty() || 
                pc.almacenamientosInstalados.isEmpty() || pc.gpu == null || 
                pc.fuenteDeAlimentacion == null || pc.gabinete == null) {
                throw new IllegalStateException("Faltan componentes obligatorios para construir la PC");
            }
            
            // Calcular precio total
            pc.calcularPrecioTotal();
            
            return pc;
        }
    }
    
    /**
     * Representación en texto de la PC.
     * 
     * @return Una cadena que representa a la PC.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==== Configuración de la PC ====\n");
        sb.append("CPU: ").append(cpu).append("\n");
        sb.append("Motherboard: ").append(motherboard).append("\n");
        
        sb.append("RAM Instalada:\n");
        for (RAM ram : ramsInstaladas) {
            sb.append("  - ").append(ram).append("\n");
        }
        
        sb.append("Almacenamiento Instalado:\n");
        for (Almacenamiento almacenamiento : almacenamientosInstalados) {
            sb.append("  - ").append(almacenamiento).append("\n");
        }
        
        sb.append("GPU: ").append(gpu).append("\n");
        sb.append("Fuente de alimentación: ").append(fuenteDeAlimentacion).append("\n");
        sb.append("Gabinete: ").append(gabinete).append("\n");
        
        if (!softwaresInstalados.isEmpty()) {
            sb.append("Software Instalado:\n");
            for (Software software : softwaresInstalados) {
                sb.append("  - ").append(software).append("\n");
            }
        }
        
        sb.append("Precio Total: $").append(String.format("%.2f", precioTotal)).append("\n");
        
        return sb.toString();
    }
}
