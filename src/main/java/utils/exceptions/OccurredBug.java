package utils.exceptions;

/**
 * OccurredBug defines internal error exception.
 */
public class OccurredBug extends Exception {
  public OccurredBug(String msg) {
    super(String.format("Internal Error (Occurred Bug) : %s", msg));
  }
}
