package algoritmos

import kotlin.coroutines.*
import kotlin.math.min
class III_4_ParallelBlock (

    private val size1: Int, // Tamaño de las matrices
    private val blockSize: Int // Tamaño de los bloques
    ) {
    /*
        // Método para la multiplicación en bloques
        private suspend fun blockMultiply(
            matrizA: Array<DoubleArray>,
            matrizB: Array<DoubleArray>,
            matrizRes: Array<DoubleArray>,
            i1: Int
        ) = coroutineScope {
            for (j1 in 0 until size1 step blockSize) {
                for (k1 in 0 until size1 step blockSize) {
                    for (i in i1 until min(i1 + blockSize, size1)) {
                        for (j in j1 until min(j1 + blockSize, size1)) {
                            for (k in k1 until min(k1 + blockSize, size1)) {
                                matrizRes[i][j] += matrizA[i][k] * matrizB[k][j]
                            }
                        }
                    }
                }
            }
        }

        // Método para la multiplicación en bloques paralela
        suspend fun multiply(
            matrizA: Array<DoubleArray>,
            matrizB: Array<DoubleArray>
        ): Array<DoubleArray> = coroutineScope {
            val matrizRes = Array(size1) { DoubleArray(size1) { 0.0 } }
            val jobs = mutableListOf<Job>()

            // Lanzar una tarea por cada bloque de filas
            for (i1 in 0 until size1 step blockSize) {
                jobs += launch {
                    blockMultiply(matrizA, matrizB, matrizRes, i1)
                }
            }

            // Esperar a que todas las tareas se completen
            jobs.joinAll()
            return@coroutineScope matrizRes
        }
     */
}