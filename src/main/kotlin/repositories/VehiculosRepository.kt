package repositories

import models.Vehiculo

class VehiculosRepository : CRUDRepository<Vehiculo, String> {
    // también puedo escribirlo como vehiculos = mutableMapOf<String, Vehiculo>()
    private var vehiculos: MutableMap<String, Vehiculo> = mutableMapOf()

    override fun findAll(): List<Vehiculo> {
        return vehiculos.values.toList()
    }

    override fun findById(matricula: String): Vehiculo? {
        return vehiculos[matricula]
    }

    override fun save(entity: Vehiculo): Vehiculo {
        vehiculos[entity.matricula] = entity
        return entity
    }

    override fun update(matricula: String, entity: Vehiculo): Vehiculo? {
        // Como no se puede cambiar la clave, borro e inserto el nuevo elemento
        // vehiculos[matricula] = entity
        vehiculos.remove(matricula) ?: return null
        vehiculos[entity.matricula] = entity
        return entity
    }

    override fun delete(matricula: String): Vehiculo? {
        return vehiculos.remove(matricula)
    }

    override fun deleteAll() {
        vehiculos.clear()
    }
}