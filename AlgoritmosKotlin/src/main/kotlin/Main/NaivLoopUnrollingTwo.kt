package Main

import algoritmos.NaivLoopUnrollingTwo
import java.io.File
import java.io.FileWriter
import java.io.IOException

fun main() {

    val algoritmo = NaivLoopUnrollingTwo()
    // Nombre del archivo donde se guardarán todos los tiempos
    val nombreArchivo = "Matrices/TiemposNaivLoopUnrollingTwo.txt"

    /*
        ____________________________________ PRUEBAS CON LAS DIFERENTES DIMENSIONES DE MATRICES _____________________________________
     */
    // Caso con matrices 16x16
    var startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 16x16:")
    val resultado16x16 = metodoConRutas("Matrices/Matriz 16x16/MatrizA.txt", "Matrices/Matriz 16x16/MatrizB.txt", algoritmo)
    resultado16x16.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    val tiempo16x16 = System.currentTimeMillis() - startTime
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")
    guardarTiempoEnArchivo(nombreArchivo, "Matrices 16x16", tiempo16x16)

    // Caso con matrices 32x32
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 32x32:")
    val resultado32x32 = metodoConRutas("Matrices/Matriz 32x32/MatrizA.txt", "Matrices/Matriz 32x32/MatrizB.txt", algoritmo)
   /* resultado32x32.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }*/
    val tiempo32x32 = System.currentTimeMillis() - startTime
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")
    guardarTiempoEnArchivo(nombreArchivo, "Matrices 32x32", tiempo32x32)


    // Caso con matrices 64x64
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 64x64:")
    val resultado64x64 = metodoConRutas("Matrices/Matriz 64x64/MatrizA.txt", "Matrices/Matriz 64x64/MatrizB.txt", algoritmo)
    /*resultado64x64.forEach { fila 32->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }*/
    val tiempo64x64 = System.currentTimeMillis() - startTime
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")
    guardarTiempoEnArchivo(nombreArchivo, "Matrices 64x64", tiempo64x64)


    // Caso con matrices 256x256
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 256x256:")
    val resultado256x256 = metodoConRutas("Matrices/Matriz 256x256/MatrizA.txt", "Matrices/Matriz 256x256/MatrizB.txt", algoritmo)
    /*resultado256x256.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }*/
    val tiempo256x256 = System.currentTimeMillis() - startTime
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")
    guardarTiempoEnArchivo(nombreArchivo, "Matrices 256x256", tiempo256x256)


    // Caso con matrices 512x512
    startTime = System.currentTimeMillis()
    println("Tiempo de ejecución con matrices 512x512:")
    val resultado512x512 = metodoConRutas("Matrices/Matriz 512x512/MatrizA.txt", "Matrices/Matriz 512x512/MatrizB.txt", algoritmo)
    /*resultado512x512.forEach { fila ->
        println(fila.joinToString(" ") { valor -> valor.toLong().toString() })
    }
    */
    val tiempo512x512 = System.currentTimeMillis() - startTime
    println("Tiempo total: ${System.currentTimeMillis() - startTime} ms\n")
    guardarTiempoEnArchivo(nombreArchivo, "Matrices 512x512", tiempo512x512)


    /*
     __________________________________________________________________________________________________________
     */
}



// Función que realiza la multiplicación, imprime el tiempo de ejecución y retorna la matriz resultante
fun metodoConRutas(rutaA: String, rutaB: String, algoritmo: NaivLoopUnrollingTwo): Array<DoubleArray> {
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
        val resultado = algoritmo.naivLoopUnrollingTwo(arrayA, arrayB, n, p, m)

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


// Método para guardar los tiempos en un archivo .txt
fun guardarTiempoEnArchivo(nombreArchivo: String, nombreMatriz: String, tiempo: Long) {
    try {
        // Crear una instancia de FileWriter en modo de agregar (append)
        val fileWriter = FileWriter(File(nombreArchivo), true)

        // Escribir el nombre de la matriz y el tiempo de ejecución en el archivo
        fileWriter.append("Tiempo de Ejecución de las ${nombreMatriz}: ${tiempo} ms\n")

        // Cerrar el FileWriter para asegurarse de que los datos se escriben en el archivo
        fileWriter.close()

        println("Tiempo guardado en el archivo: $nombreArchivo")
    } catch (e: IOException) {
        println("Error al guardar el tiempo en el archivo: ${e.message}")
    }
}


