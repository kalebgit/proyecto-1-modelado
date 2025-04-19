package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import componentes.*;
import sucursales.*;
import pc.PC;
import db.DBManager;

/**
 * Clase que implementa la interfaz gráfica principal del sistema.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class MainGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Sucursal sucursalActual;
    private JLabel statusLabel;
    
    // Panels
    private SucursalSelectionPanel sucursalPanel;
    private MainMenuPanel menuPanel;
    private PreArmadoPanel preArmadoPanel;
    private PersonalizadoPanel personalizadoPanel;
    private CatalogoPanel catalogoPanel;
    
    /**
     * Constructor de la interfaz gráfica principal.
     */
    public MainGUI() {
        setTitle("MonosChinos MX - Sistema de Ventas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar base de datos
        if (DBManager.connect()) {
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se pudo conectar a la base de datos. El sistema funcionará sin persistencia.",
                "Error de Conexión", JOptionPane.WARNING_MESSAGE);
        }
        
        // Crear layout y panel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Crear barra de estado
        statusLabel = new JLabel("Bienvenido a MonosChinos MX");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Crear panels
        createPanels();
        
        // Añadir panels al panel principal
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
        
        // Mostrar panel de selección de sucursal
        cardLayout.show(mainPanel, "sucursal");
        
        // Registrar evento de cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBManager.disconnect();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        });
    }
    
    /**
     * Crea los diferentes paneles de la interfaz gráfica.
     */
    private void createPanels() {
        // Panel de selección de sucursal
        sucursalPanel = new SucursalSelectionPanel(this);
        mainPanel.add(sucursalPanel, "sucursal");
        
        // Los demás paneles se crearán después de seleccionar una sucursal
    }
    
    /**
     * Establece la sucursal actual y crea los paneles relacionados.
     * 
     * @param sucursal La sucursal seleccionada.
     */
    public void setSucursal(Sucursal sucursal) {
        this.sucursalActual = sucursal;
        statusLabel.setText("Sucursal: " + sucursal.getNombre() + " - " + sucursal.getUbicacion());
        
        // Crear los paneles que dependen de la sucursal
        menuPanel = new MainMenuPanel(this);
        mainPanel.add(menuPanel, "menu");
        
        preArmadoPanel = new PreArmadoPanel(this, sucursalActual);
        mainPanel.add(preArmadoPanel, "prearmado");
        
        personalizadoPanel = new PersonalizadoPanel(this, sucursalActual);
        mainPanel.add(personalizadoPanel, "personalizado");
        
        catalogoPanel = new CatalogoPanel(this, sucursalActual);
        mainPanel.add(catalogoPanel, "catalogo");
        
        // Mostrar el menú principal
        cardLayout.show(mainPanel, "menu");
    }
    
    /**
     * Cambia el panel visible.
     * 
     * @param panelName El nombre del panel a mostrar.
     */
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    /**
     * Obtiene la sucursal actual.
     * 
     * @return La sucursal actual.
     */
    public Sucursal getSucursalActual() {
        return sucursalActual;
    }
    
    /**
     * Actualiza la etiqueta de estado.
     * 
     * @param message El mensaje a mostrar.
     */
    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    /**
     * Método principal que inicia la aplicación gráfica.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Establecer Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}
