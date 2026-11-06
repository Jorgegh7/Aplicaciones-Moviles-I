package model

class Menu {
    val productos: List<Producto> = listOf(
        Pizza("Pepperoni", 8500.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "pepperoni")),
        Pizza("Napolitana", 9000.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "anchoas")),
        Pizza("Romana", 8000.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "jamón")),
        Pizza("Hawaiana", 8200.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "piña", "jamón")),
        Pizza("Cuatro Quesos", 9500.0, ingredientes = listOf("masa", "mozzarella", "parmesano", "gorgonzola", "provolone")),
        Bebida("Coca-Cola", 2500.0),
        Bebida("Sprite", 2500.0),
        Bebida("Fanta", 2500.0),
        Combo("Combo Romana + Coca-Cola", 9500.0,
            Pizza("Romana", 8000.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "jamón")),
            Bebida("Coca-Cola", 2500.0)),
        ComboFamiliar("Combo Familiar Hawaiana + Sprite", 10000.0,
            Pizza("Hawaiana", 8200.0, ingredientes = listOf("masa", "salsa de tomate", "queso", "piña", "jamón")),
            Bebida("Sprite", 2500.0)
    )
    )

    fun buscar(nombre: String, categoria: Categoria): Producto? {
        return productos.find {
            it.categoria == categoria && it.nombre.equals(nombre, ignoreCase = true)
        }
    }

    fun mostrar(categoria: Categoria) {
        for (producto in productos.filter { it.categoria == categoria }) {
            println(producto.nombre)
        }
    }

    // filtra el catálogo del menú y deja solo los productos cuya clase tiene @Promocionable
    fun productosPromocionables(): List<Producto> {
        return productos.filter { it::class.java.isAnnotationPresent(Promocionable::class.java) }
    }

    fun mostrarPromocionables() {
        println("=== PRODUCTOS EN PROMOCIÓN ===")
        for (producto in productosPromocionables()) {
            println("${producto.nombre} (${producto.categoria})")
        }
        println()
    }
}