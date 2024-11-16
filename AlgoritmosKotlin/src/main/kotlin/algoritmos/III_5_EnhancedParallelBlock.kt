package algoritmos

import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class III_5_EnhancedParallelBlock {
    fun blockMultiplySection(
        matrizA: Array<DoubleArray>,
        matrizB: Array<DoubleArray>,
        matrizRes: Array<DoubleArray>,
        start: Int,
        end: Int,
        size2: Int,
        lock: ReentrantLock
    ) {
        val size1 = matrizA.size
        for (i1 in start until end step size2) {
            for (j1 in 0 until size1 step size2) {
                for (k1 in 0 until size1 step size2) {
                    for (i in i1 until minOf(i1 + size2, size1)) {
                        for (j in j1 until minOf(j1 + size2, size1)) {
                            for (k in k1 until minOf(k1 + size2, size1)) {
                                lock.withLock {
                                    matrizRes[i][j] += matrizA[i][k] * matrizB[k][j]
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun algIII5EnhancedParallelBlock(
        matrizA: Array<DoubleArray>,
        matrizB: Array<DoubleArray>,
        size1: Int,
        size2: Int
    ): Array<DoubleArray> {
        val matrizRes = Array(size1) { DoubleArray(size1) }
        val midPoint = size1 / 2
        val lock = ReentrantLock() // Crear un Lock para sincronización
        val executor = Executors.newFixedThreadPool(2)

        try {
            // Dividir el trabajo en dos tareas paralelas
            val task1 = Runnable {
                blockMultiplySection(matrizA, matrizB, matrizRes, 0, midPoint, size2, lock)
            }
            val task2 = Runnable {
                blockMultiplySection(matrizA, matrizB, matrizRes, midPoint, size1, size2, lock)
            }

            executor.submit(task1)
            executor.submit(task2)
        } finally {
            executor.shutdown() // Asegurarse de cerrar el executor después de completar las tareas
            while (!executor.isTerminated) {
                // Esperar hasta que todas las tareas hayan terminado
            }
        }

        return matrizRes
    }

    fun multiply(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
        val N = matrizA.size
        val P = matrizB[0].size
        return algIII5EnhancedParallelBlock(matrizA, matrizB, N, P)
    }
}
