package models

data class LineaRevision(
    val vehiculo: Vehiculo,
) {
    val id: Int = ++contador
    val subtotal = vehiculo.precio

    companion object {
        private var contador: Int = 0
    }
}
