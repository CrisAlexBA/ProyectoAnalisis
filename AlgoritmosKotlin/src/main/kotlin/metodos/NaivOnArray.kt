package metodos
import java.lang.Exception

class NaivOnArray {

    fun naivOnArray(a: Array<DoubleArray>, b: Array<DoubleArray>, n: Int, p: Int, m: Int): Array<DoubleArray>{
        val c = Array(n){DoubleArray(m)}
        for(i in 0 until n){
            for(j in 0 until m){
                c[i][j]=0.0
                for(k in 0 until p){
                    c[i][j]+=a[i][k]*b[k][j]
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
        return naivOnArray(a, b, n, p, m)
    }
}