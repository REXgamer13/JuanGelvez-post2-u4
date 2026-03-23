package com.universidad.pedidos.strategy;

import org.springframework.stereotype.Service;

// Estrategia 2 — cliente VIP (30%)
@Service
public class DescuentoVIP implements EstrategiaDescuento {
    public double calcular(double subtotal) { return subtotal * 0.30; }
    public String getNombre() { return "VIP (30%)"; }
}
