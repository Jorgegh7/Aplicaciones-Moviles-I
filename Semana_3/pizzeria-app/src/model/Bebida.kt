package model

class Bebida(
    nombre: String = "",
    precio: Double = 0.0
): Producto(nombre, precio, Categoria.BEBIDA) {
}