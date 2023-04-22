import matplotlib.pyplot as plt
import numpy as np
import sys

x = []
y = []
error = []
for i in range(1, len(sys.argv)):
    iter = []
    velocity = open(sys.argv[i], "r")
    x.append(float(next(velocity)))
    for line in velocity:
        if(float(line) < 10000):
            iter.append(float(line))
    print(len(iter))
    y.append(np.mean(iter))
    error.append(np.std(iter))

print(x)
print(y)
print(error)

fig, ax = plt.subplots()
ax.errorbar(x, y, error, fmt='o', linewidth=2, capsize=6, color= "#ba68c8")

plt.title("Variación del tiempo de finalización dependiendo de la posición inicial de la bola blanca")
plt.ylabel("Tiempo de finalización (s)")
plt.xlabel("Posición inicial de la bola blanca en y (cm)")
plt.show()