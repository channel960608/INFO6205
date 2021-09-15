'''
Author: Caspar
Date: 2021-09-14 17:45:23
Description: file content
'''
#%%
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


#%%
df_raw = pd.read_csv("./data.csv", header=None)
x = df_raw[0]
y = df_raw[1]
y_pow_2 = [item * item for item in y]
# %%

plt.title('Relation between step n and distance d')
plt.xlabel('# of Steps')
plt.ylabel('Distance')
plt.scatter(x, y, marker ='*')
plt.show()
# %%
plt.title('Relation between step n and distance d ^ 2')
plt.xlabel('# of Steps')
plt.ylabel('Distance ^ 2')
plt.scatter(x, y_pow_2, marker ='*')
plt.show()

#%%
# Linear fit
plt.title('Relation between step n and distance d ^ 2 and Linear Fit Result')
plt.xlabel('# of Steps')
plt.ylabel('Distance ^ 2')
coeff = np.polyfit(x, y_pow_2, 1)
plt.plot(x, y_pow_2)
plt.plot(x, coeff[0] * x + coeff[1], 'r-')

plt.show()
# looks good!
# %%
print("Result for the linear fit: d ^ 2 = {}n + {}".format(coeff[0], coeff[1]))
# %%
