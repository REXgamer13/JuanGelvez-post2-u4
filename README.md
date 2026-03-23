# pedidos-comportamiento

Proyecto de ejemplo en Spring Boot para demostrar dos patrones de comportamiento:

- **Observer** para reaccionar a eventos de negocio del pedido.
- **Strategy** para cambiar la logica de descuentos sin modificar `CarritoService`.

## Flujo de eventos (Observer)

### Componentes

- Publicador: `src/main/java/com/universidad/pedidos/observer/GestorPedidosService.java`
- Eventos: `PedidoConfirmadoEvent` y `PedidoCanceladoEvent`
- Suscriptores:
  - `EmailNotifier`
  - `InventarioUpdater`
  - `AuditoriaLogger`

### Flujo al confirmar un pedido

1. Se recibe un `Pedido` (estado inicial esperado: `PENDIENTE`).
2. `GestorPedidosService.confirmarPedido(...)` cambia estado a `CONFIRMADO`.
3. El servicio publica `PedidoConfirmadoEvent` con `ApplicationEventPublisher`.
4. Spring notifica automaticamente a todos los metodos con `@EventListener` que aceptan ese evento.
5. Se ejecutan los efectos secundarios:
   - Email de confirmacion.
   - Reserva de stock en inventario.
   - Registro de auditoria.

### Flujo al cancelar un pedido

1. `GestorPedidosService.cancelarPedido(...)` cambia estado a `CANCELADO`.
2. Publica `PedidoCanceladoEvent`.
3. Spring despacha el evento a los mismos suscriptores (con handlers para cancelacion).
4. Efectos secundarios:
   - Aviso de cancelacion por email.
   - Liberacion de stock.
   - Log de auditoria para cancelacion.

### Idea clave del patron Observer

`GestorPedidosService` no conoce los detalles de email, inventario o auditoria.
Solo publica eventos de dominio y los suscriptores reaccionan por separado.
Esto reduce acoplamiento y facilita agregar nuevos listeners sin tocar el servicio principal.

## Seleccion de estrategias (Strategy)

### Contrato de estrategia

Interfaz: `src/main/java/com/universidad/pedidos/strategy/EstrategiaDescuento.java`

- `double calcular(double subtotal)` -> calcula monto de descuento.
- `String getNombre()` -> nombre visible de la estrategia.

### Estrategias disponibles

- `DescuentoTemporada`: 20% del subtotal.
- `DescuentoVIP`: 30% del subtotal.
- `DescuentoCupon`: descuento fijo de 15000 (sin superar el subtotal).

### Como se selecciona la estrategia activa

En `CarritoService`:

1. Spring inyecta todas las implementaciones de `EstrategiaDescuento` en una lista.
2. `activarDescuento(String nombre)` busca una coincidencia por:
   - `getNombre()` (case-insensitive), o
   - nombre de clase (`getClass().getSimpleName()`) que contenga el texto recibido.
3. Si encuentra coincidencia, cambia `estrategiaActiva`.
4. Si no encuentra, lanza `IllegalArgumentException`.

### Como se aplica al total

`calcularTotal(subtotal)`:

- Si no hay estrategia activa, retorna el subtotal sin cambios.
- Si hay estrategia activa:
  - calcula `descuento = estrategiaActiva.calcular(subtotal)`
  - retorna `total = subtotal - descuento`

Esto permite cambiar de estrategia en tiempo de ejecucion sin modificar el codigo del carrito.

## Ejemplo rapido

- Subtotal: `100000`
- Estrategia `Temporada` -> descuento `20000`, total `80000`
- Estrategia `VIP` -> descuento `30000`, total `70000`
- Estrategia `Cupon` -> descuento `15000`, total `85000`

Estos casos estan cubiertos en `src/test/java/com/universidad/pedidos/StrategyTest.java`.

## Ejecutar pruebas

```powershell
.\mvnw.cmd test
```

