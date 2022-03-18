package factories

import extensions.roundTo
import models.Coche
import models.Motocicleta
import models.Vehiculo
import models.enums.TipoMotor
import kotlin.random.Random

class VehiculosFactory {

    fun getVehiculo(): Vehiculo {
        val randomized = (1..2).random()
        return when (randomized) {
            1 -> createCoche()
            2 -> createMotocicleta()
            else -> throw IllegalArgumentException("Tipo de vehiculo no soportado")
        }
    }

    private fun createMotocicleta(): Motocicleta {
        val marcas = listOf("Honda", "Yamaha", "Suzuki", "Kawasaki", "KTM", "BMW", "Ducati")
        val modelos = listOf("CBR", "R1", "GSX-R1000", "Ninja", "KX450", "KX450", "KX450")
        val tipos = listOf(TipoMotor.ELECTRICO, TipoMotor.GASOLINA)
        val matricula = getMatriculaRandom()

        return Motocicleta(
            marcas.random(),
            modelos.random(),
            matricula,
            tipos.random(),
            Random.nextBoolean(),
            Random.nextDouble(25.0, 60.0).roundTo(2)
        )

    }

    private fun createCoche(): Coche {
        val marcas = listOf("Ford", "Chevrolet", "Fiat", "Renault", "Opel", "Seat")
        val modelos = listOf("Focus", "Mondeo", "Punto", "Kangoo", "Fiesta", "Corsa")
        val tipos = listOf(TipoMotor.ELECTRICO, TipoMotor.GASOLINA, TipoMotor.HIBRIDO, TipoMotor.DIESEL)
        val matricula = getMatriculaRandom()

        return Coche(
            marcas.random(),
            modelos.random(),
            matricula,
            tipos.random(),
            (1..2).random(),
            Random.nextDouble(50.0, 100.0).roundTo(2)
        )
    }

    private fun getMatriculaRandom(): String {
        val letra = ('A'..'Z').random()
        val matricula: StringBuilder = StringBuilder()
        matricula.append(letra)
        val time = System.nanoTime()
        matricula.append(time.toString().substring(time.toString().length - 5))
        return matricula.toString()
    }
}