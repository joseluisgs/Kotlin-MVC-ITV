package models

import extensions.roundTo
import interfaces.IMecanica
import models.enums.TipoMotor

class Motocicleta(
    marca: String,
    modelo: String,
    matricula: String,
    motor: TipoMotor,
    var carenado: Boolean,
    precio: Double
) : Vehiculo(marca, modelo, matricula, motor, precio), IMecanica {

    override var velocidad: Double = 0.0
    override fun acelerar(velocidad: Double) {
        this.velocidad += velocidad * 0.95
    }

    override fun frenar(velocidad: Double) {
        this.velocidad -= velocidad * 0.95
    }

    override fun arrancar(): String {
        return "Arrancando moto"
    }

    override fun toString(): String {
        return "Motocicleta(id=$id, marca=$marca, modelo=$modelo, matricula=$matricula, motor=$motor, precio=${
            precio.roundTo(
                2
            )
        }, carenado=$carenado, velocidad=$velocidad)"
    }


}
