package model

class Inventario {
    private val stock = mutableMapOf(
        "masa" to 20,
        "salsa de tomate" to 20,
        "queso" to 15,
        "pepperoni" to 10,
        "anchoas" to 5,
        "jamón" to 8,
        "piña" to 12,
        "mozzarella" to 10,
        "parmesano" to 6,
        "gorgonzola" to 5,
        "provolone" to 5,
        "Coca-Cola" to 12,
        "Sprite" to 10,
        "Fanta" to 10
    )

    fun disponible(ingrediente: String): Int {
        return stock[ingrediente] ?: 0
    }

    // Calcula el maximo de productos disponibles por producto
    fun maxProducto(producto: Producto): Int {
        return when (producto) {
            is Pizza -> producto.ingredientes.minOf { disponible(it) }
            is Bebida -> disponible(producto.nombre)
            is Combo -> minOf(maxProducto(producto.pizza), maxProducto(producto.bebida))
            else -> 0
        }
    }

    // Maneja la resta de productos
    private fun restar(nombre: String, cantidad: Int) {
        stock[nombre] = disponible(nombre) - cantidad
    }

    // Maneja la resta según Tipo de Producto
    fun descontar(producto: Producto, cantidad: Int) {
        when (producto) {
            is Pizza -> for (ingrediente in producto.ingredientes) restar(ingrediente, cantidad)
            is Bebida -> restar(producto.nombre, cantidad)
            is Combo -> {
                descontar(producto.pizza, cantidad)
                descontar(producto.bebida, cantidad)
            }
            else -> {}
        }
    }

    fun faltantes(producto: Producto, cantidad: Int): List<String> {
        return when (producto) {
            is Pizza -> producto.ingredientes.filter { disponible(it) < cantidad }
            is Bebida -> if (disponible(producto.nombre) < cantidad) listOf(producto.nombre) else emptyList()
            is Combo -> faltantes(producto.pizza, cantidad) + faltantes(producto.bebida, cantidad)
            else -> emptyList()
        }
    }

    fun mensajeStock(producto: Producto, cantidad: Int): String? {
        val faltan = faltantes(producto, cantidad)
        return when {
            faltan.isEmpty() -> null
            else -> "Stock insuficiente: ${faltan.joinToString(", ")}\n" +
                    "Pasos para reabastecer:\n" +
                    "1. Revisar el stock actual de lo indicado.\n" +
                    "2. Solicitar lo faltante al proveedor.\n" +
                    "3. Actualizar el inventario al recibirlo."
        }
    }

}