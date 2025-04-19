package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import componentes.*;
import pc.PC;
import software.Software;
import util.TerminalUI;

/**
 * Clase para gestionar la conexión y operaciones con la base de datos.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:monoschinos.db";
    private static Connection conn = null;
    
    /**
     * Establece la conexión con la base de datos.
     * 
     * @return true si la conexión se estableció correctamente, false en caso contrario.
     */
    public static boolean connect() {
        try {
            // Cargar el driver JDBC de SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Crear la conexión
            conn = DriverManager.getConnection(DB_URL);
            
            if (conn != null) {
                // Crear las tablas si no existen
                createTables();
                return true;
            }
        } catch (ClassNotFoundException e) {
            TerminalUI.error("Error: Driver JDBC no encontrado. " + e.getMessage());
        } catch (SQLException e) {
            TerminalUI.error("Error al conectar a la base de datos: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Cierra la conexión con la base de datos.
     */
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            TerminalUI.error("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    /**
     * Crea las tablas necesarias en la base de datos.
     * 
     * @throws SQLException Si ocurre un error al crear las tablas.
     */
    private static void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        
        // Tabla de componentes
        stmt.execute("CREATE TABLE IF NOT EXISTS componentes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "precio REAL NOT NULL," +
                    "marca TEXT NOT NULL," +
                    "tipo_componente TEXT NOT NULL," +
                    "atributos TEXT" + // Almacenará atributos específicos en formato JSON
                    ")");
        
        // Tabla de software
        stmt.execute("CREATE TABLE IF NOT EXISTS software (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "version TEXT NOT NULL," +
                    "precio REAL NOT NULL" +
                    ")");
        
        // Tabla de PCs
        stmt.execute("CREATE TABLE IF NOT EXISTS pcs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "tipo TEXT NOT NULL," +
                    "precio_total REAL NOT NULL," +
                    "fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
        
        // Tabla de relación PC-Componente
        stmt.execute("CREATE TABLE IF NOT EXISTS pc_componentes (" +
                    "pc_id INTEGER," +
                    "componente_id INTEGER," +
                    "PRIMARY KEY (pc_id, componente_id)," +
                    "FOREIGN KEY (pc_id) REFERENCES pcs(id)," +
                    "FOREIGN KEY (componente_id) REFERENCES componentes(id)" +
                    ")");
        
        // Tabla de relación PC-Software
        stmt.execute("CREATE TABLE IF NOT EXISTS pc_software (" +
                    "pc_id INTEGER," +
                    "software_id INTEGER," +
                    "PRIMARY KEY (pc_id, software_id)," +
                    "FOREIGN KEY (pc_id) REFERENCES pcs(id)," +
                    "FOREIGN KEY (software_id) REFERENCES software(id)" +
                    ")");
        
        // Tabla de pedidos
        stmt.execute("CREATE TABLE IF NOT EXISTS pedidos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cliente TEXT NOT NULL," +
                    "pc_id INTEGER," +
                    "estado TEXT NOT NULL," +
                    "sucursal TEXT NOT NULL," +
                    "fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (pc_id) REFERENCES pcs(id)" +
                    ")");
        
        stmt.close();
    }
    
    /**
     * Guarda un componente en la base de datos.
     * 
     * @param componente El componente a guardar.
     * @return El ID del componente guardado.
     */
    public static int guardarComponente(Componente componente) {
        int id = -1;
        
        try {
            String sql = "INSERT INTO componentes (nombre, precio, marca, tipo_componente, atributos) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, componente.getNombre());
            pstmt.setDouble(2, componente.getPrecio());
            pstmt.setString(3, componente.getMarca());
            pstmt.setString(4, componente.getTipoComponente());
            
            // Serializar atributos específicos según el tipo de componente
            String atributos = serializarAtributos(componente);
            pstmt.setString(5, atributos);
            
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            TerminalUI.error("Error al guardar componente: " + e.getMessage());
        }
        
        return id;
    }
    
    /**
     * Serializa los atributos específicos de un componente en formato JSON simple.
     * 
     * @param componente El componente cuyos atributos se serializarán.
     * @return Cadena JSON con los atributos.
     */
    private static String serializarAtributos(Componente componente) {
        StringBuilder json = new StringBuilder("{");
        
        if (componente instanceof CPU) {
            CPU cpu = (CPU) componente;
            json.append("\"cantidadNucleos\":").append(cpu.getCantidadNucleos())
                .append(",\"socket\":\"").append(cpu.getSocket()).append("\"");
        } else if (componente instanceof RAM) {
            RAM ram = (RAM) componente;
            json.append("\"capacidad\":").append(ram.getCapacidad())
                .append(",\"tipo\":\"").append(ram.getTipo()).append("\"");
        } else if (componente instanceof GPU) {
            GPU gpu = (GPU) componente;
            json.append("\"tipoMemoriaGPU\":\"").append(gpu.getTipoMemoriaGPU()).append("\"")
                .append(",\"potenciaRequerida\":").append(gpu.getPotenciaRequerida());
        } else if (componente instanceof Motherboard) {
            Motherboard mb = (Motherboard) componente;
            json.append("\"chipset\":\"").append(mb.getChipset()).append("\"")
                .append(",\"socket\":\"").append(mb.getSocket()).append("\"")
                .append(",\"tipoMemoria\":\"").append(mb.getTipoMemoria()).append("\"")
                .append(",\"ranurasPCIe\":").append(mb.getRanurasPCIe());
        } else if (componente instanceof Almacenamiento) {
            Almacenamiento alm = (Almacenamiento) componente;
            json.append("\"capacidadAlmacenamiento\":").append(alm.getCapacidadAlmacenamiento())
                .append(",\"tipoAlmacenamiento\":\"").append(alm.getTipoAlmacenamiento()).append("\"")
                .append(",\"interfaz\":\"").append(alm.getInterfaz()).append("\"");
        } else if (componente instanceof FuenteDeAlimentacion) {
            FuenteDeAlimentacion fuente = (FuenteDeAlimentacion) componente;
            json.append("\"potenciaMaxima\":").append(fuente.getPotenciaMaxima())
                .append(",\"certificacion\":\"").append(fuente.getCertificacion()).append("\"");
        } else if (componente instanceof Gabinete) {
            Gabinete gabinete = (Gabinete) componente;
            json.append("\"factorForma\":\"").append(gabinete.getFactorForma()).append("\"")
                .append(",\"color\":\"").append(gabinete.getColor()).append("\"")
                .append(",\"RGB\":").append(gabinete.tieneRGB());
        }
        
        json.append("}");
        return json.toString();
    }
    
    /**
     * Guarda un software en la base de datos.
     * 
     * @param software El software a guardar.
     * @return El ID del software guardado.
     */
    public static int guardarSoftware(Software software) {
        int id = -1;
        
        try {
            String sql = "INSERT INTO software (nombre, version, precio) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, software.getNombre());
            pstmt.setString(2, software.getVersion());
            pstmt.setDouble(3, software.getPrecio());
            
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            TerminalUI.error("Error al guardar software: " + e.getMessage());
        }
        
        return id;
    }
    
    /**
     * Guarda una PC en la base de datos, junto con sus componentes y software.
     * 
     * @param pc La PC a guardar.
     * @param nombre El nombre descriptivo de la PC.
     * @param tipo El tipo de PC (prearmada o personalizada).
     * @return El ID de la PC guardada.
     */
    public static int guardarPC(PC pc, String nombre, String tipo) {
        int pcId = -1;
        
        try {
            conn.setAutoCommit(false);
            
            // Guardar la PC
            String sql = "INSERT INTO pcs (nombre, tipo, precio_total) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, tipo);
            pstmt.setDouble(3, pc.getPrecioTotal());
            
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                pcId = rs.getInt(1);
            }
            
            rs.close();
            pstmt.close();
            
            // Guardar los componentes y relacionarlos con la PC
            guardarComponentesPC(pcId, pc);
            
            // Guardar el software y relacionarlo con la PC
            guardarSoftwarePC(pcId, pc);
            
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                TerminalUI.error("Error al hacer rollback: " + ex.getMessage());
            }
            TerminalUI.error("Error al guardar PC: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                TerminalUI.error("Error al restaurar autocommit: " + e.getMessage());
            }
        }
        
        return pcId;
    }
    
    /**
     * Guarda los componentes de una PC y los relaciona con ella en la base de datos.
     * 
     * @param pcId El ID de la PC.
     * @param pc La PC cuyos componentes se guardarán.
     * @throws SQLException Si ocurre un error al guardar los componentes.
     */
    private static void guardarComponentesPC(int pcId, PC pc) throws SQLException {
        // Guardar CPU
        int cpuId = guardarComponente(pc.getCpu());
        guardarRelacionPCComponente(pcId, cpuId);
        
        // Guardar Motherboard
        int mbId = guardarComponente(pc.getMotherboard());
        guardarRelacionPCComponente(pcId, mbId);
        
        // Guardar RAM
        for (RAM ram : pc.getRamsInstaladas()) {
            int ramId = guardarComponente(ram);
            guardarRelacionPCComponente(pcId, ramId);
        }
        
        // Guardar Almacenamiento
        for (Almacenamiento alm : pc.getAlmacenamientosInstalados()) {
            int almId = guardarComponente(alm);
            guardarRelacionPCComponente(pcId, almId);
        }
        
        // Guardar GPU
        int gpuId = guardarComponente(pc.getGpu());
        guardarRelacionPCComponente(pcId, gpuId);
        
        // Guardar Fuente de Alimentación
        int fuenteId = guardarComponente(pc.getFuenteDeAlimentacion());
        guardarRelacionPCComponente(pcId, fuenteId);
        
        // Guardar Gabinete
        int gabineteId = guardarComponente(pc.getGabinete());
        guardarRelacionPCComponente(pcId, gabineteId);
    }
    
    /**
     * Guarda la relación entre una PC y un componente en la base de datos.
     * 
     * @param pcId El ID de la PC.
     * @param componenteId El ID del componente.
     * @throws SQLException Si ocurre un error al guardar la relación.
     */
    private static void guardarRelacionPCComponente(int pcId, int componenteId) throws SQLException {
        String sql = "INSERT INTO pc_componentes (pc_id, componente_id) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setInt(1, pcId);
        pstmt.setInt(2, componenteId);
        
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    /**
     * Guarda el software instalado en una PC y lo relaciona con ella en la base de datos.
     * 
     * @param pcId El ID de la PC.
     * @param pc La PC cuyo software se guardará.
     * @throws SQLException Si ocurre un error al guardar el software.
     */
    private static void guardarSoftwarePC(int pcId, PC pc) throws SQLException {
        for (Software software : pc.getSoftwaresInstalados()) {
            int softwareId = guardarSoftware(software);
            
            String sql = "INSERT INTO pc_software (pc_id, software_id) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, pcId);
            pstmt.setInt(2, softwareId);
            
            pstmt.executeUpdate();
            pstmt.close();
        }
    }
    
    /**
     * Guarda un pedido en la base de datos.
     * 
     * @param pedido El pedido a guardar.
     * @param sucursalNombre El nombre de la sucursal que creó el pedido.
     * @return El ID del pedido guardado.
     */
    public static int guardarPedido(observer.Pedido pedido, String sucursalNombre) {
        int pedidoId = -1;
        
        try {
            // Primero guardar la PC del pedido
            int pcId = guardarPC(pedido.getPc(), "PC de " + pedido.getCliente(), "Pedido");
            
            // Luego guardar el pedido
            String sql = "INSERT INTO pedidos (cliente, pc_id, estado, sucursal) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, pedido.getCliente());
            pstmt.setInt(2, pcId);
            pstmt.setString(3, pedido.getEstado());
            pstmt.setString(4, sucursalNombre);
            
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                pedidoId = rs.getInt(1);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            TerminalUI.error("Error al guardar pedido: " + e.getMessage());
        }
        
        return pedidoId;
    }
    
    /**
     * Actualiza el estado de un pedido en la base de datos.
     * 
     * @param pedidoId El ID del pedido.
     * @param nuevoEstado El nuevo estado del pedido.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public static boolean actualizarEstadoPedido(int pedidoId, String nuevoEstado) {
        try {
            String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, pedidoId);
            
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            
            return filasAfectadas > 0;
        } catch (SQLException e) {
            TerminalUI.error("Error al actualizar estado del pedido: " + e.getMessage());
            return false;
        }
    }
}
