package pc;

import componentes.*;
import java.util.ArrayList;
import java.util.List;

public class PC {
    private CPU cpu;
    private RAM ram;
    private GPU gpu;
    private Almacenamiento almacenamiento;
    private FuenteDeAlimentacion fuenteDeAlimentacion;
    private Motherboard motherboard;
    private Gabinete gabinete;
    private List<Software> softwareInstalado;

    public PC(CPU cpu, RAM ram, GPU gpu, Almacenamiento almacenamiento,
              FuenteDeAlimentacion fuenteDeAlimentacion, Motherboard motherboard, Gabinete gabinete) {
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
        this.almacenamiento = almacenamiento;
        this.fuenteDeAlimentacion = fuenteDeAlimentacion;
        this.motherboard = motherboard;
        this.gabinete = gabinete;
        this.softwareInstalado = new ArrayList<>();
    }

    public void instalarSoftware(Software software) {
        if (!softwareInstalado.contains(software)) {
            softwareInstalado.add(software);
        }
    }

    public void mostrarConfiguracion() {
        System.out.println("Configuración de la PC:");
        System.out.println(cpu);
        System.out.println(ram);
        System.out.println(gpu);
        System.out.println(almacenamiento);
        System.out.println(fuenteDeAlimentacion);
        System.out.println(motherboard);
        System.out.println(gabinete);
        System.out.println("Software instalado:");
        for (Software s : softwareInstalado) {
            System.out.println(s);
        }
    }
}
