package algoritmos

import kotlin.math.abs
import kotlin.math.log
import kotlin.math.floor
import kotlin.math.E


class WinogradScaled {
    fun multiplyWithScalar(matrix: Array<IntArray>, scalar: Int): Array<IntArray> {
        val n = matrix.size
        val m = matrix[0].size
        val result = Array(n) { IntArray(m) }
        for (i in 0 until n) {
            for (j in 0 until m) {
                result[i][j] = matrix[i][j] * scalar
            }
        }
        return result
    }

    fun normInf(matrix: Array<IntArray>): Int {
        var maxSum = Int.MIN_VALUE
        for (row in matrix) {
            val rowSum = row.sumOf { abs(it) }
            if (rowSum > maxSum) {
                maxSum = rowSum
            }
        }
        return maxSum
    }

    fun algWinogradOriginal(
        A: Array<IntArray>,
        B: Array<IntArray>,
        N: Int,
        P: Int,
        M: Int
    ): Array<IntArray> {
        val upsilon = P % 2
        val gamma = P - upsilon
        val y = IntArray(M)
        val z = IntArray(N)
        val res = Array(M) { IntArray(N) }

        for (i in 0 until M) {
            var aux = 0
            for (j in 0 until gamma step 2) {
                aux += A[i][j] * A[i][j + 1]
            }
            y[i] = aux
        }

        for (i in 0 until N) {
            var aux = 0
            for (j in 0 until gamma step 2) {
                aux += B[j][i] * B[j + 1][i]
            }
            z[i] = aux
        }

        if (upsilon == 1) {
            val PP = P - 1
            for (i in 0 until M) {
                for (k in 0 until N) {
                    var aux = 0
                    for (j in 0 until gamma step 2) {
                        aux += (A[i][j] + B[j + 1][k]) * (A[i][j + 1] + B[j][k])
                    }
                    res[i][k] = aux - y[i] - z[k] + A[i][PP] * B[PP][k]
                }
            }
        } else {
            for (i in 0 until M) {
                for (k in 0 until N) {
                    var aux = 0
                    for (j in 0 until gamma step 2) {
                        aux += (A[i][j] + B[j + 1][k]) * (A[i][j + 1] + B[j][k])
                    }
                    res[i][k] = aux - y[i] - z[k]
                }
            }
        }

        return res
    }

    fun algWinogradScaled(
        matrizA: Array<IntArray>,
        matrizB: Array<IntArray>,
        N: Int,
        P: Int,
        M: Int
    ): Array<IntArray> {
        val a = normInf(matrizA)
        val b = normInf(matrizB)
        val lambda = floor(0.5 + log(b.toDouble() / a.toDouble(), E) / log(4.0, E)).toInt()

        val copyA = multiplyWithScalar(matrizA, 1 shl lambda) // 2^lambda -> 1 shl lambda
        val copyB = multiplyWithScalar(matrizB, 1 shl -lambda)

        return algWinogradOriginal(copyA, copyB, N, P, M)
    }

    fun multiply(matrizA: Array<IntArray>, matrizB: Array<IntArray>): Array<IntArray> {
        val N = matrizA.size
        val P = matrizB.size
        val M = matrizB[0].size
        return algWinogradScaled(matrizA, matrizB, N, P, M)
    }

}