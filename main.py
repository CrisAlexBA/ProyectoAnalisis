import os
import numpy as np
from concurrent.futures import ThreadPoolExecutor, as_completed
from algoritmos.StrassenNaiv import multiply as algStrassenNaiv
from algoritmos.StrassenWinograd import multiply as algStrassenWinograd
from algoritmos.III_3_SequentialBlock import multiply as alg_III_3_SequentialBlock
from algoritmos.III_4_ParallelBlock import multiply as alg_III_4_Parallel_Block
from algoritmos.III_5_EnhancedParallelBlock import multiply as alg_III_5_Enhanced_Parallel_Block
from algoritmos.NaivOnArray import multiply as algNaivOnArray
from algoritmos.NaivLoopUnrollingTwo import multiply as algNaivLoopUnrollingTwo
from algoritmos.NaivLoopUnrollingFour import multiply as algNaivLoopUnrollingFour
from algoritmos.WinogradOriginal import multiply as algWinogradOriginal
from algoritmos.WinogradScaled import multiply as algWinogradScaled
from utils import *  # Importar funciones útiles

# Función para cargar matrices desde archivos
def cargar_matriz(filepath):
    with open(filepath, 'r') as f:
        matriz = [list(map(int, line.strip().split())) for line in f]
    return np.array(matriz)

# Menú principal
def mostrar_menu():
    print("\nSeleccione el tamaño de las matrices:")
    print("1. 2x2")
    print("2. 4x4")
    print("3. 8x8")
    print("4. 16x16")
    print("5. 32x32")
    print("6. 64x64")
    print("7. 256x256")
    print("8. 512x512")
    print("0. Salir")
    opcion = int(input("Ingrese su opción: "))
    return opcion

# Main para probar todos los algoritmos
def main():
    # Directorio base de matrices
    base_dir = "./Matrices"
    tamanos = [2, 4, 8, 16, 32, 64, 256, 512]

    while True:
        opcion = mostrar_menu()
        if opcion == 0:
            print("Saliendo del programa.")
            break
        elif opcion < 1 or opcion > len(tamanos):
            print("Opción no válida, intente nuevamente.")
            continue

        # Obtener tamaño seleccionado
        size = tamanos[opcion - 1]
        carpeta = f"Matriz {size}x{size}"
        carpeta_path = os.path.join(base_dir, carpeta)

        # Validar si la carpeta existe
        if not os.path.exists(carpeta_path):
            print(f"No se encontró la carpeta para matrices de tamaño {size}x{size}.")
            continue

        # Cargar matrices
        matrizA_path = os.path.join(carpeta_path, "MatrizA.txt")
        matrizB_path = os.path.join(carpeta_path, "MatrizB.txt")

        if not os.path.exists(matrizA_path) or not os.path.exists(matrizB_path):
            print(f"No se encontraron los archivos 'MatrizA.txt' o 'MatrizB.txt' en {carpeta_path}.")
            continue

        matrizA = cargar_matriz(matrizA_path)
        matrizB = cargar_matriz(matrizB_path)

        print(f"\nMatrices cargadas de {size}x{size}.")
        print("Matriz A:")
        print(matrizA)
        print("Matriz B:")
        print(matrizB)

        # Diccionario de algoritmos
        algoritmos = {
            "StrassenNaiv": algStrassenNaiv,
            "StrassenWinograd": algStrassenWinograd,
            "III_3_SequentialBlock": alg_III_3_SequentialBlock,
            "III_4_ParallelBlock": alg_III_4_Parallel_Block,
            "III_5_EnhancedParallelBlock": alg_III_5_Enhanced_Parallel_Block,
            "NaivOnArray": algNaivOnArray,
            "NaivLoopUnrollingTwo": algNaivLoopUnrollingTwo,
            "NaivLoopUnrollingFour": algNaivLoopUnrollingFour,
            "WinogradOriginal": algWinogradOriginal,
            "WinogradScaled": algWinogradScaled,
        }

        # Listado para guardar resultados
        resultados = []

        # Probar cada algoritmo
        for nombre, algoritmo in algoritmos.items():
            print(f"\nEjecutando {nombre}...")
            matrizRes, tiempo = medir_tiempo(algoritmo, matrizA, matrizB)
            print(f"Tiempo de ejecución: {tiempo:.6f} segundos.")
            resultados.append([nombre, size, tiempo])

            # Guardar tiempo en archivo
            with open("tiempos_ejecucion.txt", "a") as archivo:
                archivo.write(f"{nombre} - Tamaño: {size} - Tiempo: {tiempo:.6f} segundos\n")

        # Guardar resultados en CSV
        guardar_resultados_csv(resultados)

        # Graficar resultados si matplotlib está disponible
        importar_matplotlib = False
        try:
            import matplotlib.pyplot as plt
            importar_matplotlib = True
        except ImportError:
            print("matplotlib no está instalado, se saltará la gráfica.")

        if importar_matplotlib:
            nombres = [r[0] for r in resultados]
            tiempos = [r[2] for r in resultados]

            nombres, tiempos = zip(*sorted(zip(nombres, tiempos), key=lambda x: x[1], reverse=True))

            plt.bar(nombres, tiempos)
            plt.xlabel("Algoritmos")
            plt.ylabel("Tiempo de Ejecución (segundos)")
            plt.title(f"Comparación de Algoritmos para Matrices {size}x{size}")
            plt.xticks(rotation=45)
            plt.tight_layout()
            plt.show()


if __name__ == "__main__":
    main()
