fun algIII3SequentialBlock(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>, size1: Int, size2: Int): Array<DoubleArray> {
    // Crear la matriz de resultado inicializada en ceros
    val matrizRes = Array(size1) { DoubleArray(size1) { 0.0 } }

    // Multiplicación por bloques
    for (i1 in 0 until size1 step size2) {
        for (j1 in 0 until size1 step size2) {
            for (k1 in 0 until size1 step size2) {
                for (i in i1 until minOf(i1 + size2, size1)) {
                    for (j in j1 until minOf(j1 + size2, size1)) {
                        for (k in k1 until minOf(k1 + size2, size1)) {
                            matrizRes[i][j] += matrizA[i][k] * matrizB[k][j]
                        }
                    }
                }
            }
        }
    }
    return matrizRes
}

fun multiply(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
    val N = matrizA.size
    val P = matrizB[0].size
    return algIII3SequentialBlock(matrizA, matrizB, N, P)
}