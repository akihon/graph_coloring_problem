package localsearch;

import algorithm.AlgorithmInterface;

/**
 * LocalSearch implements local search algorithm.
 * T is type of result.
 */
public class LocalSearch<T> implements LocalSearchInterface {
  private final int maxIteration;
  private final int maxInnerLoop;
  private final AlgorithmInterface<T> algo;
  private final boolean verbose;

  /**
   * constructor.
   *
   * @param maxIteration int
   * @param maxInnerLoop int
   * @param algo         algorithm.AlgorithmInterface
   * @param verbose      boolean
   */
  public LocalSearch(
      final int maxIteration,
      final int maxInnerLoop,
      final AlgorithmInterface<T> algo,
      final boolean verbose
  ) {
    this.maxIteration = maxIteration;
    this.maxInnerLoop = maxInnerLoop;
    this.algo = algo;
    this.verbose = verbose;
  }

  @Override
  public void go() {
    int iteration = 0;
    boolean improved = true;

    T result = algo.initialize();
    double eval = algo.evaluate(result);

    System.out.printf("\nstart !\ninitial evaluation value : %16.5f\n", eval);

    while (improved && iteration++ < maxIteration) {
      int innerLoop = 0;

      algo.preprocessing(iteration);

      while (innerLoop++ < maxInnerLoop) {
        T newResult = algo.algorithm(iteration);
        double newEval = algo.evaluate(newResult);

        improved = eval >= newEval;

        if (improved) {
          eval = newEval;
          algo.update(newResult);
          break;
        }
      }

      if (verbose) {
        System.out.printf("\n"
                + "   iteration           : %10d\n"
                + "   evaluate value      : %16.5f\n"
                + "   improve             : %b\n\n",
            iteration, eval, improved
        );
      }
    }

    System.out.printf("\nfinish !\nevaluation value : %16.5f\n", eval);
  }
}
