package models

import models.enums.TipoMotor
import java.time.LocalDateTime

/**
 * Clase Vehículo, es abstracta.
 */
abstract class Vehiculo(
    var marca: String,
    var modelo: String,
    var matricula: String,
    var motor: TipoMotor,
    var precio: Double = 0.0
) {
    val id: Int = ++contador
    val createdAt: LocalDateTime = LocalDateTime.now()

    companion object {
        private var contador: Int = 0
    }

    abstract fun arrancar(): String
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vehiculo) return false

        if (marca != other.marca) return false
        if (modelo != other.modelo) return false
        if (matricula != other.matricula) return false
        if (motor != other.motor) return false
        if (id != other.id) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = marca.hashCode()
        result = 31 * result + modelo.hashCode()
        result = 31 * result + matricula.hashCode()
        result = 31 * result + motor.hashCode()
        result = 31 * result + id
        result = 31 * result + createdAt.hashCode()
        return result
    }

    override fun toString(): String {
        return "Vehiculo(id=$id, marca=$marca, modelo=$modelo, matricula=$matricula, motor=$motor, precio=$precio, createdAt=$createdAt)"
    }


}