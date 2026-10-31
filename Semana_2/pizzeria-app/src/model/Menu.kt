package model

class Menu {
    val pizzas = listOf(
        Pizza("Pepperoni", 8500.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "pepperoni")),
        Pizza("Napolitana", 9000.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "anchoas")),
        Pizza("Romana", 8000.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "jamón")),
        Pizza("Hawaiana", 8200.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "piña", "jamón")),
        Pizza("Cuatro Quesos", 9500.0, ingredientes = listOf("masa", "mozzarella", "parmesano", "gorgonzola", "provolone"))
    )

    fun buscar(nombre: String): Pizza? {
        return pizzas.find { it.nombre.equals(nombre, ignoreCase = true) }
    }

    fun mostrar() {
        for (pizza in pizzas) {
            println(pizza.nombre)
        }
    }
}