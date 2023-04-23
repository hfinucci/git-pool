import matplotlib.pyplot as plt
from scipy.stats import norm
import numpy as np
import pandas as pd
import sys

labels = []

x1 = []
x2 = []
x3 = []

file = open(sys.argv[1], "r")
labels.append(float(next(file)))
for line in file:
    line = line.split()
    line = [float(i) for i in line]
    for time in line:
        x1.append(float(time))

file = open(sys.argv[2], "r")
labels.append(float(next(file)))
for line in file:
    line = line.split()
    line = [float(i) for i in line]
    for time in line:
        x2.append(float(time))

file = open(sys.argv[3], "r")
labels.append(float(next(file)))
for line in file:
    line = line.split()
    line = [float(i) for i in line]
    for time in line:
        x3.append(float(time))



# Grafica la densidad
plt.xlabel('Tiempo (s)')
plt.ylabel('Densidad de probabilidad')
plt.xscale("log")
plt.yscale("log")

max_value = 2
step = 0.0001
range_value = int(max_value / step)

# Obtener las coordenadas de las cimas de las barras
heights1, edges1 = np.histogram(x1, bins=[i * 0.0001 for i in range(range_value)], range=(0, 0.5), density=True)
x_vals1 = edges1[:-1] + np.diff(edges1) / 2
y_vals1 = heights1

# Dibujar la curva que conecta las cimas de las barras
plt.plot(x_vals1, y_vals1, '-', color='r', label=labels[0])

heights2, edges2 = np.histogram(x2, bins=[i * 0.0001 for i in range(range_value)], range=(0, 0.5), density=True)
x_vals2 = edges2[:-1] + np.diff(edges2) / 2
y_vals2 = heights2

# # Dibujar la curva que conecta las cimas de las barras
plt.plot(x_vals2, y_vals2, '-', color='g', label=labels[1])

heights3, edges3 = np.histogram(x3, bins=[i * 0.0001 for i in range(range_value)], range=(0, 0.5), density=True)
x_vals3 = edges3[:-1] + np.diff(edges3) / 2
y_vals3 = heights3

# # Dibujar la curva que conecta las cimas de las barras
plt.plot(x_vals3, y_vals3, '-', color='b', label=labels[2])


plt.legend()
plt.show()