package com.universidad.pedidos.modelo;

public class Pedido {
    private final String id;
    private final String productoId;
    private final int cantidad;
    private double total;
    private String estado;      // PENDIENTE, CONFIRMADO, CANCELADO
    private boolean creditoOk;
    public Pedido(String id, String productoId, int cantidad, double total, boolean creditoOk) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.total = total;
        this.estado = "PENDIENTE";
        this.creditoOk = creditoOk;
    }
    // Getters
    public String getId()        { return id; }
    public String getProductoId(){ return productoId; }
    public int getCantidad()     { return cantidad; }
    public double getTotal()     { return total; }
    public String getEstado()    { return estado; }
    public boolean isCreditoOk() { return creditoOk; }
    // Setters de estado
    public void setEstado(String estado)  { this.estado = estado; }
    public void setTotal(double total)    { this.total = total; }
    @Override
    public String toString() {
        return "Pedido{id='" + id + "', estado='" + estado
                + "', total=" + total + "}";
    }
}