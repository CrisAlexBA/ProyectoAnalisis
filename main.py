import random
from concurrent.futures import ThreadPoolExecutor, as_completed
from algoritmos.StrassenNaiv import multiply as algStrassenNaiv
from algoritmos.StrassenWinograd import multiply as algStrassenWinograd
from algoritmos.III_3_SequentialBlock import multiply as alg_III_3_SequentialBlock
from algoritmos.III_4_ParallelBlock import multiply as alg_III_4_Parallel_Block
from algoritmos.III_5_EnhancedParallelBlock import multiply as alg_III_5_Enhanced_Parallel_Block
from utils import *  # Importar funciones útiles

# Main para probar todos los algoritmos
def main():
    # Definir tamaño de la matriz
    size = 512  # Cambiar tamaño según sea necesario
    block_size = 2  # Tamaño de bloque para algoritmos en bloque
    
    # Generar matrices aleatorias
    matrizA = generar_matriz(size)
    matrizB = generar_matriz(size)
    
    # Diccionario de algoritmos
    algoritmos = {
        "StrassenNaiv": algStrassenNaiv,
        "StrassenWinograd": algStrassenWinograd,
        "III_3_SequentialBlock": alg_III_3_SequentialBlock,
        "III_4_ParallelBlock": alg_III_4_Parallel_Block,
        "III_5_EnhancedParallelBlock": alg_III_5_Enhanced_Parallel_Block,
    }

    # Listado para guardar resultados
    resultados = []

    # Probar cada algoritmo
    for nombre, algoritmo in algoritmos.items():
        print(f"\nEjecutando {nombre}...")
        matrizRes, tiempo = medir_tiempo(algoritmo, matrizA, matrizB)
        
        # Mostrar resultado (solo una parte si la matriz es grande)
        print(f"\nResultado de {nombre}:")
        for row in matrizRes:
            print(row)

        # Guardar resultados para cada algoritmo
        resultados.append([nombre, size, tiempo])

        # Guardar tiempo en archivo .txt
        with open("tiempos_ejecucion.txt", "a") as archivo:
            archivo.write(f"{nombre} - Tamaño: {size} - Tiempo: {tiempo:.6f} segundos\n")

    # Guardar todos los resultados en CSV
    guardar_resultados_csv(resultados)

    # Graficar la comparación de tiempos de ejecución
    importar_matplotlib = False
    try:
        import matplotlib.pyplot as plt
        importar_matplotlib = True
    except ImportError:
        print("matplotlib no está instalado, se saltará la gráfica.")
    
    if importar_matplotlib:
        # Extraer los nombres de los algoritmos y los tiempos para la gráfica
        nombres = [r[0] for r in resultados]
        tiempos = [r[2] for r in resultados]

        # Crear gráfico de barras
        plt.bar(nombres, tiempos)
        plt.xlabel('Algoritmos')
        plt.ylabel('Tiempo de Ejecución (segundos)')
        plt.title('Comparación de Tiempos de Ejecución de Algoritmos de Multiplicación de Matrices')
        plt.xticks(rotation=45)
        plt.tight_layout()
        plt.show()

if __name__ == "__main__":
    main()
