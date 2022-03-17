package views

import comparators.VehiculoPorCreatedAtComparator
import comparators.VehiculoPorMarcaComparator
import comparators.VehiculoPorTipoComparator
import controllers.ITVController
import models.*
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
        val regex = "[1-7]".toRegex() // Es equivalente a Regex("[1-6]")
        var opcion: String? = null
        do {
            println(
                """
            1. Listar Vehículos
            2. Consultar Vehículo
            3. Añadir Vehículo
            4. Modificar Vehículo
            5. Eliminar Vehículo
            6. Realizar Revisión
            7. Salir
            """.trimIndent()
            )
            print("Seleccione una opción [1-7]: ")
            opcion = readLine() ?: ""
            if (!regex.matches(opcion)) {
                println("Opción no válida. Inténtelo de nuevo.")
            }
        } while (!opcion?.trim()!!.matches(regex))

        when (opcion) {
            "1" -> listarVehiculos()
            "2" -> consultarVehiculo()
            "3" -> crearVehiculo()
            "4" -> modificarVehiculo()
            "5" -> eliminarVehiculo()
            "6" -> realizarRevision()
            "7" -> salir()
        }
        menu()
    }

    private fun salir() {
        println("Gracias por utilizar el sistema de gestión de la ITV")
        exitProcess(0)
    }


    private fun realizarRevision() {
        println("Realizar Revisión")
        // No me apetece hacer mas factiries o entrada de datos para el operador.
        // Voy a suponer que como se ha logueado el operador, es el mismo que realiza la revisión y sus datos ya están cargados.
        val operador = Operador("Pepe", "pepe@prueba.com")
        println("Le atiende el operador: $operador")

        val lineasRevision = inputLineasRevision()

        try {
            // Realizamos la revisión
            val res = ITVController.realizarRevision(operador, lineasRevision)
            println("La revisión se ha realizado correctamente.")
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun inputLineasRevision(): MutableList<LineaRevision> {
        val lineas = mutableListOf<LineaRevision>()
        var correcto = false
        do {
            print("Introduzca las matriculas de los vehículos a revisar separada por comas: ")
            val matriculas = readLine()?.split(",") ?: listOf()
            // revisamos que las matriculas introducidas existan
            for (matricula in matriculas) {
                try {
                    val vehiculo = ITVController.getVehiculo(matricula.trim().uppercase())
                    lineas.add(LineaRevision(vehiculo))
                } catch (e: Exception) {
                    println(e.message)
                }
            }
            // Si salimos de aqui es que todo es correcto
            if (lineas.isEmpty()) {
                println("No se han introducido matriculas válidas. Vuelva a intentarlo.")
            } else {
                correcto = true
            }
        } while (!correcto)
        return lineas
    }

    private fun modificarVehiculo() {
        println("Modificar Vehículo")
        val mat = readMatricula()
        try {
            val vehiculo = ITVController.getVehiculo(mat)
            println("Actualizando: $vehiculo")
            println("Introduzca los nuevos datos del vehículo o pulse intro para conservar el valor actual")
            if (vehiculo is Coche) {
                actualizarCoche(mat, vehiculo)
            } else {
                actualizarMoto(mat, vehiculo as Motocicleta)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun actualizarMoto(mat: String, vehiculo: Motocicleta) {
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
            val res = ITVController.updateVehiculo(mat, vehiculo)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun actualizarCoche(mat: String, vehiculo: Coche) {
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
            val res = ITVController.updateVehiculo(mat, vehiculo)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun crearVehiculo() {
        println("Añadir Vehículo")
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
            "1" -> crearCoche()
            "2" -> crearMotocicleta()
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
        println("Eliminar Vehículo")
        val mat = readMatricula()
        try {
            val res = ITVController.deleteVehiculo(mat)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }

    }

    private fun consultarVehiculo() {
        println("Consultar Vehículo")
        val mat = readMatricula()
        try {
            val res = ITVController.getVehiculo(mat)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun listarVehiculos() {
        println("Listar Vehículos")
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
            "1" -> res = vehiculos.sortedWith(VehiculoPorTipoComparator())
            "2" -> res = vehiculos.sortedWith(VehiculoPorMarcaComparator())
            "3" -> res = vehiculos.sortedWith(VehiculoPorCreatedAtComparator())
        }
        for (vehiculo in res) {
            println(vehiculo)
        }
    }
}