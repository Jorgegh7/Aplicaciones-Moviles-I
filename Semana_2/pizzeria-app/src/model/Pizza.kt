package model

class Pizza(
    nombre: String = "",
    precio: Double = 0.0,
    var ingredientes: List<String> = emptyList()
) : Producto(nombre, precio) {

    fun detallarProducto(){
        println("Nombre: $nombre")
        println("Precio: $precio")
        println("Ingredientes: ${ingredientes.joinToString(", ")}\"")
    }

    fun detalleBoleta(){
        println("Nombre: $nombre - Precio: $precio")
    }
}