package test;

import componentes.*;
import pc.PC;
import software.Software;
import factories.SoftwareFactory;

/**
 * Clase de prueba para verificar la funcionalidad de la clase PC.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class TestPC {
    
    /**
     * Método principal que ejecuta las pruebas.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("Iniciando pruebas de PC...");
        
        // Crear componentes para prueba
        CPU cpu = new CPU("Core i7-13700K", 7999.99, "Intel", 8, "LGA1700");
        Motherboard motherboard = new Motherboard("ROG Maximus Z790 Hero", 9999.99, "ASUS", "Z790", "LGA1700", "DDR4", 3);
        RAM ram = new RAM("HyperX Fury 16GB", 1299.99, "Kingston", 16, "DDR4");
        Almacenamiento almacenamiento = new Almacenamiento("A2000 1TB", 1999.99, "Kingston", 1000, "SSD", "M.2 NVMe");
        GPU gpu = new GPU("GeForce RTX 4070", 12999.99, "NVIDIA", "GDDR6X", 650);
        FuenteDeAlimentacion fuente = new FuenteDeAlimentacion("RM1200X", 4599.99, "Corsair", 1200, "80+ Platinum");
        Gabinete gabinete = new Gabinete("H6 Flow ATX", 1999.99, "NZXT", "ATX", "Negro", true);
        
        // Probar creación de PC usando constructor directo
        System.out.println("Prueba 1: Crear PC con constructor directo");
        PC pc1 = new PC(cpu, motherboard, ram, almacenamiento, gpu, fuente, gabinete);
        // pc1.mostrarConfiguracion();
        System.out.println(pc1);  
        
        // Probar creación de PC usando Builder
        System.out.println("\nPrueba 2: Crear PC con Builder");
        PC pc2 = PC.builder()
                    .setCPU(cpu)
                    .setMotherboard(motherboard)
                    .addRAM(ram)
                    .addAlmacenamiento(almacenamiento)
                    .setGPU(gpu)
                    .setFuenteDeAlimentacion(fuente)
                    .setGabinete(gabinete)
                    .build();
        System.out.println(pc2);
        
        // Probar instalación de software
        System.out.println("\nPrueba 3: Instalar software");
        Software windows = SoftwareFactory.crearWindows();
        Software office = SoftwareFactory.crearOffice();
        
        boolean instaladoWindows = pc2.instalarSoftware(windows);
        boolean instaladoOffice = pc2.instalarSoftware(office);
        
        System.out.println("Windows instalado: " + instaladoWindows);
        System.out.println("Office instalado: " + instaladoOffice);
        
        // Intentar instalar el mismo software de nuevo
        boolean instaladoWindowsDuplicado = pc2.instalarSoftware(windows);
        System.out.println("Windows instalado (duplicado): " + instaladoWindowsDuplicado);
        
        // Mostrar PC con software instalado
        System.out.println("\nPC con software instalado:");
        System.out.println(pc2);
        
        // Probar verificación de compatibilidad
        System.out.println("\nPrueba 4: Verificar compatibilidad CPU-Motherboard");
        boolean compatibleCPUMotherboard = cpu.esCompatibleCon(motherboard);
        System.out.println("¿El CPU es compatible con la motherboard? " + compatibleCPUMotherboard);
        
        // Intentar con CPU incompatible
        CPU cpuAMD = new CPU("Ryzen 9 7950X3D", 12999.99, "AMD", 16, "AM5");
        boolean incompatibleCPUMotherboard = cpuAMD.esCompatibleCon(motherboard);
        System.out.println("¿El CPU AMD es compatible con la motherboard Intel? " + incompatibleCPUMotherboard);
        
        System.out.println("\nPruebas completadas exitosamente.");
    }
}
