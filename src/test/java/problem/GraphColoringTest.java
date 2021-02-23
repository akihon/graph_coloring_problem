package problem;

import model.UndirectedGraph;
import org.junit.jupiter.api.Test;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;
import static org.junit.jupiter.api.Assertions.*;

class GraphColoringTest {
  @Test
  public void TestRandomNetwork() {
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
        GraphColoringInterface createInterface = new GraphColoring(false);
        UndirectedGraph got = createInterface.randomNetwork(tc.vertex, tc.dense);

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

    // exception (dense)
    {
      GraphColoringInterface invalid;
      double[] dense = new double[]{-0.0001, 1.00001};

      for (double dens : dense) {
        try {
          invalid = new GraphColoring(false);
          invalid.randomNetwork(10, dens);
        } catch (InvalidArgument invalidArgument) {
          assertEquals(
              "Invalid Argument : problem.GraphColoring : dense is a number between 0 and 1",
              invalidArgument.getMessage()
          );
        }
      }
    }
  }

  @Test
  public void TestWattsStrogatzNetwork() {
    // test that this method does not throw exceptions.
    for (int i = 0; i < 20; i++) {
      try {
        GraphColoringInterface create = new GraphColoring(false);
        WattsStrogatzNetworkArgs args = new WattsStrogatzNetworkArgs(128, 10, 0.5);
        create.wattsStrogatzNetwork(args);
      } catch (InvalidArgument | OccurredBug e) {
        assertEquals("", e.getMessage());
      }
    }
  }

  @Test
  public void TestReadFile() {
    GraphColoringInterface create = new GraphColoring(false);
    // basic
    {
      String fileName = "myciel3.txt";
      UndirectedGraph want = new UndirectedGraph(
          11,
          20,
          new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 6, 7, 8, 9},
          new int[]{1, 3, 6, 8, 2, 5, 7, 4, 6, 9, 4, 5, 9, 7, 8, 10, 10, 10, 10, 10}
      );

      try {
        UndirectedGraph got = create.readFile(fileName);

        assertEquals(want.vertex, got.vertex);
        assertEquals(want.edge, got.edge);
        assertEquals(want.tail.length, got.tail.length);
        assertEquals(want.head.length, got.head.length);

        for (int i = 0; i < got.tail.length; i++) {
          assertEquals(want.tail[i], got.tail[i]);
        }
        for (int i = 0; i < got.head.length; i++) {
          assertEquals(want.head[i], got.head[i]);
        }
      } catch (NotFound | OccurredBug e) {
        assertEquals("", e.toString());
      }
    }
  }
}