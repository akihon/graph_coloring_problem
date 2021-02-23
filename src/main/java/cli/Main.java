package cli;

import algorithm.FruchtermanReingold;
import cli.graphcoloring.Executor;
import cli.graphcoloring.GenerateProblem;
import java.util.Arrays;
import localsearch.Annealing;
import localsearch.LocalSearch;
import model.Coloring;
import model.Coordinate;
import model.UndirectedGraph;
import problem.GraphColoring;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;

/**
 * Main class.
 */
public class Main {
  /**
   * main.
   *
   * @param args arguments
   */
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
          GenerateProblem.help();
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
            GenerateProblem.help();
            return;
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

  private void graphColoring(
      final String[] args, final boolean draw, final boolean verbose
  ) {
    if (args == null || args.length < 1) {
      GenerateProblem.help();
      return;
    }

    Executor exec;
    try {
      GenerateProblem generateProblem = new GenerateProblem(
          args[0],
          new GraphColoring(verbose)
      );
      UndirectedGraph graph = generateProblem.generate(
          Arrays.copyOfRange(args, 1, args.length)
      );

      algorithm.GraphColoring graphColoringAlgo = new algorithm.GraphColoring(graph);
      Annealing<Coloring> annealing = new Annealing<>(
          (int) (1.0e6), // TODO
          (int) (1.5e2), // TODO
          graphColoringAlgo,
          verbose
      );

      FruchtermanReingold fruchtermanReingold = new FruchtermanReingold(graph);
      LocalSearch<Coordinate[]> localSearch = new LocalSearch<>(
          graph.vertex * graph.vertex + graph.edge,
          1,
          fruchtermanReingold,
          verbose
      );

      exec = new Executor(
          graph,
          graphColoringAlgo,
          annealing,
          fruchtermanReingold,
          localSearch,
          draw,
          verbose
      );
    } catch (InvalidArgument | NotFound | OccurredBug e) {
      System.out.println(e.getMessage());
      GenerateProblem.help();
      return;
    }

    exec.go();
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
