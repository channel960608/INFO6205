<!--
 * @Author: Caspar
 * @Date: 2021-09-14 18:52:52
 * @Description: file content
-->

# Assignment 1
Random Walk  
Luo Chen   
001564677  

## Conclusion
Let d present the distance and n present the steps. The relation between d and n should be like :   
$d^2$ = $k * n + b$  
or  
d = $\sqrt[2]{k * n + b}$  
where k is slop and b is bias.  
To be specific, the calculated result in my experience is:  
$d^2$ = 0.7868118246468458 * n - 0.06496410245863907   

## Evidence
The relation between d and n  
![](./relation_n_d.png)  
The relation between $d^2$ and n  
![](./relation_n_d_p_2.png)  
The linear fit result  
![](./fit_result.png)  

## Code  
[RandomWalk.java](RandomWalk.java)  for the java code, which will also generate the output data file.  
[data.csv](./data.csv) for the result of my experience.  
[draw_plot.py](./draw_plot.py) for the python code to draw the plots and do linear fit. Python 3.6.8 in my case and requirements.txt for dependency.  

## Unit Test
![](./unit_test.jpg) 

<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">
  MathJax.Hub.Config({ tex2jax: {inlineMath: [['$', '$']]}, messageStyle: "none" });
</script>