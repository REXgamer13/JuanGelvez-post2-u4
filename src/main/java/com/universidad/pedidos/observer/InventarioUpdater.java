package com.universidad.pedidos.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// Suscriptor 2 — actualización de inventario
@Component
public class InventarioUpdater {
    @EventListener
    public void onConfirmado(PedidoConfirmadoEvent e) {
        System.out.println("[INVENTARIO] Reserva de stock aplicada para: "
                + e.getPedido().getId());
    }
    @EventListener
    public void onCancelado(PedidoCanceladoEvent e) {
        System.out.println("[INVENTARIO] Stock liberado para: "
                + e.getPedido().getId());
    }
}