package model

class Clientes(iniciales: List<Cliente>) {
    private val registrados = iniciales.toMutableSet()

    fun buscar(cliente: Cliente): Cliente? {
        return registrados.find { it == cliente }
    }

    fun registrar(cliente: Cliente): Cliente {
        val existente = buscar(cliente)
        if (existente != null) {
            println("Bienvenido de vuelta, ${existente.nombre}")
            return existente
        }
        registrados.add(cliente)
        println("Cliente registrado")
        return cliente
    }
}