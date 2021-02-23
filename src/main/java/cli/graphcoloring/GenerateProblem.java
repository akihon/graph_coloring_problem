package cli.graphcoloring;

import model.UndirectedGraph;
import problem.GraphColoringInterface;
import problem.WattsStrogatzNetworkArgs;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;

/**
 * GenerateProblem generate a graph coloring problem.
 */
public class GenerateProblem {
  private final GraphColoringInterface graphColoringInterface;
  private final GenerateProblemType type;

  /**
   * constructor.
   *
   * @param type                   String
   * @param graphColoringInterface problem.GraphColoringInterface
   * @throws InvalidArgument invalid argument exception
   */
  public GenerateProblem(
      final String type, final GraphColoringInterface graphColoringInterface
  ) throws InvalidArgument {
    if (type == null) {
      throw new InvalidArgument("unknown problem type");
    }

    // null check... whatever
    this.graphColoringInterface = graphColoringInterface;

    switch (type) {
      case "random":
      case "r":
        this.type = GenerateProblemType.random;
        break;
      case "ws":
      case "wattsStrogatz":
      case "WattsStrogatz":
        this.type = GenerateProblemType.wattsStrogatz;
        break;
      case "f":
      case "file":
        this.type = GenerateProblemType.file;
        break;
      default:
        throw new InvalidArgument("unknown problem type");
    }
  }

  /**
   * help return the detail of help command.
   */
  public static void help() {
    System.out.println(
        "solve the graph vertex coloring problem.\n"
            + "coloring [random | ws | file] [options...]\n\n"
            + "options\n"
            + "***       random       ***\n"
            + "   --vertex (-v) [number] : #vertexes (default = 10)\n"
            + "   --dense  (-d) [number] : dense of edge (0 < dense <= 1) (default = 0.3)\n"
            + "*** ws (WattsStrogatz) ***\n"
            + "   --vertex (-v) [number] : #vertexes (default = 40)\n"
            + "   --degree (-d) [number] : degree of a vertex (default = 6)\n"
            + "   --beta   (-b) [number] : parameter (0 <= beta <= 1) (default = 0.1)\n"
            + "***  file (read file)  ***\n"
            + "   --file-name (-f) [string] : file name\n"
    );
  }

  /**
   * generate generate a undirected graph.
   *
   * @param args array of String
   * @return model.UndirectedGraph
   * @throws InvalidArgument invalid argument exception
   * @throws NotFound        not found exception
   * @throws OccurredBug     internal error exception
   */
  public UndirectedGraph generate(
      final String[] args
  ) throws InvalidArgument, NotFound, OccurredBug {
    Arguments arguments = toModel(args);

    switch (type) {
      case random:
        return graphColoringInterface.randomNetwork(arguments.vertex, arguments.dense);
      case wattsStrogatz:
        return graphColoringInterface.wattsStrogatzNetwork(
            new WattsStrogatzNetworkArgs(
                arguments.vertex, arguments.degree, arguments.beta
            )
        );
      case file:
        return graphColoringInterface.readFile(arguments.fineName);
      default:
        throw new InvalidArgument("generate problem type is unknown");
    }
  }

  public GenerateProblemType getType() {
    return type;
  }

  private Arguments toModel(final String[] args) throws InvalidArgument {
    try {
      switch (type) {
        case random:
          return toModelForRandom(args);
        case wattsStrogatz:
          return toModelForWattsStrogatz(args);
        case file:
          return toModelForFile(args);
        default:
          throw new InvalidArgument("Invalid Argument");
      }
    } catch (NumberFormatException e) {
      throw new InvalidArgument("no number");
    }
  }

  private Arguments toModelForRandom(final String[] args) throws NumberFormatException {
    int i = 0;
    int vertex = 10;
    double dense = 0.3;

    if (args != null) {
      while (i < args.length) {
        switch (args[i].trim()) {
          case "-v":
          case "--vertex":
            if (++i < args.length) {
              vertex = Integer.parseInt(args[i]);
            }
            break;
          case "-d":
          case "--dense":
            if (++i < args.length) {
              dense = Double.parseDouble(args[i]);
            }
            break;
          default:
        }

        i++;
      }
    }

    return new Arguments(
        vertex,
        dense,
        0,
        0.0,
        null
    );
  }

  private Arguments toModelForWattsStrogatz(
      final String[] args
  ) throws NumberFormatException {
    int i = 0;
    int vertex = 40;
    int degree = 6;
    double beta = 0.1;

    if (args != null) {
      while (i < args.length) {
        switch (args[i].trim()) {
          case "-v":
          case "--vertex":
            if (++i < args.length) {
              vertex = Integer.parseInt(args[i]);
            }
            break;
          case "-d":
          case "--degree":
            if (++i < args.length) {
              degree = Integer.parseInt(args[i]);
            }
            break;
          case "-b":
          case "--beta":
            if (++i < args.length) {
              beta = Double.parseDouble(args[i]);
            }
            break;
          default:
        }

        i++;
      }
    }

    return new Arguments(
        vertex,
        0.0,
        degree,
        beta,
        null
    );
  }

  private Arguments toModelForFile(final String[] args) throws NumberFormatException {
    int i = 0;
    String fileName = null;

    if (args != null) {
      while (i < args.length) {
        switch (args[i].trim()) {
          case "-f":
          case "--file-name":
            if (++i < args.length) {
              fileName = args[i];
            }
            break;
          default:
        }

        i++;
      }
    }

    return new Arguments(
        0,
        0.0,
        0,
        0.0,
        fileName
    );
  }

  private static class Arguments {
    private final int vertex;
    private final double dense;
    private final int degree;
    private final double beta;
    private final String fineName;

    private Arguments(
        final int vertex,
        final double dense,
        final int degree,
        final double beta,
        final String fineName
    ) {
      this.vertex = vertex;
      this.dense = dense;
      this.degree = degree;
      this.beta = beta;
      this.fineName = fineName;
    }
  }
}
