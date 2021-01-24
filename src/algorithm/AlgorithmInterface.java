package algorithm;

/**
 * AlgorithmInterface is interface of algorithm.
 *
 * @param <T> T is type of result
 */
public interface AlgorithmInterface<T> {
  T initialize();

  T algorithm(final int iteration);

  boolean isImproved();

  void update(final int iteration, final T result);

  T getResult();
}
