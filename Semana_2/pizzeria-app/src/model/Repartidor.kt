package model

class Repartidor(
    nombre: String = "",
    telefono: Int = 0,
    var estadoRepartidor: EstadoRepartidor = EstadoRepartidor.DISPONIBLE
): Usuario(nombre, telefono)