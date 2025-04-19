package sucursales;

import componentes.*;
import factories.*;
import observer.Observer;
import observer.Pedido;
import pc.PC;
import software.Software;
import util.TerminalUI;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa una sucursal de MonosChinos MX.
 * Implementa el patrón Observer para recibir notificaciones sobre pedidos.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Sucursal implements Observer {
    private String nombre;
    private String ubicacion;
    private List<Pedido> pedidos;
    private Map<String, List<Componente>> inventario;
    private List<PC> configuracionesPrearmadas;
    
    /**
     * Constructor de la clase Sucursal.
     * 
     * @param nombre El nombre de la sucursal.
     * @param ubicacion La ubicación de la sucursal.
     */
    public Sucursal(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.pedidos = new ArrayList<>();
        this.inventario = new HashMap<>();
        this.configuracionesPrearmadas = new ArrayList<>();
        
        // Inicializar el inventario
        inicializarInventario();
        
        // Crear configuraciones prearmadas
        crearConfiguracionesPrearmadas();
    }
    
    /**
     * Inicializa el inventario con componentes predeterminados.
     */
    private void inicializarInventario() {
        // Inicializar listas para cada tipo de componente
        inventario.put("CPU", new ArrayList<>());
        inventario.put("RAM", new ArrayList<>());
        inventario.put("Motherboard", new ArrayList<>());
        inventario.put("GPU", new ArrayList<>());
        inventario.put("Almacenamiento", new ArrayList<>());
        inventario.put("Fuente de Alimentacion", new ArrayList<>());
        inventario.put("Gabinete", new ArrayList<>());
        
        // Agregar CPUs Intel
        inventario.get("CPU").add(new CPU("Core i3-13100", 1999.99, "Intel", 4, "LGA1700"));
        inventario.get("CPU").add(new CPU("Core i5-13600K", 4999.99, "Intel", 6, "LGA1700"));
        inventario.get("CPU").add(new CPU("Core i7-13700K", 7999.99, "Intel", 8, "LGA1700"));
        inventario.get("CPU").add(new CPU("Core i9-13900K", 11999.99, "Intel", 12, "LGA1700"));
        
        // Agregar CPUs AMD
        inventario.get("CPU").add(new CPU("Ryzen 5 5600G", 2499.99, "AMD", 6, "AM4"));
        inventario.get("CPU").add(new CPU("Ryzen 5 7600X", 5499.99, "AMD", 6, "AM5"));
        inventario.get("CPU").add(new CPU("Ryzen 7 7700X", 7999.99, "AMD", 8, "AM5"));
        inventario.get("CPU").add(new CPU("Ryzen 9 7950X3D", 12999.99, "AMD", 16, "AM5"));
        
        // Agregar RAM Adata
        inventario.get("RAM").add(new RAM("XPG Spectrix D41 8GB", 599.99, "Adata", 8, "DDR4"));
        inventario.get("RAM").add(new RAM("XPG Spectrix D41 16GB", 1199.99, "Adata", 16, "DDR4"));
        inventario.get("RAM").add(new RAM("XPG Spectrix D41 32GB", 2399.99, "Adata", 32, "DDR4"));
        
        // Agregar RAM Kingston
        inventario.get("RAM").add(new RAM("HyperX Fury 8GB", 649.99, "Kingston", 8, "DDR4"));
        inventario.get("RAM").add(new RAM("HyperX Fury 16GB", 1299.99, "Kingston", 16, "DDR4"));
        inventario.get("RAM").add(new RAM("HyperX Fury 32GB", 2599.99, "Kingston", 32, "DDR4"));
        
        // Agregar Motherboards ASUS
        inventario.get("Motherboard").add(new Motherboard("ROG Maximus Z790 Hero", 9999.99, "ASUS", "Z790", "LGA1700", "DDR4", 3));
        inventario.get("Motherboard").add(new Motherboard("TUF Gaming B760-Plus WIFI D4", 3999.99, "ASUS", "B760", "LGA1700", "DDR4", 2));
        inventario.get("Motherboard").add(new Motherboard("ROG Strix X670E-E Gaming", 7999.99, "ASUS", "X670E", "AM5", "DDR5", 3));
        
        // Agregar Motherboards MSI
        inventario.get("Motherboard").add(new Motherboard("MEG Godlike", 15999.99, "MSI", "Z790", "LGA1700", "DDR5", 4));
        inventario.get("Motherboard").add(new Motherboard("MAG B760 Tomahawk WIFI DDR4", 4999.99, "MSI", "B760", "LGA1700", "DDR4", 2));
        inventario.get("Motherboard").add(new Motherboard("MPG X670E Carbon WIFI", 6999.99, "MSI", "X670E", "AM5", "DDR5", 3));
        
        // Agregar HDDs
        inventario.get("Almacenamiento").add(new Almacenamiento("Blue 500GB", 799.99, "Western Digital", 500, "HDD", "SATA"));
        inventario.get("Almacenamiento").add(new Almacenamiento("Blue 1TB", 999.99, "Western Digital", 1000, "HDD", "SATA"));
        inventario.get("Almacenamiento").add(new Almacenamiento("Barracuda 1TB", 949.99, "Seagate", 1000, "HDD", "SATA"));
        inventario.get("Almacenamiento").add(new Almacenamiento("Barracuda 2TB", 1499.99, "Seagate", 2000, "HDD", "SATA"));
        
        // Agregar SSDs
        inventario.get("Almacenamiento").add(new Almacenamiento("A2000 500GB", 1299.99, "Kingston", 500, "SSD", "M.2 NVMe"));
        inventario.get("Almacenamiento").add(new Almacenamiento("A2000 1TB", 1999.99, "Kingston", 1000, "SSD", "M.2 NVMe"));
        inventario.get("Almacenamiento").add(new Almacenamiento("KC3000 2TB", 3999.99, "Kingston", 2000, "SSD", "M.2 NVMe"));
        inventario.get("Almacenamiento").add(new Almacenamiento("KC3000 4TB", 7999.99, "Kingston", 4000, "SSD", "M.2 NVMe"));
        
        // Agregar GPUs
        inventario.get("GPU").add(new GPU("GeForce GTX 1660", 4999.99, "NVIDIA", "GDDR6", 450));
        inventario.get("GPU").add(new GPU("GeForce RTX 3060", 7999.99, "NVIDIA", "GDDR6", 550));
        inventario.get("GPU").add(new GPU("GeForce RTX 4070", 12999.99, "NVIDIA", "GDDR6X", 650));
        inventario.get("GPU").add(new GPU("GeForce RTX 4080", 19999.99, "NVIDIA", "GDDR6X", 750));
        inventario.get("GPU").add(new GPU("GeForce RTX 4090", 29999.99, "NVIDIA", "GDDR6X", 850));
        
        // Agregar Fuentes de alimentación
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("SuperNOVA 800G6", 2999.99, "EVGA", 800, "80+ Gold"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("SuperNOVA 1000G6", 3999.99, "EVGA", 1000, "80+ Gold"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("SuperNOVA 1500G6", 5999.99, "EVGA", 1500, "80+ Gold"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("RM850X", 3299.99, "Corsair", 800, "80+ Gold"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("RM1200X", 4599.99, "Corsair", 1200, "80+ Platinum"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("AX1500i", 6999.99, "Corsair", 1500, "80+ Titanium"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("Core Reactor 500", 1999.99, "XPG", 500, "80+ Gold"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("Core Reactor 700", 2799.99, "XPG", 700, "80+ Gold"));
        inventario.get("Fuente de Alimentacion").add(new FuenteDeAlimentacion("Core Reactor 1000", 3799.99, "XPG", 1000, "80+ Gold"));
        
        // Agregar Gabinetes
        inventario.get("Gabinete").add(new Gabinete("H6 Flow ATX", 1999.99, "NZXT", "ATX", "Negro", true));
        inventario.get("Gabinete").add(new Gabinete("H6 Flow ATX", 1999.99, "NZXT", "ATX", "Blanco", true));
        inventario.get("Gabinete").add(new Gabinete("Lancer ATX", 1799.99, "Yeyian", "ATX", "Negro", true));
        inventario.get("Gabinete").add(new Gabinete("Lancer ATX", 1799.99, "Yeyian", "ATX", "Rojo", true));
    }
    
    /**
     * Crea las configuraciones prearmadas de PC.
     */
    private void crearConfiguracionesPrearmadas() {
        // PC Gamer Básica
        configuracionesPrearmadas.add(PC.builder()
            .setCPU((CPU)buscarComponente("CPU", "Core i5-13600K"))
            .setMotherboard((Motherboard)buscarComponente("Motherboard", "TUF Gaming B760-Plus WIFI D4"))
            .addRAM((RAM)buscarComponente("RAM", "XPG Spectrix D41 16GB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "Blue 1TB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "A2000 500GB"))
            .setGPU((GPU)buscarComponente("GPU", "GeForce RTX 3060"))
            .setFuenteDeAlimentacion((FuenteDeAlimentacion)buscarComponente("Fuente de Alimentacion", "SuperNOVA 800G6"))
            .setGabinete((Gabinete)buscarComponente("Gabinete", "H6 Flow ATX"))
            .build());
        
        // PC Gamer Intermedia
        configuracionesPrearmadas.add(PC.builder()
            .setCPU((CPU)buscarComponente("CPU", "Core i7-13700K"))
            .setMotherboard((Motherboard)buscarComponente("Motherboard", "ROG Maximus Z790 Hero"))
            .addRAM((RAM)buscarComponente("RAM", "HyperX Fury 16GB"))
            .addRAM((RAM)buscarComponente("RAM", "HyperX Fury 16GB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "Barracuda 2TB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "A2000 1TB"))
            .setGPU((GPU)buscarComponente("GPU", "GeForce RTX 4070"))
            .setFuenteDeAlimentacion((FuenteDeAlimentacion)buscarComponente("Fuente de Alimentacion", "RM1200X"))
            .setGabinete((Gabinete)buscarComponente("Gabinete", "Lancer ATX"))
            .build());
            
        // PC Gamer Avanzada
        configuracionesPrearmadas.add(PC.builder()
            .setCPU((CPU)buscarComponente("CPU", "Core i9-13900K"))
            .setMotherboard((Motherboard)buscarComponente("Motherboard", "MEG Godlike"))
            .addRAM((RAM)buscarComponente("RAM", "HyperX Fury 32GB"))
            .addRAM((RAM)buscarComponente("RAM", "HyperX Fury 32GB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "Barracuda 2TB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "KC3000 2TB"))
            .setGPU((GPU)buscarComponente("GPU", "GeForce RTX 4090"))
            .setFuenteDeAlimentacion((FuenteDeAlimentacion)buscarComponente("Fuente de Alimentacion", "AX1500i"))
            .setGabinete((Gabinete)buscarComponente("Gabinete", "H6 Flow ATX"))
            .build());
            
        // PC AMD Básica
        configuracionesPrearmadas.add(PC.builder()
            .setCPU((CPU)buscarComponente("CPU", "Ryzen 5 7600X"))
            .setMotherboard((Motherboard)buscarComponente("Motherboard", "ROG Strix X670E-E Gaming"))
            .addRAM((RAM)buscarComponente("RAM", "XPG Spectrix D41 16GB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "Blue 1TB"))
            .addAlmacenamiento((Almacenamiento)buscarComponente("Almacenamiento", "A2000 500GB"))
            .setGPU((GPU)buscarComponente("GPU", "GeForce RTX 3060"))
            .setFuenteDeAlimentacion((FuenteDeAlimentacion)buscarComponente("Fuente de Alimentacion", "Core Reactor 700"))
            .setGabinete((Gabinete)buscarComponente("Gabinete", "Lancer ATX"))
            .build());
    }
    
    /**
     * Busca un componente en el inventario por su tipo y nombre.
     * 
     * @param tipo El tipo de componente.
     * @param nombre El nombre del componente.
     * @return El componente encontrado o null si no existe.
     */
    public Componente buscarComponente(String tipo, String nombre) {
        List<Componente> componentes = inventario.get(tipo);
        if (componentes != null) {
            for (Componente componente : componentes) {
                if (componente.getNombre().equals(nombre)) {
                    return componente;
                }
            }
        }
        return null;
    }
    
    /**
     * Obtiene una lista de componentes por tipo.
     * 
     * @param tipo El tipo de componentes a obtener.
     * @return Lista de componentes del tipo especificado.
     */
    public List<Componente> getComponentesPorTipo(String tipo) {
        return inventario.getOrDefault(tipo, new ArrayList<>());
    }
    
    /**
     * Crea un nuevo pedido para un cliente.
     * 
     * @param nombreCliente El nombre del cliente.
     * @param pc La PC configurada.
     * @return El pedido creado.
     */
    public Pedido crearPedido(String nombreCliente, PC pc) {
        Pedido pedido = new Pedido(nombreCliente, pc);
        pedido.agregarObservador(this);
        pedidos.add(pedido);
        
        // Enviar el pedido a la sucursal central
        SucursalCentral.getInstance().recibirPedido(pedido);
        
        return pedido;
    }
    
    /**
     * Obtiene el nombre de la sucursal.
     * 
     * @return El nombre de la sucursal.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene la ubicación de la sucursal.
     * 
     * @return La ubicación de la sucursal.
     */
    public String getUbicacion() {
        return ubicacion;
    }
    
    /**
     * Obtiene la lista de configuraciones prearmadas.
     * 
     * @return Lista de configuraciones prearmadas.
     */
    public List<PC> getConfiguracionesPrearmadas() {
        return configuracionesPrearmadas;
    }
    
    /**
     * Actualiza el estado cuando recibe una notificación de un pedido.
     * 
     * @param mensaje El mensaje de la notificación.
     */
    @Override
    public void actualizar(String mensaje) {
        TerminalUI.info("Sucursal " + nombre + " recibió notificación: " + mensaje);
    }
    
    /**
     * Muestra las configuraciones prearmadas disponibles.
     */
    public void mostrarConfiguracionesPrearmadas() {
        TerminalUI.info("Configuraciones prearmadas disponibles en " + nombre + ":");
        for (int i = 0; i < configuracionesPrearmadas.size(); i++) {
            TerminalUI.print((i + 1) + ". PC Gamer " + getTipoPorIndice(i));
        }
    }
    
    /**
     * Obtiene el tipo de configuración prearmada por su índice.
     * 
     * @param indice El índice de la configuración.
     * @return Descripción del tipo de configuración.
     */
    private String getTipoPorIndice(int indice) {
        switch (indice) {
            case 0: return "Básica Intel";
            case 1: return "Intermedia Intel";
            case 2: return "Avanzada Intel";
            case 3: return "Básica AMD";
            default: return "Personalizada";
        }
    }
}
