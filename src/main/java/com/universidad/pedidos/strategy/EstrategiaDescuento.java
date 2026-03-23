package com.universidad.pedidos.strategy;

public interface EstrategiaDescuento {
    double calcular(double subtotal);
    String getNombre();
}
