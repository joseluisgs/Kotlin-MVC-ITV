package controllers

import errors.VehiculoException
import models.Coche
import models.enums.TipoMotor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ITVControllerTest {

    private val vehiculo = Coche(
        marca = "pruebaMarca",
        modelo = "PruebaModelo",
        matricula = "A12345",
        motor = TipoMotor.GASOLINA,
        numPlazas = 5,
        precio = 100.0
    )

    @BeforeEach
    fun setUpTest() {
        ITVController.clearData()
    }

    @Test
    fun getVehiculos() {
        val vehiculos = ITVController.getVehiculos()
        assertEquals(0, vehiculos.size)
    }

    @Test
    fun getVehiculo() {
        ITVController.createVehiculo(vehiculo)
        val res = ITVController.getVehiculo(vehiculo.id)
        assertAll(
            { assertNotNull(res) },
            { assertEquals(vehiculo.id, res.id) }
        )

    }

    @Test
    fun getVehiculoException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.getVehiculo(vehiculo.id)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con id: ${vehiculo.id}"))
    }

    @Test
    fun createVehiculo() {
        val res = ITVController.createVehiculo(vehiculo)
        assertAll(
            { assertNotNull(res) },
            { assertEquals(vehiculo.id, res.id) },
            { assertEquals(vehiculo.marca, res.marca) },
            { assertEquals(vehiculo.modelo, res.modelo) },
            { assertEquals(vehiculo.matricula, res.matricula) },
            { assertEquals(vehiculo.motor, res.motor) },
        )
    }

    @Test
    fun updateVehiculo() {
        val vh = ITVController.createVehiculo(vehiculo)
        vh.apply {
            marca = "marcaModificada"
            modelo = "modeloModificado"
            matricula = "matriculaModificada"
            motor = TipoMotor.DIESEL
        }

        val res = ITVController.updateVehiculo(vh.id, vh)
        assertAll(
            { assertNotNull(res) },
            { assertEquals(vehiculo.id, res.id) },
            { assertEquals(vehiculo.marca, res.marca) },
            { assertEquals(vehiculo.modelo, res.modelo) },
            { assertEquals(vehiculo.matricula, res.matricula) },
            { assertEquals(vehiculo.motor, res.motor) },
        )
    }

    @Test
    fun updateVehiculoException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.updateVehiculo(vehiculo.id, vehiculo)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con id: ${vehiculo.id}"))
    }

    @Test
    fun deleteVehiculo() {
        val vh = ITVController.createVehiculo(vehiculo)
        val res = ITVController.deleteVehiculo(vehiculo.id)
        assertAll(
            { assertEquals(vh.id, res.id) },
            { assertEquals(vh.marca, res.marca) },
            { assertEquals(vh.modelo, res.modelo) },
            { assertEquals(vh.matricula, res.matricula) },
            { assertEquals(vh.motor, res.motor) },
        )
    }

    @Test
    fun deleteVehiculoException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.deleteVehiculo(vehiculo.id)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con id: ${vehiculo.id}"))
    }
}