package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import componentes.*;
import sucursales.Sucursal;

/**
 * Panel para mostrar el catálogo de componentes disponibles.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class CatalogoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainGUI mainGUI;
    private Sucursal sucursal;
    private JComboBox<String> componentTypeCombo;
    private JList<String> componentList;
    private DefaultListModel<String> listModel;
    private JTextArea detailsArea;
    
    /**
     * Constructor del panel de catálogo.
     * 
     * @param mainGUI Referencia a la interfaz gráfica principal.
     * @param sucursal La sucursal actual.
     */
    public CatalogoPanel(MainGUI mainGUI, Sucursal sucursal) {
        this.mainGUI = mainGUI;
        this.sucursal = sucursal;
        
        // Configurar panel
        setLayout(new BorderLayout());
        
        // Título
        JLabel titleLabel = new JLabel("Catálogo de Componentes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel de selección de tipo
        JPanel typePanel = new JPanel();
        typePanel.add(new JLabel("Tipo de componente:"));
        
        String[] componentTypes = {"CPU", "RAM", "Motherboard", "Almacenamiento", "GPU", "Fuente de Alimentacion", "Gabinete"};
        componentTypeCombo = new JComboBox<>(componentTypes);
        componentTypeCombo.addActionListener(e -> updateComponentList());
        typePanel.add(componentTypeCombo);
        
        // Panel principal con lista y detalles
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        
        // Panel izquierdo: Lista de componentes
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        listModel = new DefaultListModel<>();
        componentList = new JList<>(listModel);
        componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        componentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails();
            }
        });
        
        JScrollPane listScroller = new JScrollPane(componentList);
        leftPanel.add(new JLabel("Componentes disponibles:"), BorderLayout.NORTH);
        leftPanel.add(listScroller, BorderLayout.CENTER);
        
        // Panel derecho: Detalles
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane detailsScroller = new JScrollPane(detailsArea);
        
        rightPanel.add(new JLabel("Detalles del componente:"), BorderLayout.NORTH);
        rightPanel.add(detailsScroller, BorderLayout.CENTER);
        
        // Botón de volver
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainGUI.showPanel("menu"));
        buttonPanel.add(backButton);
        
        // Añadir todo al panel
        add(typePanel, BorderLayout.NORTH);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Cargar componentes iniciales
        updateComponentList();
    }
    
    /**
     * Actualiza la lista de componentes según el tipo seleccionado.
     */
    private void updateComponentList() {
        String selectedType = (String) componentTypeCombo.getSelectedItem();
        List<Componente> componentes = sucursal.getComponentesPorTipo(selectedType);
        
        listModel.clear();
        
        if (componentes.isEmpty()) {
            listModel.addElement("No hay componentes disponibles de este tipo");
        } else {
            for (Componente componente : componentes) {
                listModel.addElement(componente.getNombre() + " - " + componente.getMarca() + " - $" + componente.getPrecio());
            }
        }
        
        // Seleccionar el primer item por defecto
        if (listModel.getSize() > 0) {
            componentList.setSelectedIndex(0);
        }
    }
    
    /**
     * Actualiza el área de detalles con la información del componente seleccionado.
     */
    private void updateDetails() {
        int index = componentList.getSelectedIndex();
        if (index >= 0) {
            String selectedType = (String) componentTypeCombo.getSelectedItem();
            List<Componente> componentes = sucursal.getComponentesPorTipo(selectedType);
            
            if (!componentes.isEmpty() && index < componentes.size()) {
                Componente componente = componentes.get(index);
                detailsArea.setText(getDetallesComponente(componente));
            } else {
                detailsArea.setText("");
            }
        } else {
            detailsArea.setText("");
        }
    }
    
    /**
     * Obtiene los detalles de un componente en formato de texto.
     * 
     * @param componente El componente a describir.
     * @return String con los detalles del componente.
     */
    private String getDetallesComponente(Componente componente) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(componente.getNombre()).append("\n");
        sb.append("Marca: ").append(componente.getMarca()).append("\n");
        sb.append("Precio: $").append(String.format("%.2f", componente.getPrecio())).append("\n");
        sb.append("Tipo: ").append(componente.getTipoComponente()).append("\n\n");
        
        // Agregar atributos específicos según el tipo
        if (componente instanceof CPU) {
            CPU cpu = (CPU) componente;
            sb.append("Núcleos: ").append(cpu.getCantidadNucleos()).append("\n");
            sb.append("Socket: ").append(cpu.getSocket()).append("\n");
        } else if (componente instanceof RAM) {
            RAM ram = (RAM) componente;
            sb.append("Capacidad: ").append(ram.getCapacidad()).append(" GB\n");
            sb.append("Tipo: ").append(ram.getTipo()).append("\n");
        } else if (componente instanceof Motherboard) {
            Motherboard mb = (Motherboard) componente;
            sb.append("Chipset: ").append(mb.getChipset()).append("\n");
            sb.append("Socket: ").append(mb.getSocket()).append("\n");
            sb.append("Tipo de Memoria: ").append(mb.getTipoMemoria()).append("\n");
            sb.append("Ranuras PCIe: ").append(mb.getRanurasPCIe()).append("\n");
        } else if (componente instanceof Almacenamiento) {
            Almacenamiento alm = (Almacenamiento) componente;
            sb.append("Capacidad: ").append(alm.getCapacidadAlmacenamiento()).append(" GB\n");
            sb.append("Tipo: ").append(alm.getTipoAlmacenamiento()).append("\n");
            sb.append("Interfaz: ").append(alm.getInterfaz()).append("\n");
        } else if (componente instanceof GPU) {
            GPU gpu = (GPU) componente;
            sb.append("Tipo de Memoria: ").append(gpu.getTipoMemoriaGPU()).append("\n");
            sb.append("Potencia Requerida: ").append(gpu.getPotenciaRequerida()).append(" W\n");
        } else if (componente instanceof FuenteDeAlimentacion) {
            FuenteDeAlimentacion fuente = (FuenteDeAlimentacion) componente;
            sb.append("Potencia Máxima: ").append(fuente.getPotenciaMaxima()).append(" W\n");
            sb.append("Certificación: ").append(fuente.getCertificacion()).append("\n");
        } else if (componente instanceof Gabinete) {
            Gabinete gabinete = (Gabinete) componente;
            sb.append("Factor de Forma: ").append(gabinete.getFactorForma()).append("\n");
            sb.append("Color: ").append(gabinete.getColor()).append("\n");
            sb.append("RGB: ").append(gabinete.tieneRGB() ? "Sí" : "No").append("\n");
        }
        
        return sb.toString();
    }
}
