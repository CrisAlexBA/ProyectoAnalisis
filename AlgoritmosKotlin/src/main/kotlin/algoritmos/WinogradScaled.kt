package algoritmos

import kotlin.math.*

class WinogradScaled {
    fun multiplyWithScalar(matrix: Array<DoubleArray>, scalar: Double): Array<DoubleArray> {
        val n = matrix.size
        val m = matrix[0].size
        val result = Array(n) { DoubleArray(m) }
        for (i in 0 until n) {
            for (j in 0 until m) {
                result[i][j] = matrix[i][j] * scalar
            }
        }
        return result
    }

    fun normInf(matrix: Array<DoubleArray>): Double {
        var maxSum = Double.NEGATIVE_INFINITY
        for (row in matrix) {
            val rowSum = row.sumOf { abs(it) }
            if (rowSum > maxSum) {
                maxSum = rowSum
            }
        }
        return maxSum
    }

    fun algWinogradOriginal(
        A: Array<DoubleArray>,
        B: Array<DoubleArray>,
        N: Int,
        P: Int,
        M: Int
    ): Array<DoubleArray> {
        val upsilon = P % 2
        val gamma = P - upsilon
        val y = DoubleArray(M)
        val z = DoubleArray(N)
        val res = Array(M) { DoubleArray(N) }

        for (i in 0 until M) {
            var aux = 0.0
            for (j in 0 until gamma step 2) {
                aux += A[i][j] * A[i][j + 1]
            }
            y[i] = aux
        }

        for (i in 0 until N) {
            var aux = 0.0
            for (j in 0 until gamma step 2) {
                aux += B[j][i] * B[j + 1][i]
            }
            z[i] = aux
        }

        if (upsilon == 1) {
            val PP = P - 1
            for (i in 0 until M) {
                for (k in 0 until N) {
                    var aux = 0.0
                    for (j in 0 until gamma step 2) {
                        aux += (A[i][j] + B[j + 1][k]) * (A[i][j + 1] + B[j][k])
                    }
                    res[i][k] = aux - y[i] - z[k] + A[i][PP] * B[PP][k]
                }
            }
        } else {
            for (i in 0 until M) {
                for (k in 0 until N) {
                    var aux = 0.0
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
        matrizA: Array<DoubleArray>,
        matrizB: Array<DoubleArray>,
        N: Int,
        P: Int,
        M: Int
    ): Array<DoubleArray> {
        val a = normInf(matrizA)
        val b = normInf(matrizB)
        val lambda = floor(0.5 + log(b / a, E) / log(4.0, E)).toInt()

        val copyA = multiplyWithScalar(matrizA, 2.0.pow(lambda))
        val copyB = multiplyWithScalar(matrizB, 2.0.pow(-lambda))

        return algWinogradOriginal(copyA, copyB, N, P, M)
    }

    fun multiply(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
        val N = matrizA.size
        val P = matrizB.size
        val M = matrizB[0].size
        return algWinogradScaled(matrizA, matrizB, N, P, M)
    }

    fun Double.pow(exponent: Int): Double = this.pow(exponent.toDouble())

}