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

  /**
   * constructor.
   *
   * @param maxIteration int
   * @param maxInnerLoop int
   * @param algo algorithm.AlgorithmInterface
   */
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

    T result = algo.initialize();
    double eval = algo.evaluate(result);

    while (improved && iteration++ < maxIteration) {
      int innerLoop = 0;

      while (innerLoop++ < maxInnerLoop) {
        T newResult = algo.algorithm(iteration);
        double newEval = algo.evaluate(newResult);

        improved = eval >= newEval;

        if (improved) {
          eval = newEval;
          algo.update(iteration, newResult);
          break;
        }
      }
    }
  }
}
