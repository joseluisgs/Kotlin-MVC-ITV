package controllers

import errors.VehiculoException
import factories.VehiculosFactory
import models.LineaRevision
import models.Operador
import models.Revision
import models.Vehiculo
import repositories.RevisionesRepository
import repositories.VehiculosRepository

object ITVController {
    val vehiculosRepository = VehiculosRepository()
    val revisionesRepository = RevisionesRepository()

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

    fun getVehiculo(matricula: String): Vehiculo {
        return vehiculosRepository.findById(matricula)
            ?: throw VehiculoException("Vehiculo no encontrado con matricula: $matricula")
    }

    fun createVehiculo(vehiculo: Vehiculo): Vehiculo {
        // No se puede crear un vehiculo con una matricula que ya existe, si no excepcion
        vehiculosRepository.findById(vehiculo.matricula) ?: return vehiculosRepository.save(vehiculo)
        throw VehiculoException("Ya existe un vehiculo con la matricula ${vehiculo.matricula}. No se puede crear otro con la misma matricula")
    }

    fun updateVehiculo(matricula: String, vehiculo: Vehiculo): Vehiculo {
        // Debemos comprobar si la nueva matricula ya existe, si existe y el id es distinto, lanzar excepcion
        // porque no me pertenece y no puedo cambiar datos de otro vehiculo
        val vehiculoExistente = vehiculosRepository.findById(vehiculo.matricula)
        if (vehiculoExistente != null && vehiculoExistente.id != vehiculo.id) {
            throw VehiculoException("Ya existe un vehiculo con matricula ${vehiculo.matricula}. No puedes modificar datos de otro vehiculo")
        }
        return vehiculosRepository.update(matricula, vehiculo)
            ?: throw VehiculoException("Vehiculo no encontrado con matricula $matricula")
    }

    fun deleteVehiculo(matricula: String): Vehiculo {
        return vehiculosRepository.delete(matricula)
            ?: throw VehiculoException("Vehiculo no encontrado con matricula $matricula")
    }

    fun realizarRevision(operador: Operador, lineasRevision: MutableList<LineaRevision>): Revision? {
        val revision = Revision(operador, lineasRevision)
        return revisionesRepository.save(revision)
    }

}