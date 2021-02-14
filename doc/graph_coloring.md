# Graph Coloring
![](picraph_coloring.png)

## Problem
we solve the vertex coloring.  
in case of want to know this problem, show the following site.  
[wikipedia](https://en.wikipedia.org/wiki/Graph_coloring)  

## LocalSearch
we use local search algorithm and annealing method to this problem.  

### Evaluation Function
| sign | desc |
| --- | --- |
| ![](https://render.githubusercontent.com/render/math?math=(V,E)) | graph |
| ![](https://render.githubusercontent.com/render/math?math=c) | colorings |
| ![](https://render.githubusercontent.com/render/math?math=g(c)) | induce subgraph whose vertexes is color c |
| ![](https://render.githubusercontent.com/render/math?math=V_c) | the vertexes in ![](https://render.githubusercontent.com/render/math?math=g(c)) |
| ![](https://render.githubusercontent.com/render/math?math=E_c) | the edges in ![](https://render.githubusercontent.com/render/math?math=g(c)) |

evaluation function is following.  
![](https://render.githubusercontent.com/render/math?math=eval=|c|%2B\frac{1}{\alpha}(\sum_{0}^{|c|}{P(c)}-|V|)) .  
![](https://render.githubusercontent.com/render/math?math=P(c)) is penalty function defined as ![](https://render.githubusercontent.com/render/math?math=P(c)=|V_c|^2%2B|E_c|)  .

### strategy
#### swap
TBD

#### replace
TBD

#### determine probability
TBD
