package model

import java.time.LocalDate
import java.util.UUID
import kotlin.math.roundToInt

class Pedido(
    var idPedido: String = UUID.randomUUID().toString(),
    var cliente: Cliente = Cliente(),
    var productos: MutableList<Producto> = mutableListOf(),
    var repartidor: Repartidor? = null,
    var numeroPizzas: Int = 0,
    var totalPedido: Double = 0.0,
    var montoIva: Double = 0.0,
    var estado: EstadoPedido = EstadoPedido.PREPARACION,
    var fecha: LocalDate = LocalDate.now(),
    var notas: String? = null
) {

    companion object {
        const val IVA = 0.19
        const val DESCUENTO_MINIMO = 3
        const val PORCENTAJE_DESCUENTO = 0.10
    }


    fun cambiarEstado(nuevoEstadoPedido: EstadoPedido) {
        estado = nuevoEstadoPedido
    }

    fun agregarProducto(producto: Producto, cantidad: Int = 1) {
        if (cantidad <= 0) {
            println("La cantidad debe ser mayor a 0")
            return
        }
        repeat(cantidad) {
            productos.add(producto)
            numeroPizzas++
        }
    }

    fun calcularSubtotal(): Double {
        var subtotal = 0.0
        for (producto in productos) {
            subtotal += producto.precio
        }
        return subtotal
    }

    fun calcularMontoDescuento(): Double {
        return if (numeroPizzas > DESCUENTO_MINIMO) calcularSubtotal() * PORCENTAJE_DESCUENTO else 0.0
    }

    fun calcularTotal() {
        totalPedido = calcularSubtotal() - calcularMontoDescuento()
    }

    fun calcularIva() {
        montoIva =  totalPedido - totalPedido / (1 + IVA)
    }

    fun agregarNotas(texto: String) {
        notas = if (texto.isBlank()) null else texto.trim()
    }

    // Agrupa los Productos por nombre sumando sus unidades
    fun mostrarDatosProductos() {
        println("--- Productos ---")
        val agrupados = productos.groupBy { it.nombre }
        for ((nombre, unidades) in agrupados) {
            val precio = unidades[0].precio
            println("$nombre x${unidades.size} - Precio unitario: $precio - Subtotal: ${precio * unidades.size}")
        }
        println()
    }

    fun imprimirEstado(){
        println("==========================")
        println("===  Estado del Pedido ===")
        println("==========================")
        println()
        println("ID: $idPedido")
        cliente.imprimirDatos()
        println("Repartidor: ${repartidor?.nombre}" )
        println("Estado Pedido: $estado")

    }

    fun imprimirBoleta() {
        calcularTotal()
        calcularIva()

        println("==========================")
        println("===  Boleta Di Lorenzo ===")
        println("==========================")
        println()
        println("Numero boleta: $idPedido")
        cliente.imprimirDatos()
        mostrarDatosProductos()
        println("--- Resumen ---")
        println("Total Unidades: $numeroPizzas")
        println("Subtotal: ${calcularSubtotal()}")
        println("Descuento: ${calcularMontoDescuento()}")
        println("IVA incluido: ${montoIva.roundToInt()}")
        println("Total a pagar: $totalPedido")
        println()
        println("¡Gracias por preferir Pizzeria Di Lorenzo!")
        println()
    }
}