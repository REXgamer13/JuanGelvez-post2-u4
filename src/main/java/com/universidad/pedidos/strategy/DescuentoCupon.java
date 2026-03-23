package com.universidad.pedidos.strategy;

import org.springframework.stereotype.Service;

// Estrategia 3 — cupón fijo ($15.000)
@Service
public class DescuentoCupon implements EstrategiaDescuento {
    private static final double VALOR_CUPON = 15000.0;
    public double calcular(double subtotal) {
        return Math.min(subtotal, VALOR_CUPON);
    }
    public String getNombre() { return "Cupón (-$15.000)"; }
}
