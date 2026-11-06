package model

class Repartidor(
    nombre: String = "",
    telefono: String = "",
    var estadoRepartidor: EstadoRepartidor = EstadoRepartidor.DISPONIBLE
): Usuario(nombre, telefono)