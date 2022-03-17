package views.inputs

import models.enums.TipoMotor
import models.enums.TipoVehiculo

fun readID(): String {
    val regex = "\\d+".toRegex() // Es equivalente a Regex("\\d+")
    var codigo = ""
    do {
        print("Introduzca el código del vehículo que desea consultar: ")
        codigo = readLine() ?: ""
        if (!codigo.matches(regex)) {
            println("El código id debe ser un número entero")
        }
    } while (!codigo.matches(regex))
    return codigo
}

fun readMarca(): String {
    print("Introduzca la marca: ")
    return readln().trim()
}

fun updateMarca(marca: String): String {
    print("Introduzca la nueva marca [$marca]: ")
    val newMarca = readln()
    return if (newMarca.isNotEmpty()) newMarca.trim() else marca
}

fun readModelo(): String {
    print("Introduzca el modelo: ")
    return readln().trim()
}

fun updateModelo(modelo: String): String {
    print("Introduzca el nuevo modelo [$modelo]: ")
    val newModelo = readln()
    return if (newModelo.isNotEmpty()) newModelo.trim() else modelo
}

fun readTipoMotor(tipoVehiculo: TipoVehiculo): TipoMotor {
    val listTipos = mutableListOf<String>()
    for (tipo in TipoMotor.values()) {
        listTipos.add(tipo.name.uppercase())
    }

    if (tipoVehiculo == TipoVehiculo.MOTOCICLETA) {
        listTipos.remove("DIESEL")
        listTipos.remove("HIBRIDO")
    }


    var tipo: String? = null

    do {
        print("Introduzca el tipo de motor ${listTipos}: ")
        tipo = readln().trim().uppercase()
    } while (!listTipos.contains(tipo))
    return TipoMotor.valueOf(tipo!!)
}

fun updateTipoMotor(tipoMotor: TipoMotor, tipoVehiculo: TipoVehiculo): TipoMotor {
    val listTipos = mutableListOf<String>()
    for (tipo in TipoMotor.values()) {
        listTipos.add(tipo.name.uppercase())
    }

    if (tipoVehiculo == TipoVehiculo.MOTOCICLETA) {
        listTipos.remove("DIESEL")
        listTipos.remove("HIBRIDO")
    }

    var tipo: String? = null

    do {
        println("Introduzca el tipo de motor ${listTipos}: ")
        print("Motor actual [${tipoMotor.name}]: ")
        tipo = readln().trim().uppercase()
        if (tipo.isEmpty()) {
            tipo = tipoMotor.name
        }
    } while (!listTipos.contains(tipo))
    return TipoMotor.valueOf(tipo!!)
}

fun readNumPlazas(): Int {
    val regex = Regex("[2-5]")
    var num: String = ""
    do {
        print("Introduzca el número de plazas [2-5]: ")
        num = readln().trim()
    } while (!num.matches(regex))
    return num.toInt()
}

fun updateNumPlazas(numPlazas: Int): Int {
    val regex = Regex("[2-5]")
    var num: String = ""
    do {
        println("Introduzca el nuevo número de plazas [2-5]: ")
        print("Num plazas actuales [$numPlazas]: ")
        num = readln().trim()
        if (num.isEmpty()) {
            num = numPlazas.toString()
        }
    } while (!num.matches(regex))
    return num.toInt()
}

fun readMatricula(): String {
    val regex = "[A-Z][0-9]{5}|[0-9]{4}[A-Z]{3}".toRegex()
    var mat: String = ""
    do {
        print("Introduzca la matrícula [LNNNNN] o [NNNNLLL]: ")
        mat = readln().trim().uppercase()
    } while (!mat.matches(regex))
    return mat
}

fun updateMatricula(matricula: String): String {
    val regex = "[A-Z][0-9]{5}|[0-9]{4}[A-Z]{3}".toRegex()
    var mat: String = ""
    do {
        println("Introduzca la matrícula [LNNNNN] o [NNNNLLL]: ")
        print("Matricula actual [$matricula]: ")
        mat = readln().trim().uppercase()
        if (mat.isEmpty()) {
            mat = matricula
        }
    } while (!mat.matches(regex))
    return mat
}

fun readCarenado(): Boolean {
    var carenado: String? = null
    do {
        print("¿Tiene carenado? [S/N]: ")
        carenado = readln().trim().uppercase()
    } while (carenado != "S" && carenado != "N")
    return carenado == "S"
}

fun updateCarenado(carenado: Boolean): Boolean {
    var care: String? = null
    do {
        println("¿Tiene carenado? [S/N]: ")
        print("Carenado actual [${if (carenado) "S" else "N"}]: ")
        care = readln().trim().uppercase()
        if (care.isEmpty()) {
            care = if (carenado) "S" else "N"
        }
    } while (care != "S" && care != "N")
    return care == "S"
}

fun readPrecio(): Double {
    val regex = """^[0-9]\d+\.\d{1,2}${'$'}""".toRegex()
    var precio: String = ""
    do {
        print("Introduzca el precio [Ej: NN.DD]: ")
        precio = readln().trim()
    } while (!precio.matches(regex))
    return precio.toDouble()
}

fun updatePrecio(precio: Double): Double {
    val regex = """^[0-9]\d+\.\d{1,2}${'$'}""".toRegex()
    var pre: String = ""
    do {
        println("Introduzca el precio [Ej: NN.DD]: ")
        print(
            "Precio actual [$precio]: "
        )
        pre = readln().trim()
        if (pre.isEmpty()) {
            pre = precio.toString()
        }
    } while (!pre.matches(regex))
    return pre.toDouble()
}