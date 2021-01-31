package localsearch;

import algorithm.AlgorithmInterface;
import utils.logger.Logger;

/**
 * LocalSearch implements local search algorithm.
 * T is type of result.
 */
public class LocalSearch<T> implements LocalSearchInterface {
  private final int maxIteration;
  private final int maxInnerLoop;
  private final AlgorithmInterface<T> algo;
  private final Logger logger;


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
    logger = new Logger(LocalSearch.class.getName(), null, true);
  }

  @Override
  public void go() {
    int iteration = 0;
    boolean improved = true;

    T result = algo.initialize();
    double eval = algo.evaluate(result);

    logger.logger.info(
        String.format(
            "\nstart !\n" +
                "initial evaluation value : %16.5f",
           eval
        )
    );

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

      logger.logger.config(
          String.format(
              "\n   iteration           : %10d\n" +
                  "   evaluate value      : %16.5f\n" +
                  "   improve             : %b\n\n",
              iteration, eval, improved
          )
      );
    }

    logger.logger.info(
        String.format("\nfinish !\nevaluation value : %16.5f", eval)
    );
  }
}
