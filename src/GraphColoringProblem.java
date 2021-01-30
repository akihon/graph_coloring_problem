import algorithm.AlgorithmInterface;
import algorithm.FruchtermanReingold;
import algorithm.GraphColoring;
import draw.Graph;
import localsearch.Annealing;
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
      problem = new Create(50, 0.15);
    } catch (InvalidArgument invalidArgument) {
      invalidArgument.printStackTrace();
      return;
    }

    DirectedGraph directedGraph = problem.directedGraph();
    AlgorithmInterface<Coloring> gc = new GraphColoring(directedGraph);
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
    System.out.printf("feasible : %b\n", gc.getResult().isFeasible(directedGraph));
    System.out.printf("evaluation value: %6.3f\n\n", gc.evaluate(gc.getResult()));

    AlgorithmInterface<Coordinate[]> fr = new FruchtermanReingold(directedGraph);
    LocalSearchInterface lsForFruchtermanReingold = new LocalSearch<>(
        directedGraph.vertex * directedGraph.vertex + directedGraph.edge,
        1,
        fr
    );
    lsForFruchtermanReingold.go();

    //System.out.println("coordinates");
    //for (Coordinate coordinate : fr.getResult()) {
    //  System.out.println(coordinate);
    //}

    new Graph(directedGraph, fr.getResult(), gc.getResult());
  }
}
