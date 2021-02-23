package utils.exceptions;

/**
 * InvalidArgument is invalid argument exception.
 */
public class InvalidArgument extends Exception {
  public InvalidArgument(String msg) {
    super(String.format("Invalid Argument : %s", msg));
  }
}
