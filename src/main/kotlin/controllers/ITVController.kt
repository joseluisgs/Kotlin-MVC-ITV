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

    fun getVehiculoByMatricula(matricula: String): Vehiculo {
        return vehiculosRepository.findByMat(matricula)
            ?: throw VehiculoException("Vehiculo no encontrado con matricula: $matricula")
    }

    fun getVehiculoById(id: Int): Vehiculo {
        return vehiculosRepository.findById(id)
            ?: throw VehiculoException("Vehiculo no encontrado con id: $id")
    }

    fun createVehiculo(vehiculo: Vehiculo): Vehiculo {
        // No se puede crear un vehiculo con una matricula que ya existe, si no excepcion
        vehiculosRepository.findByMat(vehiculo.matricula) ?: return vehiculosRepository.save(vehiculo)
        throw VehiculoException("Ya existe un vehiculo con la matricula ${vehiculo.matricula}. No se puede crear otro con la misma matricula")
    }

    fun updateVehiculo(id: Int, vehiculo: Vehiculo): Vehiculo {
        // Debemos comprobar si la nueva matricula ya existe, si existe y el id es distinto, lanzar excepcion
        // porque no me pertenece y no puedo cambiar datos de otro vehiculo
        val vehiculoExistente = vehiculosRepository.findByMat(vehiculo.matricula)
            ?: throw throw VehiculoException("Vehiculo no encontrado con id: $id")

        if (vehiculoExistente != null && vehiculoExistente.id != id) {
            throw VehiculoException("Ya existe un vehiculo con matricula ${vehiculo.matricula}. No puedes modificar datos de otro vehiculo")
        }
        return vehiculosRepository.update(id, vehiculo)
            ?: throw VehiculoException("Vehiculo no encontrado con id: $id")
    }

    fun deleteVehiculo(matricula: String): Vehiculo {
        val vehiculo = vehiculosRepository.findByMat(matricula)
            ?: throw VehiculoException("Vehiculo no encontrado con matricula: $matricula")
        return vehiculosRepository.delete(vehiculo.id)!!
    }

    fun realizarRevision(operador: Operador, vehiculos: MutableList<Vehiculo>): Revision? {
        val lineas = mutableListOf<LineaRevision>()
        for (vehiculo in vehiculos) {
            val linea = LineaRevision(vehiculo)
            lineas.add(linea)
        }
        val revision = Revision(operador, lineas)
        return revisionesRepository.save(revision)
    }

}