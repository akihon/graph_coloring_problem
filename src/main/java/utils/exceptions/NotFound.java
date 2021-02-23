package utils.exceptions;

/**
 * NotFound is not found exception.
 */
public class NotFound extends Exception {
  public NotFound(String msg) {
    super(String.format("Not Found : %s", msg));
  }
}
