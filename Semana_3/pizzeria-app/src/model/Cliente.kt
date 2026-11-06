package model

class Cliente(
    nombre: String = "",
    telefono: String = "",
    var direccion: String = "",
    var correo: String = "correo@invalido.com",
) : Usuario(nombre, telefono){

    override fun imprimirDatos(){
        println("Nombre Cliente: $nombre")
        println("Dirección: $direccion")
        println("Teléfono: $telefono")
        println("Correo: $correo")
        println()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cliente) return false
        return nombre == other.nombre && correo == other.correo
    }

    override fun hashCode(): Int {
        return 31 * nombre.hashCode() + correo.hashCode()
    }

    // Desestructuración
    operator fun component1(): String = nombre
    operator fun component2(): String = correo
    operator fun component3(): String = telefono
}