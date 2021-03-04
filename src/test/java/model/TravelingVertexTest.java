package model;

import org.junit.jupiter.api.Test;
import utils.exceptions.InvalidArgument;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TravelingVertexTest {
  @Test
  public void TestTravelingVertex() {
    class TestCase {
      private final int input;
      private final int[] travelingOrder;
      private final int[] orderFromVertex;

      private TestCase(
          final int input, final int[] travelingOrder, final int[] orderFromVertex
      ) {
        this.input = input;
        this.travelingOrder = travelingOrder;
        this.orderFromVertex = orderFromVertex;
      }
    }

    // success
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(1, new int[]{0}, new int[]{0}),
          new TestCase(2, new int[]{0, 1}, new int[]{0, 1}),
          new TestCase(
              10,
              new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
              new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
          )
      };

      for (TestCase tc : testCases) {
        try {
          TravelingVertex tv = new TravelingVertex(tc.input);
          assertEquals(Arrays.toString(tc.travelingOrder), Arrays.toString(tv.travelingOrder));
          assertEquals(Arrays.toString(tc.orderFromVertex), Arrays.toString(tv.getOrderFromVertex()));
        } catch (InvalidArgument e) {
          assertEquals("", e.getMessage());
        }
      }
    }

    // invalid argument
    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(0, new int[]{0}, new int[]{0}),
          new TestCase(-1, new int[]{0}, new int[]{0})
      };

      for (TestCase tc : testCases) {
        try {
          TravelingVertex tv = new TravelingVertex(tc.input);
        } catch (InvalidArgument e) {
          assertEquals("Invalid Argument : vertex is less than 0", e.getMessage());
        }
      }
    }
  }

  @Test
  public void TestSwap() {
    class TestCase {
      private final int vertex;
      private final int v1;
      private final int v2;
      private final int[] travelingOrder;
      private final int[] orderFromVertex;

      private TestCase(
          final int vertex,
          final int v1,
          final int v2,
          final int[] travelingOrder,
          final int[] orderFromVertex
      ) {
        this.vertex = vertex;
        this.v1 = v1;
        this.v2 = v2;
        this.travelingOrder = travelingOrder;
        this.orderFromVertex = orderFromVertex;
      }
    }

    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(1, 0, 0, new int[]{0}, new int[]{0}),
          new TestCase(
              5, 1, 3, new int[]{0, 3, 2, 1, 4}, new int[]{0, 3, 2, 1, 4}
          ),
          new TestCase(
              5, 0, 4, new int[]{4, 1, 2, 3, 0}, new int[]{4, 1, 2, 3, 0}
          ),
          new TestCase(
              5, 3, 2, new int[]{0, 1, 3, 2, 4}, new int[]{0, 1, 3, 2, 4}
          ),
          new TestCase(
              5, 1, 1, new int[]{0, 1, 2, 3, 4}, new int[]{0, 1, 2, 3, 4}
          )
      };

      for (TestCase tc : testCases) {
        try {
          TravelingVertex tv = new TravelingVertex(tc.vertex);
          tv.swap(tc.v1, tc.v2);

          assertEquals(Arrays.toString(tc.travelingOrder), Arrays.toString(tv.travelingOrder));
          assertEquals(Arrays.toString(tc.orderFromVertex), Arrays.toString(tv.getOrderFromVertex()));
        } catch (InvalidArgument e) {
          assertEquals("", e.getMessage());
        }
      }

      TestCase[] testCasesRandom = new TestCase[]{
          new TestCase(
              5, 4, 1, new int[]{3, 1, 4, 0, 2}, new int[]{3, 1, 4, 0, 2}
          ),
          new TestCase(
              5, 0, 3, new int[]{0, 4, 1, 3, 2}, new int[]{0, 2, 4, 3, 1}
          ),
          new TestCase(
              5, 2, 4, new int[]{3, 2, 1, 0, 4}, new int[]{3, 2, 1, 0, 4}
          ),
          new TestCase(
              5, 3, 3, new int[]{3, 4, 1, 0, 2}, new int[]{3, 2, 4, 0, 1}
          )
      };

      for (TestCase tc : testCasesRandom) {
        try {
          TravelingVertex tv = new TravelingVertex(tc.vertex);
          // 0, 1, 2, 3, 4
          tv.swap(0, 3);
          // 3, 1, 2, 0, 4
          tv.swap(1, 2);
          // 3, 2, 1, 0, 4
          tv.swap(2, 4);
          // 3, 4, 1, 0, 2
          tv.swap(tc.v1, tc.v2);

          assertEquals(Arrays.toString(tc.travelingOrder), Arrays.toString(tv.travelingOrder));
          assertEquals(Arrays.toString(tc.orderFromVertex), Arrays.toString(tv.getOrderFromVertex()));
        } catch (InvalidArgument e) {
          assertEquals("", e.getMessage());
        }
      }
    }

    {
      TestCase[] testCases = new TestCase[]{
          new TestCase(1, -1, 0, new int[]{0}, new int[]{0}),
          new TestCase(1, 1, 0, new int[]{0}, new int[]{0}),
          new TestCase(1, 0, -1, new int[]{0}, new int[]{0}),
          new TestCase(1, 0, 1, new int[]{0}, new int[]{0}),
      };

      for (TestCase tc : testCases) {
        try {
          TravelingVertex tv = new TravelingVertex(tc.vertex);
          tv.swap(tc.v1, tc.v2);
        } catch (InvalidArgument e) {
          assertEquals("Invalid Argument : out of range", e.getMessage());
        }
      }
    }
  }
}