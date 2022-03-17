package interfaces

interface IMecanica {
    var velocidad: Double
    fun acelerar(velocidad: Double = 0.0)
    fun frenar(velocidad: Double = 0.0)
}