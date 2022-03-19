package repositories

import models.Vehiculo

class VehiculosRepository : CRUDRepository<Vehiculo, Int> {
    // también puedo escribirlo como vehiculos = mutableMapOf<String, Vehiculo>()
    private var vehiculos: MutableMap<Int, Vehiculo> = mutableMapOf()

    override fun findAll(): List<Vehiculo> {
        return vehiculos.values.toList()
    }

    override fun findById(id: Int): Vehiculo? {
        return vehiculos[id]
    }

    fun findByMat(matricula: String): Vehiculo? {
        for (v in vehiculos.values) {
            if (v.matricula == matricula) {
                return v
            }
        }
        return null
    }

    override fun save(entity: Vehiculo): Vehiculo {
        vehiculos[entity.id] = entity
        return entity
    }

    override fun update(id: Int, entity: Vehiculo): Vehiculo? {
        // Como no se puede cambiar la clave, borro e inserto el nuevo elemento
        vehiculos[id] = entity
        return entity
    }

    override fun delete(id: Int): Vehiculo? {
        return vehiculos.remove(id)
    }

    override fun deleteAll() {
        vehiculos.clear()
    }
}