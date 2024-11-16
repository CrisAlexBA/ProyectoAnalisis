package metodos

import kotlin.math.abs
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.E

class WinogradScaled {

    class WinogradOriginal {
        fun winogradOriginal(a: Array<DoubleArray>, b: Array<DoubleArray>, c: Array<DoubleArray>, n: Int, p: Int, m: Int): Array<DoubleArray> {
            val upsilon = p % 2
            val gamma = p - upsilon
            val y = DoubleArray(m)
            val z = DoubleArray(n)

            // Precomputación del vector y
            for (i in 0 until m) {
                var aux = 0.0
                for (j in 0 until gamma step 2) {
                    aux += a[i][j] * a[i][j + 1]
                }
                y[i] = aux
            }

            // Precomputación del vector z
            for (i in 0 until n) {
                var aux = 0.0
                for (j in 0 until gamma step 2) {
                    aux += b[j][i] * b[j + 1][i]
                }
                z[i] = aux
            }

            // Cálculo de los elementos de la matriz c
            if (upsilon == 1) {
                val PP = p - 1
                for (i in 0 until m) {
                    for (k in 0 until n) {
                        var aux = 0.0
                        for (j in 0 until gamma step 2) {
                            aux += (a[i][j] + b[j + 1][k]) * (a[i][j + 1] + b[j][k])
                        }
                        c[i][k] = aux - y[i] - z[k] + a[i][PP] * b[PP][k]
                    }
                }
            } else {
                for (i in 0 until m) {
                    for (k in 0 until n) {
                        var aux = 0.0
                        for (j in 0 until gamma step 2) {
                            aux += (a[i][j] + b[j + 1][k]) * (a[i][j + 1] + b[j][k])
                        }
                        c[i][k] = aux - y[i] - z[k]
                    }
                }
            }

            return c
        }
    }

    // Multiplica todos los elementos de una matriz por un escalar y los almacena en otra matriz
    fun multiplyWithScalar(a: Array<DoubleArray>, b: Array<DoubleArray>, n: Int, m: Int, scalar: Double) {
        for (i in 0 until n) {
            for (j in 0 until m) {
                b[i][j] = a[i][j] * scalar
            }
        }
    }

    // Calcula la norma infinita de una matriz
    fun normInf(a: Array<DoubleArray>, n: Int, m: Int): Double {
        var maxVal = Double.NEGATIVE_INFINITY
        for (i in 0 until n) {
            var sumVal = 0.0
            for (j in 0 until m) {
                sumVal += abs(a[i][j])
            }
            if (sumVal > maxVal) {
                maxVal = sumVal
            }
        }
        return maxVal
    }

    // Método principal Winograd escalado
    fun winogradScaled(a: Array<DoubleArray>, b: Array<DoubleArray>, c: Array<DoubleArray>, n: Int, p: Int, m: Int): Array<DoubleArray> {
        val metodo = WinogradOriginal()

        val copya = Array(n) { DoubleArray(p) }
        val copyb = Array(p) { DoubleArray(m) }

        val aa = normInf(a, n, p)
        val bb = normInf(b, p, m)
        val lambdaVal = (0.5 + log(bb / aa, E) / log(4.0, E)).toInt()

        multiplyWithScalar(a, copya, n, p, 2.0.pow(lambdaVal))
        multiplyWithScalar(b, copyb, p, m, 2.0.pow(-lambdaVal))

        return metodo.winogradOriginal(copya, copyb, c, n, p, m)
    }

}