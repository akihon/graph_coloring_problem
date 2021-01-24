package model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {
  @Test
  public void TestConstructor() {
    class Input {
      final int vertex;
      final int edge;
      final int[] tail;
      final int[] head;

      Input(
          int vertex,
          int edge,
          int[] tail,
          int[] head
      ) {
        this.vertex = vertex;
        this.edge = edge;
        this.tail = Arrays.copyOf(tail, tail.length);
        this.head = Arrays.copyOf(head, head.length);
      }
    }
    class Want {
      final int vertex;
      final int edge;
      final int[] tail;
      final int[] head;
      final int[] first;
      final int[] adjList;

      Want(
          int vertex,
          int edge,
          int[] tail,
          int[] head,
          int[] fist,
          int[] adjList
      ) {
        this.vertex = vertex;
        this.edge = edge;
        this.tail = Arrays.copyOf(tail, tail.length);
        this.head = Arrays.copyOf(head, head.length);
        this.first = Arrays.copyOf(fist, fist.length);
        this.adjList = Arrays.copyOf(adjList, adjList.length);
      }
    }
    class TestCase {
      final Input input;
      final Want want;

      TestCase(Input input, Want want) {
        this.input = input;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        // path
        new TestCase(
            new Input(3, 2, new int[]{0, 1}, new int[]{1, 2}),
            new Want(
                3,
                4,
                new int[]{0, 1, 1, 2},
                new int[]{1, 0, 2, 1},
                new int[]{0, 1, 3},
                new int[]{-1, 2, -1, -1}
            )
        ),
        // complete
        new TestCase(
            new Input(
                4,
                6,
                new int[]{0, 0, 0, 1, 1, 2},
                new int[]{1, 2, 3, 2, 3, 3}
            ),
            new Want(
                4,
                12,
                new int[]{0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 2, 3},
                new int[]{1, 0, 2, 0, 3, 0, 2, 1, 3, 1, 3, 2},
                new int[]{0, 1, 3, 5},
                new int[]{2, 6, 4, 7, -1, 9, 8, 10, -1, 11, -1, -1}
            )
        ),
        // general
        // 0 - 1 - 2 - 6
        //     |     /
        //     3 - 4 - 5
        new TestCase(
            new Input(
                7,
                7,
                new int[]{0, 1, 1, 2, 3, 4, 4},
                new int[]{1, 2, 3, 6, 4, 5, 6}
            ),
            new Want(
                7,
                14,
                new int[]{0, 1, 1, 2, 1, 3, 2, 6, 3, 4, 4, 5, 4, 6},
                new int[]{1, 0, 2, 1, 3, 1, 6, 2, 4, 3, 5, 4, 6, 4},
                new int[]{0, 1, 3, 5, 9, 11, 7},
                new int[]{-1, 2, 4, 6, 8, -1, 13, -1, 10, 12, -1, -1, -1, -1}
            )
        )
    };

    for (TestCase tc : testCases) {
      DirectedGraph got = new DirectedGraph(tc.input.vertex, tc.input.edge, tc.input.tail, tc.input.head);

      assertEquals(got.vertex, tc.want.vertex);
      assertEquals(got.edge, tc.want.edge);
      assertEquals(got.tail.length, tc.want.tail.length);
      assertEquals(got.head.length, tc.want.head.length);
      assertEquals(got.first.length, tc.want.first.length);
      assertEquals(got.adjList.length, tc.want.adjList.length);

      for (int i = 0; i < tc.want.tail.length; i++) {
        assertEquals(got.tail[i], tc.want.tail[i]);
      }
      for (int i = 0; i < tc.want.head.length; i++) {
        assertEquals(got.head[i], tc.want.head[i]);
      }
      for (int i = 0; i < tc.want.first.length; i++) {
        assertEquals(got.first[i], tc.want.first[i]);
      }
      for (int i = 0; i < tc.want.first.length; i++) {
        assertEquals(got.first[i], tc.want.first[i]);
      }
    }
  }
}