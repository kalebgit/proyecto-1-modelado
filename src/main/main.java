package main;

import componentes.*;
import factories.*;
import observer.Pedido;
import pc.PC;
import software.Software;
import sucursales.*;
import util.TerminalUI;
import adaptadores.*;
import db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que contiene el método main.
 * Implementa la interfaz de usuario en consola para interactuar con el sistema.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Main {
    private static List<Sucursal> sucursales = new ArrayList<>();
    private static Sucursal sucursalActual;
    
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Inicializar base de datos
        if (DBManager.connect()) {
            TerminalUI.success("Conexión a la base de datos establecida correctamente.");
        } else {
            TerminalUI.warning("No se pudo conectar a la base de datos. El sistema funcionará sin persistencia.");
        }
        
        // Inicializar sucursales
        inicializarSucursales();
        
        // Mostrar mensaje de bienvenida
        mostrarBienvenida();
        
        // Seleccionar sucursal
        seleccionarSucursal();
        
        // Mostrar menú principal
        mostrarMenuPrincipal();
        
        // Cerrar conexión a la base de datos
        DBManager.disconnect();
    }
    
    /**
     * Inicializa las sucursales del sistema.
     */
    private static void inicializarSucursales() {
        sucursales.add(new Sucursal("MonosChinos CDMX", "Ciudad de México"));
        sucursales.add(new Sucursal("MonosChinos Chihuahua", "Chihuahua"));
        sucursales.add(new Sucursal("MonosChinos Jalisco", "Guadalajara"));
        sucursales.add(new Sucursal("MonosChinos Yucatán", "Mérida"));
    }
    
    /**
     * Muestra un mensaje de bienvenida al sistema.
     */
    private static void mostrarBienvenida() {
        System.out.println();
        System.out.println("╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║             BIENVENIDO A MONOSCHINOS MX                     ║");
        System.out.println("║                                                             ║");
        System.out.println("║   El mejor lugar para armar tu PC Gamer de alta calidad     ║");
        System.out.println("║                                                             ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    /**
     * Muestra el menú para seleccionar una sucursal.
     */
    private static void seleccionarSucursal() {
        String[] opciones = new String[sucursales.size()];
        for (int i = 0; i < sucursales.size(); i++) {
            opciones[i] = sucursales.get(i).getNombre() + " (" + sucursales.get(i).getUbicacion() + ")";
        }
        
        int opcion = TerminalUI.showMenu("Selecciona una sucursal:", opciones);
        
        if (opcion == 0) {
            TerminalUI.info("Gracias por visitar MonosChinos MX. ¡Hasta pronto!");
            System.exit(0);
        } else {
            sucursalActual = sucursales.get(opcion - 1);
            TerminalUI.success("Bienvenido a " + sucursalActual.getNombre() + " en " + sucursalActual.getUbicacion());
        }
    }
    
    /**
     * Muestra el menú principal del sistema.
     */
    private static void mostrarMenuPrincipal() {
        boolean salir = false;
        
        while (!salir) {
            int opcion = TerminalUI.showMenu("Menú Principal", 
                                           "Comprar equipo prearmado", 
                                           "Armar equipo personalizado", 
                                           "Ver catálogo de componentes", 
                                           "Cambiar de sucursal");
            
            switch (opcion) {
                case 1:
                    comprarEquipoPrearmado();
                    break;
                case 2:
                    armarEquipoPersonalizado();
                    break;
                case 3:
                    verCatalogoComponentes();
                    break;
                case 4:
                    seleccionarSucursal();
                    break;
                case 0:
                    salir = true;
                    TerminalUI.info("Gracias por visitar MonosChinos MX. ¡Hasta pronto!");
                    break;
            }
        }
    }
    
    /**
     * Muestra el menú para comprar un equipo prearmado.
     */
    private static void comprarEquipoPrearmado() {
        List<PC> prearmados = sucursalActual.getConfiguracionesPrearmadas();
        String[] opciones = new String[prearmados.size()];
        
        for (int i = 0; i < prearmados.size(); i++) {
            String tipo = "";
            if (i == 0) tipo = "Básica Intel";
            else if (i == 1) tipo = "Intermedia Intel";
            else if (i == 2) tipo = "Avanzada Intel";
            else if (i == 3) tipo = "Básica AMD";
            
            opciones[i] = "PC Gamer " + tipo + " - $" + String.format("%.2f", prearmados.get(i).getPrecioTotal());
        }
        
        int opcion = TerminalUI.showMenu("Selecciona un equipo prearmado:", opciones);
        
        if (opcion == 0) {
            return;
        }
        
        PC seleccionada = prearmados.get(opcion - 1);
        
        // Mostrar detalle de la PC
        TerminalUI.print(seleccionada.toString());
        
        // Confirmar compra
        String confirmacion = TerminalUI.inputString("¿Deseas comprar esta PC? (S/N):");
        if (confirmacion.equalsIgnoreCase("S")) {
            // Pedir datos del cliente
            String nombreCliente = TerminalUI.inputString("Ingresa tu nombre:");
            
            // Ofrecer software adicional
            agregarSoftwareOpcional(seleccionada);
            
            // Crear pedido
            Pedido pedido = sucursalActual.crearPedido(nombreCliente, seleccionada);
            
            // Guardar en la base de datos
            if (DBManager.guardarPedido(pedido, sucursalActual.getNombre()) > 0) {
                TerminalUI.success("Pedido guardado en la base de datos.");
            }
            
            // Mostrar ticket
            mostrarTicket(pedido);
        }
    }
    
    /**
     * Permite armar un equipo personalizado seleccionando componentes.
     */
    private static void armarEquipoPersonalizado() {
        TerminalUI.info("Vamos a armar tu PC personalizada. Selecciona los componentes uno por uno.");
        
        // Crear builder
        PC.Builder builder = PC.builder();
        
        // Seleccionar componentes
        CPU cpu = seleccionarCPU();
        if (cpu == null) return;
        builder.setCPU(cpu);
        
        Motherboard motherboard = seleccionarMotherboard();
        if (motherboard == null) return;
        builder.setMotherboard(motherboard);
        
        // Verificar compatibilidad CPU-Motherboard
        if (!cpu.esCompatibleCon(motherboard)) {
            TerminalUI.warning("El CPU " + cpu.getNombre() + " no es compatible con la placa base " + motherboard.getNombre());
            String adaptarCPU = TerminalUI.inputString("¿Deseas adaptar el CPU para hacerlo compatible? (S/N):");
            
            if (adaptarCPU.equalsIgnoreCase("S")) {
                CPU cpuAdaptado = adaptarCPU(cpu, motherboard);
                builder.setCPU(cpuAdaptado);
                TerminalUI.success("CPU adaptado correctamente.");
            } else {
                TerminalUI.error("No se puede continuar sin componentes compatibles.");
                return;
            }
        }
        
        RAM ram = seleccionarRAM();
        if (ram == null) return;
        builder.addRAM(ram);
        
        // Preguntar si desea agregar más RAM
        String masRAM = TerminalUI.inputString("¿Deseas agregar otra memoria RAM? (S/N):");
        if (masRAM.equalsIgnoreCase("S")) {
            RAM ram2 = seleccionarRAM();
            if (ram2 != null) {
                builder.addRAM(ram2);
            }
        }
        
        Almacenamiento almacenamiento = seleccionarAlmacenamiento();
        if (almacenamiento == null) return;
        builder.addAlmacenamiento(almacenamiento);
        
        // Preguntar si desea agregar más almacenamiento
        String masAlmacenamiento = TerminalUI.inputString("¿Deseas agregar otro dispositivo de almacenamiento? (S/N):");
        if (masAlmacenamiento.equalsIgnoreCase("S")) {
            Almacenamiento almacenamiento2 = seleccionarAlmacenamiento();
            if (almacenamiento2 != null) {
                builder.addAlmacenamiento(almacenamiento2);
            }
        }
        
        GPU gpu = seleccionarGPU();
        if (gpu == null) return;
        builder.setGPU(gpu);
        
        FuenteDeAlimentacion fuente = seleccionarFuenteDeAlimentacion();
        if (fuente == null) return;
        builder.setFuenteDeAlimentacion(fuente);
        
        // Verificar compatibilidad GPU-Fuente
        if (!gpu.esCompatibleCon(fuente)) {
            TerminalUI.warning("La GPU " + gpu.getNombre() + " requiere más potencia que la que proporciona la fuente " + fuente.getNombre());
            String cambiarFuente = TerminalUI.inputString("¿Deseas seleccionar otra fuente de alimentación? (S/N):");
            
            if (cambiarFuente.equalsIgnoreCase("S")) {
                TerminalUI.info("Selecciona una fuente de alimentación con al menos " + gpu.getPotenciaRequerida() + "W");
                fuente = seleccionarFuenteDeAlimentacion();
                if (fuente == null) return;
                builder.setFuenteDeAlimentacion(fuente);
            } else {
                TerminalUI.error("No se puede continuar sin componentes compatibles.");
                return;
            }
        }
        
        Gabinete gabinete = seleccionarGabinete();
        if (gabinete == null) return;
        builder.setGabinete(gabinete);
        
        // Construir la PC
        PC pc = builder.build();
        
        // Ofrecer software adicional
        agregarSoftwareOpcional(pc);
        
        // Mostrar detalle de la PC
        TerminalUI.print(pc.toString());
        
        // Confirmar compra
        String confirmacion = TerminalUI.inputString("¿Deseas comprar esta PC? (S/N):");
        if (confirmacion.equalsIgnoreCase("S")) {
            // Pedir datos del cliente
            String nombreCliente = TerminalUI.inputString("Ingresa tu nombre:");
            
            // Crear pedido
            Pedido pedido = sucursalActual.crearPedido(nombreCliente, pc);
            
            // Guardar en la base de datos
            if (DBManager.guardarPedido(pedido, sucursalActual.getNombre()) > 0) {
                TerminalUI.success("Pedido guardado en la base de datos.");
            }
            
            // Mostrar ticket
            mostrarTicket(pedido);
        }
    }
    
    /**
     * Muestra el ticket de un pedido.
     * 
     * @param pedido El pedido cuyo ticket se mostrará.
     */
    private static void mostrarTicket(Pedido pedido) {
        System.out.println();
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.println("                    TICKET DE COMPRA                         ");
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.println("Sucursal: " + sucursalActual.getNombre());
        System.out.println("Ubicación: " + sucursalActual.getUbicacion());
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
    
    /**
     * Muestra el catálogo de componentes disponibles.
     */
    private static void verCatalogoComponentes() {
        String[] tiposComponentes = {"CPU", "RAM", "Motherboard", "Almacenamiento", "GPU", "Fuente de Alimentacion", "Gabinete"};
        
        int opcion = TerminalUI.showMenu("Ver catálogo de componentes:", tiposComponentes);
        
        if (opcion == 0) {
            return;
        }
        
        String tipoSeleccionado = tiposComponentes[opcion - 1];
        List<Componente> componentes = sucursalActual.getComponentesPorTipo(tipoSeleccionado);
        
        if (componentes.isEmpty()) {
            TerminalUI.warning("No hay componentes de tipo " + tipoSeleccionado + " disponibles.");
            return;
        }
        
        TerminalUI.info("Catálogo de " + tipoSeleccionado + ":");
        
        for (int i = 0; i < componentes.size(); i++) {
            TerminalUI.print((i + 1) + ". " + componentes.get(i));
        }
    }
    
    /**
     * Permite al usuario seleccionar un CPU del catálogo.
     * 
     * @return El CPU seleccionado o null si se cancela.
     */
    private static CPU seleccionarCPU() {
        List<Componente> cpus = sucursalActual.getComponentesPorTipo("CPU");
        String[] opciones = new String[cpus.size()];
        
        for (int i = 0; i < cpus.size(); i++) {
            opciones[i] = cpus.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona un CPU:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (CPU) cpus.get(opcion - 1);
    }
    
    /**
     * Permite al usuario seleccionar una placa base del catálogo.
     * 
     * @return La placa base seleccionada o null si se cancela.
     */
    private static Motherboard seleccionarMotherboard() {
        List<Componente> motherboards = sucursalActual.getComponentesPorTipo("Motherboard");
        String[] opciones = new String[motherboards.size()];
        
        for (int i = 0; i < motherboards.size(); i++) {
            opciones[i] = motherboards.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona una placa base:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (Motherboard) motherboards.get(opcion - 1);
    }
    
    /**
     * Permite al usuario seleccionar una memoria RAM del catálogo.
     * 
     * @return La RAM seleccionada o null si se cancela.
     */
    private static RAM seleccionarRAM() {
        List<Componente> rams = sucursalActual.getComponentesPorTipo("RAM");
        String[] opciones = new String[rams.size()];
        
        for (int i = 0; i < rams.size(); i++) {
            opciones[i] = rams.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona una memoria RAM:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (RAM) rams.get(opcion - 1);
    }
    
    /**
     * Permite al usuario seleccionar un dispositivo de almacenamiento del catálogo.
     * 
     * @return El almacenamiento seleccionado o null si se cancela.
     */
    private static Almacenamiento seleccionarAlmacenamiento() {
        List<Componente> almacenamientos = sucursalActual.getComponentesPorTipo("Almacenamiento");
        String[] opciones = new String[almacenamientos.size()];
        
        for (int i = 0; i < almacenamientos.size(); i++) {
            opciones[i] = almacenamientos.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona un dispositivo de almacenamiento:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (Almacenamiento) almacenamientos.get(opcion - 1);
    }
    
    /**
     * Permite al usuario seleccionar una tarjeta gráfica del catálogo.
     * 
     * @return La GPU seleccionada o null si se cancela.
     */
    private static GPU seleccionarGPU() {
        List<Componente> gpus = sucursalActual.getComponentesPorTipo("GPU");
        String[] opciones = new String[gpus.size()];
        
        for (int i = 0; i < gpus.size(); i++) {
            opciones[i] = gpus.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona una tarjeta gráfica:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (GPU) gpus.get(opcion - 1);
    }
    
    /**
     * Permite al usuario seleccionar una fuente de alimentación del catálogo.
     * 
     * @return La fuente seleccionada o null si se cancela.
     */
    private static FuenteDeAlimentacion seleccionarFuenteDeAlimentacion() {
        List<Componente> fuentes = sucursalActual.getComponentesPorTipo("Fuente de Alimentacion");
        String[] opciones = new String[fuentes.size()];
        
        for (int i = 0; i < fuentes.size(); i++) {
            opciones[i] = fuentes.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona una fuente de alimentación:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (FuenteDeAlimentacion) fuentes.get(opcion - 1);
    }
    
    /**
     * Permite al usuario seleccionar un gabinete del catálogo.
     * 
     * @return El gabinete seleccionado o null si se cancela.
     */
    private static Gabinete seleccionarGabinete() {
        List<Componente> gabinetes = sucursalActual.getComponentesPorTipo("Gabinete");
        String[] opciones = new String[gabinetes.size()];
        
        for (int i = 0; i < gabinetes.size(); i++) {
            opciones[i] = gabinetes.get(i).toString();
        }
        
        int opcion = TerminalUI.showMenu("Selecciona un gabinete:", opciones);
        
        if (opcion == 0) {
            return null;
        }
        
        return (Gabinete) gabinetes.get(opcion - 1);
    }
    
    /**
     * Adapta un CPU para hacerlo compatible con una placa base.
     * Implementa el patrón Adapter.
     * 
     * @param cpu El CPU a adaptar.
     * @param motherboard La placa base con la que debe ser compatible.
     * @return El CPU adaptado.
     */
    private static CPU adaptarCPU(CPU cpu, Motherboard motherboard) {
        return (CPU) new CPUAdapter(cpu, motherboard.getSocket());
    }
    
    /**
     * Permite al usuario agregar software opcional a la PC.
     * 
     * @param pc La PC a la que se agregará software.
     */
    private static void agregarSoftwareOpcional(PC pc) {
        TerminalUI.info("¿Te gustaría agregar software adicional a tu PC?");
        
        ofrecerSoftware(pc, "Windows", "¿Deseas instalar Windows 11? (S/N):");
        ofrecerSoftware(pc, "Office", "¿Deseas instalar Microsoft Office 365? (S/N):");
        ofrecerSoftware(pc, "Photoshop", "¿Deseas instalar Adobe Photoshop? (S/N):");
        ofrecerSoftware(pc, "AutoCAD", "¿Deseas instalar AutoCAD? (S/N):");
        ofrecerSoftware(pc, "Terminal", "¿Deseas instalar una terminal con WSL? (S/N):");
    }
    
    /**
     * Ofrece un software específico al usuario.
     * 
     * @param pc La PC a la que se agregará el software.
     * @param tipo El tipo de software a ofrecer.
     * @param mensaje El mensaje que se mostrará al usuario.
     */
    private static void ofrecerSoftware(PC pc, String tipo, String mensaje) {
        String respuesta = TerminalUI.inputString(mensaje);
        if (respuesta.equalsIgnoreCase("S")) {
            Software software = SoftwareFactory.crearSoftware(tipo);
            if (pc.instalarSoftware(software)) {
                TerminalUI.success(software.getNombre() + " instalado correctamente.");
            } else {
                TerminalUI.warning(software.getNombre() + " ya estaba instalado.");
            }
        }
    }
}
