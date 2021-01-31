package problem;

import utils.exceptions.InvalidArgument;

/**
 * WattsStrogatzNetworkArgs define arguments of wattsStrogatzNetwork method.
 */
public class WattsStrogatzNetworkArgs {
  final int degree;
  final double beta;

  public WattsStrogatzNetworkArgs(final int degree, final double beta) {
    this.degree = degree;
    this.beta = beta;
  }

  void valid(final int vertex) throws InvalidArgument {
    if (degree >= vertex) {
      throw new InvalidArgument(String.format("degree is more than vertex (%d)", vertex));
    }

    double log2V = Math.log(vertex) / Math.log(2);
    if (degree <= log2V) {
      throw new InvalidArgument(String.format("degree is less than log_2(vertex) (%9.5f)", log2V));
    }

    if (degree % 2 != 0) {
      throw new InvalidArgument("degree is not even");
    }

    if (beta < 0.0) {
      throw new InvalidArgument("beta is less than 0.0");
    }

    if (beta > 1.0) {
      throw new InvalidArgument("beta is more than 1.0");
    }
  }
}
