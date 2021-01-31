import algorithm.AlgorithmInterface;
import algorithm.FruchtermanReingold;
import algorithm.GraphColoring;
import draw.Graph;
import localsearch.Annealing;
import localsearch.LocalSearch;
import localsearch.LocalSearchInterface;
import model.Coloring;
import model.Coordinate;
import model.UndirectedGraph;
import problem.Create;
import problem.CreateInterface;
import utils.exceptions.InvalidArgument;

public class GraphColoringProblem {
  public static void main(String[] args) {
    new GraphColoringProblem();
  }

  private GraphColoringProblem() {
    CreateInterface problem;
    try {
      problem = new Create(50, 0.15);
    } catch (InvalidArgument invalidArgument) {
      invalidArgument.printStackTrace();
      return;
    }

    UndirectedGraph graph = problem.undirectedGraph();
    AlgorithmInterface<Coloring> gc = new GraphColoring(graph);
    //LocalSearchInterface lsForGraphColoring = new LocalSearch<>(
    //    10000,
    //    2000,
    //    gc
    //);
    LocalSearchInterface lsForGraphColoring = new Annealing<>(
        5000,
        30,
        gc
    );
    lsForGraphColoring.go();

    System.out.println(gc.getResult());
    System.out.printf("feasible : %b\n", gc.getResult().isFeasible(graph));
    System.out.printf("evaluation value: %6.3f\n\n", gc.evaluate(gc.getResult()));

    AlgorithmInterface<Coordinate[]> fr = new FruchtermanReingold(graph);
    LocalSearchInterface lsForFruchtermanReingold = new LocalSearch<>(
        graph.vertex * graph.vertex + graph.edge,
        1,
        fr
    );
    lsForFruchtermanReingold.go();

    //System.out.println("coordinates");
    //for (Coordinate coordinate : fr.getResult()) {
    //  System.out.println(coordinate);
    //}

    new Graph(graph, fr.getResult(), gc.getResult());
  }
}
