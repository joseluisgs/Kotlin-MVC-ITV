package models

/**
 * Clase que representa un operador
 * He usado data class para hacerla más directa... en el fondo todas son POKOs
 */
data class Operador(
    val username: String,
    val email: String
) {
    val id: Int = ++contador

    companion object {
        private var contador: Int = 0
    }

    override fun toString(): String {
        return "Operador(id=$id, username=$username, email=$email)"
    }
}