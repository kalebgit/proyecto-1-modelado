package observer;

import java.util.ArrayList;
import java.util.List;
import pc.PC;

/**
 * Clase que representa un pedido de PC.
 * Implementa el patrón Observer como sujeto (Subject).
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class Pedido implements Subject {
    private int id;
    private static int nextId = 1;
    private String cliente;
    private PC pc;
    private String estado;
    private List<Observer> observadores;
    
    /**
     * Constructor de la clase Pedido.
     * 
     * @param cliente El nombre del cliente.
     * @param pc La PC que se está pidiendo.
     */
    public Pedido(String cliente, PC pc) {
        this.id = nextId++;
        this.cliente = cliente;
        this.pc = pc;
        this.estado = "Nuevo";
        this.observadores = new ArrayList<>();
    }
    
    /**
     * Obtiene el ID del pedido.
     * 
     * @return El ID del pedido.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene el nombre del cliente.
     * 
     * @return El nombre del cliente.
     */
    public String getCliente() {
        return cliente;
    }
    
    /**
     * Obtiene la PC del pedido.
     * 
     * @return La PC.
     */
    public PC getPc() {
        return pc;
    }
    
    /**
     * Obtiene el estado del pedido.
     * 
     * @return El estado del pedido.
     */
    public String getEstado() {
        return estado;
    }
    
    /**
     * Establece el estado del pedido y notifica a los observadores.
     * 
     * @param estado El nuevo estado del pedido.
     */
    public void setEstado(String estado) {
        this.estado = estado;
        notificarObservadores("El pedido #" + id + " ha cambiado su estado a: " + estado);
    }
    
    /**
     * Agrega un observador al pedido.
     * 
     * @param observer El observador a agregar.
     */
    @Override
    public void agregarObservador(Observer observer) {
        observadores.add(observer);
    }
    
    /**
     * Elimina un observador del pedido.
     * 
     * @param observer El observador a eliminar.
     */
    @Override
    public void eliminarObservador(Observer observer) {
        observadores.remove(observer);
    }
    
    /**
     * Notifica a todos los observadores sobre un cambio en el pedido.
     * 
     * @param mensaje El mensaje que se enviará a los observadores.
     */
    @Override
    public void notificarObservadores(String mensaje) {
        for (Observer observer : observadores) {
            observer.actualizar(mensaje);
        }
    }
    
    /**
     * Representación en texto del pedido.
     * 
     * @return Una cadena que representa al pedido.
     */
    @Override
    public String toString() {
        return "Pedido #" + id + " - Cliente: " + cliente + " - Estado: " + estado;
    }
}
