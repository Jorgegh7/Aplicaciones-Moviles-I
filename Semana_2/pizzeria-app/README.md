# Pizzería Di Lorenzo

Aplicación de consola en Kotlin para gestionar pedidos de una pizzería. Actividad "Aplicando utilidades de Kotlin" (Desarrollo de Aplicaciones Móviles, Semana 2).

## Qué hace

- Registra los datos del cliente (nombre, dirección, teléfono y correo) con validación y valores por defecto.
- Muestra el menú y permite elegir pizza sin distinguir mayúsculas/minúsculas.
- Verifica el stock del inventario compartido. Si faltan ingredientes, indica cuáles y los pasos para reabastecer.
- Permite agregar notas opcionales al pedido ("Sin notas" por defecto).
- Calcula subtotal, descuento (10% en más de 3 pizzas) e IVA incluido en el precio.
- Genera la boleta, asigna un repartidor disponible y actualiza el estado del pedido.
- Guarda los pedidos en un historial y genera un reporte del día con completados y pendientes.

## Estructura

```
src/
├── Main.kt                  # Flujo principal del programa
├── model/
│   ├── Usuario.kt           # Clase base (nombre, teléfono)
│   ├── Cliente.kt           # Hereda de Usuario (dirección, correo)
│   ├── Repartidor.kt        # Hereda de Usuario (estado)
│   ├── Repartidores.kt      # Busca disponibles, asigna y finaliza entregas
│   ├── Producto.kt          # Clase base de los productos
│   ├── Pizza.kt             # Hereda de Producto (ingredientes)
│   ├── Menu.kt              # Pizzas disponibles
│   ├── Inventario.kt        # Stock de ingredientes y verificación con when
│   ├── Pedido.kt            # Productos, totales, estado y notas
│   ├── HistorialPedidos.kt  # Pedidos del día y reporte con filter
│   ├── EstadoPedido.kt      # Enum: PREPARACION, LISTO, DESPACHO, ENTREGADO
│   └── EstadoRepartidor.kt  # Enum: DISPONIBLE, REPARTO
└── util/
    └── Util.kt              # Entradas validadas (texto, número, correo, SI/NO)
```

## Cómo ejecutarlo

1. Abrir el proyecto en IntelliJ IDEA.
2. Ejecutar `main()` en `Main.kt` (botón verde o `Mayús + F10`).
3. Seguir las instrucciones en la consola.

## Ejemplo de uso

1. Ingresar nombre, dirección, teléfono (8 dígitos) y correo.
2. Elegir una pizza del menú, por ejemplo `Romana`, e indicar las unidades.
3. Repetir si se desea seguir comprando.
4. Agregar una nota (o Enter para omitir).
5. Revisar la boleta, el despacho y el reporte del día.

## Conceptos aplicados

- **Flujos de control:** `when` para verificar el stock, `if` como expresión, ciclos `while` y `for`.
- **Colecciones:** `Map` para el inventario, `List` para menú, productos y repartidores, con `find`, `filter`, `groupBy` y `minOf`.
- **Strings:** plantillas `$variable` y `${expresión}`, validación de correo con `Regex`.
- **POO:** clases, herencia (`Usuario`, `Producto`), `open`/`override`, enums y `companion object` para constantes.
- **Errores y nulls:** `try/catch` en las entradas, operadores `?.` y `?:`, y `let` para notificar solo si hay valor.