package repositories

import models.Coche
import models.enums.TipoMotor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VehiculosRepositoryTest {
    private var vehiculosRepository = VehiculosRepository()
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
        vehiculosRepository = VehiculosRepository()
    }

    @Test
    fun findAll() {
        val res = vehiculosRepository.findAll()
        assertAll(
            { assertNotNull(res) },
            { assertEquals(0, res.size) }
        )
    }

    @Test
    fun findById() {
        val res = vehiculosRepository.findById("NO-EXISTE")
        assertNull(res)
        vehiculosRepository.save(vehiculo)
        val res2 = vehiculosRepository.findById(vehiculo.matricula)
        assertAll(
            { assertNotNull(res2) },
            { assertEquals(vehiculo.id, res2?.id) },
            { assertEquals(vehiculo.marca, res2?.marca) },
            { assertEquals(vehiculo.modelo, res2?.modelo) },
            { assertEquals(vehiculo.matricula, res2?.matricula) },
            { assertEquals(vehiculo.motor, res2?.motor) },
            { assertEquals(vehiculo.precio, res2?.precio) }
        )
    }

    @Test
    fun save() {
        val res = vehiculosRepository.save(vehiculo)
        val list = vehiculosRepository.findAll()
        assertAll(
            { assertNotNull(res) },
            { assertEquals(vehiculo.id, res.id) },
            { assertEquals(1, list.size) },
            { assertEquals(vehiculo.id, list[0].id) }
        )
    }

    @Test
    fun update() {
        val res = vehiculosRepository.save(vehiculo)
        val mat = vehiculo.matricula
        res.marca = "pruebaMarca2"
        res.modelo = "PruebaModelo2"
        val update = vehiculosRepository.update(mat, res)
        val list = vehiculosRepository.findAll()
        assertAll(
            { assertNotNull(update) },
            { assertEquals(update?.id, res.id) },
            { assertEquals(update?.marca, res.marca) },
            { assertEquals(update?.modelo, res.modelo) },
            { assertEquals(update?.matricula, res.matricula) },
            { assertEquals(update?.motor, res.motor) },
            { assertEquals(update?.precio, res.precio) },
            { assertEquals(1, list.size) },
            { assertEquals(update?.id, list[0].id) },
            { assertEquals(update?.marca, list[0].marca) },
            { assertEquals(update?.modelo, list[0].modelo) },
            { assertEquals(update?.matricula, list[0].matricula) },
            { assertEquals(update?.motor, list[0].motor) },
            { assertEquals(update?.precio, list[0].precio) }
        )
    }

    @Test
    fun delete() {
        val res = vehiculosRepository.save(vehiculo)
        val deleted = vehiculosRepository.delete(res.matricula)
        val list2 = vehiculosRepository.findAll()
        assertAll(
            { assertNotNull(list2) },
            { assertEquals(0, list2.size) },
            { assertEquals(vehiculo.id, deleted?.id) },
            { assertEquals(vehiculo.marca, deleted?.marca) },
            { assertEquals(vehiculo.modelo, deleted?.modelo) },
            { assertEquals(vehiculo.matricula, deleted?.matricula) },
            { assertEquals(vehiculo.motor, deleted?.motor) },
            { assertEquals(vehiculo.precio, deleted?.precio) }
        )
    }
}