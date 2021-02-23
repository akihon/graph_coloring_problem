package problem;

import utils.exceptions.InvalidArgument;

/**
 * WattsStrogatzNetworkArgs define arguments of wattsStrogatzNetwork method.
 */
public class WattsStrogatzNetworkArgs {
  final int vertex;
  final int degree;
  final double beta;

  public WattsStrogatzNetworkArgs(final int vertex, final int degree, final double beta) {
    this.vertex = vertex;
    this.degree = degree;
    this.beta = beta;
  }

  String valid() {
    if (vertex <= 0) {
      return "vertex is less than 1";
    }

    if (degree >= vertex) {
      return String.format(
          "%s : degree is more than vertex (%d)",
          WattsStrogatzNetworkArgs.class.getName(),
          vertex
      );
    }

    double log2V = Math.log(vertex) / Math.log(2);
    if (degree <= log2V) {
      return String.format(
          "%s : degree is less than log_2(vertex) (%9.5f)",
          WattsStrogatzNetworkArgs.class.getName(),
          log2V
      );
    }

    if (degree % 2 != 0) {
      return String.format(
          "%s : degree is not even",
          WattsStrogatzNetworkArgs.class.getName()
      );
    }

    if (beta < 0.0) {
      return String.format(
          "%s : beta is less than 0.0",
          WattsStrogatzNetworkArgs.class.getName()
      );
    }

    if (beta > 1.0) {
      return String.format(
          "%s : beta is more than 1.0",
          WattsStrogatzNetworkArgs.class.getName()
      );
    }

    return "";
  }
}
