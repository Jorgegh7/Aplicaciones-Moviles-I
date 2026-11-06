package model

import kotlin.math.roundToInt

class Boleta {
    fun imprimir(pedido: Pedido) {
        pedido.calcularTotal()
        pedido.calcularIva()

        // Desestructuración se hace después de calcular, para que total ya tenga valor
        val (cliente, productos, total) = pedido
        val (nombre, correo, telefono) = cliente

        println("==========================")
        println("===  BOLETA Di LORENZO ===")
        println("==========================")
        pedido.imprimirPromocion()
        println()
        println("Numero boleta: ${pedido.idPedido}")
        println("Nombre Cliente: $nombre")
        println("Dirección: ${cliente.direccion}")
        println("Teléfono: $telefono")
        println("Correo: $correo")
        println("Notas: ${pedido.notas ?: "Sin notas"}")
        println()
        pedido.mostrarDatosProductos()
        println("--- RESUMEN ---")
        println("Total Productos: ${productos.size}")
        println("Total Combos: ${pedido.cantidadPorCategoria(Categoria.COMBO)}")
        println("Total Pizzas: ${pedido.cantidadPorCategoria(Categoria.PIZZA)}")
        println("Total Bebidas: ${pedido.cantidadPorCategoria(Categoria.BEBIDA)}")
        println("Subtotal: ${pedido.calcularSubtotal()}")
        println("Descuento: ${pedido.calcularMontoDescuento()}")
        println("IVA incluido: ${pedido.montoIva.roundToInt()}")
        println("Total a pagar: $total")
        println()
        println("¡Gracias por preferir Pizzeria Di Lorenzo!")
        println()
    }
}