package model

import java.time.LocalDate

class HistorialPedidos {
    private val lista = mutableListOf<Pedido>()

    fun agregar(pedido: Pedido) {
        lista.add(pedido)
    }

    fun reporte(dia: LocalDate) {
        val delDia = lista.filter { it.fecha == dia }
        val completados = delDia.filter { it.estado == EstadoPedido.ENTREGADO }
        val pendientes = delDia.filter { it.estado != EstadoPedido.ENTREGADO }

        println("=== Reporte del día $dia ===")
        for (pedido in delDia) {
            println("Cliente: ${pedido.cliente.nombre} - Pizzas: ${pedido.numeroPizzas} - Total: ${pedido.totalPedido} - Estado: ${pedido.estado}")
        }
        println("Completados: ${completados.size} - Pendientes: ${pendientes.size}")
        println()
    }
}