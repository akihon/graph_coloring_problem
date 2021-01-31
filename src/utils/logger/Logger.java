package utils.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * Logger wrap java.util.logging.
 */
public class Logger {
  public final java.util.logging.Logger logger;

  /**
   * constructor.
   *
   * @param name     String
   * @param filename String (if empty or null, output console)
   * @param verbose  boolean
   */
  public Logger(final String name, final String filename, final boolean verbose) {
    logger = java.util.logging.Logger.getLogger(name);

    try {
      if (filename == null || filename.equals("")) {
        logger.addHandler(new ConsoleHandler());
      } else {
        logger.addHandler(new FileHandler(filename));
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (SecurityException e) {
      e.printStackTrace();
    }

    logger.setUseParentHandlers(false);

    // TODO: message whose level is lower than INFO  messages do not output. why ???
    try {
      if (verbose) {
        logger.setLevel(Level.ALL);
      } else {
        logger.setLevel(Level.INFO);
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }
}
