package cli.graphcoloring;

import mockit.Expectations;
import mockit.Mocked;
import model.UndirectedGraph;
import org.junit.jupiter.api.Test;
import problem.GraphColoringInterface;
import problem.WattsStrogatzNetworkArgs;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;
import static org.junit.jupiter.api.Assertions.*;

class GenerateProblemTest {
  @Test
  public void TestGenerateProblem(@Mocked GraphColoringInterface mock) {
    class TestCase {
      private final String type;
      private final GraphColoringInterface problem;
      private final GenerateProblemType want;

      private TestCase(
          final String type,
          final GraphColoringInterface problem,
          final GenerateProblemType want
      ) {
        this.type = type;
        this.problem = problem;
        this.want = want;
      }
    }

    // success
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase("random", mock, GenerateProblemType.random),
          new TestCase("random", null, GenerateProblemType.random),
          new TestCase("r", mock, GenerateProblemType.random),
          new TestCase("r", null, GenerateProblemType.random),
          new TestCase("ws", mock, GenerateProblemType.wattsStrogatz),
          new TestCase("ws", null, GenerateProblemType.wattsStrogatz),
          new TestCase("wattsStrogatz", mock, GenerateProblemType.wattsStrogatz),
          new TestCase("wattsStrogatz", null, GenerateProblemType.wattsStrogatz),
          new TestCase("WattsStrogatz", mock, GenerateProblemType.wattsStrogatz),
          new TestCase("WattsStrogatz", null, GenerateProblemType.wattsStrogatz),
          new TestCase("file", mock, GenerateProblemType.file),
          new TestCase("file", null, GenerateProblemType.file),
          new TestCase("f", mock, GenerateProblemType.file),
          new TestCase("f", null, GenerateProblemType.file)
      };

