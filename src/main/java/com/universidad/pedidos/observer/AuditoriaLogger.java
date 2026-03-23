package com.universidad.pedidos.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// Suscriptor 3 — log de auditoría
@Component
public class AuditoriaLogger {
    @EventListener
    public void onConfirmado(PedidoConfirmadoEvent e) {
        System.out.println("[AUDITORIA] Pedido " + e.getPedido().getId() +
                " -> CONFIRMADO");
    }
    @EventListener
    public void onCancelado(PedidoCanceladoEvent e) {
        System.out.println("[AUDITORIA] Pedido " + e.getPedido().getId() +
                " -> CANCELADO");
    }
}