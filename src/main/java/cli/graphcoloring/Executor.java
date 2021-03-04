package cli.graphcoloring;

import algorithm.AlgorithmInterface;
import draw.Graph;
import localsearch.LocalSearchInterface;
import model.Coloring;
import model.Coordinate;
import model.UndirectedGraph;

/**
 * Executor solve the graph coloring.
 */
public class Executor {
  private final UndirectedGraph graph;
  private final AlgorithmInterface<Coloring> graphColoringAlgo;
  private final LocalSearchInterface graphColoringLocalSearch;
  private final AlgorithmInterface<Coordinate[]> coordinatesAlgo;
  private final LocalSearchInterface coordinatesLocalSearch;
  private final boolean verbose;
  private final boolean draw;

  /**
   * constructor.
   *
   * @param graph                    model.UndirectedGraph
   * @param graphColoringAlgo        algorithm.AlgorithmInterface
   * @param graphColoringLocalSearch localsearch.LocalSearchInterface
   * @param coordinatesAlgo          algorithm.AlgorithmInterface
   * @param coordinatesLocalSearch   localsearch.LocalSearchInterface
   * @param draw                     boolean
   * @param verbose                  boolean
   */
  public Executor(
      final UndirectedGraph graph,
      final AlgorithmInterface<Coloring> graphColoringAlgo,
      final LocalSearchInterface graphColoringLocalSearch,
      final AlgorithmInterface<Coordinate[]> coordinatesAlgo,
      final LocalSearchInterface coordinatesLocalSearch,
      final boolean draw,
      final boolean verbose
  )  {
    this.graph = graph;
    this.graphColoringAlgo = graphColoringAlgo;
    this.graphColoringLocalSearch = graphColoringLocalSearch;
    this.coordinatesAlgo = coordinatesAlgo;
    this.coordinatesLocalSearch = coordinatesLocalSearch;
    this.verbose = verbose;
    this.draw = draw;
  }

  /**
   * go solve the graph coloring problem.
   */
  public void go() {
    graphColoringLocalSearch.go();

    System.out.println(graphColoringAlgo.getResult());
    System.out.printf("feasible : %b\n", graphColoringAlgo.getResult().isFeasible(graph));
    System.out.printf("evaluation value: %6.3f\n\n",
        graphColoringAlgo.evaluate(graphColoringAlgo.getResult())
    );

    if (draw) {
      coordinatesLocalSearch.go();

      if (verbose) {
        System.out.println("coordinates");
        for (Coordinate coordinate : coordinatesAlgo.getResult()) {
          System.out.println(coordinate);
        }
      }

      new Graph(graph, coordinatesAlgo.getResult(), graphColoringAlgo.getResult());
    }
  }
}