      for (TestCase tc : testCases) {
        try {
          GenerateProblem got = new GenerateProblem(tc.type, tc.problem);
          assertEquals(tc.want, got.getType());
        } catch (InvalidArgument e) {
          assertEquals("", e.getMessage());
        }
      }
    }

    // throw exception
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase("test", mock, null),
          new TestCase("test", null, null),
          new TestCase("", mock, null),
          new TestCase("", null, null),
          new TestCase(null, mock, null),
          new TestCase(null, null, null)
      };

      for (TestCase tc : testCases) {
        try {
          new GenerateProblem(tc.type, tc.problem);
        } catch (InvalidArgument e) {
          assertEquals("Invalid Argument : unknown problem type", e.getMessage());
        }
      }
    }
  }

  @Test
  public void TestGenerate_random(@Mocked GraphColoringInterface mock) {
    class TestCase {
      private final String[] args;
      private final int vertex;
      private final double dense;

      private TestCase(final String[] args, final int vertex, final double dense) {
        this.args = args;
        this.vertex = vertex;
        this.dense = dense;
      }
    }

    UndirectedGraph graph = new UndirectedGraph(2, 1, new int[]{0}, new int[]{1});

    // success
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(new String[]{"-v", "100", "-d", "0.5"}, 100, 0.5),
          new TestCase(new String[]{"-v", "100", "--dense", "0.5"}, 100, 0.5),
          new TestCase(new String[]{"--vertex", "100", "-d", "0.5"}, 100, 0.5),
          new TestCase(new String[]{"--vertex", "100", "--dense", "0.5"}, 100, 0.5),
          new TestCase(new String[]{"-d", "0.5", "-v", "100"}, 100, 0.5),
          new TestCase(new String[]{"-d", "0.5", "--vertex", "100"}, 100, 0.5),
          new TestCase(new String[]{"--dense", "0.5", "-v", "100"}, 100, 0.5),
          new TestCase(new String[]{"--dense", "0.5", "--vertex", "100"}, 100, 0.5),
          new TestCase(new String[]{"-v", "-5", "-d", "-0.3"}, -5, -0.3),
          new TestCase(new String[]{"-v", "-5", "-d", "-0.3"}, -5, -0.3),
          new TestCase(new String[]{"-v", "0", "-d", "0"}, 0, 0.0),
          new TestCase(
              new String[]{"-v", "100", "-d", "0.5", "-v", "20", "-d", "0.1"},
              20,
              0.1
          ),
          new TestCase(
              new String[]{"-v", "100", "--test", "-x", "20", "-d", "0.5", "-a", "ok", "20"},
              100,
              0.5
          ),
          new TestCase(new String[]{"-d", "0.5"}, 10, 0.5),
          new TestCase(new String[]{"-v", "100"}, 100, 0.3),
          new TestCase(new String[]{"-v"}, 10, 0.3),
          new TestCase(new String[]{"-d"}, 10, 0.3),
          new TestCase(new String[]{}, 10, 0.3),
          new TestCase(null, 10, 0.3)
      };

      for (TestCase tc : testCases) {
        new Expectations() {{
          try {
            mock.randomNetwork(tc.vertex, tc.dense);
          } catch (InvalidArgument e) {
            assertEquals("", e.getMessage());
          }

          result = graph;
        }};

        try {
          GenerateProblem generateProblem = new GenerateProblem("random", mock);
          UndirectedGraph got = generateProblem.generate(tc.args);

          assertEquals(graph.toString(), got.toString());
        } catch (InvalidArgument | NotFound | OccurredBug e) {
          assertEquals("", e.getMessage());
        }
      }
    }

    // throw exception
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(new String[]{"-v", "test", "-d", "0.5"}, 0, 0.0),
          new TestCase(new String[]{"-v", "100", "-d", "test"}, 0, 0.0),
          new TestCase(new String[]{"-v", "100", "-d", "ok"}, 0, 0.0),
          new TestCase(new String[]{"-v", "-d", "0.5"}, 0, 0.0),
          new TestCase(new String[]{"-v", "10.5", "-d", "0.5"}, 0, 0.0)
      };

      for (TestCase tc : testCases) {
        try {
          GenerateProblem generateProblem = new GenerateProblem("random", mock);
          generateProblem.generate(tc.args);
        } catch (InvalidArgument | NotFound | OccurredBug e) {
          assertEquals("Invalid Argument : no number", e.getMessage());
        }
      }
    }
  }

  @Test
  public void TestGenerate_wattsStrogatz(@Mocked GraphColoringInterface mock) {
    class TestCase {
      private final String[] args;
      private final int vertex;
      private final int degree;
      private final double beta;

      private TestCase(
          final String[] args,
          final int vertex,
          final int degree,
          final double beta
      ) {
        this.args = args;
        this.vertex = vertex;
        this.degree = degree;
        this.beta = beta;
      }
    }

    UndirectedGraph graph = new UndirectedGraph(2, 1, new int[]{0}, new int[]{1});

    // success
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(new String[]{"-v", "100", "-d", "5", "-b", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"-v", "100", "-d", "5", "--beta", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"-v", "100", "--degree", "5", "-b", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"-v", "100", "--degree", "5", "--beta", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"--vertex", "100", "-d", "5", "-b", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"--vertex", "100", "-d", "5", "--beta", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"--vertex", "100", "--degree", "5", "-b", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"--vertex", "100", "--degree", "5", "--beta", "0.5"}, 100, 5, 0.5),
          new TestCase(new String[]{"-d", "5", "-b", "0.5", "-v", "100"}, 100, 5, 0.5),
          new TestCase(new String[]{"-b", "0.5", "-v", "100", "-d", "5"}, 100, 5, 0.5),
          new TestCase(new String[]{"-v", "-100", "-d", "-5", "-b", "-0.5"}, -100, -5, -0.5),
          new TestCase(new String[]{"-v", "0", "-d", "0", "-b", "0.0"}, 0, 0, 0.0),
          new TestCase(
              new String[]{"-v", "0", "-d", "0", "-b", "0.0", "-b", "0.5", "-v", "100", "-d", "5"},
              100,
              5,
              0.5
          ),
          new TestCase(
              new String[]{"ok", "--test", "12.5", "-b", "0.5", "0", "-v", "100", "-d", "5"},
              100,
              5,
              0.5
          ),
          new TestCase(new String[]{"-v", "100"}, 100, 6, 0.1),
          new TestCase(new String[]{"-d", "5"}, 40, 5, 0.1),
          new TestCase(new String[]{"-b", "0.5"}, 40, 6, 0.5),
          new TestCase(new String[]{"-v"}, 40, 6, 0.1),
          new TestCase(new String[]{"-d"}, 40, 6, 0.1),
          new TestCase(new String[]{"-b"}, 40, 6, 0.1),
          new TestCase(new String[]{}, 40, 6, 0.1),
          new TestCase(null, 40, 6, 0.1)
      };

      for (TestCase tc : testCases) {
        new Expectations() {{
          WattsStrogatzNetworkArgs args = new WattsStrogatzNetworkArgs(tc.vertex, tc.degree, tc.beta);
          try {
            mock.wattsStrogatzNetwork(args);
          } catch (InvalidArgument | OccurredBug e) {
            assertEquals("", e.getMessage());
          }

          result = graph;
        }};

        try {
          GenerateProblem generateProblem = new GenerateProblem("ws", mock);
          UndirectedGraph got = generateProblem.generate(tc.args);

          assertEquals(graph.toString(), got.toString());
        } catch (InvalidArgument | NotFound | OccurredBug e) {
          assertEquals("", e.getMessage());
        }
      }
    }

    // throw exception
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(new String[]{"-v", "100", "-d", "5", "-b", "test"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "100", "-d", "test", "-b", "0.5"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "test", "-d", "5", "-b", "0.5"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "-d", "5", "-b", "0.5"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "100", "-d", "-b", "0.5"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "100", "-b", "-d", "5"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "1.5", "-d", "5", "-b", "0.5"}, 0, 0, 0.0),
          new TestCase(new String[]{"-v", "100", "-d", "5.2", "-b", "0.5"}, 0, 0, 0.0)
      };

      for (TestCase tc : testCases) {
        try {
          GenerateProblem generateProblem = new GenerateProblem("random", mock);
          generateProblem.generate(tc.args);
        } catch (InvalidArgument | NotFound | OccurredBug e) {
          assertEquals("Invalid Argument : no number", e.getMessage());
        }
      }
    }
  }

  @Test
  public void TestGenerate_file(@Mocked GraphColoringInterface mock) {
    class TestCase {
      private final String[] args;
      private final String fileName;

      private TestCase(final String[] args, final String fileName) {
        this.args = args;
        this.fileName = fileName;
      }
    }

    UndirectedGraph graph = new UndirectedGraph(2, 1, new int[]{0}, new int[]{1});

    TestCase[] testCases = new TestCase[]{
        new TestCase(new String[]{"-f", "test"}, "test"),
        new TestCase(new String[]{"--file-name", "test"}, "test"),
        new TestCase(new String[]{"-v", "100", "0", "-f", "test", "-t", "ok"}, "test"),
        new TestCase(new String[]{"-f", "test", "-f", "ok"}, "ok"),
        new TestCase(new String[]{"-f"}, null),
        new TestCase(new String[]{}, null),
        new TestCase(null, null)
    };

    for (TestCase tc : testCases) {
      new Expectations() {{
        try {
          mock.readFile(tc.fileName);
        } catch (NotFound | OccurredBug e) {
          assertEquals("", e.getMessage());
        }

        result = graph;
      }};

      try {
        GenerateProblem generateProblem = new GenerateProblem("file", mock);
        UndirectedGraph got = generateProblem.generate(tc.args);

        assertEquals(graph.toString(), got.toString());
      } catch (InvalidArgument | NotFound | OccurredBug e) {
        assertEquals("", e.getMessage());
      }
    }
  }
}