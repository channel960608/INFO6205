'''
Author: Caspar
Date: 2021-09-23 13:09:25
Description: file content
'''
#%%
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


#%%
df_raw = pd.read_csv("./data_assignment2.csv", header=None)
# %%
x = df_raw[0]
y_random = df_raw[1]
y_ordered = df_raw[2]
y_partially_ordered = df_raw[3]
y_reversed = df_raw[4]
# %%
plt.title('Relation between the length of the array and sort time for 4 different order')
plt.xlabel('The length of the array 2^n')
plt.plot(x, y_random, 'r--', label = 'Random Array')
plt.plot(x, y_ordered, 'g--', label = 'Ordered Array')
plt.plot(x, y_partially_ordered, 'b-*', label = 'Partially Ordered Array')
plt.plot(x, y_reversed, 'y--', label = 'Reverse Ordered Array')

plt.show()


# %%
