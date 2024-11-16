package algoritmos
import kotlin.math.log2
import kotlin.math.pow

class StrassenWinograd {
    fun max(a: Int, b: Int): Int = if (a > b) a else b

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

    fun standardMultiply(A: Array<DoubleArray>, B: Array<DoubleArray>, C: Array<DoubleArray>, N: Int, P: Int, M: Int) {
        for (i in 0 until N) {
            for (j in 0 until M) {
                C[i][j] = 0.0
                for (k in 0 until P) {
                    C[i][j] += A[i][k] * B[k][j]
                }
            }
        }
    }

    fun strassenWinogradStep(
        A: Array<DoubleArray>,
        B: Array<DoubleArray>,
        C: Array<DoubleArray>,
        size: Int,
        minSize: Int
    ) {
        if (size <= minSize) {
            standardMultiply(A, B, C, size, size, size)
            return
        }

        val newSize = size / 2
        val A11 = Array(newSize) { DoubleArray(newSize) }
        val A12 = Array(newSize) { DoubleArray(newSize) }
        val A21 = Array(newSize) { DoubleArray(newSize) }
        val A22 = Array(newSize) { DoubleArray(newSize) }
        val B11 = Array(newSize) { DoubleArray(newSize) }
        val B12 = Array(newSize) { DoubleArray(newSize) }
        val B21 = Array(newSize) { DoubleArray(newSize) }
        val B22 = Array(newSize) { DoubleArray(newSize) }
        val C11 = Array(newSize) { DoubleArray(newSize) }
        val C12 = Array(newSize) { DoubleArray(newSize) }
        val C21 = Array(newSize) { DoubleArray(newSize) }
        val C22 = Array(newSize) { DoubleArray(newSize) }
        val tempA = Array(newSize) { DoubleArray(newSize) }
        val tempB = Array(newSize) { DoubleArray(newSize) }

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                A11[i][j] = A[i][j]
                A12[i][j] = A[i][j + newSize]
                A21[i][j] = A[i + newSize][j]
                A22[i][j] = A[i + newSize][j + newSize]
                B11[i][j] = B[i][j]
                B12[i][j] = B[i][j + newSize]
                B21[i][j] = B[i + newSize][j]
                B22[i][j] = B[i + newSize][j + newSize]
            }
        }

        add(A11, A22, tempA, newSize)
        add(B11, B22, tempB, newSize)
        strassenWinogradStep(tempA, tempB, C11, newSize, minSize)

        add(A21, A22, tempA, newSize)
        strassenWinogradStep(tempA, B11, C21, newSize, minSize)

        subtract(B12, B22, tempB, newSize)
        strassenWinogradStep(A11, tempB, C12, newSize, minSize)

        subtract(B21, B11, tempB, newSize)
        strassenWinogradStep(A22, tempB, C22, newSize, minSize)

        add(A11, A12, tempA, newSize)
        strassenWinogradStep(tempA, B22, tempA, newSize, minSize)
        add(tempA, C11, C11, newSize)
        subtract(C12, tempA, C12, newSize)

        subtract(A21, A11, tempA, newSize)
        add(B11, B12, tempB, newSize)
        strassenWinogradStep(tempA, tempB, tempA, newSize, minSize)
        add(C22, tempA, C22, newSize)

        subtract(A12, A22, tempA, newSize)
        add(B21, B22, tempB, newSize)
        strassenWinogradStep(tempA, tempB, tempA, newSize, minSize)
        subtract(C21, tempA, C21, newSize)

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                C[i][j] = C11[i][j]
                C[i][j + newSize] = C12[i][j]
                C[i + newSize][j] = C21[i][j]
                C[i + newSize][j + newSize] = C22[i][j]
            }
        }
    }

    fun algStrassenWinograd(
        A: Array<DoubleArray>,
        B: Array<DoubleArray>,
        C: Array<DoubleArray>,
        N: Int,
        P: Int,
        M: Int
    ) {
        var maxSize = max(N, P)
        maxSize = max(maxSize, M)
        if (maxSize < 16) {
            maxSize = 16
        }
        val valorK = (log2(maxSize.toDouble()) - 4).toInt()
        val valorM = (maxSize * 2.0.pow(-valorK) + 1).toInt()
        val newSize = valorM * 2.0.pow(valorK).toInt()

        val newA = Array(newSize) { DoubleArray(newSize) }
        val newB = Array(newSize) { DoubleArray(newSize) }
        val auxResultado = Array(newSize) { DoubleArray(newSize) }

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                newA[i][j] = if (i >= N || j >= P) 0.0 else A[i][j]
                newB[i][j] = if (i >= P || j >= M) 0.0 else B[i][j]
            }
        }

        strassenWinogradStep(newA, newB, auxResultado, newSize, 16)

        for (i in 0 until N) {
            for (j in 0 until M) {
                C[i][j] = auxResultado[i][j]
            }
        }
    }

    fun multiply(A: Array<DoubleArray>, B: Array<DoubleArray>): Array<DoubleArray> {
        val N = A.size
        val P = B.size
        val M = B[0].size
        val C = Array(N) { DoubleArray(M) }
        algStrassenWinograd(A, B, C, N, P, M)
        return C
    }
}
