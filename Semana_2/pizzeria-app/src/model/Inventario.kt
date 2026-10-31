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
        "provolone" to 5
    )

    fun disponible(ingrediente: String): Int {
        return stock[ingrediente] ?: 0
    }

    fun maxPizzas(pizza: Pizza): Int {
        return pizza.ingredientes.minOf { disponible(it) }
    }

    fun descontar(pizza: Pizza, cantidad: Int) {
        for (ingrediente in pizza.ingredientes) {
            stock[ingrediente] = disponible(ingrediente) - cantidad
        }
    }

    fun faltantes(pizza: Pizza, cantidad: Int): List<String> {
        return pizza.ingredientes.filter { disponible(it) < cantidad }
    }

    fun mensajeStock(pizza: Pizza, cantidad: Int): String? {
        val faltan = faltantes(pizza, cantidad)
        return when {
            faltan.isEmpty() -> null
            else -> "Ingredientes insuficientes: ${faltan.joinToString(", ")}\n" +
                    "Pasos para reabastecer:\n" +
                    "1. Revisar el stock actual de los ingredientes indicados.\n" +
                    "2. Solicitar los ingredientes faltantes al proveedor.\n" +
                    "3. Actualizar el inventario al recibirlos."
        }
    }

}