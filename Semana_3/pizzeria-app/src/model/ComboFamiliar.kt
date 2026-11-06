package model

@Promocionable
class ComboFamiliar(
    nombre: String,
    precio: Double,
    pizza: Pizza,
    bebida: Bebida
) : Combo(nombre, precio, pizza, bebida)