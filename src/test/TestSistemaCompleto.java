package test;

import componentes.*;
import factories.*;
import observer.Pedido;
import pc.PC;
import software.Software;
import sucursales.*;
import util.TerminalUI;
import adaptadores.*;
import db.DBManager;

/**
 * Clase de prueba que demuestra el funcionamiento completo del sistema.
 * Simula un flujo de uso completo desde la selección de componentes hasta la generación del pedido.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class TestSistemaCompleto {
    
    /**
     * Método principal que ejecuta la demostración del sistema.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL SISTEMA COMPLETO MONOSCHINOS MX ===");
        
        // 1. Inicializar base de datos
        System.out.println("\n1. Conectando a la base de datos...");
        if (DBManager.connect()) {
            System.out.println("Conexión exitosa a la base de datos.");
        } else {
            System.out.println("No se pudo conectar a la base de datos. Continuando sin persistencia.");
        }
        
        // 2. Inicializar sucursales
        System.out.println("\n2. Inicializando sucursales...");
        Sucursal sucursalCDMX = new Sucursal("MonosChinos CDMX", "Ciudad de México");
        Sucursal sucursalJalisco = new Sucursal("MonosChinos Jalisco", "Guadalajara");
        
        System.out.println("Sucursales creadas: ");
        System.out.println("- " + sucursalCDMX.getNombre() + " (" + sucursalCDMX.getUbicacion() + ")");
        System.out.println("- " + sucursalJalisco.getNombre() + " (" + sucursalJalisco.getUbicacion() + ")");
        
        // 3. Seleccionar sucursal
        System.out.println("\n3. Seleccionando sucursal CDMX...");
        Sucursal sucursalActual = sucursalCDMX;
        System.out.println("Sucursal seleccionada: " + sucursalActual.getNombre());
        
        // 4. Mostrar opciones
        System.out.println("\n4. Mostrando opciones de compra...");
        System.out.println("1. Comprar equipo prearmado");
        System.out.println("2. Armar equipo personalizado");
        
        // 5. Seleccionar opción (simularemos armar un equipo personalizado)
        System.out.println("\n5. Seleccionando opción 2: Armar equipo personalizado...");
        
        // 6. Crear componentes
        System.out.println("\n6. Seleccionando componentes...");
        
        // Obtener componentes del inventario
        System.out.println("\n   a. Seleccionando CPU: Intel Core i7-13700K");
        CPU cpu = (CPU) sucursalActual.buscarComponente("CPU", "Core i7-13700K");
        System.out.println("      " + cpu);
        
        System.out.println("\n   b. Seleccionando Motherboard: ROG Maximus Z790 Hero");
        Motherboard motherboard = (Motherboard) sucursalActual.buscarComponente("Motherboard", "ROG Maximus Z790 Hero");
        System.out.println("      " + motherboard);
        
        System.out.println("\n   c. Verificando compatibilidad CPU-Motherboard...");
        boolean compatibleCPUMotherboard = cpu.esCompatibleCon(motherboard);
        System.out.println("      ¿Son compatibles? " + compatibleCPUMotherboard);
        
        System.out.println("\n   d. Seleccionando RAM: Kingston HyperX Fury 16GB (x2)");
        RAM ram1 = (RAM) sucursalActual.buscarComponente("RAM", "HyperX Fury 16GB");
        RAM ram2 = (RAM) sucursalActual.buscarComponente("RAM", "HyperX Fury 16GB");
        System.out.println("      " + ram1);
        
        System.out.println("\n   e. Seleccionando Almacenamiento: Kingston A2000 1TB");
        Almacenamiento ssd = (Almacenamiento) sucursalActual.buscarComponente("Almacenamiento", "A2000 1TB");
        System.out.println("      " + ssd);
        
        System.out.println("\n   f. Seleccionando Almacenamiento adicional: Seagate Barracuda 2TB");
        Almacenamiento hdd = (Almacenamiento) sucursalActual.buscarComponente("Almacenamiento", "Barracuda 2TB");
        System.out.println("      " + hdd);
        
        System.out.println("\n   g. Seleccionando GPU: NVIDIA GeForce RTX 4070");
        GPU gpu = (GPU) sucursalActual.buscarComponente("GPU", "GeForce RTX 4070");
        System.out.println("      " + gpu);
        
        System.out.println("\n   h. Seleccionando Fuente de Alimentación: Corsair RM1200X");
        FuenteDeAlimentacion fuente = (FuenteDeAlimentacion) sucursalActual.buscarComponente("Fuente de Alimentacion", "RM1200X");
        System.out.println("      " + fuente);
        
        System.out.println("\n   i. Verificando compatibilidad GPU-Fuente...");
        boolean compatibleGPUFuente = gpu.esCompatibleCon(fuente);
        System.out.println("      ¿Son compatibles? " + compatibleGPUFuente);
        
        System.out.println("\n   j. Seleccionando Gabinete: NZXT H6 Flow ATX");
        Gabinete gabinete = (Gabinete) sucursalActual.buscarComponente("Gabinete", "H6 Flow ATX");
        System.out.println("      " + gabinete);
        
        // 7. Construir la PC
        System.out.println("\n7. Construyendo PC con el patrón Builder...");
        PC pc = PC.builder()
                .setCPU(cpu)
                .setMotherboard(motherboard)
                .addRAM(ram1)
                .addRAM(ram2)
                .addAlmacenamiento(ssd)
                .addAlmacenamiento(hdd)
                .setGPU(gpu)
                .setFuenteDeAlimentacion(fuente)
                .setGabinete(gabinete)
                .build();
        
        // 8. Agregar software
        System.out.println("\n8. Agregando software...");
        
        System.out.println("\n   a. Instalando Windows 11");
        Software windows = SoftwareFactory.crearWindows();
        pc.instalarSoftware(windows);
        System.out.println("      " + windows);
        
        System.out.println("\n   b. Instalando Microsoft Office 365");
        Software office = SoftwareFactory.crearOffice();
        pc.instalarSoftware(office);
        System.out.println("      " + office);
        
        // 9. Crear pedido
        System.out.println("\n9. Creando pedido...");
        String nombreCliente = "Juan Pérez";
        System.out.println("   Cliente: " + nombreCliente);
        
        Pedido pedido = sucursalActual.crearPedido(nombreCliente, pc);
        System.out.println("   " + pedido);
        
        // 10. Mostrar ticket
        System.out.println("\n10. Generando ticket de compra...");
        mostrarTicket(pedido, sucursalActual);
        
        // 11. Guardar en la base de datos
        System.out.println("\n11. Guardando pedido en la base de datos...");
        int pedidoId = DBManager.guardarPedido(pedido, sucursalActual.getNombre());
        if (pedidoId > 0) {
            System.out.println("   Pedido guardado con ID: " + pedidoId);
        } else {
            System.out.println("   Error al guardar el pedido en la base de datos.");
        }
        
        // 12. Actualizar estado del pedido
        System.out.println("\n12. Simulando cambios de estado del pedido...");
        pedido.setEstado("En proceso de ensamblaje");
        System.out.println("   Nuevo estado: " + pedido.getEstado());
        
        // Actualizar en la base de datos
        if (DBManager.actualizarEstadoPedido(pedidoId, pedido.getEstado())) {
            System.out.println("   Estado actualizado en la base de datos.");
        }
        
        pedido.setEstado("Listo para envío");
        System.out.println("   Nuevo estado: " + pedido.getEstado());
        
        // 13. Desconectar de la base de datos
        System.out.println("\n13. Desconectando de la base de datos...");
        DBManager.disconnect();
        System.out.println("   Desconexión completada.");
        
        System.out.println("\n=== PRUEBA DEL SISTEMA COMPLETADA EXITOSAMENTE ===");
    }
    
    /**
     * Muestra el ticket de un pedido.
     * 
     * @param pedido El pedido cuyo ticket se mostrará.
     * @param sucursal La sucursal donde se realizó el pedido.
     */
    private static void mostrarTicket(Pedido pedido, Sucursal sucursal) {
        System.out.println();
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.println("                    TICKET DE COMPRA                         ");
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.println("Sucursal: " + sucursal.getNombre());
        System.out.println("Ubicación: " + sucursal.getUbicacion());
        System.out.println("Fecha: " + java.time.LocalDate.now());
        System.out.println("Hora: " + java.time.LocalTime.now().withNano(0));
        System.out.println("---------------------------------------------------------------");
        System.out.println("Cliente: " + pedido.getCliente());
        System.out.println("Número de pedido: " + pedido.getId());
        System.out.println("Estado: " + pedido.getEstado());
        System.out.println("---------------------------------------------------------------");
        System.out.println("DETALLES DE LA PC:");
        
        PC pc = pedido.getPc();
        
        System.out.println("CPU: " + pc.getCpu().getNombre() + " - $" + pc.getCpu().getPrecio());
        System.out.println("Motherboard: " + pc.getMotherboard().getNombre() + " - $" + pc.getMotherboard().getPrecio());
        
        System.out.println("RAM:");
        for (RAM ram : pc.getRamsInstaladas()) {
            System.out.println("  - " + ram.getNombre() + " - $" + ram.getPrecio());
        }
        
        System.out.println("Almacenamiento:");
        for (Almacenamiento alm : pc.getAlmacenamientosInstalados()) {
            System.out.println("  - " + alm.getNombre() + " - $" + alm.getPrecio());
        }
        
        System.out.println("GPU: " + pc.getGpu().getNombre() + " - $" + pc.getGpu().getPrecio());
        System.out.println("Fuente de alimentación: " + pc.getFuenteDeAlimentacion().getNombre() + " - $" + pc.getFuenteDeAlimentacion().getPrecio());
        System.out.println("Gabinete: " + pc.getGabinete().getNombre() + " - $" + pc.getGabinete().getPrecio());
        
        if (!pc.getSoftwaresInstalados().isEmpty()) {
            System.out.println("Software instalado:");
            for (Software software : pc.getSoftwaresInstalados()) {
                System.out.println("  - " + software.getNombre() + " " + software.getVersion() + " - $" + software.getPrecio());
            }
        }
        
        System.out.println("---------------------------------------------------------------");
        System.out.println("PRECIO TOTAL: $" + String.format("%.2f", pc.getPrecioTotal()));
        System.out.println("---------------------------------------------------------------");
        System.out.println("¡Gracias por tu compra en MonosChinos MX!");
        System.out.println("Tu PC será ensamblada y enviada desde nuestra sucursal central.");
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.println();
    }
}
