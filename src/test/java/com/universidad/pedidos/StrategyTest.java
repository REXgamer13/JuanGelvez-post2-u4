package com.universidad.pedidos;

import com.universidad.pedidos.modelo.Pedido;
import com.universidad.pedidos.strategy.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class StrategyTest {
    private CarritoService buildCarrito() {
        return new CarritoService(List.of(
                new DescuentoTemporada(),
                new DescuentoVIP(),
                new DescuentoCupon()
        ));
    }
    @Test
    void testDescuentoTemporadaVeintePercent() {
        CarritoService carrito = buildCarrito();
        carrito.activarDescuento("Temporada");
        assertEquals(80000.0, carrito.calcularTotal(100000.0), 0.01);
    }
    @Test
    void testDescuentoVIPTreintaPercent() {
        CarritoService carrito = buildCarrito();
        carrito.activarDescuento("VIP");
        assertEquals(70000.0, carrito.calcularTotal(100000.0), 0.01);
    }
    @Test
    void testDescuentoCuponFijo() {
        CarritoService carrito = buildCarrito();
        carrito.activarDescuento("Cupon");
        assertEquals(85000.0, carrito.calcularTotal(100000.0), 0.01);
    }
    @Test
    void testCambioDeEstrategiaEnTiempoDeEjecucion() {
        CarritoService carrito = buildCarrito();
        carrito.activarDescuento("VIP");
        double con_vip = carrito.calcularTotal(100000.0);
        carrito.activarDescuento("Temporada"); // swap sin modificar CarritoService
        double con_temporada = carrito.calcularTotal(100000.0);
        assertNotEquals(con_vip, con_temporada);
    }
    @Test
    void testEstrategiaInvalidaLanzaExcepcion() {
        CarritoService carrito = buildCarrito();
        assertThrows(IllegalArgumentException.class,
                () -> carrito.activarDescuento("NoExiste"));
    }
}