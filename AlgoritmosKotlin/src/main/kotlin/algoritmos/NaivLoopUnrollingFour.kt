package algoritmos

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

    fun multiply(){

    }

}