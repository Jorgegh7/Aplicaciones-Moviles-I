# Pizzería Di Lorenzo

Aplicación de consola en Kotlin para gestionar pedidos de una pizzería. Actividad sumativa "Creando soluciones con Kotlin" (Desarrollo de Aplicaciones Móviles, Semana 3).

## Qué hace

- Registra al cliente (nombre, dirección, teléfono y correo) y lo reconoce si ya estaba registrado.
- Muestra las promociones disponibles y permite comprarlas (qué promoción y cuántas).
- Muestra el menú de pizzas y bebidas, con verificación de stock.
- Calcula subtotal, descuentos (más de 3 pizzas y promoción de aniversario) e IVA incluido.
- Genera la boleta, asigna un repartidor disponible y guarda el pedido en un historial con reporte del día.
- Incluye un resumen final que demuestra los conceptos avanzados de Kotlin.

## Estructura

```
src/
├── Main.kt                    # Flujo principal y resumen final
├── model/
│   ├── Usuario.kt             # Clase base (nombre, teléfono)
│   ├── Cliente.kt             # Hereda de Usuario; equals/hashCode y desestructuración
│   ├── Clientes.kt            # Registro de clientes sin duplicados
│   ├── Repartidor.kt          # Hereda de Usuario
│   ├── Repartidores.kt        # Busca disponibles, asigna y finaliza entregas
│   ├── Producto.kt            # Clase base; equals/hashCode por nombre y categoría
│   ├── Pizza.kt               # Hereda de Producto (ingredientes)
│   ├── Bebida.kt              # Hereda de Producto
│   ├── Combo.kt               # Pizza + bebida, marcado con @Promocionable
│   ├── ComboFamiliar.kt       # Hereda de Combo, marcado con @Promocionable
│   ├── Categoria.kt           # Enum: PIZZA, BEBIDA, INGREDIENTE, COMBO
│   ├── Promocionable.kt       # Anotación personalizada
│   ├── Menu.kt                # Catálogo y lectura dinámica de promocionables
│   ├── Inventario.kt          # Stock y verificación con when
│   ├── Pedido.kt              # Productos, totales, operador + y desestructuración
│   ├── HistorialPedidos.kt    # Reporte del día con filter
│   ├── Boleta.kt              # Impresión de la boleta
│   ├── EstadoPedido.kt        # Enum: PREPARACION, LISTO, DESPACHO, ENTREGADO
│   └── EstadoRepartidor.kt    # Enum: DISPONIBLE, REPARTO
└── util/
    └── Util.kt                # Entradas validadas, formato de teléfono y reflection
```

## Cómo ejecutarlo

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar que la librería `org.jetbrains.kotlin:kotlin-reflect:2.1.20` esté agregada (File → Project Structure → Libraries). Es necesaria para la reflection.
3. Ejecutar `main()` en `Main.kt` (botón verde o `Mayús + F10`).
4. Seguir las instrucciones en la consola.

## Requisitos de la actividad

### Regex y Ranges
- **Correo:** se valida con `Regex` y se vuelve a pedir hasta que cumpla el formato `nombre@dominio.com`.
- **Teléfono:** se piden 8 dígitos y se formatea como `+569 XXXXXXXX` con `Regex`.
- **Fechas:** un rango de fechas define el mes de aniversario, con descuento extra del 15%.
- **Cantidad:** debe estar en el rango `1..100`.

### Anotaciones y reflection
- `@Promocionable` marca `Combo` y `ComboFamiliar`.
- `Menu.promocionables()` identifica por reflection los productos marcados, y con eso se muestra la lista informativa y la oferta de compra.
- `Util.listarMiembros()` lista propiedades y métodos de `Cliente`, `Pedido` y `Producto`.

### Sobrecarga de operadores
- `Pedido + Pedido` combina dos pedidos del mismo cliente, sumando los productos. El total y los descuentos se recalculan al imprimir la boleta.
- `==` en `Producto` compara por nombre y categoría.

### Desestructuración
- `Cliente` se desestructura en nombre, correo y teléfono.
- `Pedido` se desestructura en cliente, productos y total.

### equals() y hashCode()
- `Cliente`: nombre y correo.
- `Producto`: nombre y categoría.
- Con un `Set` se evitan duplicados, y `Clientes` los usa para reconocer a un cliente ya registrado.

## Ejemplo de uso

1. Ingresar nombre, dirección, teléfono (8 dígitos) y correo.
2. Elegir si se desea una promoción, cuál y cuántas.
3. Elegir pizzas y bebidas, con sus unidades.
4. Agregar una nota (o Enter para omitir).
5. Revisar la boleta, el despacho, el reporte del día y el resumen final.