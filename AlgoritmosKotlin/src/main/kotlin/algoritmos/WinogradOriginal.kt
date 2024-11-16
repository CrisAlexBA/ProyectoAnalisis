package algoritmos

import java.lang.Exception

class WinogradOriginal {

    fun winogradOriginal(a: Array<DoubleArray>, b: Array<DoubleArray>, n: Int, p: Int, m: Int): Array<DoubleArray> {
        val c = Array(m) { DoubleArray(n) }
        val upsilon = p % 2
        val gamma = p - upsilon
        val y = DoubleArray(m) { 0.0 }
        val z = DoubleArray(n) { 0.0 }

        // Calcular el vector y
        for (i in 0 until m) {
            var aux = 0.0
            for (j in 0 until gamma step 2) {
                aux += a[i][j] * a[i][j + 1]
            }
            y[i] = aux
        }

        // Calcular el vector z
        for (i in 0 until n) {
            var aux = 0.0
            for (j in 0 until gamma step 2) {
                aux += b[j][i] * b[j + 1][i]
            }
            z[i] = aux
        }

        if (upsilon == 1) {
            val pp = p - 1
            for (i in 0 until m) {
                for (k in 0 until n) {
                    var aux = 0.0
                    for (j in 0 until gamma step 2) {
                        aux += (a[i][j] + b[j + 1][k]) * (a[i][j + 1] + b[j][k])
                    }
                    c[i][k] = aux - y[i] - z[k] + a[i][pp] * b[pp][k]
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

    fun multiply(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        var n = a.size
        var p = b.size
        var m = b[0].size
        if (p != b.size) {
            throw Exception("El número de columnas de A debe ser igual al número de filas de B.")
        }
        return winogradOriginal(a,b,n,p,m)
    }

}