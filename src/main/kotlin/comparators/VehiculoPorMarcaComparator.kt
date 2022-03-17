package comparators

import models.Vehiculo

class VehiculoPorMarcaComparator : Comparator<Vehiculo> {
    override fun compare(o1: Vehiculo, o2: Vehiculo): Int {
        return o1.marca.compareTo(o2.marca)
    }
}
