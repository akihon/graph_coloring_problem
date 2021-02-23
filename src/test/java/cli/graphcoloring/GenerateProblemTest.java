package cli.graphcoloring;

import mockit.Expectations;
import mockit.Mocked;
import model.UndirectedGraph;
import org.junit.jupiter.api.Test;
import problem.GraphColoring;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;
import static org.junit.jupiter.api.Assertions.*;

class GenerateProblemTest {
  @Test
  public void TestGenerateProblem() {
    class TestCase {
      private final String in;
      private final String want;

      TestCase(final String in, final String want) {
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase("random", ""),
        new TestCase("r", ""),
        new TestCase("ws", ""),
        new TestCase("wattsStrogatz", ""),
        new TestCase("WattsStrogatz", ""),
        new TestCase("file", ""),
        new TestCase("f", ""),
        new TestCase("hoge", "Invalid Argument : Invalid Argument"),
        new TestCase("", "Invalid Argument : Invalid Argument")
    };

    for (TestCase tc : testCases) {
      try {
        new GenerateProblem(tc.in, null);
      } catch (InvalidArgument e) {
        assertEquals(tc.want, e.getMessage());
      }
    }
  }

  @Test
  public void TestGenerate_random(@Mocked GraphColoring mock) {
    UndirectedGraph graph = new UndirectedGraph(2, 1, new int[]{0}, new int[]{1});
    GenerateProblem generateProblem;
    try {
      generateProblem = new GenerateProblem("random", mock);
    } catch (InvalidArgument e) {
      assertEquals("", e.getMessage());
      return;
    }

    class TestCase {
      private final String[] in;
      private final int vertex;
      private final double dense;

      private TestCase(final String[] in, final int vertex, final double dense) {
        this.in = in;
        this.vertex = vertex;
        this.dense = dense;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(new String[]{"-v", "10", "-d", "0.2"}, 10, 0.2),
    };

    for (TestCase tc : testCases) {
      new Expectations() {{
        try {
          mock.randomNetwork(tc.vertex, tc.dense);
        } catch (InvalidArgument ignored) {
        }

        result = graph;
      }};

      try {
        UndirectedGraph got = generateProblem.generate(tc.in);
        assertEquals(graph.toString(), got.toString());
      } catch (InvalidArgument | NotFound | OccurredBug e) {
        assertEquals("", e.getMessage());
      }
    }
  }
}