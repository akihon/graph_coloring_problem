package localsearch;

import algorithm.AlgorithmInterface;
import utils.logger.Logger;
import java.util.Random;

/**
 * Annealing implements annealing method.
 *
 * @param <T> solution type
 */
public class Annealing<T> implements LocalSearchInterface {
  private static final int INITIAL_TEMPERATURE_LOOP_COUNT = 1000;
  private static final double ALPHA = 0.97;  // 0.80 <= ALPHA <= 0.99

  private final int maxIteration;
  private final int maxInnerLoop;
  private double initialTemperature;
  private final AlgorithmInterface<T> algo;
  private final Random random;
  private final Logger logger;

  /**
   * constructor.
   *
   * @param maxIteration int
   * @param maxInnerLoop int
   * @param algo         algorithm.AlgorithmInterface
   */
  public Annealing(
      final int maxIteration,
      final int maxInnerLoop,
      final AlgorithmInterface<T> algo
  ) {
    this.maxIteration = maxIteration;
    this.maxInnerLoop = maxInnerLoop;
    this.algo = algo;
    random = new Random(System.currentTimeMillis());
    logger = new Logger(Annealing.class.getName(), null, true);

    initializeTemperature();
  }

  @Override
  public void go() {
    T best = algo.initialize();
    double bestEval = algo.evaluate(best);
    double eval = bestEval;

    logger.logger.info(
        String.format(
            "\nstart !\n" +
                "initial evaluation value : %16.5f",
            bestEval
        )
    );

    int iteration = 0;
    boolean improve = true;

    while (improve && iteration++ < maxIteration) {
      algo.preprocessing(iteration);

      int innerLoop = 0;
      double temperature = calcTemperature(iteration);

      while (innerLoop++ < maxInnerLoop) {
        T newResult = algo.algorithm(iteration);
        double newEval = algo.evaluate(newResult);

        if (bestEval >= newEval) {
          bestEval = newEval;
          best = newResult;
        }

        improve = updateProbability(eval, newEval, temperature);

        if (improve) {
          eval = newEval;
          algo.update(newResult);
          break;
        }
      }

      logger.logger.config(
          String.format(
              "\n   iteration           : %10d\n" +
                  "   temperature         : %16.5f\n" +
                  "   best evaluate value : %16.5f\n" +
                  "   evaluate value      : %16.5f\n" +
                  "   improve             : %b\n\n",
              iteration, temperature, bestEval, eval, improve
          )
      );
    }

    logger.logger.info(
        String.format("\nfinish !\nevaluation value : %16.5f", bestEval)
    );

    algo.update(best);
  }

  private void initializeTemperature() {
    double left = 0.0;
    double right = 1.0e10;
    double eval = algo.evaluate(algo.initialize());

    while (right - left >= 0.1) {
      int improveCount = 0;
      initialTemperature = (left + right) / 2.0;

      for (int i = 0; i < INITIAL_TEMPERATURE_LOOP_COUNT; i++) {
        double newEval = algo.evaluate(algo.algorithm(1));

        if (updateProbability(eval, newEval, calcTemperature(1))) {
          improveCount++;
        }
      }

      double probability = (double) improveCount / INITIAL_TEMPERATURE_LOOP_COUNT;
      if (probability > 0.85 && probability <= 0.90) {
        break;
      } else if (probability > 0.90) {
        right = initialTemperature;
      } else {
        left = initialTemperature;
      }
    }

    logger.logger.info(String.format("initial temperature : %16.5f", initialTemperature));
  }

  private boolean updateProbability(
      final double eval,
      final double newEval,
      final double temperature
  ) {
    return random.nextDouble() < Math.exp((eval - newEval) / temperature);
  }

  private double calcTemperature(final int iteration) {
    // other cooling schedule
    // - linear
    //   temperature_k = ALPHA^k * temperature_0
    //return initialTemperature * Math.pow(ALPHA, iteration);

    // - temperature_k = temperature_0 / log_2(k + 2)
    //   it is cool too slowly.
    return initialTemperature / Math.log(iteration + 2) * Math.log(2);
  }
}
