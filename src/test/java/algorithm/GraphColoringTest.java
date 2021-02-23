package algorithm;

import model.Coloring;
import model.UndirectedGraph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphColoringTest {
  @Test
  public void TestEvaluate() {
    // general
    // 0 - 1 - 2 - 6
    //     |     /
    //     3 - 4 - 5
    UndirectedGraph graph = new UndirectedGraph(
        7,
        7,
        new int[]{0, 1, 1, 2, 3, 4, 4},
        new int[]{1, 2, 3, 6, 4, 5, 6}
    );

    class TestCase {
      final int[] vertexColors;
      final double want;

      TestCase(final int[] vertexColors, final double want) {
        this.vertexColors = vertexColors;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new int[]{1, 2, 3, 4, 5, 6, 7},
            7.0
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 1, 1},
            3.0
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 2, 1},
            5.5
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 1, 2},
            8.5
        ),
        new TestCase(
            new int[]{1, 1, 2, 1, 2, 1, 1},
            7.5
        ),
        new TestCase(
            new int[]{1, 1, 1, 2, 1, 1, 1},
            22.5
        ),
        new TestCase(
            new int[]{1, 2, 2, 2, 2, 1, 2},
            17.0
        ),
    };

    for (TestCase tc : testCases) {
      Coloring coloring = new Coloring(graph.vertex);
      coloring.vertexColors = tc.vertexColors;
      coloring.updateColor();

      AlgorithmInterface<Coloring> algo = new GraphColoring(graph);
      double got = algo.evaluate(coloring);
      assertEquals(tc.want, got);
    }
  }
}