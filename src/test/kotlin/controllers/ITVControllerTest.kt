package controllers

import errors.VehiculoException
import factories.VehiculosFactory
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
        val res = ITVController.getVehiculo(vehiculo.matricula)
        assertAll(
            { assertNotNull(res) },
            { assertEquals(vehiculo.id, res.id) },
            { assertEquals(vehiculo.marca, res.marca) },
            { assertEquals(vehiculo.modelo, res.modelo) },
            { assertEquals(vehiculo.matricula, res.matricula) },
            { assertEquals(vehiculo.motor, res.motor) },
            { assertEquals(vehiculo.precio, res.precio) }
        )

    }

    @Test
    fun getVehiculoNotExistsException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.getVehiculo(vehiculo.matricula)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con matricula ${vehiculo.matricula}"))
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
    fun createVehiculoOtherException() {
        ITVController.createVehiculo(vehiculo)
        val ex = assertThrows<VehiculoException> {
            ITVController.createVehiculo(vehiculo)
        }
        assertTrue(ex.message!!.contains("Ya existe un vehiculo con la matricula ${vehiculo.matricula}. No se puede crear otro con la misma matricula"))
    }

    @Test
    fun updateVehiculo() {
        val vh = ITVController.createVehiculo(vehiculo)
        val mat = vehiculo.matricula
        vh.apply {
            marca = "marcaModificada"
            modelo = "modeloModificado"
            matricula = "matriculaModificada"
            motor = TipoMotor.DIESEL
        }

        val res = ITVController.updateVehiculo(mat, vh)
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
    fun updateVehiculoNotExistsException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.updateVehiculo(vehiculo.matricula, vehiculo)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con matricula ${vehiculo.matricula}"))
    }

    @Test
    fun updateVehiculoUpdateOtherException() {
        val factory = VehiculosFactory()
        val v1 = factory.getVehiculo()
        val v2 = factory.getVehiculo()
        ITVController.createVehiculo(v1)
        ITVController.createVehiculo(v2)
        val mat = v2.matricula
        v2.matricula = v1.matricula

        val ex = assertThrows<VehiculoException> {
            ITVController.updateVehiculo(mat, v2)
        }
        assertTrue(ex.message!!.contains("Ya existe un vehiculo con matricula ${v2.matricula}. No puedes modificar datos de otro vehiculo"))
    }

    @Test
    fun deleteVehiculo() {
        val vh = ITVController.createVehiculo(vehiculo)
        val res = ITVController.deleteVehiculo(vehiculo.matricula)
        assertAll(
            { assertEquals(vh.id, res.id) },
            { assertEquals(vh.marca, res.marca) },
            { assertEquals(vh.modelo, res.modelo) },
            { assertEquals(vh.matricula, res.matricula) },
            { assertEquals(vh.motor, res.motor) },
        )
    }

    @Test
    fun deleteVehiculoNotExistsException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.deleteVehiculo(vehiculo.matricula)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con matricula ${vehiculo.matricula}"))
    }
}