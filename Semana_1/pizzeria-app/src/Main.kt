import model.Cliente
import model.Pedido
import model.Pizza
import java.util.UUID

fun main() {
    //Inventario mutable para poder ajustar el inventario (no es solo lectura)
    val inventario = mutableMapOf(
        "masa" to 20,
        "salsa de tomate" to 20,
        "queso" to 15,
        "pepperoni" to 10,
        "anchoas" to 5,
        "jamón" to 8,
        "piña" to 12,
        "mozzarella" to 10,
        "parmesano" to 6,
        "gorgonzola" to 5,
        "provolone" to 5
    )

    val pizzas = mapOf(
        "Pepperoni" to mapOf(
            "precio" to 8500,
            "ingredientes" to listOf("masa", "salsa de tomate", "queso", "pepperoni")
        ),
        "Napolitana" to mapOf(
            "precio" to 9000,
            "ingredientes" to listOf("masa", "salsa de tomate", "queso", "anchoas")
        ),
        "Romana" to mapOf(
            "precio" to 8000,
            "ingredientes" to listOf("masa", "salsa de tomate", "queso", "jamón")
        ),
        "Hawaiana" to mapOf(
            "precio" to 8200,
            "ingredientes" to listOf("masa", "salsa de tomate", "queso", "piña", "jamón")
        ),
        "Cuatro Quesos" to mapOf(
            "precio" to 9500,
            "ingredientes" to listOf("masa", "mozzarella", "parmesano", "gorgonzola", "provolone")
        )
    )

    val cliente = Cliente()
    val pizza = Pizza()
    val pedido = Pedido()

    pedido.repartidor = "Carlos Torres"


    fun saludarCliente(){
        println("==========================================")
        println("===  Bienvenido a Pizzeria Di Lorenzo  ===")
        println("==========================================")
        println("A continuacion ingresa tus datos")
    }

    fun datosCliente (){
        //Variables de validación de flujo
        var validandoNombre = true
        var valindandoDireccion = true
        var validandoTelefono = true

        while (validandoNombre){
            print("Ingresa tu nombre: ")
            cliente.nombre = readLine() ?: ""
            if(cliente.nombre.isBlank()){
                println("Ingresa un nombre válido")
            }else{
                validandoNombre = false
            }
        }

        while (valindandoDireccion){
            print("Ingresa tu dirección de entrega: ")
            cliente.direccion = readLine() ?: ""
            if(cliente.direccion.isBlank()){
                println("Ingresa una dirección válida")
            }else{
                valindandoDireccion = false
            }
        }

        while(validandoTelefono){
            print("Ingresa tu telefono de contacto: +569  ")
            try{
                val numero = readLine()?.trim()?.toInt()
                if(numero in 10000000..99999999){
                    cliente.telefono = numero ?: 0
                    validandoTelefono = false
                }else{
                    println("Ingresa un valor correcto")
                }
            } catch (e: NumberFormatException){
                println("Error: El telefono debe ser un número de 8 dígitos")
            }
        }
        println()
    }

    fun imprimirDatosCliente(){
        println("Nombre Cliente: " + cliente.nombre)
        println("Dirección: "  + cliente.direccion)
        println("Telefono: +569" + cliente.telefono)
        println()
    }

    fun datosPizza() {
        var continuaComprando = true;
        println("========================")
        println("===  Menu Di Lorenzo ===")
        println("========================")
        println("A continuacioón elige tu pedido según la siguiente lista:")
        for (pizza in pizzas){
            println(pizza.key)
        }
        while (continuaComprando) {
            print("Ingresa el nombre de tu Pizza: ")
            //Elimina los espacios vacios de la entrada y deja un String "" por defecto en lugar de null
            val entrada = readLine()?.trim() ?: ""
            val pizzaElegida = pizzas.keys.find { it.equals(entrada, ignoreCase = true) }

            if (pizzaElegida != null) {
                pizza.nombre = pizzaElegida
                pizza.ingredientes = pizzas[pizzaElegida]?.get("ingredientes") as List<String>
                println("Excelente elección: $pizzaElegida")
                println("Ingredientes:  ${pizza.ingredientes}")
                pizza.precio = pizzas[pizzaElegida]?.get("precio") as Int
                continuaComprando = false
            } else {
                println("Esa pizza no está en el menú")
                println("Ingresa nuevamente una opción válida")
            }
        }

        continuaComprando = true
        while (continuaComprando) {
            val ingredientesStock = pizzas[pizza.nombre]?.get("ingredientes") as List<String>
            print("Ingresa el numero de unidades: ")
            try {
                // si readLine() devuelve null se usa "" y toInt() lanza NumberFormatException,
                // así null, vacío y letras terminan todos en el catch
                pedido.numeroPizzas= (readLine() ?: "").toInt()
                if (pedido.numeroPizzas <= 0) {
                    println("El numero de Pizzas debe ser mayor a 0")
                    println()

                }else if (pedido.numeroPizzas > ingredientesStock.minOf {inventario[it] ?: 0 }){
                    println("Stock superado. Elige un número menor de unidades")
                    println()
                 }else {
                     for(ingrediente in ingredientesStock){
                         inventario[ingrediente] = inventario[ingrediente]!! - pedido.numeroPizzas
                         println("Inventario actualizado: $ingrediente ${inventario[ingrediente]} unidades")
                     }

                    continuaComprando = false
                    println()
                }
            } catch (e: NumberFormatException) {
                println("Error: No se pudo convertir el número.")
                println()
            }
        }
    }

    fun aplicarDescuento(total: Int, cantidad: Int): Int {
        val totalFinal = if (cantidad > 3) {
            println()
            println("Felicidades tienes un 10% de descuento por una compra superior a 3 Unidades!!!!")
            println("Total sin descuento: " + total)
            (total * 0.9).toInt()
        } else {
            total
        }
        return totalFinal
    }

    fun estadoPedido(){
        println("==========================")
        println("===  Estado del Pedido ===")
        println("==========================")
        println()
        println("Repartidor: " + pedido.repartidor)
        println("ESTADO PEDIDO:${pedido.estado }. Tiempo estimado 5 minutos")
        Thread.sleep(3000)
        pedido.estado = "REPARTO"
        println("ESTADO PEDIDO: ${pedido.estado }. Tiempo estimado 25 minutos")
        Thread.sleep(4000)
        pedido.estado = "ENTREGADO"
        println("ESTADO PEDIDO: ${pedido.estado }.")

    }

    fun boleta(){
        pedido.totalPedido = pizza.precio * pedido.numeroPizzas

        println("==========================")
        println("===  Boleta Di Lorenzo ===")
        println("==========================")
        println()
        pedido.idPedido = UUID.randomUUID().toString()
        println("Numero boleta: " + pedido.idPedido)
        imprimirDatosCliente()
        println("Pizza: " + pizza.nombre)
        println("Valor Unidad: " + pizza.precio)
        println("Cantidad: " + pedido.numeroPizzas)
        pedido.totalPedido = aplicarDescuento(pedido.totalPedido, pedido.numeroPizzas)
        print("Total a pagar: " + pedido.totalPedido)

        println()
        println("¡Gracias por preferir Pizzeria Di Lorenzo!")
        println()
    }

    fun ingresarPedido(){
        saludarCliente()
        datosCliente()
        datosPizza()
        println("Generando tu boleta...")
        Thread.sleep(1000)
        boleta()
        estadoPedido()
    }

    ingresarPedido()
}