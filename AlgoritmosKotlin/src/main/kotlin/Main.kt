import metodos.NaivOnArray

fun main(args: Array<String>) {
    val naivOnArray= NaivOnArray()

    val a = arrayOf(doubleArrayOf(1.0, 2.0), doubleArrayOf(3.0, 4.0))
    val b = arrayOf(doubleArrayOf(5.0, 6.0), doubleArrayOf(7.0, 8.0))

    val result = naivOnArray.multiply(a,b)
    println("Resultado del método naive:")
    result.forEach { println(it.contentToString()) }


}