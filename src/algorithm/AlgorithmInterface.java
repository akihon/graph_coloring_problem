package algorithm;

/**
 * AlgorithmInterface is interface of algorithm.
 *
 * @param <T> T is type of result
 */
public interface AlgorithmInterface<T> {
  T initialize();

  void preprocessing(final int iteration);

  T algorithm(final int iteration);

  /**
   * evaluate return evaluation value.
   * the value is lower, the solution is better.
   *
   * @param result T
   * @return double
   */
  double evaluate(T result);

  void update(final T result);

  T getResult();
}
