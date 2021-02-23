package problem;

import model.UndirectedGraph;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;

/**
 * GraphColoring is repository regarding create graph coloring problem.
 */
public interface GraphColoringInterface {
  /**
   * randomNetwork create a random network.
   * if vertex <= 0, throws invalid exception.
   * if dense < 0 or dense > 1, throws invalid exception.
   *
   * @param vertex int (0 < vertex)
   * @param dense  double (0 <= dense <= 1)
   * @return model.UndirectedGraph
   * @throws InvalidArgument invalid argument exception
   */
  UndirectedGraph randomNetwork(final int vertex, final double dense) throws InvalidArgument;

  /**
   * wattsStrogatzNetwork construct a network using watts-strogatz model.
   *
   * @param args WattsStrogatzNetworkArgs
   * @return model.UndirectedGraph
   * @throws InvalidArgument invalid argument exception
   * @throws OccurredBug     internal error exception
   */
  UndirectedGraph wattsStrogatzNetwork(
      final WattsStrogatzNetworkArgs args
  ) throws InvalidArgument, OccurredBug;

  /**
   * readFile construct a network to read the graph file.
   *
   * @param fileName String
   * @return model.UndirectedGraph
   * @throws NotFound    not found exception
   * @throws OccurredBug internal error exception
   */
  UndirectedGraph readFile(final String fileName) throws NotFound, OccurredBug;
}