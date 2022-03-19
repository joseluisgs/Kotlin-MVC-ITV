package extensions

import kotlin.math.pow
import kotlin.math.roundToInt

// Función de extension para aplicar el redondeo :)
fun Double.roundTo(numDecimalDigits: Int): Double {
    val factor = 10.0.pow(numDecimalDigits.toDouble())
    return (this * factor).roundToInt() / factor
}