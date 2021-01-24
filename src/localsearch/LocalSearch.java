package localsearch;

import algorithm.AlgorithmInterface;

/**
 * LocalSearch implements local search algorithm.
 * T is type of result.
 */
public class LocalSearch<T> implements LocalSearchInterface {
  final int maxIteration;
  final int maxInnerLoop;
  final AlgorithmInterface<T> algo;

  public LocalSearch(
      final int maxIteration,
      final int maxInnerLoop,
      final AlgorithmInterface<T> algo
  ) {
    this.maxIteration = maxIteration;
    this.maxInnerLoop = maxInnerLoop;
    this.algo = algo;
  }

  @Override
  public void go() {
    int iteration = 0;
    boolean improved = true;

    algo.initialize();

    while (improved && iteration++ < maxIteration) {
      int innerLoop = 0;

      while (innerLoop++ < maxInnerLoop) {
        T result = algo.algorithm(iteration);
        improved = algo.isImproved();

        if (improved) {
          algo.update(iteration, result);
          break;
        }
      }
    }
  }
}
