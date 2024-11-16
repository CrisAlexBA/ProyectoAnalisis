package algoritmos

import java.lang.Exception

class NaivLoopUnrollingTwo {

    fun naivLoopUnrollingTwo(a: Array<DoubleArray>, b: Array<DoubleArray>, n: Int, p: Int, m: Int): Array<DoubleArray> {
        val c = Array(n) { DoubleArray(m) }

        if (p % 2 == 0) {
            for (i in 0 until n) {
                for (j in 0 until m) {
                    var aux = 0.0
                    for (k in 0 until p step 2) {
                        aux += a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j]
                    }
                    c[i][j] = aux
                }
            }
        } else {
            val pp = p - 1
            for (i in 0 until n) {
                for (j in 0 until m) {
                    var aux = 0.0
                    for (k in 0 until pp step 2) {
                        aux += a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j]
                    }
                    c[i][j] = aux + a[i][pp] * b[pp][j]
                }
            }
        }

        return c
    }

    fun multiply(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        var n = a.size
        var p = b.size
        var m = b[0].size
        if (p != b.size) {
            throw Exception("El número de columnas de A debe ser igual al número de filas de B.")
        }
        return naivLoopUnrollingTwo(a, b, n, p, m)
    }
}