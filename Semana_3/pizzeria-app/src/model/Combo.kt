package model

@Promocionable
open class Combo(
    nombre: String = "",
    precio: Double = 0.0,
    val pizza: Pizza,
    val bebida: Bebida
): Producto(nombre, precio, Categoria.COMBO) {
}