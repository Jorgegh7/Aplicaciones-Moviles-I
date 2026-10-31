package model

class Repartidores(
    val listaRepartidores: List<Repartidor>
) {

    fun buscarDisponible(): Repartidor? {
        return listaRepartidores.find { it.estadoRepartidor == EstadoRepartidor.DISPONIBLE }
    }

    // Modifica el estado del repartidor a Estado = REPARTO
    // Modifica el estado del pedido a Estado = DESPACHO
    fun entregarPedido(repartidor: Repartidor, pedido: Pedido){
        repartidor.estadoRepartidor = EstadoRepartidor.REPARTO
        pedido.estado = EstadoPedido.DESPACHO
        println("${repartidor.nombre} despacha el pedido con ID: ${pedido.idPedido}")

    }

    // Si los pedidos no están listos no se puede asignar el pedido
    fun asignarPedido(pedido: Pedido) {
        if(pedido.estado != EstadoPedido.LISTO){
            println("El pedido aun no esta disponible para despacho")
            return
        }
        val repartidor = buscarDisponible()
        if(repartidor == null){
            println("No hay repartidores disponibles en este momento")
        }else{
            pedido.repartidor = repartidor
            entregarPedido(repartidor, pedido)
        }
    }

    // Modifica el estado del Pedido a ENTREGADO
    // Modifica el estado de Repartidor a DISPONIBLE
    fun finalizarEntrega(pedido: Pedido) {
        val repartidor = pedido.repartidor
        if (pedido.estado == EstadoPedido.DESPACHO && repartidor != null) {
            pedido.estado = EstadoPedido.ENTREGADO
            repartidor.estadoRepartidor = EstadoRepartidor.DISPONIBLE
            println("${repartidor.nombre} entregó el pedido ${pedido.idPedido}")
        } else {
            println("No se puede finalizar: el pedido no está en despacho o no tiene repartidor")
        }
    }


}