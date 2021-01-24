import algorithm.AlgorithmInterface;
import algorithm.FruchtermanReingold;
import draw.Graph;
import localsearch.LocalSearch;
import localsearch.LocalSearchInterface;
import model.Coordinate;
import model.DirectedGraph;
import problem.Create;
import problem.CreateInterface;
import utils.exceptions.InvalidArgument;
import java.util.Arrays;

public class GraphColoringProblem {
  public static void main(String[] args) {
    new GraphColoringProblem();
  }

  private GraphColoringProblem() {
    CreateInterface problem;
    try {
      problem = new Create(20, 0.2);
    } catch (InvalidArgument invalidArgument) {
      invalidArgument.printStackTrace();
      return;
    }

    DirectedGraph directedGraph = problem.directedGraph();
    AlgorithmInterface<Coordinate[]> fr = new FruchtermanReingold(directedGraph);
    LocalSearchInterface ls = new LocalSearch<>(
        100,
        10,
        fr
    );

    ls.go();
    new Graph(directedGraph, fr.getResult());
  }
}
