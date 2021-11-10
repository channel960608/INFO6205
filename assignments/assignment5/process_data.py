'''
Author: Caspar
Date: 2021-11-08 20:30:57
Description: file content
'''
#%%
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from pandas.core.frame import DataFrame

#%%
df_raw = pd.read_csv("./result_4core.csv", header=None)
# %%
time_per_num = []
for index in range(len(df_raw[0])):
    time_per_num.append((df_raw[4][index] * 10000 / df_raw[3][index]))
df_raw[5] = time_per_num
# %%
# df_raw.insert(df_raw.shape[1], 5, time_per_num)
#%%
df_raw.sort_values(by=5, ascending=True, na_position='last')
# %%
# choose the max array size 33554432
df_raw[df_raw[3] == 33554432][df_raw[1] == 8].sort_values(by=5, ascending=True, na_position='last')
# %%

def filter(size, parall, partion):
    df_choose = df_raw
    if size:
        df_choose = df_choose[df_choose[3] == size]
    if parall:
        df_choose = df_choose[df_choose[1] == parall]
    if partion:
        df_choose = df_choose[df_choose[2] == partion]
    return df_choose

def draw(df, column_x, title, xlabel):
    x = df[column_x]
    y = [1 / x for x in df[5]]
    plt.title(title)
    plt.xlabel(xlabel)
    plt.plot(x, y, 'r', label = 'Random Array')
    plt.show()

#%%
# Find Cutoff
def comparePartionXY(size, parall):
    df_cur = filter(size, parall, None)
    x = df_cur[2]
    y = [1 / i for i in df_cur[5]]
    return [x, y]
xy_pair = []
xy_pair.append(comparePartionXY(33554432, 8))
xy_pair.append(comparePartionXY(16777216, 8))
xy_pair.append(comparePartionXY(8388608, 8))
xy_pair.append(comparePartionXY(4194304, 8))
xy_pair.append(comparePartionXY(2097152, 8))
xy_pair.append(comparePartionXY(33554432, 16))
xy_pair.append(comparePartionXY(16777216, 16))
xy_pair.append(comparePartionXY(8388608, 16))
xy_pair.append(comparePartionXY(4194304, 8))
xy_pair.append(comparePartionXY(33554432, 4))
xy_pair.append(comparePartionXY(16777216, 4))
xy_pair.append(comparePartionXY(8388608, 4))
xy_pair.append(comparePartionXY(4194304, 8))
xy_pair.append(comparePartionXY(2097152, 8))
x = [x[0] for x in xy_pair]
y = [x[1] for x in xy_pair]

def drawMultipleLines(xs, ys):
    plt.title("The efficiency of sorting given different array size(2^21 ~ 2^25) and parallelism(4~16)")
    plt.xlabel("#cutoff")
    plt.ylabel("sorting efficiency (number of integers)/time")
    for i in range(len(xs)):
        plt.plot(xs[i], ys[i], 'r')        
    plt.show()

drawMultipleLines(x, y)
# %%
# Given cutoff, find parall
def compare_parall_XY(size, partion):
    df_cur = filter(size, None, partion)
    x = df_cur[1]
    y = [1 / i for i in df_cur[5]]
    return [x, y]

xy_pair2 = []
list_size = [33554432, 16777216, 8388608, 4194304]
list_cutoff = [262144, 131072, 65536, 32768, 16384]
for m_size in list_size:
    for m_cutoff in list_cutoff:
        xy_pair2.append(compare_parall_XY(m_size, m_cutoff))
x2 = [x[0] for x in xy_pair2]
y2 = [x[1] for x in xy_pair2]
def drawMultipleLinesPartion(xs, ys):
    plt.title("The efficiency of sorting given different array size(2^21 ~ 2^25) and cufoff(2^14 ~ 2^18)")
    plt.xlabel("parallelism")
    plt.ylabel("sorting efficiency (number of integers)/time")
    for i in range(len(xs)):
        plt.plot(xs[i], ys[i], 'r')        
    plt.show()
drawMultipleLinesPartion(x2, y2)

# %%
### Given cutoff and parallelism, find size of array
def compare_size_XY(parall, partion):
    df_cur = filter(None, parall, partion)
    x = df_cur[3]
    y = [1 / i for i in df_cur[5]]
    return [x, y]

xy_pair3 = []
list_parall= [8, 4, 2]
list_cutoff = [262144, 131072, 65536, 32768, 16384]
for m_parall in list_parall:
    for m_cutoff in list_cutoff:
        xy_pair3.append(compare_size_XY(m_parall, m_cutoff))
x3 = [x[0] for x in xy_pair3]
y3 = [x[1] for x in xy_pair3]
def drawMultipleLinesSize(xs, ys):
    plt.title("The efficiency of sorting given different parallelism(2, 4, 8) and cutoff(2^14 ~ 2^18)")
    plt.xlabel("array size")
    plt.ylabel("sorting efficiency (number of integers)/time")
    for i in range(len(xs)):
        plt.plot(xs[i], ys[i], 'r')        
    plt.show()
drawMultipleLinesSize(x3, y3)
# %%
# Fix Length / cutoff / parallelism
import math
df_ch = filter(33554432, 8, None)
x4 = []
y4 = []
for index in range(df_ch.shape[0]):
    curr_size = list(df_ch[3])[index] 
    curr_cutoff = list(df_ch[2])[index] 
    curr_parr= list(df_ch[1])[index]
    if curr_parr > 1:
        y4.append(1 / list(df_ch[5])[index])
        x4.append(curr_cutoff)

plt.plot(x4, y4, 'r')     
plt.show()      

# %%
df_ch = filter(33554432 / 2, 8, None)
x4 = []
y4 = []
for index in range(df_ch.shape[0]):
    curr_size = list(df_ch[3])[index] 
    curr_cutoff = list(df_ch[2])[index] 
    curr_parr= list(df_ch[1])[index]
    if curr_parr > 1:
        y4.append(1 / list(df_ch[5])[index])
        x4.append(curr_cutoff)

plt.plot(x4, y4, 'r')     
plt.show()   
# %%
df_ch = filter(33554432 / 4, 8, None)
x4 = []
y4 = []
for index in range(df_ch.shape[0]):
    curr_size = list(df_ch[3])[index] 
    curr_cutoff = list(df_ch[2])[index] 
    curr_parr= list(df_ch[1])[index]
    if curr_parr > 1:
        y4.append(1 / list(df_ch[5])[index])
        x4.append(curr_cutoff)

plt.plot(x4, y4, 'r')     
plt.show()   

# %%
df_ch = filter(33554432 / 8, 8, None)
x4 = []
y4 = []
for index in range(df_ch.shape[0]):
    curr_size = list(df_ch[3])[index] 
    curr_cutoff = list(df_ch[2])[index] 
    curr_parr= list(df_ch[1])[index]
    if curr_parr > 1:
        y4.append(1 / list(df_ch[5])[index])
        x4.append(curr_cutoff)

plt.plot(x4, y4, 'r')     
plt.show()  
# %%
