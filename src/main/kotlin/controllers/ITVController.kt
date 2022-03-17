package controllers

import errors.VehiculoException
import factories.VehiculosFactory
import models.Vehiculo
import repositories.VehiculosRepository

object ITVController {
    val vehiculosRepository = VehiculosRepository()

    fun loadData() {
        val factory = VehiculosFactory()
        val numVehiculos = 10
        repeat(numVehiculos) {
            val vehiculo = factory.getVehiculo()
            vehiculosRepository.save(vehiculo)
        }
    }

    fun clearData() {
        vehiculosRepository.deleteAll()
    }

    fun getVehiculos() = vehiculosRepository.findAll()

    fun getVehiculo(id: Int): Vehiculo {
        return vehiculosRepository.findById(id) ?: throw VehiculoException("Vehiculo no encontrado con id: $id")
    }

    fun createVehiculo(vehiculo: Vehiculo): Vehiculo {
        return vehiculosRepository.save(vehiculo)
    }

    fun updateVehiculo(id: Int, vehiculo: Vehiculo): Vehiculo {
        return vehiculosRepository.update(id, vehiculo) ?: throw VehiculoException("Vehiculo no encontrado con id: $id")
    }

    fun deleteVehiculo(id: Int): Vehiculo {
        return vehiculosRepository.delete(id) ?: throw VehiculoException("Vehiculo no encontrado con id: $id")
    }

}