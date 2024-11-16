package Main

import algoritmos.NaivLoopUnrollingFour
import java.io.File

fun main() {

    val algoritmo = NaivLoopUnrollingFour()

    // Caso con matrices 16x16
    var startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 16x16:")
    val resultado16x16 = metodoConRutas("Matrices/Matriz 16x16/MatrizA.txt", "Matrices/Matriz 16x16/MatrizB.txt", algoritmo)
    resultado16x16.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")

    // Caso con matrices 32x32
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 32x32:")
    val resultado32x32 = metodoConRutas("Matrices/Matriz 32x32/MatrizA.txt", "Matrices/Matriz 32x32/MatrizB.txt", algoritmo)
    resultado32x32.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")

    // Caso con matrices 64x64
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 64x64:")
    val resultado64x64 = metodoConRutas("Matrices/Matriz 64x64/MatrizA.txt", "Matrices/Matriz 64x64/MatrizB.txt", algoritmo)
    resultado64x64.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")

    // Caso con matrices 256x256
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 256x256:")
    val resultado256x256 = metodoConRutas("Matrices/Matriz 256x256/MatrizA.txt", "Matrices/Matriz 256x256/MatrizB.txt", algoritmo)
    resultado256x256.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")

    // Caso con matrices 512x512
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 512x512:")
    val resultado512x512 = metodoConRutas("Matrices/Matriz 512x512/MatrizA.txt", "Matrices/Matriz 512x512/MatrizB.txt", algoritmo)
    resultado512x512.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")
}

// Función que realiza la multiplicación, imprime el tiempo de ejecución y retorna la matriz resultante
fun metodoConRutas(rutaA: String, rutaB: String, algoritmo: NaivLoopUnrollingFour): Array<DoubleArray> {
    try {
        // Leer las matrices desde los archivos
        val matrizA = algoritmo.leerMatrizDesdeArchivo(File(rutaA).readText())
        val matrizB = algoritmo.leerMatrizDesdeArchivo(File(rutaB).readText())

        // Convertir las matrices a Array<DoubleArray>
        val arrayA = matrizA.map { it.map { num -> num.toDouble() }.toDoubleArray() }.toTypedArray()
        val arrayB = matrizB.map { it.map { num -> num.toDouble() }.toDoubleArray() }.toTypedArray()

        // Dimensiones de las matrices
        val n = arrayA.size         // Número de filas de la matriz A
        val p = arrayA[0].size      // Número de columnas de la matriz A
        val m = arrayB[0].size      // Número de columnas de la matriz B

        // Medir el tiempo de ejecución
        val startTime = System.currentTimeMillis()

        // Llamar a la función de multiplicación de matrices
        val resultado = algoritmo.naivLoopUnrollingFour(arrayA, arrayB, n, p, m)

        // Medir el tiempo de ejecución
        val endTime = System.currentTimeMillis()

        // Imprimir el tiempo de ejecución
        println("Tiempo de ejecución: ${endTime - startTime} ms")

        // Retornar la matriz resultante
        return resultado

    } catch (e: Exception) {
        println("Error al procesar las matrices: ${e.message}")
        return emptyArray()
    }
}


