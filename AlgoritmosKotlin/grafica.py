import matplotlib.pyplot as plt

def graficar(resultados):
    nombres = [r[0] for r in resultados]
    tiempos = [r[1] for r in resultados]
    
    plt.bar(nombres, tiempos)
    plt.xlabel('Algoritmos')
    plt.ylabel('Tiempo de Ejecución (Milisegundos)')
    plt.title('Comparación de Tiempos de Ejecución')
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()

# Leer resultados de un archivo
import sys
import json

if __name__ == "__main__":
    resultados = []
    with open("resultados.txt") as f:
        for line in f:
        # Dividir cada línea en partes
            partes = line.strip().split(" ")
            if len(partes) == 4:
                algoritmo = partes[0]
                tiempo = int(partes[1])
                tamano = partes[2] + " " + partes[3]
                resultados.append([algoritmo,tiempo,tamano])
    graficar(resultados)
