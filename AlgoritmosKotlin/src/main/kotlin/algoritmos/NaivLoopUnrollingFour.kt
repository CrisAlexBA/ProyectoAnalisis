package algoritmos

import java.lang.Exception

class NaivLoopUnrollingFour {

    fun naivLoopUnrollingFour(a: Array<DoubleArray>, b: Array<DoubleArray>, n: Int, p: Int, m: Int): Array<DoubleArray> {
        val c = Array(n) { DoubleArray(m) }

        for (i in 0 until n) {
            for (j in 0 until m) {
                var aux = 0.0

                when (p % 4) {
                    0 -> {
                        for (k in 0 until p step 4) {
                            aux += (a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j] +
                                    a[i][k + 2] * b[k + 2][j] + a[i][k + 3] * b[k + 3][j])
                        }
                    }
                    1 -> {
                        val pp = p - 1
                        for (k in 0 until pp step 4) {
                            aux += (a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j] +
                                    a[i][k + 2] * b[k + 2][j] + a[i][k + 3] * b[k + 3][j])
                        }
                        aux += a[i][pp] * b[pp][j]
                    }
                    2 -> {
                        val pp = p - 2
                        val ppp = p - 1
                        for (k in 0 until pp step 4) {
                            aux += (a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j] +
                                    a[i][k + 2] * b[k + 2][j] + a[i][k + 3] * b[k + 3][j])
                        }
                        aux += a[i][pp] * b[pp][j] + a[i][ppp] * b[ppp][j]
                    }
                    3 -> {
                        val pp = p - 3
                        val ppp = p - 2
                        val pppp = p - 1
                        for (k in 0 until pp step 4) {
                            aux += (a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j] +
                                    a[i][k + 2] * b[k + 2][j] + a[i][k + 3] * b[k + 3][j])
                        }
                        aux += (a[i][pp] * b[pp][j] + a[i][ppp] * b[ppp][j] + a[i][pppp] * b[pppp][j])
                    }
                }

                c[i][j] = aux
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
        return naivLoopUnrollingFour(a,b,n,p,m)
    }



    fun procesarMatrices(ruta1: String, ruta2: String): Int {
        // Implementación que lee las rutas y procesa las matrices
        val matriz1 = leerMatrizDesdeArchivo(ruta1)
        val matriz2 = leerMatrizDesdeArchivo(ruta2)
        return matriz1.sumOf { it.sum() } + matriz2.sumOf { it.sum() } // Ejemplo
    }

    fun leerMatrizDesdeArchivo(ruta: String): List<List<Int>> {
        return ruta.split("\n")
                .filter { it.isNotBlank() } // Filtra líneas vacías
                .map { it.trim().split(" ").filter { n -> n.isNotBlank() }.map { n -> n.toInt() } }
    }


}