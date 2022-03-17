package comparators

import models.Coche
import models.Motocicleta
import models.Vehiculo

class VehiculoPorTipoComparator : Comparator<Vehiculo> {
    override fun compare(o1: Vehiculo, o2: Vehiculo): Int {
        // forma sencilla de hacerlo
        //val tipo1 = getTipo(o1)
        //val tipo2 = getTipo(o2)
        // Usando API REFLECTION
        val tipo1 = o1::class.java.simpleName.uppercase()
        val tipo2 = o2::class.java.simpleName.uppercase()
        return tipo1.compareTo(tipo2)
    }

    fun getTipo(vehiculo: Vehiculo) = when (vehiculo) {
        is Coche -> "COCHE"
        is Motocicleta -> "MOTOCICLETA"
        else -> "OTRO"
    }
}
