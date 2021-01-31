package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColoringTest {
  @Test
  public void TestColoring() {
    Coloring coloring = new Coloring(10);

    assertEquals(0, coloring.color);
    assertEquals(10, coloring.vertexColors.length);
  }

  @Test
  public void TestSwapVertexColors() {
    class TestCase {
      final int v1;
      final int v2;
      final int[] in;
      final int[] want;

      TestCase(final int v1, final int v2, final int[] in, final int[] want) {
        this.v1 = v1;
        this.v2 = v2;
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            0,
            1,
            new int[]{1, 2, 3, 4},
            new int[]{2, 1, 3, 4}
        ),
        new TestCase(
            3,
            0,
            new int[]{1, 2, 3, 4},
            new int[]{4, 2, 3, 1}
        ),
    };

    for (TestCase tc : testCases) {
      Coloring coloring = new Coloring(4);
      coloring.vertexColors = tc.in;
      coloring.swapVertexColors(tc.v1, tc.v2);

      for (int v = 0; v < coloring.vertexColors.length; v++) {
        assertEquals(tc.want[v], coloring.vertexColors[v]);
      }
    }
  }

  @Test
  public void TestIsFeasible() {
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
      final int[] in;
      final boolean want;

      TestCase(final int[] in, final boolean want) {
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new int[]{1, 2, 3, 4, 5, 6, 7},
            true
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 1, 1},
            true
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 1, 2},
            false
        ),
        new TestCase(
            new int[]{1, 1, 2, 1, 2, 1, 1},
            false
        ),
        new TestCase(
            new int[]{3, 3, 2, 1, 2, 2, 1},
            false
        ),
    };

    for (TestCase tc : testCases) {
      Coloring coloring = new Coloring(graph.vertex);
      coloring.vertexColors = tc.in;
      assertEquals(tc.want, coloring.isFeasible(graph));
    }
  }

  @Test
  public void TestUpdateColor() {
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
      final int[] in;
      final int want;

      TestCase(final int[] in, final int want) {
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new int[]{1, 2, 3, 4, 5, 6, 7},
            7
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 1, 1},
            3
        ),
        new TestCase(
            new int[]{1, 3, 2, 1, 2, 1, 2},
            3
        ),
        new TestCase(
            new int[]{1, 1, 2, 1, 2, 1, 1},
            2
        ),
        new TestCase(
            new int[]{3, 3, 2, 1, 2, 2, 1},
            3
        ),
    };

    for (TestCase tc : testCases) {
      Coloring coloring = new Coloring(graph.vertex);
      coloring.vertexColors = tc.in;
      coloring.updateColor();
      assertEquals(tc.want, coloring.color);
    }
  }
}