package PC;

import software.Software;
import java.util.List;

public class PCConSoftware extends PC {
    private List<Software> softwarePreinstalado;

    public PCConSoftware(CPU cpu, RAM ram, GPU gpu, Almacenamiento almacenamiento,
                         FuenteDeAlimentacion fuenteDeAlimentacion, Motherboard motherboard, Gabinete gabinete,
                         List<Software> softwarePreinstalado) {
        super(cpu, ram, gpu, almacenamiento, fuenteDeAlimentacion, motherboard, gabinete);
        this.softwarePreinstalado = softwarePreinstalado;
    }

    public void mostrarSoftware() {
        System.out.println("Software preinstalado en la PC:");
        for (Software software : softwarePreinstalado) {
            System.out.println(software);
        }
    }
}
