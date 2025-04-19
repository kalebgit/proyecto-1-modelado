package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import sucursales.*;

/**
 * Panel para seleccionar una sucursal.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class SucursalSelectionPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainGUI mainGUI;
    private List<Sucursal> sucursales;
    private JComboBox<String> sucursalCombo;
    
    /**
     * Constructor del panel de selección de sucursal.
     * 
     * @param mainGUI Referencia a la interfaz gráfica principal.
     */
    public SucursalSelectionPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.sucursales = new ArrayList<>();
        
        // Inicializar sucursales
        inicializarSucursales();
        
        // Configurar panel
        setLayout(new BorderLayout());
        
        // Crear panel de bienvenida
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("Bienvenido a MonosChinos MX", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(titleLabel, BorderLayout.NORTH);
        
        // Logo (se podría agregar una imagen aquí)
        JLabel logoLabel = new JLabel("El mejor lugar para armar tu PC Gamer", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        welcomePanel.add(logoLabel, BorderLayout.CENTER);
        
        // Panel de selección
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel selectLabel = new JLabel("Selecciona una sucursal:");
        
        // Preparar ComboBox
        String[] sucursalNames = new String[sucursales.size()];
        for (int i = 0; i < sucursales.size(); i++) {
            sucursalNames[i] = sucursales.get(i).getNombre() + " (" + sucursales.get(i).getUbicacion() + ")";
        }
        sucursalCombo = new JComboBox<>(sucursalNames);
        
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = sucursalCombo.getSelectedIndex();
                if (index >= 0) {
                    mainGUI.setSucursal(sucursales.get(index));
                }
            }
        });
        
        // Agregar componentes al panel de selección
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        selectionPanel.add(selectLabel, gbc);
        
        gbc.gridy = 1;
        selectionPanel.add(sucursalCombo, gbc);
        
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        selectionPanel.add(confirmButton, gbc);
        
        welcomePanel.add(selectionPanel, BorderLayout.SOUTH);
        
        // Agregar panel de bienvenida
        add(welcomePanel, BorderLayout.CENTER);
    }
    
    /**
     * Inicializa las sucursales disponibles.
     */
    private void inicializarSucursales() {
        sucursales.add(new Sucursal("MonosChinos CDMX", "Ciudad de México"));
        sucursales.add(new Sucursal("MonosChinos Chihuahua", "Chihuahua"));
        sucursales.add(new Sucursal("MonosChinos Jalisco", "Guadalajara"));
        sucursales.add(new Sucursal("MonosChinos Yucatán", "Mérida"));
    }
}
