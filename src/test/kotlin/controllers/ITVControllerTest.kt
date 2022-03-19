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
    fun getVehiculoByMatricula() {
        ITVController.createVehiculo(vehiculo)
        val res = ITVController.getVehiculoByMatricula(vehiculo.matricula)
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
    fun getVehiculoById() {
        ITVController.createVehiculo(vehiculo)
        val res = ITVController.getVehiculoById(vehiculo.id)
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
    fun getVehiculoNotExistsMatriculaException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.getVehiculoByMatricula(vehiculo.matricula)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con matricula: ${vehiculo.matricula}"))
    }

    @Test
    fun getVehiculoNotExistsIdException() {
        val ex = assertThrows<VehiculoException> {
            ITVController.getVehiculoById(vehiculo.id)
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
        vh.apply {
            marca = "marcaModificada"
            modelo = "modeloModificado"
            matricula = "matriculaModificada"
            motor = TipoMotor.DIESEL
        }

        val res = ITVController.updateVehiculo(vehiculo.id, vh)
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
        val id = -1
        val ex = assertThrows<VehiculoException> {
            ITVController.updateVehiculo(id, vehiculo)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con id: $id"))
    }

    @Test
    fun updateVehiculoUpdateOtherException() {
        val factory = VehiculosFactory()
        val v1 = factory.getVehiculo()
        val v2 = factory.getVehiculo()
        ITVController.createVehiculo(v1)
        ITVController.createVehiculo(v2)
        v2.matricula = v1.matricula

        val ex = assertThrows<VehiculoException> {
            ITVController.updateVehiculo(v2.id, v2)
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
        val mat = "NO-EXISTE"
        val ex = assertThrows<VehiculoException> {
            ITVController.deleteVehiculo(mat)
        }
        assertTrue(ex.message!!.contains("Vehiculo no encontrado con matricula: $mat"))
    }
}