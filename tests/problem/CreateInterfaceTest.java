package problem;

import model.UndirectedGraph;
import org.junit.jupiter.api.Test;
import utils.exceptions.InvalidArgument;
import static org.junit.jupiter.api.Assertions.*;

class CreateInterfaceTest {
  @Test
  public void TestCreate() {
    // basic
    {
      CreateInterface valid;
      double[] dense = new double[]{0.0, 0.0001, 0.99999, 1.0000};

      for (double dens : dense) {
        try {
          valid = new Create(1, dens);
          assertEquals(String.format("vertex: 1, dense: %7.5f", dens), valid.toString());
        } catch (InvalidArgument invalidArgument) {
          assertEquals("", invalidArgument.getMessage());
        }
      }
    }

    // exception (vertex)
    {
      CreateInterface invalid;
      int[] vertexes = new int[]{0, -1};

      for (int vertex : vertexes) {
        try {
          invalid = new Create(vertex, 0.1);
          assertEquals("", invalid.toString());
        } catch (InvalidArgument invalidArgument) {
          assertEquals(
              "Invalid Argument : vertex is more than 0",
              invalidArgument.getMessage()
          );
        }
      }
    }

    // exception (dense)
    {
      CreateInterface invalid;
      double[] dense = new double[]{-0.0001, 1.00001};

      for (double dens : dense) {
        try {
          invalid = new Create(10, dens);
          assertEquals("", invalid.toString());
        } catch (InvalidArgument invalidArgument) {
          assertEquals(
              "Invalid Argument : dense is a number between 0 and 1",
              invalidArgument.getMessage()
          );
        }
      }
    }
  }

  @Test
  public void TestUndirectedGraph() {
    class TestCase {
      final int vertex;
      final double dense;
      final UndirectedGraph want;

      TestCase(final int vertex, final double dense, final UndirectedGraph want) {
        this.vertex = vertex;
        this.dense = dense;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            4,
            0.0,
            new UndirectedGraph(4, 0, new int[]{}, new int[]{})
        ),
        new TestCase(
            4,
            1.0,
            new UndirectedGraph(
                4,
                6,
                new int[]{0, 0, 0, 1, 1, 2},
                new int[]{1, 2, 3, 2, 3, 3}
            )
        ),
    };

    for (TestCase tc : testCases) {
      try {
        CreateInterface createInterface = new Create(tc.vertex, tc.dense);
        UndirectedGraph got = createInterface.undirectedGraph();

        assertEquals(tc.want.vertex, got.vertex);
        assertEquals(tc.want.edge, got.edge);
        assertEquals(tc.want.tail.length, got.tail.length);
        assertEquals(tc.want.head.length, got.head.length);

        for (int i = 0; i < got.tail.length; i++) {
          assertEquals(tc.want.tail[i], got.tail[i]);
        }
        for (int i = 0; i < got.head.length; i++) {
          assertEquals(tc.want.head[i], got.head[i]);
        }
      } catch (InvalidArgument invalidArgument) {
        assertEquals("", invalidArgument.getMessage());
      }
    }
  }
}