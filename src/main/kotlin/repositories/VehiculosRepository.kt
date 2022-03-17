package repositories

import models.Vehiculo

class VehiculosRepository : CRUDRepository<Vehiculo, Int> {
    private var vehiculos: MutableMap<Int, Vehiculo> = mutableMapOf()

    override fun findAll(): List<Vehiculo> {
        return vehiculos.values.toList()
    }

    override fun findById(id: Int): Vehiculo? {
        return vehiculos[id]
    }

    override fun save(entity: Vehiculo): Vehiculo {
        vehiculos[entity.id] = entity
        return entity
    }

    override fun update(id: Int, entity: Vehiculo): Vehiculo? {
        return vehiculos.replace(id, entity)
    }

    override fun delete(id: Int): Vehiculo? {
        return vehiculos.remove(id)
    }

    override fun deleteAll() {
        vehiculos.clear()
    }
}