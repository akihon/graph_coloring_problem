import algorithm.AlgorithmInterface;
import algorithm.FruchtermanReingold;
import algorithm.GraphColoring;
import draw.Graph;
import localsearch.LocalSearch;
import localsearch.LocalSearchInterface;
import model.Coloring;
import model.Coordinate;
import model.DirectedGraph;
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
      problem = new Create(20, 0.3);
    } catch (InvalidArgument invalidArgument) {
      invalidArgument.printStackTrace();
      return;
    }

    DirectedGraph directedGraph = problem.directedGraph();
    AlgorithmInterface<Coloring> gc = new GraphColoring(directedGraph);
    LocalSearchInterface lsForGraphColoring = new LocalSearch<>(
        10000,
        2000,
        gc
    );
    lsForGraphColoring.go();

    System.out.println(gc.getResult());
    System.out.printf("evaluation value: %6.3f\n\n", gc.evaluate(gc.getResult()));

    AlgorithmInterface<Coordinate[]> fr = new FruchtermanReingold(directedGraph);
    LocalSearchInterface lsForFruchtermanReingold = new LocalSearch<>(
        directedGraph.vertex * directedGraph.vertex + directedGraph.edge,
        1,
        fr
    );
    lsForFruchtermanReingold.go();

    System.out.println("coordinates");
    for (Coordinate coordinate : fr.getResult()) {
      System.out.println(coordinate);
    }

    new Graph(directedGraph, fr.getResult(), gc.getResult());
  }
}
