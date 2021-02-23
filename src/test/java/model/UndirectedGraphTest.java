package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {
  @Test
  public void TestUndirectedGraph() {
    class TestCase {
      final int vertex;
      final int edge;
      final int[] tail;
      final int[] head;
      final UndirectedGraph want;

      TestCase(
          final int vertex,
          final int edge,
          final int[] tail,
          final int[] head,
          final UndirectedGraph want
      ) {
        this.vertex = vertex;
        this.edge = edge;
        this.tail = tail;
        this.head = head;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        // path
        new TestCase(
            3,
            2,
            new int[]{0, 1},
            new int[]{1, 2},
            new UndirectedGraph(3, 2, new int[]{0, 1}, new int[]{1, 2})
        ),
        // complete
        new TestCase(
            4,
            6,
            new int[]{0, 0, 0, 1, 1, 2},
            new int[]{1, 2, 3, 2, 3, 3},
            new UndirectedGraph(
                4,
                6,
                new int[]{0, 0, 0, 1, 1, 2},
                new int[]{1, 2, 3, 2, 3, 3}
            )
        ),
        // general
        // 0 - 1 - 2 - 6
        //     |     /
        //     3 - 4 - 5
        new TestCase(
            7,
            7,
            new int[]{0, 1, 1, 2, 3, 4, 4},
            new int[]{1, 2, 3, 6, 4, 5, 6},
            new UndirectedGraph(
                7,
                7,
                new int[]{0, 1, 1, 2, 3, 4, 4},
                new int[]{1, 2, 3, 6, 4, 5, 6}
            )
        )
    };


    for (TestCase tc : testCases) {
      UndirectedGraph got = new UndirectedGraph(
          tc.vertex,
          tc.edge,
          tc.tail,
          tc.head
      );

      assertEquals(tc.want.vertex, got.vertex);
      assertEquals(tc.want.edge, got.edge);
      assertEquals(tc.want.edge, got.tail.length);
      assertEquals(tc.want.edge, got.head.length);
      assertEquals(tc.want.tail.length, got.tail.length);
      assertEquals(tc.want.head.length, got.head.length);

      for (int e = 0; e < got.edge; e++) {
        assertEquals(tc.want.tail[e], got.tail[e]);
        assertEquals(tc.want.head[e], got.head[e]);
      }
    }
  }

  @Test
  public void TestCopy() {
    class TestCase {
      final UndirectedGraph in;
      final UndirectedGraph want;

      TestCase(final UndirectedGraph in, final UndirectedGraph want) {
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        // path
        new TestCase(
            new UndirectedGraph(
                3,
                2,
                new int[]{0, 1},
                new int[]{1, 2}
            ),
            new UndirectedGraph(
                3,
                2,
                new int[]{0, 1},
                new int[]{1, 2}
            )
        ),
        // complete
        new TestCase(
            new UndirectedGraph(
                4,
                6,
                new int[]{0, 0, 0, 1, 1, 2},
                new int[]{1, 2, 3, 2, 3, 3}
            ),
            new UndirectedGraph(
                4,
                6,
                new int[]{0, 0, 0, 1, 1, 2},
                new int[]{1, 2, 3, 2, 3, 3}
            )
        ),
        // general
        // 0 - 1 - 2 - 6
        //     |     /
        //     3 - 4 - 5
        new TestCase(
            new UndirectedGraph(
                7,
                7,
                new int[]{0, 1, 1, 2, 3, 4, 4},
                new int[]{1, 2, 3, 6, 4, 5, 6}
            ),
            new UndirectedGraph(
                7,
                7,
                new int[]{0, 1, 1, 2, 3, 4, 4},
                new int[]{1, 2, 3, 6, 4, 5, 6}
            )
        )
    };
    for (TestCase tc : testCases) {
      UndirectedGraph got = tc.in.copy();

      assertEquals(tc.want.vertex, got.vertex);
      assertEquals(tc.want.edge, got.edge);
      assertEquals(tc.want.edge, got.tail.length);
      assertEquals(tc.want.edge, got.head.length);
      assertEquals(tc.want.tail.length, got.tail.length);
      assertEquals(tc.want.head.length, got.head.length);

      for (int e = 0; e < got.edge; e++) {
        assertEquals(tc.want.tail[e], got.tail[e]);
        assertEquals(tc.want.head[e], got.head[e]);
      }
    }
  }

  @Test
  public void TestToString() {
    class TestCase {
      final UndirectedGraph in;
      final String want;

      TestCase(final UndirectedGraph in, final String want) {
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        // path
        new TestCase(
            new UndirectedGraph(
                3,
                2,
                new int[]{0, 1},
                new int[]{1, 2}
            ),
            "#vertexes : 3 , #edges : 2\n"
                + "edge\n"
                + "      0 :    0 -    1\n"
                + "      1 :    1 -    2\n"
        ),
        // complete
        new TestCase(
            new UndirectedGraph(
                4,
                6,
                new int[]{0, 0, 0, 1, 1, 2},
                new int[]{1, 2, 3, 2, 3, 3}
            ),
            "#vertexes : 4 , #edges : 6\n"
                + "edge\n"
                + "      0 :    0 -    1\n"
                + "      1 :    0 -    2\n"
                + "      2 :    0 -    3\n"
                + "      3 :    1 -    2\n"
                + "      4 :    1 -    3\n"
                + "      5 :    2 -    3\n"
        ),
        // general
        // 0 - 1 - 2 - 6
        //     |     /
        //     3 - 4 - 5
        new TestCase(
            new UndirectedGraph(
                7,
                7,
                new int[]{0, 1, 1, 2, 3, 4, 4},
                new int[]{1, 2, 3, 6, 4, 5, 6}
            ),
            "#vertexes : 7 , #edges : 7\n"
                + "edge\n"
                + "      0 :    0 -    1\n"
                + "      1 :    1 -    2\n"
                + "      2 :    1 -    3\n"
                + "      3 :    2 -    6\n"
                + "      4 :    3 -    4\n"
                + "      5 :    4 -    5\n"
                + "      6 :    4 -    6\n"
        )
    };

    for (TestCase tc : testCases) {
      assertEquals(tc.want, tc.in.toString());
    }
  }
}