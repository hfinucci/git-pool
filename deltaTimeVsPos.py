import math

import matplotlib.pyplot as plt
import numpy as np
import sys

CALCULATE_FRECUENCY = False
STANDARD_ERROR = False

x = []
y = []
error = []
for i in range(1, len(sys.argv)):
    averages = []
    file = open(sys.argv[i], "r")
    x.append(float(next(file)))
    for line in file:
        line = line.split()
        line = [float(i) for i in line]
        aux = np.mean(line)
        if aux < 10:
            averages.append(np.mean(line))
    if CALCULATE_FRECUENCY:
        y.append(1/np.mean(averages))
    else:
        y.append(np.mean(averages))
    if STANDARD_ERROR:
        error.append(np.std(averages, ddof=1)/math.sqrt(len(line)))
    else:
        error.append(np.std(averages, ddof=1))

# value = np.mean(averages)
# error = np.std(averages, ddof=1)
print(len(averages))

fig, ax = plt.subplots()
ax.errorbar(x, y, error, fmt='o', linewidth=2, capsize=6, color="#ba68c8")

# plt.title("Promedio de tiempo entre colisiones dependiendo\nde la posición inicial de la bola blanca")
plt.ylabel("Promedio de tiempo entre colisiones (s)")
plt.xlabel("Posición inicial de la bola blanca en y (cm)")
plt.savefig("fig1")
plt.show()
