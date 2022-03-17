package comparators

import models.Vehiculo

class VehiculoPorCreatedAtComparator : Comparator<Vehiculo> {
    override fun compare(o1: Vehiculo, o2: Vehiculo): Int {
        return o1.createdAt.compareTo(o2.createdAt)
    }
}
