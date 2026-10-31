import model.Cliente
import model.EstadoPedido
import model.HistorialPedidos
import model.Pedido
import model.Repartidor
import model.Repartidores
import model.Inventario
import model.Menu
import model.Pizza
import util.Util
import java.time.LocalDate

fun main() {
    val cliente = Cliente()
    val menu = Menu()
    val pedido = Pedido()
    var inventario = Inventario()
    val util = Util()
    var historialPedidos = HistorialPedidos()

    val equipoRepartidores = Repartidores(
        listOf(
            Repartidor("Carlos Torres", 87654321),
            Repartidor("Ana Rojas", 12345678)
        )
    )

    fun saludarCliente(){
        println("==========================================")
        println("===  Bienvenido a Pizzeria Di Lorenzo  ===")
        println("==========================================")
        println("A continuacion ingresa tus datos")
    }

    fun datosCliente (){
        cliente.nombre = util.pedirTexto("Ingresa tu nombre: ", "Ingresa un nombre válido")
        cliente.direccion = util.pedirTexto("Ingresa tu dirección de entrega: ", "Ingresa una dirección válida")
        cliente.telefono = util.pedirEntero("Ingresa tu telefono de contacto: +569 ", 10000000, 99999999)
        cliente.correo = util.pedirCorreo("Ingresa tu correo electronico: ")
        println()
        pedido.cliente = cliente
    }

    fun pedirNotas() {
        print("¿Alguna nota para tu pedido? (Enter para omitir): ")
        pedido.agregarNotas(readLine() ?: "")
    }

    fun datosPizza() {
        var continuaComprando = true
        println("========================")
        println("===  Menu Di Lorenzo ===")
        println("========================")
        while (continuaComprando) {
            println("A continuación elige tu pedido según la siguiente lista:")
            menu.mostrar()

            var pizzaElegida: Pizza? = null
            while (pizzaElegida == null) {
                print("Ingresa el nombre de tu Pizza: ")
                pizzaElegida = menu.buscar((readLine() ?: "").trim())
                if (pizzaElegida == null) {
                    println("Esa pizza no está en el menú")
                }
            }
            println("Ingredientes: ${pizzaElegida.ingredientes}")

            // Maximo de Unidades disponibles para la Pizza seleccionada
            val maxPizzas = inventario.maxPizzas(pizzaElegida)
            if (maxPizzas == 0) {
                println("El pedido no puede ser completado: no hay ingredientes suficientes")
                println()
                continue
            }

            var cantidad = 0
            while (cantidad <= 0) {
                print("Ingresa el numero de unidades (máximo $maxPizzas): ")
                try {
                    cantidad = (readLine() ?: "").toInt()
                    if (cantidad <= 0) {
                        println("El numero de Pizzas debe ser mayor a 0")
                    } else if (cantidad > maxPizzas) {
                        inventario.mensajeStock(pizzaElegida, cantidad)?.let {
                            println("El pedido no puede ser completado:")
                            println(it)
                        }
                        cantidad = 0
                    }
                } catch (e: NumberFormatException) {
                    println("Error: No se pudo convertir el número.")
                }
            }

            inventario.descontar(pizzaElegida, cantidad)
            pedido.agregarProducto(pizzaElegida, cantidad)
            println("Pedido confirmado: inventario actualizado")
            println()

            continuaComprando = util.pedirSiNo("¿Deseas seguir comprando? SI/NO: ")
        }
        pedirNotas()
    }


    fun procesarPedido(){
        pedido.cambiarEstado(EstadoPedido.LISTO)
        equipoRepartidores.asignarPedido(pedido)
        equipoRepartidores.finalizarEntrega(pedido)
    }

    fun ingresarPedido(){
        saludarCliente()
        datosCliente()
        datosPizza()
        println("Generando tu boleta...")
        procesarPedido()
        pedido.imprimirBoleta()
        historialPedidos.agregar(pedido)
    }

    ingresarPedido()
    pedido.imprimirEstado()
    historialPedidos.reporte(LocalDate.now())
}