package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import pc.PC;
import sucursales.Sucursal;
import observer.Pedido;
import software.Software;
import factories.SoftwareFactory;
import db.DBManager;

/**
 * Panel para seleccionar y comprar equipos prearmados.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class PreArmadoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainGUI mainGUI;
    private Sucursal sucursal;
    private JList<String> pcList;
    private DefaultListModel<String> listModel;
    private JTextArea detailsArea;
    private List<PC> prearmados;
    
    /**
     * Constructor del panel de equipos prearmados.
     * 
     * @param mainGUI Referencia a la interfaz gráfica principal.
     * @param sucursal La sucursal actual.
     */
    public PreArmadoPanel(MainGUI mainGUI, Sucursal sucursal) {
        this.mainGUI = mainGUI;
        this.sucursal = sucursal;
        this.prearmados = sucursal.getConfiguracionesPrearmadas();
        
        // Configurar panel
        setLayout(new BorderLayout());
        
        // Título
        JLabel titleLabel = new JLabel("Equipos Prearmados", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel principal con dos secciones
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        
        // Panel izquierdo: Lista de PCs
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        listModel = new DefaultListModel<>();
        for (int i = 0; i < prearmados.size(); i++) {
            String tipo = "";
            if (i == 0) tipo = "Básica Intel";
            else if (i == 1) tipo = "Intermedia Intel";
            else if (i == 2) tipo = "Avanzada Intel";
            else if (i == 3) tipo = "Básica AMD";
            
            listModel.addElement("PC Gamer " + tipo + " - $" + String.format("%.2f", prearmados.get(i).getPrecioTotal()));
        }
        
        pcList = new JList<>(listModel);
        pcList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pcList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails();
            }
        });
        
        JScrollPane listScroller = new JScrollPane(pcList);
        leftPanel.add(new JLabel("Selecciona un equipo:"), BorderLayout.NORTH);
        leftPanel.add(listScroller, BorderLayout.CENTER);
        
        // Panel derecho: Detalles
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane detailsScroller = new JScrollPane(detailsArea);
        
        rightPanel.add(new JLabel("Detalles del equipo:"), BorderLayout.NORTH);
        rightPanel.add(detailsScroller, BorderLayout.CENTER);
        
        // Botones
        JPanel buttonPanel = new JPanel();
        
        JButton buyButton = new JButton("Comprar");
        buyButton.addActionListener(e -> comprarPC());
        
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainGUI.showPanel("menu"));
        
        buttonPanel.add(buyButton);
        buttonPanel.add(backButton);
        
        // Agregar todo al panel
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Seleccionar el primer item por defecto
        if (pcList.getModel().getSize() > 0) {
            pcList.setSelectedIndex(0);
        }
    }
    
    /**
     * Actualiza el área de detalles con la información de la PC seleccionada.
     */
    private void updateDetails() {
        int index = pcList.getSelectedIndex();
        if (index >= 0 && index < prearmados.size()) {
            PC pc = prearmados.get(index);
            detailsArea.setText(pc.toString());
        } else {
            detailsArea.setText("");
        }
    }
    
    /**
     * Maneja la compra de una PC prearmada.
     */
    private void comprarPC() {
        int index = pcList.getSelectedIndex();
        if (index >= 0 && index < prearmados.size()) {
            PC pc = prearmados.get(index);
            
            // Pedir nombre del cliente
            String nombreCliente = JOptionPane.showInputDialog(this, 
                                                             "Ingresa tu nombre:", 
                                                             "Datos del Cliente", 
                                                             JOptionPane.QUESTION_MESSAGE);
            
            if (nombreCliente != null && !nombreCliente.trim().isEmpty()) {
                // Preguntar por software adicional
                int respuesta = JOptionPane.showConfirmDialog(this, 
                                                           "¿Deseas agregar software adicional?", 
                                                           "Software Adicional", 
                                                           JOptionPane.YES_NO_OPTION);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    agregarSoftware(pc);
                }
                
                // Crear pedido
                Pedido pedido = sucursal.crearPedido(nombreCliente, pc);
                
                // Guardar en la base de datos
                if (DBManager.guardarPedido(pedido, sucursal.getNombre()) > 0) {
                    JOptionPane.showMessageDialog(this, 
                                               "Pedido guardado en la base de datos.", 
                                               "Éxito", 
                                               JOptionPane.INFORMATION_MESSAGE);
                }
                
                // Mostrar ticket
                JOptionPane.showMessageDialog(this, 
                                           "¡Gracias por tu compra, " + nombreCliente + "!\n" + 
                                           "Número de pedido: " + pedido.getId() + "\n" + 
                                           "Total: $" + String.format("%.2f", pc.getPrecioTotal()) + "\n\n" + 
                                           "Tu PC será ensamblada y enviada desde nuestra sucursal central.", 
                                           "Compra Exitosa", 
                                           JOptionPane.INFORMATION_MESSAGE);
                
                // Volver al menú principal
                mainGUI.showPanel("menu");
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                                       "Por favor, selecciona un equipo primero.", 
                                       "Selección Requerida", 
                                       JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Muestra diálogos para agregar software a la PC.
     * 
     * @param pc La PC a la que se agregará software.
     */
    private void agregarSoftware(PC pc) {
        // Windows
        int respuestaWindows = JOptionPane.showConfirmDialog(this, 
                                                         "¿Deseas instalar Windows 11?", 
                                                         "Windows 11", 
                                                         JOptionPane.YES_NO_OPTION);
        
        if (respuestaWindows == JOptionPane.YES_OPTION) {
            Software windows = SoftwareFactory.crearWindows();
            pc.instalarSoftware(windows);
        }
        
        // Office
        int respuestaOffice = JOptionPane.showConfirmDialog(this, 
                                                        "¿Deseas instalar Microsoft Office 365?", 
                                                        "Microsoft Office 365", 
                                                        JOptionPane.YES_NO_OPTION);
        
        if (respuestaOffice == JOptionPane.YES_OPTION) {
            Software office = SoftwareFactory.crearOffice();
            pc.instalarSoftware(office);
        }
        
        // Photoshop
        int respuestaPhotoshop = JOptionPane.showConfirmDialog(this, 
                                                           "¿Deseas instalar Adobe Photoshop?", 
                                                           "Adobe Photoshop", 
                                                           JOptionPane.YES_NO_OPTION);
        
        if (respuestaPhotoshop == JOptionPane.YES_OPTION) {
            Software photoshop = SoftwareFactory.crearPhotoshop();
            pc.instalarSoftware(photoshop);
        }
        
        // AutoCAD
        int respuestaAutoCad = JOptionPane.showConfirmDialog(this, 
                                                          "¿Deseas instalar AutoCAD?", 
                                                          "AutoCAD", 
                                                          JOptionPane.YES_NO_OPTION);
        
        if (respuestaAutoCad == JOptionPane.YES_OPTION) {
            Software autocad = SoftwareFactory.crearAutoCad();
            pc.instalarSoftware(autocad);
        }
        
        // Terminal
        int respuestaTerminal = JOptionPane.showConfirmDialog(this, 
                                                           "¿Deseas instalar una terminal con WSL?", 
                                                           "Terminal WSL", 
                                                           JOptionPane.YES_NO_OPTION);
        
        if (respuestaTerminal == JOptionPane.YES_OPTION) {
            Software terminal = SoftwareFactory.crearTerminalWSL();
            pc.instalarSoftware(terminal);
        }
        
        // Actualizar detalles
        updateDetails();
    }
}
