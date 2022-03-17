package views

import comparators.VehiculoPorCreatedAtComparator
import comparators.VehiculoPorMarcaComparator
import comparators.VehiculoPorTipoComparator
import controllers.ITVController
import models.Coche
import models.Motocicleta
import models.Vehiculo
import models.enums.TipoVehiculo
import views.inputs.*
import kotlin.system.exitProcess

object ITVView {
    init {
        ITVController.loadData()
    }

    fun presentacion() {
        println("Bienvenido al sistema de gestión de la empresa ITV")
        println("****************************************************")
    }

    fun menu() {
        val regex = "[1-6]".toRegex() // Es equivalente a Regex("[1-6]")
        var opcion: String? = null
        do {
            println(
                """
            1. Listar Vehículos
            2. Consultar Vehículo
            3. Añadir Vehículo
            4. Modificar Vehículo
            5. Eliminar Vehículo
            6. Salir
            """.trimIndent()
            )
            print("Seleccione una opción [1-6]: ")
            opcion = readLine() ?: ""
            if (!regex.matches(opcion)) {
                println("Opción no válida. Inténtelo de nuevo.")
            }
        } while (!opcion?.trim()!!.matches(regex))

        when (opcion) {
            "1" -> {
                println("Listar Vehículos")
                listarVehiculos()
            }
            "2" -> {
                println("Consultar Vehículo")
                consultarVehiculo()
            }
            "3" -> {
                println("Añadir Vehículo")
                crearVehiculo()
            }
            "4" -> {
                println("Modificar Vehículo")
                modificarVehiculo()
            }
            "5" -> {
                println("Eliminar Vehículo")
                eliminarVehiculo()
            }
            "6" -> {
                println("Adiós")
                exitProcess(0)
            }
        }
        menu()
    }

    private fun modificarVehiculo() {
        val id = readID()
        try {
            val vehiculo = ITVController.getVehiculo(id.toInt())
            println("Actualizando: $vehiculo")
            println("Introduzca los nuevos datos del vehículo o pulse intro para conservar el valor actual")
            if (vehiculo is Coche) {
                actualizarCoche(vehiculo)
            } else {
                actualizarMoto(vehiculo as Motocicleta)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun actualizarMoto(vehiculo: Motocicleta) {
        val marca = updateMarca(vehiculo.marca)
        val modelo = updateModelo(vehiculo.modelo)
        val matricula = updateMatricula(vehiculo.matricula)
        val motor = updateTipoMotor(vehiculo.motor, TipoVehiculo.MOTOCICLETA)
        val carenado = updateCarenado(vehiculo.carenado)
        val precio = updatePrecio(vehiculo.precio)
        vehiculo.apply {
            this.marca = marca
            this.modelo = modelo
            this.matricula = matricula
            this.motor = motor
            this.carenado = carenado
            this.precio = precio
        }
        try {
            val res = ITVController.updateVehiculo(vehiculo.id, vehiculo)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun actualizarCoche(vehiculo: Coche) {
        val marca = updateMarca(vehiculo.marca)
        val modelo = updateModelo(vehiculo.modelo)
        val matricula = updateMatricula(vehiculo.matricula)
        val motor = updateTipoMotor(vehiculo.motor, TipoVehiculo.COCHE)
        val numPlazas = updateNumPlazas(vehiculo.numPlazas)
        val precio = updatePrecio(vehiculo.precio)
        vehiculo.apply {
            this.marca = marca
            this.modelo = modelo
            this.matricula = matricula
            this.motor = motor
            this.numPlazas = numPlazas
            this.precio = precio
        }
        try {
            val res = ITVController.updateVehiculo(vehiculo.id, vehiculo)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun crearVehiculo() {
        // Elegir si es coche o motocicleta
        println("¿Es un coche [1] o una motocicleta [2]?")
        val regex = "[1-2]".toRegex() // Es equivalente a Regex("[1-2]")
        var opcion: String? = null
        do {
            opcion = readLine() ?: ""
            if (!regex.matches(opcion)) {
                println("Opción no válida, elija una opción del 1 para coche y 2 Motocicleta")
            }
        } while (!opcion?.trim()!!.matches(regex))
        when (opcion) {
            "1" -> {
                crearCoche()
            }
            "2" -> {
                crearMotocicleta()
            }
        }
    }

    private fun crearMotocicleta() {
        val marca = readMarca()
        val modelo = readModelo()
        val matricula = readMatricula()
        val motor = readTipoMotor(TipoVehiculo.MOTOCICLETA)
        val carenado = readCarenado()
        val precio = readPrecio()
        val moto = Motocicleta(marca, modelo, matricula, motor, carenado, precio)
        try {
            val res = ITVController.createVehiculo(moto)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun crearCoche() {
        val marca = readMarca()
        val modelo = readModelo()
        val matricula = readMatricula()
        val motor = readTipoMotor(TipoVehiculo.COCHE)
        val numPlazas = readNumPlazas()
        val precio = readPrecio()
        val coche = Coche(marca, modelo, matricula, motor, numPlazas, precio)
        try {
            val res = ITVController.createVehiculo(coche)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun eliminarVehiculo() {
        val codigo = readID()
        try {
            val res = ITVController.deleteVehiculo(codigo.toInt())
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }

    }

    private fun consultarVehiculo() {
        val codigo = readID()
        try {
            val res = ITVController.getVehiculo(codigo.toInt())
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun listarVehiculos() {
        val vehiculos = ITVController.getVehiculos()
        val regex = "[1-3]".toRegex() // Es equivalente a Regex("[1-6]")
        var opcion: String? = null
        var res = emptyList<Vehiculo>()
        do {
            println(
                """
            1. Por Tipo de Vehículo
            2. Por Marca
            3. Por Antigüedad
         
            """.trimIndent()
            )
            print("Seleccione una opción [1-3]: ")
            opcion = readLine() ?: ""
            if (!regex.matches(opcion)) {
                println("Opción no válida. Inténtelo de nuevo.")
            }
        } while (!opcion?.trim()!!.matches(regex))

        when (opcion) {
            "1" -> {
                res = vehiculos.sortedWith(VehiculoPorTipoComparator())
            }
            "2" -> {
                res = vehiculos.sortedWith(VehiculoPorMarcaComparator())
            }
            "3" -> {
                res = vehiculos.sortedWith(VehiculoPorCreatedAtComparator())
            }
        }
        for (vehiculo in res) {
            println(vehiculo)
        }
    }
}