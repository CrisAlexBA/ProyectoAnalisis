import kotlin.math.log2
import kotlin.math.max
import kotlin.math.pow

fun max(N: Int, P: Int): Int = if (N < P) P else N

fun add(A: Array<DoubleArray>, B: Array<DoubleArray>, C: Array<DoubleArray>, size: Int) {
    for (i in 0 until size) {
        for (j in 0 until size) {
            C[i][j] = A[i][j] + B[i][j]
        }
    }
}

fun subtract(A: Array<DoubleArray>, B: Array<DoubleArray>, C: Array<DoubleArray>, size: Int) {
    for (i in 0 until size) {
        for (j in 0 until size) {
            C[i][j] = A[i][j] - B[i][j]
        }
    }
}

fun algStrassenNaiv(
    matrizA: Array<DoubleArray>,
    matrizB: Array<DoubleArray>,
    matrizRes: Array<DoubleArray>,
    N: Int,
    P: Int,
    M: Int
) {
    val maxSize = max(max(N, P), M).coerceAtLeast(16)
    val k = log2(maxSize.toDouble()).toInt() - 4
    val m = (maxSize * (2.0).pow(-k)).toInt() + 1
    val newSize = m * (2.0.pow(k)).toInt()

    val newA = Array(newSize) { DoubleArray(newSize) }
    val newB = Array(newSize) { DoubleArray(newSize) }
    val auxResult = Array(newSize) { DoubleArray(newSize) }

    for (i in 0 until N) {
        for (j in 0 until P) {
            newA[i][j] = matrizA[i][j]
        }
    }
    for (i in 0 until P) {
        for (j in 0 until M) {
            newB[i][j] = matrizB[i][j]
        }
    }

    strassenNaivStep(newA, newB, auxResult, newSize, m)

    for (i in 0 until N) {
        for (j in 0 until M) {
            matrizRes[i][j] = auxResult[i][j]
        }
    }
}

fun strassenNaivStep(
    matrizA: Array<DoubleArray>,
    matrizB: Array<DoubleArray>,
    matrizRes: Array<DoubleArray>,
    N: Int,
    m: Int
) {
    if (N % 2 == 0 && N > m) {
        val newSize = N / 2

        val varA11 = Array(newSize) { DoubleArray(newSize) }
        val varA12 = Array(newSize) { DoubleArray(newSize) }
        val varA21 = Array(newSize) { DoubleArray(newSize) }
        val varA22 = Array(newSize) { DoubleArray(newSize) }
        val varB11 = Array(newSize) { DoubleArray(newSize) }
        val varB12 = Array(newSize) { DoubleArray(newSize) }
        val varB21 = Array(newSize) { DoubleArray(newSize) }
        val varB22 = Array(newSize) { DoubleArray(newSize) }

        val resultadoPart11 = Array(newSize) { DoubleArray(newSize) }
        val resultadoPart12 = Array(newSize) { DoubleArray(newSize) }
        val resultadoPart21 = Array(newSize) { DoubleArray(newSize) }
        val resultadoPart22 = Array(newSize) { DoubleArray(newSize) }

        val helper1 = Array(newSize) { DoubleArray(newSize) }
        val helper2 = Array(newSize) { DoubleArray(newSize) }

        val aux1 = Array(newSize) { DoubleArray(newSize) }
        val aux2 = Array(newSize) { DoubleArray(newSize) }
        val aux3 = Array(newSize) { DoubleArray(newSize) }
        val aux4 = Array(newSize) { DoubleArray(newSize) }
        val aux5 = Array(newSize) { DoubleArray(newSize) }
        val aux6 = Array(newSize) { DoubleArray(newSize) }
        val aux7 = Array(newSize) { DoubleArray(newSize) }

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                varA11[i][j] = matrizA[i][j]
                varA12[i][j] = matrizA[i][j + newSize]
                varA21[i][j] = matrizA[i + newSize][j]
                varA22[i][j] = matrizA[i + newSize][j + newSize]
                varB11[i][j] = matrizB[i][j]
                varB12[i][j] = matrizB[i][j + newSize]
                varB21[i][j] = matrizB[i + newSize][j]
                varB22[i][j] = matrizB[i + newSize][j + newSize]
            }
        }

        add(varA11, varA22, helper1, newSize)
        add(varB11, varB22, helper2, newSize)
        strassenNaivStep(helper1, helper2, aux1, newSize, m)

        add(varA21, varA22, helper1, newSize)
        strassenNaivStep(helper1, varB11, aux2, newSize, m)

        subtract(varB12, varB22, helper1, newSize)
        strassenNaivStep(varA11, helper1, aux3, newSize, m)

        subtract(varB21, varB11, helper1, newSize)
        strassenNaivStep(varA22, helper1, aux4, newSize, m)

        add(varA11, varA12, helper1, newSize)
        strassenNaivStep(helper1, varB22, aux5, newSize, m)

        subtract(varA21, varA11, helper1, newSize)
        add(varB11, varB12, helper2, newSize)
        strassenNaivStep(helper1, helper2, aux6, newSize, m)

        subtract(varA12, varA22, helper1, newSize)
        add(varB21, varB22, helper2, newSize)
        strassenNaivStep(helper1, helper2, aux7, newSize, m)

        add(aux1, aux4, resultadoPart11, newSize)
        subtract(resultadoPart11, aux5, resultadoPart11, newSize)
        add(resultadoPart11, aux7, resultadoPart11, newSize)

        add(aux3, aux5, resultadoPart12, newSize)
        add(aux2, aux4, resultadoPart21, newSize)

        add(aux1, aux3, resultadoPart22, newSize)
        subtract(resultadoPart22, aux2, resultadoPart22, newSize)
        add(resultadoPart22, aux6, resultadoPart22, newSize)

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                matrizRes[i][j] = resultadoPart11[i][j]
                matrizRes[i][j + newSize] = resultadoPart12[i][j]
                matrizRes[i + newSize][j] = resultadoPart21[i][j]
                matrizRes[i + newSize][j + newSize] = resultadoPart22[i][j]
            }
        }
    } else {
        algoritmoNaivStandard(matrizA, matrizB, matrizRes, N, N, N)
    }
}

fun algoritmoNaivStandard(
    matrizA: Array<DoubleArray>,
    matrizB: Array<DoubleArray>,
    matrizRes: Array<DoubleArray>,
    N: Int,
    P: Int,
    M: Int
) {
    for (i in 0 until N) {
        for (j in 0 until M) {
            var aux = 0.0
            for (k in 0 until P) {
                aux += matrizA[i][k] * matrizB[k][j]
            }
            matrizRes[i][j] = aux
        }
    }
}

fun multiply(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
    val N = matrizA.size
    val P = matrizB.size
    val M = matrizB[0].size
    val matrizRes = Array(N) { DoubleArray(M) }
    algStrassenNaiv(matrizA, matrizB, matrizRes, N, P, M)
    return matrizRes
}
