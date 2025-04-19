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
    
    // Constructor y métodos previos se mantienen igual...
    
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
