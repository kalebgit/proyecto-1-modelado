package PC;

public class PCBuilder {
    private CPU cpu;
    private RAM ram;
    private GPU gpu;
    private Almacenamiento almacenamiento;
    private FuenteDeAlimentacion fuenteDeAlimentacion;
    private Motherboard motherboard;
    private Gabinete gabinete;

    public PCBuilder setCPU(CPU cpu) {
        this.cpu = cpu;
        return this;
    }

    public PCBuilder setRAM(RAM ram) {
        this.ram = ram;
        return this;
    }

    public PCBuilder setGPU(GPU gpu) {
        this.gpu = gpu;
        return this;
    }

    public PCBuilder setAlmacenamiento(Almacenamiento almacenamiento) {
        this.almacenamiento = almacenamiento;
        return this;
    }

    public PCBuilder setFuenteDeAlimentacion(FuenteDeAlimentacion fuenteDeAlimentacion) {
        this.fuenteDeAlimentacion = fuenteDeAlimentacion;
        return this;
    }

    public PCBuilder setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
        return this;
    }

    public PCBuilder setGabinete(Gabinete gabinete) {
        this.gabinete = gabinete;
        return this;
    }

    public PC build() {
        return new PC(cpu, ram, gpu, almacenamiento, fuenteDeAlimentacion, motherboard, gabinete);
    }
}