import algoritmos.*
import utils.MatrixMultiplicationResult
import utils.Utils
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val ruta = "src/main/kotlin/Matrices/Matriz 64x64"
    val utils = Utils()

    val size = 512 // Tamaño de la matriz
    val matrizA = utils.loadMatrixFromFile("$ruta/MatrizA.txt")
    val matrizB = utils.loadMatrixFromFile("$ruta/MatrizB.txt")

    val methods = listOf(
        NaivOnArray(),
        NaivLoopUnrollingTwo(),
        NaivLoopUnrollingFour(),
        WinogradOriginal(),
        WinogradScaled(),
        StrassenNaiv(),
        StrassenWinograd(),
        III_3_SequentialBlock(),
        III_5_EnhancedParallelBlock()
    )

    val results = mutableListOf<MatrixMultiplicationResult>()

    for (method in methods){
        val nombre = method::class.simpleName ?: "MetodoDesconocido"

        val tiempoEjecucion = measureTimeMillis {
            method.javaClass.getMethod("multiply", Array<DoubleArray>::class.java, Array<DoubleArray>::class.java)
                .invoke(method, matrizA, matrizB)
        }

        results.add(MatrixMultiplicationResult(
            nombreMetodo = nombre,
            tiempoEjecucion = tiempoEjecucion,
            tamano = Pair(matrizA.size, matrizA[0].size)))
    }

    results.forEach {
        println("Method: ${it.nombreMetodo}, Time: ${it.tiempoEjecucion} ms, Matrix Size: ${it.tamano}")
    }
}