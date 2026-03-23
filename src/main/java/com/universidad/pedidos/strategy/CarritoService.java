package com.universidad.pedidos.strategy;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CarritoService {
    // Spring inyecta TODAS las implementaciones de EstrategiaDescuento
    private final List<EstrategiaDescuento> estrategiasDisponibles;
    private EstrategiaDescuento estrategiaActiva;
    public CarritoService(List<EstrategiaDescuento> estrategias) {
        this.estrategiasDisponibles = estrategias;
// Por defecto, sin descuento activo
        this.estrategiaActiva = null;
    }
    // Selecciona la estrategia activa por nombre
    public void activarDescuento(String nombre) {
        this.estrategiaActiva = estrategiasDisponibles.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre) ||
                        e.getClass().getSimpleName().toLowerCase().contains(nombre.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estrategia no encontrada: " + nombre));
        System.out.println("[CARRITO] Estrategia activada: "
                +
                estrategiaActiva.getNombre());
    }
    public double calcularTotal(double subtotal) {
        if (estrategiaActiva == null) {
            System.out.println("[CARRITO] Sin descuento activo. Total: $" +
                    subtotal);
            return subtotal;
        }
        double descuento = estrategiaActiva.calcular(subtotal);
        double total = subtotal - descuento;
        System.out.printf("[CARRITO] %s -> Descuento: $%.2f | Total: $%.2f%n",
                estrategiaActiva.getNombre(), descuento, total);
        return total;
    }
    public List<String> listarEstrategias() {
        return estrategiasDisponibles.stream()
                .map(EstrategiaDescuento::getNombre).toList();
    }
}