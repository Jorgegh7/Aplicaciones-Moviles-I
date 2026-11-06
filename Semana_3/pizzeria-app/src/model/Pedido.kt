package model

import java.time.LocalDate
import java.util.UUID
import kotlin.math.roundToInt

class Pedido(
    var idPedido: String = UUID.randomUUID().toString(),
    var cliente: Cliente = Cliente(),
    var productos: MutableList<Producto> = mutableListOf(),
    var repartidor: Repartidor? = null,
    var totalPedido: Double = 0.0,
    var montoIva: Double = 0.0,
    var estado: EstadoPedido = EstadoPedido.PREPARACION,
    var fecha: LocalDate = LocalDate.now(),
    var notas: String? = null
) {

    companion object {
        const val IVA = 0.19
        const val PROMOCION_UNIDADES_PIZZA = 3
        const val DESCUENTO_POR_UNIDADES = 0.10
        const val DESCUENTO_POR_FECHA = 0.15
        val PROMOCION_FECHA = LocalDate.of(2026, 11, 1)..LocalDate.of(2026, 11, 30)
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
        }
    }

    fun enPromocionFecha(): Boolean{
        return fecha in PROMOCION_FECHA
    }

    fun promocionPorUnidadesPizza(): Boolean{
        return cantidadPorCategoria(Categoria.PIZZA) > PROMOCION_UNIDADES_PIZZA
    }

    // Desestructuración
    operator fun component1(): Cliente = cliente
    operator fun component2(): List<Producto> = productos
    operator fun component3(): Double = totalPedido

    // Suma objetos
    operator fun plus(otro: Pedido): Pedido {
        require(cliente == otro.cliente) { "Solo se pueden combinar pedidos del mismo cliente" }
        val combinado = Pedido(cliente = this.cliente)
        combinado.productos.addAll(this.productos)
        combinado.productos.addAll(otro.productos)
        return combinado
    }

    //===== CÁLCULOS =====

    fun calcularMontoDescuento(): Double {
        var porcentaje = 0.0
        if (promocionPorUnidadesPizza()) porcentaje += DESCUENTO_POR_UNIDADES
        if (enPromocionFecha()) porcentaje += DESCUENTO_POR_FECHA
        return calcularSubtotal() * porcentaje

    }

    fun calcularTotal() {
        totalPedido = calcularSubtotal() - calcularMontoDescuento()
    }

    fun calcularSubtotal(): Double {
        var subtotal = 0.0
        for (producto in productos) {
            subtotal += producto.precio
        }
        return subtotal
    }

    fun calcularIva() {
        montoIva =  totalPedido - totalPedido / (1 + IVA)
    }

    fun agregarNotas(texto: String) {
        notas = if (texto.isBlank()) null else texto.trim()
    }

    fun cantidadPorCategoria(categoria: Categoria): Int {
        return productos.count { it.categoria == categoria }
    }

    //===== IMPRIMIR DATOS =====

    fun imprimirPromocion(){
        if(promocionPorUnidadesPizza()) println("Felicidades posees un descuento de un 10% por compras superiores a 3 unidades")
        if(enPromocionFecha()) println("Mes de aniversario!! Posees un descuento de un 15% extra. Promoción válida hasta el 30/11/2026")
    }

    // Agrupa los Productos por nombre sumando sus unidades
    fun mostrarDatosProductos() {
        println("--- PRODUCTOS ---")
        val agrupados = productos.groupBy { it.nombre }
        for ((nombre, unidades) in agrupados) {
            val precio = unidades[0].precio
            val tipo = unidades[0].categoria
            println("$nombre ($tipo) x${unidades.size} - Precio unitario: $precio - Subtotal: ${precio * unidades.size}")
        }
        println()
    }

    fun imprimirEstado(){
        println("==========================")
        println("===  ESTADO DEL PEDIDO ===")
        println("==========================")
        println()
        println("ID: $idPedido")
        cliente.imprimirDatos()
        println("Repartidor: ${repartidor?.nombre}" )
        println("Estado Pedido: $estado")

    }

}