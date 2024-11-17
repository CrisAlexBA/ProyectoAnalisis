package algoritmos

import kotlin.math.min

class III_4_ParallelBlock(
    private val size1: Int, // Tamaño de las matrices
    private val blockSize: Int? = null // Tamaño de los bloques, opcional (será calculado si es nulo)
) {

    // Método para la multiplicación en bloques
    private fun blockMultiply(
        matrizA: Array<DoubleArray>,
        matrizB: Array<DoubleArray>,
        matrizRes: Array<DoubleArray>,
        i1: Int
    ) {
        for (j1 in 0 until size1 step blockSize!!) {
            for (k1 in 0 until size1 step blockSize!!) {
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
    fun multiply(
        matrizA: Array<DoubleArray>,
        matrizB: Array<DoubleArray>
    ): Array<DoubleArray> {
        val matrizRes = Array(size1) { DoubleArray(size1) { 0.0 } }

        // Obtener el número de núcleos disponibles
        val availableProcessors = Runtime.getRuntime().availableProcessors()

        // Si el bloque no está definido, calcularlo en función de los núcleos
        val dynamicBlockSize = blockSize ?: (size1 / availableProcessors).coerceAtLeast(1)

        // Lanzar un hilo por cada bloque de filas
        val threads = mutableListOf<Thread>()
        for (i1 in 0 until size1 step dynamicBlockSize) {
            val thread = Thread {
                blockMultiply(matrizA, matrizB, matrizRes, i1)
            }
            threads.add(thread)
            thread.start()
        }

        // Esperar a que todos los hilos se completen
        threads.forEach { it.join() }

        return matrizRes
    }
}