package cli;

import cli.graphcoloring.Command;
import java.util.Arrays;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      help();
      return;
    }

    new Main(args);
  }

  private Main(final String[] args) {
    if (args.length > 1 && args[0].equals("help")) {
      switch (args[1]) {
        case "coloring":
          Command.help();
          break;
        default:
          help();
          break;
      }

      return;
    }

    boolean verbose = false;
    boolean draw = false;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "--verbose":
        case "-v":
          verbose = true;
          break;
        case "--draw":
        case "-d":
          draw = true;
          break;
        case "coloring":
          if (i + 1 >= args.length) {
            Command.help();
          }

          graphColoring(Arrays.copyOfRange(args, i + 1, args.length), draw, verbose);
          return;
        default:
          help();
          return;
      }
    }

    help();
  }

  private void graphColoring(final String[] args, final boolean draw, final boolean verbose) {
    Command cmd;
    try {
      cmd = new Command(args, draw, verbose);
    } catch (InvalidArgument | NotFound | OccurredBug e) {
      e.printStackTrace();
      return;
    }

    cmd.go();
  }

  private static void help() {
    System.out.println(
        "solve graph problems.\n"
            + "[Global] [command] ...\n\n"
            + "command\n"
            + "   coloring : solve graph vertex coloring problem\n"
            + "\n"
            + "Global\n"
            + "   --verbose (-v) : verbose\n"
            + "   --draw    (-d) : draw the result\n"
            + "   --help    (-h) : help\n\n"
            + "help [command] : show the command help"
    );
  }
}
