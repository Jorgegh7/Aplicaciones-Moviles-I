package model

open class Producto(
    var nombre: String = "",
    var precio: Double = 0.0,
    var categoria: Categoria
) {
    fun detalleProducto(){}

    //Define cuando 2 Producots son iguales considerando nombre y categoria
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Producto) return false
        return nombre == other.nombre && categoria == other.categoria
    }

    override fun hashCode(): Int {
        return 31 * nombre.hashCode() + categoria.hashCode()
    }
}