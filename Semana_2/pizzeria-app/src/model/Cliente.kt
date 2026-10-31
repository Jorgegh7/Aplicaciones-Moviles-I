package model

class Cliente(
    nombre: String = "",
    telefono: Int = 0,
    var direccion: String = "",
    var correo: String = "correo@invalido.com",
) : Usuario(nombre, telefono){

    override fun imprimirDatos(){
        println("Nombre Cliente: $nombre")
        println("Dirección: $direccion")
        println("Teléfono: +569$telefono")
        println("Correo: $correo")
        println()
    }
}