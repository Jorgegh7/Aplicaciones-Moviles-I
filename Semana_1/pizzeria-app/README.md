# Pizzería Di Lorenzo

Aplicación de consola en Kotlin para gestionar pedidos de una pizzería. Actividad "Mi primera app en Kotlin" (Desarrollo de Aplicaciones Móviles, Semana 1).

## Qué hace

- Registra los datos del cliente (nombre, dirección y teléfono) con validación de entradas.
- Muestra el menú y permite elegir pizza sin distinguir mayúsculas/minúsculas.
- Verifica el stock de ingredientes y descuenta el inventario compartido al confirmar el pedido.
- Calcula el total y aplica 10% de descuento en pedidos de más de 3 pizzas.
- Genera la boleta y simula el estado del pedido (en cocina, reparto, entregado) con repartidor asignado.

## Estructura

```
src/
├── Main.kt          # Flujo principal del programa
└── model/
    ├── Cliente.kt   # nombre, dirección, teléfono
    ├── Pizza.kt     # nombre, precio, ingredientes
    └── Pedido.kt    # id, cliente, pizza, cantidad, total, estado, repartidor
```

## Cómo ejecutarlo

1. Abrir el proyecto en IntelliJ IDEA.
2. Ejecutar `main()` en `Main.kt` (botón verde o `Mayús + F10`).
3. Seguir las instrucciones en la consola.

## Ejemplo de uso

1. Ingresar nombre, dirección y teléfono (8 dígitos).
2. Elegir una pizza del menú, por ejemplo `Romana`.
3. Indicar la cantidad de unidades.
4. Revisar la boleta y el seguimiento del pedido.

## Conceptos aplicados

Variables (`val`/`var`), clases con valores por defecto, mapas y listas mutables, `if` como expresión, `try/catch`, ciclos `while`/`for`, funciones locales y manejo de nulos.
