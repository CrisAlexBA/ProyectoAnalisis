import algoritmos.*
import utils.MatrixMultiplicationResult
import utils.Utils
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val tamanos= listOf(2,4,8,16,32,64,256,512)
    val utils = Utils()

    while (true) {
        val opcion = mostrarMenu()
        if(opcion==0){
            println("Saliendo del programa.")
            break
        }else if(opcion< 1 || opcion>tamanos.size){
            println("Opcion no valida")
            continue
        }
        val tamanoElegido= tamanos[opcion-1]
        val ruta = "src/main/kotlin/Matrices/Matriz ${tamanoElegido}x${tamanoElegido}"
        val matrizA = utils.loadMatrixFromFile("$ruta/MatrizA.txt")
        val matrizB = utils.loadMatrixFromFile("$ruta/MatrizA.txt")

        val methods = listOf(
            NaivOnArray(),
            NaivLoopUnrollingTwo(),
            NaivLoopUnrollingFour(),
            WinogradOriginal(),
            WinogradScaled(),
            StrassenNaiv(),
            StrassenWinograd(),
            III_3_SequentialBlock(),
            III_4_ParallelBlock(tamanoElegido, 2),
            III_5_EnhancedParallelBlock()
        )

        val results = mutableListOf<MatrixMultiplicationResult>()

        for (method in methods) {
            val nombre = method::class.simpleName ?: "MetodoDesconocido"

            val tiempoEjecucion = measureTimeMillis {
                method.javaClass.getMethod("multiply", Array<DoubleArray>::class.java, Array<DoubleArray>::class.java)
                    .invoke(method, matrizA, matrizB)
            }

            results.add(
                MatrixMultiplicationResult(
                    nombreMetodo = nombre,
                    tiempoEjecucion = tiempoEjecucion,
                    tamano = Pair(matrizA.size, matrizA[0].size)
                )
            )
        }
        utils.guardarResultadosCsv(results)
        results.forEach {
            println("Method: ${it.nombreMetodo}, Time: ${it.tiempoEjecucion} ms, Matrix Size: ${it.tamano}")
        }
        utils.ejecutarScriptPython(results)
    }
}

fun mostrarMenu(): Int {
    println("\nSeleccione el tamaño de las matrices:")
    println("1. 2x2")
    println("2. 4x4")
    println("3. 8x8")
    println("4. 16x16")
    println("5. 32x32")
    println("6. 64x64")
    println("7. 256x256")
    println("8. 512x512")
    println("0. Salir")
    print("Ingrese su opción: ")

    // Leer la opción ingresada por el usuario
    val opcion = readLine()?.toIntOrNull()

    // Validar entrada: si no es válida, retornar -1 como error
    return opcion ?: -1
}