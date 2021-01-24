import algorithm.AlgorithmInterface;
import algorithm.FruchtermanReingold;
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
      problem = new Create(10, 0.7);
    } catch (InvalidArgument invalidArgument) {
      invalidArgument.printStackTrace();
      return;
    }

    DirectedGraph directedGraph = problem.directedGraph();
    AlgorithmInterface<Coordinate[]> fr = new FruchtermanReingold(directedGraph);
    LocalSearchInterface ls = new LocalSearch<>(
        10,
        10,
        fr
    );

    ls.go();

    System.out.println(Arrays.toString(fr.getResult()));
  }
}
