package factories;

import componentes.CPU;
import componentes.Componente;

/**
 * Fábrica concreta para crear procesadores (CPUs).
 * Implementa la interfaz ComponenteFactory.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class CPUFactory implements ComponenteFactory {
    private String nombre;
    private double precio;
    private String marca;
    private int cantidadNucleos;
    private String socket;
    
    /**
     * Constructor de la fábrica de CPUs.
     * 
     * @param nombre El nombre del CPU.
     * @param precio El precio del CPU.
     * @param marca La marca del CPU.
     * @param cantidadNucleos La cantidad de núcleos del procesador.
     * @param socket El tipo de socket del procesador.
     */
    public CPUFactory(String nombre, double precio, String marca, int cantidadNucleos, String socket) {
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.cantidadNucleos = cantidadNucleos;
        this.socket = socket;
    }
    
    /**
     * Crea un nuevo procesador (CPU) con los atributos especificados.
     * 
     * @return Un nuevo objeto CPU.
     */
    @Override
    public Componente crearComponente() {
        return new CPU(nombre, precio, marca, cantidadNucleos, socket);
    }
}
