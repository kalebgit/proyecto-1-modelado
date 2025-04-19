package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import componentes.*;
import pc.PC;
import sucursales.Sucursal;
import adaptadores.CPUAdapter;
import observer.Pedido;
import software.Software;
import factories.SoftwareFactory;
import db.DBManager;

/**
 * Panel para armar equipos personalizados.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class PersonalizadoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainGUI mainGUI;
    private Sucursal sucursal;
    
    // Componentes seleccionados
    private CPU cpu;
    private Motherboard motherboard;
    private RAM ram1;
    private RAM ram2;
    private Almacenamiento almacenamiento1;
    private Almacenamiento almacenamiento2;
    private GPU gpu;
    private FuenteDeAlimentacion fuente;
    private Gabinete gabinete;
    
    // Componentes de la interfaz
    private JButton selectCPUButton;
    private JLabel cpuLabel;
    private JButton selectMotherboardButton;
    private JLabel motherboardLabel;
    private JButton selectRAM1Button;
    private JLabel ram1Label;
    private JButton selectRAM2Button;
    private JLabel ram2Label;
    private JButton selectAlmacenamiento1Button;
    private JLabel almacenamiento1Label;
    private JButton selectAlmacenamiento2Button;
    private JLabel almacenamiento2Label;
    private JButton selectGPUButton;
    private JLabel gpuLabel;
    private JButton selectFuenteButton;
    private JLabel fuenteLabel;
    private JButton selectGabineteButton;
    private JLabel gabineteLabel;
    
    private JButton buildButton;
    private JButton backButton;
    
    /**
     * Constructor del panel de equipos personalizados.
     * 
     * @param mainGUI Referencia a la interfaz gráfica principal.
     * @param sucursal La sucursal actual.
     */
    public PersonalizadoPanel(MainGUI mainGUI, Sucursal sucursal) {
        this.mainGUI = mainGUI;
        this.sucursal = sucursal;
        
        // Configurar panel
        setLayout(new BorderLayout());
        
        // Título
        JLabel titleLabel = new JLabel("Armar Equipo Personalizado", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel principal con selector de componentes
        JPanel mainPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // CPU
        selectCPUButton = new JButton("Seleccionar CPU");
        selectCPUButton.addActionListener(e -> selectCPU());
        cpuLabel = new JLabel("No seleccionado");
        mainPanel.add(selectCPUButton);
        mainPanel.add(cpuLabel);
        
        // Motherboard
        selectMotherboardButton = new JButton("Seleccionar Motherboard");
        selectMotherboardButton.addActionListener(e -> selectMotherboard());
        motherboardLabel = new JLabel("No seleccionado");
        mainPanel.add(selectMotherboardButton);
        mainPanel.add(motherboardLabel);
        
        // RAM 1
        selectRAM1Button = new JButton("Seleccionar RAM Principal");
        selectRAM1Button.addActionListener(e -> selectRAM1());
        ram1Label = new JLabel("No seleccionado");
        mainPanel.add(selectRAM1Button);
        mainPanel.add(ram1Label);
        
        // RAM 2
        selectRAM2Button = new JButton("Seleccionar RAM Adicional (Opcional)");
        selectRAM2Button.addActionListener(e -> selectRAM2());
        ram2Label = new JLabel("No seleccionado");
        mainPanel.add(selectRAM2Button);
        mainPanel.add(ram2Label);
        
        // Almacenamiento 1
        selectAlmacenamiento1Button = new JButton("Seleccionar Almacenamiento Principal");
        selectAlmacenamiento1Button.addActionListener(e -> selectAlmacenamiento1());
        almacenamiento1Label = new JLabel("No seleccionado");
        mainPanel.add(selectAlmacenamiento1Button);
        mainPanel.add(almacenamiento1Label);
        
        // Almacenamiento 2
        selectAlmacenamiento2Button = new JButton("Seleccionar Almacenamiento Adicional (Opcional)");
        selectAlmacenamiento2Button.addActionListener(e -> selectAlmacenamiento2());
        almacenamiento2Label = new JLabel("No seleccionado");
        mainPanel.add(selectAlmacenamiento2Button);
        mainPanel.add(almacenamiento2Label);
        
        // GPU
        selectGPUButton = new JButton("Seleccionar Tarjeta Gráfica");
        selectGPUButton.addActionListener(e -> selectGPU());
        gpuLabel = new JLabel("No seleccionado");
        mainPanel.add(selectGPUButton);
        mainPanel.add(gpuLabel);
        
        // Fuente de Alimentación
        selectFuenteButton = new JButton("Seleccionar Fuente de Alimentación");
        selectFuenteButton.addActionListener(e -> selectFuente());
        fuenteLabel = new JLabel("No seleccionado");
        mainPanel.add(selectFuenteButton);
        mainPanel.add(fuenteLabel);
        
        // Gabinete
        selectGabineteButton = new JButton("Seleccionar Gabinete");
        selectGabineteButton.addActionListener(e -> selectGabinete());
        gabineteLabel = new JLabel("No seleccionado");
        mainPanel.add(selectGabineteButton);
        mainPanel.add(gabineteLabel);
        
        // Panel para botones principales
        JPanel buttonPanel = new JPanel();
        
        buildButton = new JButton("Armar PC");
        buildButton.addActionListener(e -> buildPC());
        buildButton.setEnabled(false); // Deshabilitado hasta que se seleccionen los componentes obligatorios
        
        backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainGUI.showPanel("menu"));
        
        buttonPanel.add(buildButton);
        buttonPanel.add(backButton);
        
        // Añadir todo al panel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Selecciona un CPU del inventario.
     */
    private void selectCPU() {
        List<Componente> cpus = sucursal.getComponentesPorTipo("CPU");
        if (cpus.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay CPUs disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[cpus.size()];
        for (int i = 0; i < cpus.size(); i++) {
            options[i] = cpus.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona un CPU:", 
                                                            "Selección de CPU", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : cpus) {
                if (comp.toString().equals(selected)) {
                    cpu = (CPU) comp;
                    cpuLabel.setText(cpu.getNombre() + " - $" + cpu.getPrecio());
                    checkCompatibilidad();
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Selecciona una motherboard del inventario.
     */
    private void selectMotherboard() {
        List<Componente> motherboards = sucursal.getComponentesPorTipo("Motherboard");
        if (motherboards.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay placas base disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[motherboards.size()];
        for (int i = 0; i < motherboards.size(); i++) {
            options[i] = motherboards.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona una placa base:", 
                                                            "Selección de Motherboard", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : motherboards) {
                if (comp.toString().equals(selected)) {
                    motherboard = (Motherboard) comp;
                    motherboardLabel.setText(motherboard.getNombre() + " - $" + motherboard.getPrecio());
                    checkCompatibilidad();
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Selecciona una memoria RAM principal del inventario.
     */
    private void selectRAM1() {
        List<Componente> rams = sucursal.getComponentesPorTipo("RAM");
        if (rams.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay memorias RAM disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[rams.size()];
        for (int i = 0; i < rams.size(); i++) {
            options[i] = rams.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona una memoria RAM:", 
                                                            "Selección de RAM Principal", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : rams) {
                if (comp.toString().equals(selected)) {
                    ram1 = (RAM) comp;
                    ram1Label.setText(ram1.getNombre() + " - $" + ram1.getPrecio());
                    checkCompatibilidad();
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Selecciona una memoria RAM adicional del inventario.
     */
    private void selectRAM2() {
        List<Componente> rams = sucursal.getComponentesPorTipo("RAM");
        if (rams.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay memorias RAM disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[rams.size() + 1];
        options[0] = "Ninguna";
        for (int i = 0; i < rams.size(); i++) {
            options[i + 1] = rams.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona una memoria RAM adicional:", 
                                                            "Selección de RAM Adicional", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            if (selected.equals("Ninguna")) {
                ram2 = null;
                ram2Label.setText("No seleccionado");
            } else {
                for (Componente comp : rams) {
                    if (comp.toString().equals(selected)) {
                        ram2 = (RAM) comp;
                        ram2Label.setText(ram2.getNombre() + " - $" + ram2.getPrecio());
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Selecciona un dispositivo de almacenamiento principal del inventario.
     */
    private void selectAlmacenamiento1() {
        List<Componente> almacenamientos = sucursal.getComponentesPorTipo("Almacenamiento");
        if (almacenamientos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay dispositivos de almacenamiento disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[almacenamientos.size()];
        for (int i = 0; i < almacenamientos.size(); i++) {
            options[i] = almacenamientos.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona un dispositivo de almacenamiento:", 
                                                            "Selección de Almacenamiento Principal", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : almacenamientos) {
                if (comp.toString().equals(selected)) {
                    almacenamiento1 = (Almacenamiento) comp;
                    almacenamiento1Label.setText(almacenamiento1.getNombre() + " - $" + almacenamiento1.getPrecio());
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Selecciona un dispositivo de almacenamiento adicional del inventario.
     */
    private void selectAlmacenamiento2() {
        List<Componente> almacenamientos = sucursal.getComponentesPorTipo("Almacenamiento");
        if (almacenamientos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay dispositivos de almacenamiento disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[almacenamientos.size() + 1];
        options[0] = "Ninguno";
        for (int i = 0; i < almacenamientos.size(); i++) {
            options[i + 1] = almacenamientos.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona un dispositivo de almacenamiento adicional:", 
                                                            "Selección de Almacenamiento Adicional", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            if (selected.equals("Ninguno")) {
                almacenamiento2 = null;
                almacenamiento2Label.setText("No seleccionado");
            } else {
                for (Componente comp : almacenamientos) {
                    if (comp.toString().equals(selected)) {
                        almacenamiento2 = (Almacenamiento) comp;
                        almacenamiento2Label.setText(almacenamiento2.getNombre() + " - $" + almacenamiento2.getPrecio());
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Selecciona una tarjeta gráfica del inventario.
     */
    private void selectGPU() {
        List<Componente> gpus = sucursal.getComponentesPorTipo("GPU");
        if (gpus.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay tarjetas gráficas disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[gpus.size()];
        for (int i = 0; i < gpus.size(); i++) {
            options[i] = gpus.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona una tarjeta gráfica:", 
                                                            "Selección de GPU", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : gpus) {
                if (comp.toString().equals(selected)) {
                    gpu = (GPU) comp;
                    gpuLabel.setText(gpu.getNombre() + " - $" + gpu.getPrecio());
                    checkCompatibilidad();
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Selecciona una fuente de alimentación del inventario.
     */
    private void selectFuente() {
        List<Componente> fuentes = sucursal.getComponentesPorTipo("Fuente de Alimentacion");
        if (fuentes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay fuentes de alimentación disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[fuentes.size()];
        for (int i = 0; i < fuentes.size(); i++) {
            options[i] = fuentes.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona una fuente de alimentación:", 
                                                            "Selección de Fuente", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : fuentes) {
                if (comp.toString().equals(selected)) {
                    fuente = (FuenteDeAlimentacion) comp;
                    fuenteLabel.setText(fuente.getNombre() + " - $" + fuente.getPrecio());
                    checkCompatibilidad();
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Selecciona un gabinete del inventario.
     */
    private void selectGabinete() {
        List<Componente> gabinetes = sucursal.getComponentesPorTipo("Gabinete");
        if (gabinetes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                       "No hay gabinetes disponibles.", 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = new String[gabinetes.size()];
        for (int i = 0; i < gabinetes.size(); i++) {
            options[i] = gabinetes.get(i).toString();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this, 
                                                            "Selecciona un gabinete:", 
                                                            "Selección de Gabinete", 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            options, 
                                                            options[0]);
        
        if (selected != null) {
            for (Componente comp : gabinetes) {
                if (comp.toString().equals(selected)) {
                    gabinete = (Gabinete) comp;
                    gabineteLabel.setText(gabinete.getNombre() + " - $" + gabinete.getPrecio());
                    updateBuildButton();
                    break;
                }
            }
        }
    }
    
    /**
     * Verifica la compatibilidad entre los componentes seleccionados.
     */
    private void checkCompatibilidad() {
        // Verificar CPU-Motherboard
        if (cpu != null && motherboard != null) {
            if (!cpu.esCompatibleCon(motherboard)) {
                int respuesta = JOptionPane.showConfirmDialog(this, 
                                                           "El CPU " + cpu.getNombre() + " no es compatible con la placa base " + motherboard.getNombre() + ".\n" +
                                                           "¿Deseas adaptar el CPU para hacerlo compatible?", 
                                                           "Incompatibilidad Detectada", 
                                                           JOptionPane.YES_NO_OPTION);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    CPU cpuAdaptado = (CPU) new CPUAdapter(cpu, motherboard.getSocket());
                    cpu = cpuAdaptado;
                    cpuLabel.setText(cpu.getNombre() + " - $" + cpu.getPrecio());
                    JOptionPane.showMessageDialog(this, 
                                               "CPU adaptado correctamente.", 
                                               "Adaptación Exitosa", 
                                               JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
        // Verificar GPU-Fuente
        if (gpu != null && fuente != null) {
            if (!gpu.esCompatibleCon(fuente)) {
                JOptionPane.showMessageDialog(this, 
                                           "La GPU " + gpu.getNombre() + " requiere más potencia que la proporcionada por la fuente " + fuente.getNombre() + ".\n" +
                                           "Por favor, selecciona una fuente de alimentación con mayor potencia.", 
                                           "Incompatibilidad Detectada", 
                                           JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    /**
     * Actualiza el estado del botón de armar PC según los componentes seleccionados.
     */
    private void updateBuildButton() {
        boolean allRequired = (cpu != null && motherboard != null && ram1 != null && 
                              almacenamiento1 != null && gpu != null && fuente != null && gabinete != null);
        
        buildButton.setEnabled(allRequired);
    }
    
    /**
     * Construye la PC con los componentes seleccionados.
     */
    private void buildPC() {
        try {
            // Usar el patrón Builder
            PC.Builder builder = PC.builder()
                .setCPU(cpu)
                .setMotherboard(motherboard)
                .addRAM(ram1)
                .setGPU(gpu)
                .addAlmacenamiento(almacenamiento1)
                .setFuenteDeAlimentacion(fuente)
                .setGabinete(gabinete);
            
            // Agregar componentes opcionales
            if (ram2 != null) {
                builder.addRAM(ram2);
            }
            
            if (almacenamiento2 != null) {
                builder.addAlmacenamiento(almacenamiento2);
            }
            
            // Construir la PC
            PC pc = builder.build();
            
            // Preguntar por software adicional
            int respuesta = JOptionPane.showConfirmDialog(this, 
                                                       "¿Deseas agregar software adicional?", 
                                                       "Software Adicional", 
                                                       JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                agregarSoftware(pc);
            }
            
            // Mostrar resumen
            JTextArea summaryArea = new JTextArea(pc.toString());
            summaryArea.setEditable(false);
            summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(summaryArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, 
                                       scrollPane, 
                                       "Resumen de tu PC", 
                                       JOptionPane.INFORMATION_MESSAGE);
            
            // Confirmar compra
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                                                          "¿Deseas comprar esta PC?\nPrecio total: $" + String.format("%.2f", pc.getPrecioTotal()), 
                                                          "Confirmar Compra", 
                                                          JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Pedir datos del cliente
                String nombreCliente = JOptionPane.showInputDialog(this, 
                                                                "Ingresa tu nombre:", 
                                                                "Datos del Cliente", 
                                                                JOptionPane.QUESTION_MESSAGE);
                
                if (nombreCliente != null && !nombreCliente.trim().isEmpty()) {
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
                    
                    // Reiniciar selecciones
                    resetSelections();
                    
                    // Volver al menú principal
                    mainGUI.showPanel("menu");
                }
            }
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, 
                                       "Error al construir la PC: " + e.getMessage(), 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Reinicia todas las selecciones de componentes.
     */
    private void resetSelections() {
        cpu = null;
        motherboard = null;
        ram1 = null;
        ram2 = null;
        almacenamiento1 = null;
        almacenamiento2 = null;
        gpu = null;
        fuente = null;
        gabinete = null;
        
        cpuLabel.setText("No seleccionado");
        motherboardLabel.setText("No seleccionado");
        ram1Label.setText("No seleccionado");
        ram2Label.setText("No seleccionado");
        almacenamiento1Label.setText("No seleccionado");
        almacenamiento2Label.setText("No seleccionado");
        gpuLabel.setText("No seleccionado");
        fuenteLabel.setText("No seleccionado");
        gabineteLabel.setText("No seleccionado");
        
        buildButton.setEnabled(false);
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
    }
}

