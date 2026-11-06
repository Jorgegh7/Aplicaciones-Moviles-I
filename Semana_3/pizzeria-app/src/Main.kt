import model.Bebida
import model.Boleta
import model.Categoria
import model.Cliente
import model.Clientes
import model.Combo
import model.EstadoPedido
import model.HistorialPedidos
import model.Pedido
import model.Repartidor
import model.Repartidores
import model.Inventario
import model.Menu
import model.Pizza
import model.Producto
import util.Util
import java.time.LocalDate

fun main() {
    val cliente = Cliente()
    val menu = Menu()
    val pedido = Pedido()
    var inventario = Inventario()
    val util = Util()
    var historialPedidos = HistorialPedidos()
    var boleta = Boleta()

    val clientes = Clientes(
        listOf(
            Cliente(nombre = "Jorge", correo = "j@c.cl", direccion = "Quilpue", telefono = "+569 56565656"),
            Cliente(nombre = "Ana", correo = "a@c.cl", direccion = "Viña del Mar", telefono = "+569 12345678")
        )
    )

    val equipoRepartidores = Repartidores(
        listOf(
            Repartidor("Carlos Torres", "87654321"),
            Repartidor("Ana Rojas", "12345678")
        )
    )

    fun mostrarDestructuracion(){
        val p = Pedido(cliente = Cliente(nombre = "Jorge", correo = "j@c.cl", telefono = "+569 44332211"))
        p.agregarProducto(Pizza("Romana", 8000.0), 2)
        p.agregarProducto(Bebida("Fanta", 2500.0), 1)
        p.calcularTotal() // Se aplican las promociones sobre el total
        val (cliente, productos, total) = p
        println("Nombre: ${cliente.nombre} - Correo: ${cliente.correo} - Teléfono: ${cliente.telefono}")
        productos.forEach { println("${it.nombre} ${ it.precio } ${it.categoria} Precio:${it.precio}") }
        println("Total: $total")
    }

    fun mostrarClienteRegistrado(){
        val c1 = Cliente(nombre = "Jorge", correo = "j@c.cl", direccion = "Quilpue", telefono = "+569 56565656")
        clientes.registrar(c1)
    }

    fun mostrarReflection() {
        println("=== REFLECTION ===")
        util.listarMiembros(Pedido::class)
        util.listarMiembros(Producto::class)
        util.listarMiembros(Cliente::class)
    }

    fun mostrarSumaObjetos(){
        println("===  PRUEBA SUMA DE OBJETOS ===")
        val p1 = Pedido(cliente = Cliente(nombre = "Jorge", correo = "j@c.cl"))
        p1.agregarProducto(Pizza("Romana", 8000.0), 2)
        val p2 = Pedido(cliente = Cliente(nombre = "Jorge", correo = "j@c.cl"))
        p2.agregarProducto(Pizza("Hawaiana", 8200.0), 2)

        val sumaPedidos = p1 + p2
        sumaPedidos.calcularTotal()
        println("Todal de productos sumados P1 +P 2 - Productos: ${sumaPedidos.productos.size} - Total: ${sumaPedidos.totalPedido}")

        val p3 = Pedido(cliente = Cliente(nombre = "Ana", correo = "a@c.cl"))
        try {
            p1 + p3
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
        }
        println("===  P1 (Jorge) ===")
        boleta.imprimir(p1)
        println("===  P2 (Jorge) ===")
        boleta.imprimir(p2)
        println("===  SUMA PEDIDOS ===")
        boleta.imprimir(sumaPedidos)
        println("===  P3 (Ana) ===")
        boleta.imprimir(p3)

    }

    fun mostrarIgualdadProductos(){
        val a = Pizza("Romana", 8000.0)
        val b = Pizza("Romana", 9000.0)
        val c = Bebida("Romana", 100.0)
        println(a == b)
        println(a == c)

        val productos = mutableSetOf<Producto>()
        productos.add(Pizza("Romana", 8000.0))
        productos.add(Pizza("Romana", 9000.0))
        productos.add(Bebida("Romana", 100.0))
        println(productos.size)
        productos.forEach { println("Nombre: ${it.nombre} - Categoria: ${it.categoria} - Precio: ${it.precio} ") }
    }

    fun mostrarIgualdadClientes(){
        val clientes = mutableSetOf<Cliente>()
        println(clientes.add(Cliente(nombre = "Jorge", correo = "j@c.cl", direccion = "Quilpue")))
        println(clientes.add(Cliente(nombre = "Jorge", correo = "j@c.cl", direccion = "Viña")))
        println(clientes.add(Cliente(nombre = "Ana", correo = "a@c.cl")))
        println(clientes.size)

        clientes.forEach { println("Nombre: ${it.nombre} - Correo: ${it.correo} - Dirección: ${it.direccion}" ) }
    }

    fun saludarCliente(){
        println("=========================================")
        println("===  BIENVENIDO A PIZZERIA DI LORENZO ===")
        println("=========================================")
        println("A continuacion ingresa tus datos")
    }

    fun datosCliente (){
        cliente.nombre = util.pedirTexto("Ingresa tu nombre: ", "Ingresa un nombre válido")
        cliente.direccion = util.pedirTexto("Ingresa tu dirección de entrega: ", "Ingresa una dirección válida")
        cliente.telefono =
            util.pedirTelefono("Ingresa tu telefono de contacto de 8 digitos: +569 ", 10000000 , 99999999 ).toString()
        cliente.correo = util.pedirCorreo("Ingresa tu correo electronico: ")
        println()
        clientes.registrar(cliente)
        pedido.cliente = cliente
    }

    fun pedirNotas() {
        print("¿Alguna nota para tu pedido? (Enter para omitir): ")
        pedido.agregarNotas(readLine() ?: "")
    }

    fun ofrecerPromo() {
        val promos = menu.productosPromocionables()
        if (promos.isNotEmpty() && util.pedirSiNo("¿Deseas comprar una promoción? SI/NO: ")) {
            println("=== PROMOCIONES ===")
            for ((i, promo) in promos.withIndex()) {
                println("${i + 1}. ${promo.nombre} - $${"%.0f".format(promo.precio)}")
            }
            val opcion = util.pedirEntero("¿Cuál deseas? (1-${promos.size}): ", 1, promos.size)
            val promo = promos[opcion - 1]

            val maxPromo = inventario.maxProducto(promo)
            if (maxPromo < 1) {
                inventario.mensajeStock(promo, 1)?.let { println(it) }
            } else {
                val cantidad = util.pedirEntero("¿Cuántas deseas? (máximo $maxPromo): ", 1, maxPromo)
                inventario.descontar(promo, cantidad)
                pedido.agregarProducto(promo, cantidad)
                println("Promo agregada al pedido (x$cantidad)")
                println()
            }
        }
    }

    fun datosPizza() {
        var continuaComprando = true
        println("========================")
        println("===  MENU DI LORENZO ===")
        println("========================")
        while (continuaComprando) {
            println("A continuación elige tu pedido según la siguiente lista:")
            menu.mostrar(Categoria.PIZZA)

            var pizzaElegida: Pizza? = null
            while (pizzaElegida == null) {
                print("Ingresa el nombre de tu Pizza: ")
                pizzaElegida = menu.buscar((readLine() ?: "").trim(), Categoria.PIZZA) as Pizza?
                if (pizzaElegida == null) {
                    println("Esa pizza no está en el menú")
                }
            }
            println("Ingredientes: ${pizzaElegida.ingredientes}")

            // Maximo de Unidades disponibles para la Pizza seleccionada
            val maxPizzas = inventario.maxProducto(pizzaElegida)
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
                    if(!util.rangoUnidades(cantidad)){
                        println("La cantidad debe estar entre 1 y 100")
                        cantidad = 0
                    }else if (cantidad > maxPizzas) {
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
    }

    fun datosBebida(){
        var continuarComprando= true
        while(continuarComprando){
            continuarComprando = util.pedirSiNo("¿Deseas agregar Bebidas? SI/NO: ")
            if(continuarComprando){
                println("A continuación elige tu Bebida según la siguiente lista:")
                menu.mostrar(Categoria.BEBIDA)
                var bebidaElegida: Bebida? = null
                while (bebidaElegida == null) {
                    print("Ingresa el nombre de tu Bebida: ")
                    bebidaElegida = menu.buscar((readLine() ?: "").trim(), Categoria.BEBIDA) as Bebida?
                    if (bebidaElegida == null) {
                        println("Esa Bebida no está en el menú")
                    }
                }
                val maxBebidas = inventario.maxProducto(bebidaElegida)
                if (maxBebidas == 0) {
                    println("El pedido no puede ser completado: no hay Bebidas suficientes")
                    println()
                    continue
                }

                var cantidad = 0
                while (cantidad <= 0) {
                    print("Ingresa el numero de unidades (máximo $maxBebidas): ")
                    try {
                        cantidad = (readLine() ?: "").toInt()
                        if (!util.rangoUnidades(cantidad)) {
                            println("La cantidad debe estar entre 1 y 100")
                            cantidad = 0
                        } else if (cantidad > maxBebidas) {
                            inventario.mensajeStock(bebidaElegida, cantidad)?.let {
                                println("El pedido no puede ser completado:")
                                println(it)
                            }
                            cantidad = 0
                        }
                    } catch (e: NumberFormatException) {
                        println("Error: No se pudo convertir el número.")
                    }
                }
                inventario.descontar(bebidaElegida, cantidad)
                pedido.agregarProducto(bebidaElegida, cantidad)
                println("Pedido confirmado: inventario actualizado")
                println()

                continuarComprando = util.pedirSiNo("¿Deseas seguir comprando? SI/NO: ")
            }
        }

    }


    fun procesarPedido(){
        println("--- REPARTIDOR ---")
        pedido.cambiarEstado(EstadoPedido.LISTO)
        equipoRepartidores.asignarPedido(pedido)
        equipoRepartidores.finalizarEntrega(pedido)
        println()
    }

    fun ingresarPedido(){
        saludarCliente()
        datosCliente()
        ofrecerPromo()
        datosPizza()
        datosBebida()
        pedirNotas()
        println("Generando tu boleta...")
        println("==========================")
        procesarPedido()
        boleta.imprimir(pedido)
        historialPedidos.agregar(pedido)
    }

    ingresarPedido()
    pedido.imprimirEstado()
    historialPedidos.reporte(LocalDate.now())

    // TODO: descomentar las siguientes líneas para ver las demostraciones del resumen final
    //mostrarDestructuracion()
    //mostrarClienteRegistrado()
    //mostrarIgualdadClientes()
    //mostrarReflection()
    //mostrarSumaObjetos()
    //mostrarIgualdadProductos()

}


