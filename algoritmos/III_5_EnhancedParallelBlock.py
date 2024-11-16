from concurrent.futures import ThreadPoolExecutor
import threading

def block_multiply_section(matrizA, matrizB, matrizRes, start, end, size2, lock):
    size1 = len(matrizA)
    for i1 in range(start, end, size2):
        for j1 in range(0, size1, size2):
            for k1 in range(0, size1, size2):
                for i in range(i1, min(i1 + size2, size1)):
                    for j in range(j1, min(j1 + size2, size1)):
                        for k in range(k1, min(k1 + size2, size1)):
                            # Usar el lock para garantizar que solo un hilo pueda actualizar la matrizRes a la vez
                            with lock:
                                matrizRes[i][j] += matrizA[i][k] * matrizB[k][j]

def alg_III_5_Enhanced_Parallel_Block(matrizA, matrizB, size1, size2):
    matrizRes = [[0.0 for _ in range(size1)] for _ in range(size1)]
    mid_point = size1 // 2
    lock = threading.Lock()  # Crear un Lock para sincronización
    with ThreadPoolExecutor(max_workers=2) as executor:
        # Dividir el trabajo en dos tareas paralelas
        executor.submit(block_multiply_section, matrizA, matrizB, matrizRes, 0, mid_point, size2, lock)
        executor.submit(block_multiply_section, matrizA, matrizB, matrizRes, mid_point, size1, size2, lock)
    return matrizRes

def multiply(matrizA, matrizB):
    N = len(matrizA)
    P = len(matrizB[0])
    matrizRes = alg_III_5_Enhanced_Parallel_Block(matrizA, matrizB, N, P)
    return matrizRes
