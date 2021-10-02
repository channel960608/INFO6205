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
df_raw = pd.read_csv("./data_assignment3.csv", header=None)
# %%
x = df_raw[0]
y = df_raw[1]

# %%
plt.title('Relation between the # of nodes and the time of union to get them connected')
plt.xlabel('# of nodes')
plt.plot(x, y, 'r--', label = 'Random Array')

plt.show()


# %%
