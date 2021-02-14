package cli.graphcoloring;

import algorithm.AlgorithmInterface;
import algorithm.FruchtermanReingold;
import draw.Graph;
import java.util.Arrays;
import localsearch.Annealing;
import localsearch.LocalSearch;
import localsearch.LocalSearchInterface;
import model.Coloring;
import model.Coordinate;
import model.UndirectedGraph;
import problem.Create;
import problem.CreateInterface;
import problem.WattsStrogatzNetworkArgs;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;

public class Command {
  private final UndirectedGraph graph;
  private final AlgorithmInterface<Coloring> graphColoringAlgo;
  private final LocalSearchInterface graphColoringLocalSearch;
  private final AlgorithmInterface<Coordinate[]> coordinatesAlgo;
  private final LocalSearchInterface coordinatesLocalSearch;
  private final boolean verbose;
  private final boolean draw;

  public Command(
      final String[] args, final boolean draw, final boolean verbose
  ) throws InvalidArgument, NotFound, OccurredBug {
    this.verbose = verbose;
    this.draw = draw;

    Arguments argumentsModel = toModel(args);
    CreateInterface problem = new Create();

    switch (argumentsModel.type) {
      case random:
        graph = problem.randomNetwork(argumentsModel.vertex, argumentsModel.dense);
        break;
      case wattsStrogatz:
        graph = problem.wattsStrogatzNetwork(
            new WattsStrogatzNetworkArgs(
                argumentsModel.vertex, argumentsModel.degree, argumentsModel.beta
            )
        );
        break;
      case file:
        graph = problem.readFile(argumentsModel.fineName);
        break;
      default:
        throw new InvalidArgument("generate problem type is unknown");
    }

    graphColoringAlgo = new algorithm.GraphColoring(graph);
    graphColoringLocalSearch = new Annealing<>(
        (int) (1.0e6), // TODO
        (int) (1.5e2), // TODO
        graphColoringAlgo
    );

    if (this.draw) {
      coordinatesAlgo = new FruchtermanReingold(graph);
      coordinatesLocalSearch = new LocalSearch<>(
          graph.vertex * graph.vertex + graph.edge,
          1,
          coordinatesAlgo
      );
    } else {
      coordinatesAlgo = null;
      coordinatesLocalSearch = null;
    }
  }

  public static void help() {
    System.out.println(
        "solve the graph vertex coloring problem.\n"
            + "coloring [random | ws | file] [options]\n\n"
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

  public void go() {
    graphColoringLocalSearch.go();

    System.out.println(graphColoringAlgo.getResult());
    System.out.printf("feasible : %b\n", graphColoringAlgo.getResult().isFeasible(graph));
    System.out.printf("evaluation value: %6.3f\n\n",
        graphColoringAlgo.evaluate(graphColoringAlgo.getResult())
    );

    if (draw) {
      coordinatesLocalSearch.go();

      if (verbose) {
        System.out.println("coordinates");
        for (Coordinate coordinate : coordinatesAlgo.getResult()) {
          System.out.println(coordinate);
        }
      }

      new Graph(graph, coordinatesAlgo.getResult(), graphColoringAlgo.getResult());
    }
  }

  private Arguments toModel(final String[] args) throws InvalidArgument {
    if (args.length == 0) {
      throw new InvalidArgument("Invalid Argument");
    }

    try {
      switch (args[0]) {
        case "random":
        case "r":
          return toModelForRandom(Arrays.copyOfRange(args, 1, args.length));
        case "ws":
        case "wattsStrogatz":
        case "WattsStrogatz":
          return toModelForWattsStrogatz(Arrays.copyOfRange(args, 1, args.length));
        case "file":
          return toModelForFile(Arrays.copyOfRange(args, 1, args.length));
        default:
          help();
          throw new InvalidArgument("Invalid Argument");
      }
    } catch (NumberFormatException e) {
      help();
      throw new InvalidArgument("Invalid Argument");
    }
  }

  private Arguments toModelForRandom(final String[] args) throws NumberFormatException {
    int i = 0;
    int vertex = 10;
    double dense = 0.3;

    while (i < args.length) {
      switch (args[i].trim()) {
        case "-v":
        case "--vertex":
          vertex = Integer.parseInt(args[++i]);
          break;
        case "-d":
        case "--dense":
          dense = Double.parseDouble(args[++i]);
          break;
        default:
      }

      i++;
    }

    return new Arguments(
        vertex,
        dense,
        0,
        0.0,
        null,
        GenerateProblemAlgorithmType.random
    );
  }

  private Arguments toModelForWattsStrogatz(
      final String[] args
  ) throws NumberFormatException {
    int i = 0;
    int vertex = 40;
    int degree = 6;
    double beta = 0.1;

    while (i < args.length) {
      switch (args[i].trim()) {
        case "-v":
        case "--vertex":
          vertex = Integer.parseInt(args[++i]);
          break;
        case "-d":
        case "--degree":
          degree = Integer.parseInt(args[++i]);
          break;
        case "-b":
        case "--beta":
          beta = Double.parseDouble(args[++i]);
          break;
        default:
      }

      i++;
    }

    return new Arguments(
        vertex,
        0.0,
        degree,
        beta,
        null,
        GenerateProblemAlgorithmType.wattsStrogatz
    );
  }

  private Arguments toModelForFile(final String[] args) throws NumberFormatException {
    int i = 0;
    String fileName = null;

    while (i < args.length) {
      switch (args[i].trim()) {
        case "-f":
        case "--file-name":
          fileName = args[++i];
          break;
        default:
      }

      i++;
    }

    return new Arguments(
        0,
        0.0,
        0,
        0.0,
        fileName,
        GenerateProblemAlgorithmType.file
    );
  }

  private static class Arguments {
    private final int vertex;
    private final double dense;
    private final int degree;
    private final double beta;
    private final String fineName;
    private final GenerateProblemAlgorithmType type;

    private Arguments(
        final int vertex,
        final double dense,
        final int degree,
        final double beta,
        final String fineName,
        final GenerateProblemAlgorithmType type
    ) {
      this.vertex = vertex;
      this.dense = dense;
      this.degree = degree;
      this.beta = beta;
      this.fineName = fineName;
      this.type = type;
    }
  }
}
