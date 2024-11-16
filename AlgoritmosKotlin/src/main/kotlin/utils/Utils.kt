package utils

import java.io.File
import java.lang.Exception
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class Utils {

    /**
     * Lee una matriz desde un archivo .txt.
     *
     * @param filePath Ruta del archivo que contiene la matriz.
     * @param delimiter Separador de los elementos en cada línea (por defecto, espacio).
     * @return Una matriz de Double (Array<DoubleArray>).
     * @throws Exception si el archivo no se puede leer o si hay un error en el formato.
     */
    fun loadMatrixFromFile(filePath: String, delimiter: String = " "): Array<DoubleArray> {
        val file = File(filePath)
        if (!file.exists()) {
            throw Exception("El archivo $filePath no existe.")
        }

        val lines = file.readLines()
        if (lines.isEmpty()) {
            throw Exception("El archivo $filePath está vacío.")
        }

        val matrix = lines.map { line ->
            line.split(delimiter).filter { it.isNotBlank() }.map { it.toDouble() }.toDoubleArray()
        }.toTypedArray()

        // Verificar que todas las filas tengan el mismo número de columnas
        val numCols = matrix[0].size
        if (matrix.any { it.size != numCols }) {
            throw Exception("Todas las filas deben tener el mismo número de columnas.")
        }

        return matrix
    }

    fun generarMatriz(n: Int, minVal: Int = 100000, maxVal: Int = 999999): Array<IntArray> {
        return Array(n) { IntArray(n) { Random.nextInt(minVal, maxVal) } }
    }

    fun medirTiempo(algoritmo: (Array<IntArray>, Array<IntArray>) -> Array<IntArray>, A: Array<IntArray>, B: Array<IntArray>): Pair<Array<IntArray>, Long> {
        var resultado: Array<IntArray> = emptyArray()
        val tiempo = measureTimeMillis {
            resultado = algoritmo(A, B)
        }
        return Pair(resultado, tiempo)
    }

    fun guardarResultadosCsv(resultados: List<List<Any>>, archivoCsv: String = "resultados.csv") {
        val archivo = File(archivoCsv)
        if (!archivo.exists()) {
            archivo.writeText("Algoritmo,Tamaño de Matriz,Tiempo de Ejecución (ms)\n") // Encabezados
        }
        resultados.forEach { fila ->
            archivo.appendText("${fila[0]},${fila[1]},${fila[2]}\n")
        }
    }
}