package test;

import componentes.*;
import db.DBManager;
import pc.PC;
import software.Software;
import factories.SoftwareFactory;
import observer.Pedido;
import observer.Observer;

/**
 * Clase de prueba para verificar la funcionalidad de la base de datos.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class TestDBManager implements Observer {
    
    /**
     * Método principal que ejecuta las pruebas.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        TestDBManager test = new TestDBManager();
        test.ejecutarPruebas();
    }
    
    /**
     * Ejecuta todas las pruebas.
     */
    public void ejecutarPruebas() {
        System.out.println("Iniciando pruebas de base de datos...");
        
        // Conectar a la base de datos
        if (!DBManager.connect()) {
            System.err.println("Error: No se pudo conectar a la base de datos. Abortando pruebas.");
            return;
        }
        
        // Crear componentes para prueba
        CPU cpu = new CPU("Core i7-13700K", 7999.99, "Intel", 8, "LGA1700");
        Motherboard motherboard = new Motherboard("ROG Maximus Z790 Hero", 9999.99, "ASUS", "Z790", "LGA1700", "DDR4", 3);
        RAM ram = new RAM("HyperX Fury 16GB", 1299.99, "Kingston", 16, "DDR4");
        Almacenamiento almacenamiento = new Almacenamiento("A2000 1TB", 1999.99, "Kingston", 1000, "SSD", "M.2 NVMe");
        GPU gpu = new GPU("GeForce RTX 4070", 12999.99, "NVIDIA", "GDDR6X", 650);
        FuenteDeAlimentacion fuente = new FuenteDeAlimentacion("RM1200X", 4599.99, "Corsair", 1200, "80+ Platinum");
        Gabinete gabinete = new Gabinete("H6 Flow ATX", 1999.99, "NZXT", "ATX", "Negro", true);
        
        // Probar inserción de componentes en la base de datos
        System.out.println("Prueba 1: Guardar componentes en la base de datos");
        int cpuId = DBManager.guardarComponente(cpu);
        int mbId = DBManager.guardarComponente(motherboard);
        int ramId = DBManager.guardarComponente(ram);
        int almId = DBManager.guardarComponente(almacenamiento);
        int gpuId = DBManager.guardarComponente(gpu);
        int fuenteId = DBManager.guardarComponente(fuente);
        int gabineteId = DBManager.guardarComponente(gabinete);
        
        System.out.println("CPU ID: " + cpuId);
        System.out.println("Motherboard ID: " + mbId);
        System.out.println("RAM ID: " + ramId);
        System.out.println("Almacenamiento ID: " + almId);
        System.out.println("GPU ID: " + gpuId);
        System.out.println("Fuente ID: " + fuenteId);
        System.out.println("Gabinete ID: " + gabineteId);
        
        // Probar inserción de software en la base de datos
        System.out.println("\nPrueba 2: Guardar software en la base de datos");
        Software windows = SoftwareFactory.crearWindows();
        Software office = SoftwareFactory.crearOffice();
        
        int windowsId = DBManager.guardarSoftware(windows);
        int officeId = DBManager.guardarSoftware(office);
        
        System.out.println("Windows ID: " + windowsId);
        System.out.println("Office ID: " + officeId);
        
        // Probar creación y guardado de PC
        System.out.println("\nPrueba 3: Guardar PC en la base de datos");
        PC pc = PC.builder()
                .setCPU(cpu)
                .setMotherboard(motherboard)
                .addRAM(ram)
                .addAlmacenamiento(almacenamiento)
                .setGPU(gpu)
                .setFuenteDeAlimentacion(fuente)
                .setGabinete(gabinete)
                .build();
        
        // Instalar software
        pc.instalarSoftware(windows);
        pc.instalarSoftware(office);
        
        // Guardar PC
        int pcId = DBManager.guardarPC(pc, "PC de Prueba", "Personalizada");
        System.out.println("PC ID: " + pcId);
        
        // Probar creación y guardado de pedido
        System.out.println("\nPrueba 4: Guardar pedido en la base de datos");
        Pedido pedido = new Pedido("Cliente de Prueba", pc);
        pedido.agregarObservador(this);
        
        int pedidoId = DBManager.guardarPedido(pedido, "Sucursal de Prueba");
        System.out.println("Pedido ID: " + pedidoId);
        
        // Probar actualización de estado de pedido
        System.out.println("\nPrueba 5: Actualizar estado de pedido");
        boolean actualizado = DBManager.actualizarEstadoPedido(pedidoId, "En proceso");
        System.out.println("¿Pedido actualizado? " + actualizado);
        
        // Imprimir estado del pedido después de actualizar
        pedido.setEstado("En proceso");
        
        // Cerrar conexión a la base de datos
        DBManager.disconnect();
        
        System.out.println("\nPruebas completadas exitosamente.");
    }
    
    /**
     * Método de la interfaz Observer para recibir notificaciones.
     * 
     * @param mensaje El mensaje recibido.
     */
    @Override
    public void actualizar(String mensaje) {
        System.out.println("Notificación recibida: " + mensaje);
    }
}
