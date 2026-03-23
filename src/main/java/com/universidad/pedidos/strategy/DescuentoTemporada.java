package com.universidad.pedidos.strategy;

import org.springframework.stereotype.Service;
// Estrategia 1 — descuento de temporada (20%)
@Service
public class DescuentoTemporada implements EstrategiaDescuento {
    public double calcular(double subtotal) { return subtotal * 0.20; }
    public String getNombre() { return "Temporada (20%)"; }
}

