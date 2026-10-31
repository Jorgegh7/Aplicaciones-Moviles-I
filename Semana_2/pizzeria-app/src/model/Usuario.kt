package model

open class Usuario(
    var nombre: String = "",
    var telefono: Int = 0
) {
    open fun imprimirDatos(){}
}