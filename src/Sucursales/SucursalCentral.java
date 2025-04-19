package sucursales;

import observer.Observer;
import observer.Pedido;
import util.TerminalUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la sucursal central de MonosChinos MX.
 * Implementa el patrón Singleton para asegurar una única instancia.
 * Implementa el patrón Observer para recibir notificaciones sobre pedidos.
 * 
 * @author Equipo MonosChinos MX
 * @version 1.0
 */
public class SucursalCentral implements Observer {
    private static SucursalCentral instance;
    private String nombre;
    private String ubicacion;
    private List<Pedido> pedidosRecibidos;
    private List<Pedido> pedidosEnProceso;
    private List<Pedido> pedidosCompletados;
    
    /**
     * Constructor privado para el patrón Singleton.
     */
    private SucursalCentral() {
        this.nombre = "MonosChinos MX Central";
        this.ubicacion = "CDMX";
        this.pedidosRecibidos = new ArrayList<>();
        this.pedidosEnProceso = new ArrayList<>();
        this.pedidosCompletados = new ArrayList<>();
    }
    
    /**
     * Obtiene la única instancia de SucursalCentral (Singleton).
     * 
     * @return La instancia de SucursalCentral.
     */
    public static SucursalCentral getInstance() {
        if (instance == null) {
            instance = new SucursalCentral();
        }
        return instance;
    }
    
    /**
     * Recibe un pedido de una sucursal.
     * 
     * @param pedido El pedido recibido.
     */
    public void recibirPedido(Pedido pedido) {
        pedido.agregarObservador(this);
        pedidosRecibidos.add(pedido);
        pedido.setEstado("Recibido en Sucursal Central");
        
        // Simular procesamiento
        procesarPedido(pedido);
    }
    
    /**
     * Procesa un pedido recibido.
     * 
     * @param pedido El pedido a procesar.
     */
    private void procesarPedido(Pedido pedido) {
        // Mover de recibidos a en proceso
        pedidosRecibidos.remove(pedido);
        pedidosEnProceso.add(pedido);
        pedido.setEstado("En proceso de ensamblaje");
        
        // Simular finalización (en un caso real, esto sería asíncrono)
        completarPedido(pedido);
    }
    
    /**
     * Completa un pedido en proceso.
     * 
     * @param pedido El pedido a completar.
     */
    private void completarPedido(Pedido pedido) {
        // Mover de en proceso a completados
        pedidosEnProceso.remove(pedido);
        pedidosCompletados.add(pedido);
        pedido.setEstado("Completado y listo para envío");
    }
    
    /**
     * Obtiene la lista de pedidos recibidos.
     * 
     * @return Lista de pedidos recibidos.
     */
    public List<Pedido> getPedidosRecibidos() {
        return pedidosRecibidos;
    }
    
    /**
     * Obtiene la lista de pedidos en proceso.
     * 
     * @return Lista de pedidos en proceso.
     */
    public List<Pedido> getPedidosEnProceso() {
        return pedidosEnProceso;
    }
    
    /**
     * Obtiene la lista de pedidos completados.
     * 
     * @return Lista de pedidos completados.
     */
    public List<Pedido> getPedidosCompletados() {
        return pedidosCompletados;
    }
    
    /**
     * Actualiza el estado cuando recibe una notificación de un pedido.
     * 
     * @param mensaje El mensaje de la notificación.
     */
    @Override
    public void actualizar(String mensaje) {
        TerminalUI.info("Sucursal Central recibió notificación: " + mensaje);
    }
}
