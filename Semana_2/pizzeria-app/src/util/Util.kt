package util

class Util {

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

    fun esCorreoValido(correo: String): Boolean {
        return correo.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }

    fun pedirCorreo(mensaje: String): String {
        print(mensaje)
        val entrada = (readLine() ?: "").trim()
        return if (esCorreoValido(entrada)) {
            entrada
        } else {
            println("Correo inválido, se usará correo@invalido.com")
            "correo@invalido.com"
        }
    }
}