package problem;

import model.UndirectedGraph;
import utils.exceptions.InvalidArgument;
import utils.exceptions.OccurredBug;

/**
 * CreateInterface is repository regarding create graph coloring problem.
 */
public interface CreateInterface {
  /**
   * randomNetwork create a random network.
   * if dense < 0 or dense > 1, throws invalid exception.
   *
   * @param dense double (0 <= dense <= 1)
   * @return model.UndirectedGraph
   * @throws InvalidArgument invalid argument exception
   */
  UndirectedGraph randomNetwork(final double dense) throws InvalidArgument;

  /**
   * wattsStrogatzNetwork construct a network using watts-strogatz model.
   *
   * @param args WattsStrogatzNetworkArgs
   * @return model.UndirectedGraph
   * @throws InvalidArgument invalid argument exception
   * @throws OccurredBug     internal error exception
   */
  UndirectedGraph wattsStrogatzNetwork(
      WattsStrogatzNetworkArgs args
  ) throws InvalidArgument, OccurredBug;
}