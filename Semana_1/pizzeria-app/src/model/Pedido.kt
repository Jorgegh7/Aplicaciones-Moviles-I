package model

class Pedido(
    var idPedido: String = "",
    var cliente: Cliente = Cliente(),
    var pizza: Pizza = Pizza(),
    var repartidor: String = "",
    var numeroPizzas: Int = 0,
    var totalPedido: Int = 0,
    var estado: String = "EN COCINA"
) {
}