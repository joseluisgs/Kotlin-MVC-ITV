package models

import extensions.roundTo
import java.time.LocalDateTime

data class Revision(
    val operador: Operador,
    val lineas: MutableList<LineaRevision>
) {
    val id: Int = ++contador
    val total: Double
        get() {
            var miTotal = 0.0
            for (linea in lineas) {
                miTotal += linea.subtotal
            }
            return miTotal.roundTo(2)
        }
    val createdAt = LocalDateTime.now()

    companion object {
        private var contador: Int = 0
    }

    override fun toString(): String {
        return "Revision(id=$id, operador=$operador, lineas=$lineas, total=$total, createdAt=$createdAt)"
    }
}