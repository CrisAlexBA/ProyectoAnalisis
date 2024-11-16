# utils.py
import random
import time
import csv
import os

def generar_matriz(n, min_val=100000, max_val=999999):
    """Genera una matriz cuadrada de tamaño n x n con valores aleatorios de 6 dígitos."""
    return [[random.randint(min_val, max_val) for _ in range(n)] for _ in range(n)]

def medir_tiempo(algoritmo, A, B):
    """Mide el tiempo de ejecución de un algoritmo de multiplicación de matrices."""
    inicio = time.time()
    resultado = algoritmo(A, B)
    fin = time.time()
    tiempo_ejecucion = fin - inicio
    return resultado, tiempo_ejecucion

def guardar_resultados_csv(resultados, archivo_csv="resultados.csv"):
    """Guarda los resultados en un archivo CSV."""
    campos = ["Algoritmo", "Tamaño de Matriz", "Tiempo de Ejecución (s)"]
    
    # Verifica si el archivo existe; si no, crea uno nuevo con encabezados
    archivo_existe = os.path.exists(archivo_csv)
    with open(archivo_csv, mode="a", newline="") as archivo:
        writer = csv.writer(archivo)
        if not archivo_existe:
            writer.writerow(campos)  # Escribe los encabezados
        writer.writerows(resultados)

def cargar_algoritmos():
    """Importa dinámicamente los algoritmos desde la carpeta 'algoritmos'."""
    from algoritmos.III_3_SequentialBlock import sequential_block as alg_III_3
    from algoritmos.III_4_ParallelBlock import parallel_block as alg_III_4
    from algoritmos.III_5_EnhancedParallelBlock import enhanced_parallel_block as alg_III_5
    from algoritmos.StrassenNaiv import strassen_naiv as alg_StrassenNaiv
    from algoritmos.StrassenWinograd import strassen_winograd as alg_StrassenWinograd
    from algoritmos.NaivOnArray import naivOnArray as alg_NaivOnArray
    from algoritmos.NaivLoopUnrollingTwo import naivLoopUnrollingTwo as alg_NaivLoopUnrollingTwo
    from algoritmos.NaivLoopUnrollingFour import naivLoopUnrollingFour as alg_NaivLoopUnrollingFour
    from algoritmos.WinogradOriginal import winogradOriginal as alg_WinogradOriginal
    from algoritmos.WinogradScaled import winogradScaled as alg_WinogradScaled
    
    return {
        "III_3_SequentialBlock": alg_III_3,
        "III_4_ParallelBlock": alg_III_4,
        "III_5_EnhancedParallelBlock": alg_III_5,
        "StrassenNaiv": alg_StrassenNaiv,
        "StrassenWinograd": alg_StrassenWinograd,
        "NaivOnArray": alg_NaivOnArray,
        "NaivLoopUnrollingTwo": alg_NaivLoopUnrollingTwo,
        "NaivLoopUnrollingFour": alg_NaivLoopUnrollingFour,
        "WinogradOriginal": alg_WinogradOriginal,
        "WinogradScaled": alg_WinogradScaled
    }
