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

    fun generarMatriz(n: Int, minVal: Double = 100000.0, maxVal: Double = 999999.0): Array<DoubleArray> {
        return Array(n) { DoubleArray(n) { Random.nextDouble(minVal, maxVal) } }
    }

    fun medirTiempo(algoritmo: (Array<IntArray>, Array<IntArray>) -> Array<IntArray>, A: Array<IntArray>, B: Array<IntArray>): Pair<Array<IntArray>, Long> {
        var resultado: Array<IntArray> = emptyArray()
        val tiempo = measureTimeMillis {
            resultado = algoritmo(A, B)
        }
        return Pair(resultado, tiempo)
    }

    fun guardarResultadosCsv(resultados: MutableList<MatrixMultiplicationResult>, archivoCsv: String = "resultados.csv") {
        val archivo = File(archivoCsv)
        if (!archivo.exists()) {
            archivo.writeText("Algoritmo,Tamano de Matriz,Tiempo de Ejecucion (ms)\n") // Encabezados
        }
        resultados.forEach { fila ->
            archivo.appendText("${fila.nombreMetodo},${fila.tamano},${fila.tiempoEjecucion}\n")
        }
    }

    fun ejecutarScriptPython(results: List<MatrixMultiplicationResult>) {
        // Crear un archivo txt con los resultados
        val resultadosJson = results.map { result ->
            "${result.nombreMetodo} ${result.tiempoEjecucion} ${result.tamano}"
        }
        val resultadosFile = File("resultados.txt")
        resultadosFile.writeText("")
        for(line in resultadosJson) {
            resultadosFile.appendText("$line\n")
        }

        // Ejecutar el script de Python
        val process = ProcessBuilder("python", "grafica.py", "resultados.json")
            .redirectErrorStream(true)
            .start()

        // Leer la salida del script
        process.inputStream.bufferedReader().forEachLine { println(it) }

        // Esperar a que el proceso termine
        process.waitFor()
    }
}