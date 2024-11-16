import kotlinx.coroutines.*
import kotlin.math.min

// Bloque de multiplicación
suspend fun blockMultiply(
    matrizA: Array<DoubleArray>,
    matrizB: Array<DoubleArray>,
    matrizRes: Array<DoubleArray>,
    i1: Int,
    size1: Int,
    size2: Int
) = coroutineScope {
    for (j1 in 0 until size1 step size2) {
        for (k1 in 0 until size1 step size2) {
            for (i in i1 until min(i1 + size2, size1)) {
                for (j in j1 until min(j1 + size2, size1)) {
                    for (k in k1 until min(k1 + size2, size1)) {
                        matrizRes[i][j] += matrizA[i][k] * matrizB[k][j]
                    }
                }
            }
        }
    }
}

// Multiplicación en bloques paralela
suspend fun algIII4ParallelBlock(
    matrizA: Array<DoubleArray>,
    matrizB: Array<DoubleArray>,
    size1: Int,
    size2: Int
): Array<DoubleArray> = coroutineScope {
    val matrizRes = Array(size1) { DoubleArray(size1) { 0.0 } }
    val jobs = mutableListOf<Job>()

    // Lanzar una tarea por cada bloque de filas
    for (i1 in 0 until size1 step size2) {
        jobs += launch {
            blockMultiply(matrizA, matrizB, matrizRes, i1, size1, size2)
        }
    }

    // Esperar a que todas las tareas se completen
    jobs.joinAll()
    return@coroutineScope matrizRes
}

// Multiplicación
suspend fun multiply(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
    val N = matrizA.size
    val P = matrizB[0].size
    return algIII4ParallelBlock(matrizA, matrizB, N, P)
}