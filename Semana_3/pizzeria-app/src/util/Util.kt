package util

import model.Cliente
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

class Util {

    //Reflection
    fun listarMiembros(clase: KClass<*>) {
        println("=== ${clase.simpleName} ===")
        println("Propiedades:")
        clase.members.filterIsInstance<KProperty<*>>().forEach { println("  - ${it.name}") }
        println("Métodos:")
        clase.members.filterIsInstance<KFunction<*>>().forEach { println("  - ${it.name}") }
        println()
    }

    //Desestructuración  Cliente
    fun imprimirCliente(cliente: Cliente) {
        val (nombre, correo, telefono) = cliente
        println("Nombre: $nombre")
        println("Correo: $correo")
        println("Teléfono: $telefono")
    }

    fun pedirTexto(mensaje: String, error: String): String{
        while(true){
            print(mensaje)
            val entrada = (readLine() ?: "").trim()
            if(entrada.isNotBlank()) return entrada
            println(error)
        }
    }

    fun pedirEntero(mensaje: String, minimoInt: Int, maximoInt: Int): Int{
        while(true){
            print(mensaje)
            try{
                val numero = (readLine() ?: "0").trim().toInt()
                if(numero in minimoInt..maximoInt) return numero
                println("Ingresa un valor correcto")
            } catch (e: NumberFormatException){
                println("Error: Debe ser un número")
            }
        }
    }

    fun rangoUnidades(numero: Int): Boolean{
        val rango = 1..100
        return numero in rango
    }

    fun pedirSiNo(mensaje: String): Boolean {
        while (true) {
            print(mensaje)
            val respuesta = (readLine() ?: "").trim()
            if (respuesta.equals("si", ignoreCase = true)) {
                return true
            }
            if (respuesta.equals("no", ignoreCase = true)) {
                return false
            }
            println("Responde SI o NO")
        }
    }

    //Regex
    fun esCorreoValido(correo: String): Boolean {
        return correo.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }

    fun pedirCorreo(mensaje: String): String {
        while(true){
            print(mensaje)
            val entrada = (readLine() ?: "").trim()
            if (esCorreoValido(entrada)) {
                 return  entrada
            }
            println("Correo inválido, se usará utiliza un formato correo@ejemplo.com")
        }
    }

    fun pedirTelefono(mensaje: String, minimoInt: Int, maximoInt: Int): String?{
        while(true){
            print(mensaje)
            try{
                val numero = (readLine() ?: "0").trim().toInt()
                if(numero in minimoInt..maximoInt){
                   return formatearTelefono("569$numero")
                }
                println("Ingresa un valor correcto")
            } catch (e: NumberFormatException){
                println("Error: Debe ser un número")
            }
        }
    }

    fun formatearTelefono(numero: String): String? {
        //Limpia la respuesta del usuario sacando todos los elementos que no sean digitos
        val digitos = numero.filter { it.isDigit() }
        //Si digitos no hace match con 8 dígitos retorna null
        val partes = Regex("^569(\\d{8})$").matchEntire(digitos) ?: return null
        return "+569 ${partes.groupValues[1]}"
    }


}