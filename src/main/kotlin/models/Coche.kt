package models

import extensions.roundTo
import interfaces.IMecanica
import models.enums.TipoMotor

class Coche(
    marca: String,
    modelo: String,
    matricula: String,
    motor: TipoMotor,
    var numPlazas: Int,
    precio: Double,
) : Vehiculo(marca, modelo, matricula, motor, precio), IMecanica {

    override var velocidad: Double = 0.0
    override fun acelerar(velocidad: Double) {
        this.velocidad += velocidad
    }

    override fun frenar(velocidad: Double) {
        this.velocidad -= velocidad
    }

    override fun arrancar(): String {
        return "Arrancando coche"
    }

    override fun toString(): String {
        return "Coche(id=$id, marca=$marca, modelo=$modelo, matricula=$matricula, motor=$motor, precio=${
            precio.roundTo(
                2
            )
        } createdAt=$createdAt, numPlazas=$numPlazas, velocidad=$velocidad)"
    }


}