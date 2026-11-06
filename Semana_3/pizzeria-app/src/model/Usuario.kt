package model

open class Usuario(
    var nombre: String = "",
    var telefono: String = ""
) {
    open fun imprimirDatos(){}
}