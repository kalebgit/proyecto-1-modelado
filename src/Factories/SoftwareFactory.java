package factories;

import software.Software;

/**
 * Fábrica para crear objetos Software.
 * Implementa el patrón Factory Method.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class SoftwareFactory {
    
    /**
     * Crea un software Windows.
     * 
     * @return Un objeto Software que representa Windows.
     */
    public static Software crearWindows() {
        return new Software("Windows", "11", 1500.0);
    }
    
    /**
     * Crea un software Office.
     * 
     * @return Un objeto Software que representa Office.
     */
    public static Software crearOffice() {
        return new Software("Microsoft Office", "365", 1200.0);
    }
    
    /**
     * Crea un software Photoshop.
     * 
     * @return Un objeto Software que representa Photoshop.
     */
    public static Software crearPhotoshop() {
        return new Software("Adobe Photoshop", "2024", 1800.0);
    }
    
    /**
     * Crea un software AutoCAD.
     * 
     * @return Un objeto Software que representa AutoCAD.
     */
    public static Software crearAutoCad() {
        return new Software("AutoCAD", "2024", 2500.0);
    }
    
    /**
     * Crea un software Terminal con WSL.
     * 
     * @return Un objeto Software que representa una terminal con WSL.
     */
    public static Software crearTerminalWSL() {
        return new Software("Terminal con WSL", "2.0", 0.0);
    }
    
    /**
     * Crea un software según el tipo especificado.
     * 
     * @param tipo El tipo de software a crear.
     * @return Un objeto Software del tipo especificado.
     * @throws IllegalArgumentException Si el tipo especificado no es válido.
     */
    public static Software crearSoftware(String tipo) {
        switch (tipo.toLowerCase()) {
            case "windows":
                return crearWindows();
            case "office":
                return crearOffice();
            case "photoshop":
                return crearPhotoshop();
            case "autocad":
                return crearAutoCad();
            case "terminal":
                return crearTerminalWSL();
            default:
                throw new IllegalArgumentException("Tipo de software no válido: " + tipo);
        }
    }
}
