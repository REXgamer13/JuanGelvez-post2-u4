package com.universidad.pedidos.observer;

import com.universidad.pedidos.modelo.Pedido;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
@Service
public class GestorPedidosService {
    private final ApplicationEventPublisher publisher;
    public GestorPedidosService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    public void confirmarPedido(Pedido pedido) {
        pedido.setEstado("CONFIRMADO");
        System.out.println("[GESTOR] Pedido " + pedido.getId() + " confirmado.");
// Notifica a todos los suscriptores automáticamente
                publisher.publishEvent(new PedidoConfirmadoEvent(this, pedido));
    }
    public void cancelarPedido(Pedido pedido) {
        pedido.setEstado("CANCELADO");
        System.out.println("[GESTOR] Pedido " + pedido.getId() + " cancelado.");
                publisher.publishEvent(new PedidoCanceladoEvent(this, pedido));
    }
}
