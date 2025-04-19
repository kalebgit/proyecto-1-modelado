package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel que muestra el menú principal de la aplicación.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class MainMenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainGUI mainGUI;
    
    /**
     * Constructor del panel de menú principal.
     * 
     * @param mainGUI Referencia a la interfaz gráfica principal.
     */
    public MainMenuPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        
        // Configurar panel
        setLayout(new BorderLayout());
        
        // Título
        JLabel titleLabel = new JLabel("Menú Principal", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        
        // Botones
        JButton preArmadoButton = createMenuButton("Comprar Equipo Prearmado", 
                                                 "Selecciona entre nuestras configuraciones predefinidas");
        preArmadoButton.addActionListener(e -> mainGUI.showPanel("prearmado"));
        
        JButton personalizadoButton = createMenuButton("Armar Equipo Personalizado", 
                                                     "Crea tu propia configuración seleccionando componentes");
        personalizadoButton.addActionListener(e -> mainGUI.showPanel("personalizado"));
        
        JButton catalogoButton = createMenuButton("Ver Catálogo de Componentes", 
                                               "Explora nuestro inventario de componentes");
        catalogoButton.addActionListener(e -> mainGUI.showPanel("catalogo"));
        
        JButton cambiarSucursalButton = createMenuButton("Cambiar de Sucursal", 
                                                      "Selecciona otra sucursal");
        cambiarSucursalButton.addActionListener(e -> mainGUI.showPanel("sucursal"));
        
        // Agregar botones al panel
        buttonPanel.add(preArmadoButton);
        buttonPanel.add(personalizadoButton);
        buttonPanel.add(catalogoButton);
        buttonPanel.add(cambiarSucursalButton);
        
        // Agregar panel de botones
        add(buttonPanel, BorderLayout.CENTER);
        
        // Añadir información de la sucursal
        JLabel sucursalLabel = new JLabel("Sucursal: " + mainGUI.getSucursalActual().getNombre() + 
                                         " - " + mainGUI.getSucursalActual().getUbicacion(), JLabel.CENTER);
        sucursalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(sucursalLabel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea un botón de menú con título y descripción.
     * 
     * @param title El título del botón.
     * @param description La descripción del botón.
     * @return El botón creado.
     */
    private JButton createMenuButton(String title, String description) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel descLabel = new JLabel(description, JLabel.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        button.add(titleLabel, BorderLayout.CENTER);
        button.add(descLabel, BorderLayout.SOUTH);
        
        button.setPreferredSize(new Dimension(300, 80));
        return button;
    }
}
