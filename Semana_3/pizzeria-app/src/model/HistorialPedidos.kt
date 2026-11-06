package model

import java.time.LocalDate

class HistorialPedidos {
    private val lista = mutableListOf<Pedido>()

    fun agregar(pedido: Pedido) {
        lista.add(pedido)
    }

    // Maneja el reporte filtrando por día que pasado como parámetro
    fun reporte(dia: LocalDate) {
        val delDia = lista.filter { it.fecha == dia }
        val completados = delDia.filter { it.estado == EstadoPedido.ENTREGADO }
        val pendientes = delDia.filter { it.estado != EstadoPedido.ENTREGADO }
        println()
        println("=== REPORTE DEL DÍA $dia ===")
        for (pedido in delDia) {
            println("Cliente: ${pedido.cliente.nombre} - Combos: ${pedido.cantidadPorCategoria(Categoria.COMBO)} " +
                    "- Pizzas: ${pedido.cantidadPorCategoria(Categoria.PIZZA)} " +
                    "- Bebidas: ${pedido.cantidadPorCategoria(Categoria.BEBIDA)}- Total: ${pedido.totalPedido} " +
                    "- Estado: ${pedido.estado}")
        }
        println("Completados: ${completados.size} - Pendientes: ${pendientes.size}")
        println()
    }
}