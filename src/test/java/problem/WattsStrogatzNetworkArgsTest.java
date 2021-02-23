package problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WattsStrogatzNetworkArgsTest {
  @Test
  public void TestWattsStrogatzNetworkArgs() {
    class TestCase {
      final int vertex;
      final int degree;
      final double beta;
      final WattsStrogatzNetworkArgs want;

      TestCase(final int vertex, final int degree, final double beta, final WattsStrogatzNetworkArgs want) {
        this.vertex = vertex;
        this.degree = degree;
        this.beta = beta;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            10,
            1,
            0.5,
            new WattsStrogatzNetworkArgs(10, 1, 0.5)
        ),
        new TestCase(
            10,
            0,
            0.0,
            new WattsStrogatzNetworkArgs(10, 0, 0.0)
        ),
        new TestCase(
            10,
            -1,
            -0.5,
            new WattsStrogatzNetworkArgs(10, -1, -0.5)
        )
    };

    for (TestCase tc : testCases) {
      WattsStrogatzNetworkArgs got = new WattsStrogatzNetworkArgs(tc.vertex, tc.degree, tc.beta);

      assertEquals(tc.want.degree, got.degree);
      assertEquals(tc.want.beta, got.beta);
    }
  }

  @Test
  public void TestValid() {
    class TestCase {
      final WattsStrogatzNetworkArgs in;
      final String want;

      TestCase(final WattsStrogatzNetworkArgs in, final String want) {
        this.in = in;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 6, 0.5),
            ""
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 6, 0.0),
            ""
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 6, 1.0),
            ""
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(0, 6, 0.1),
            "vertex is less than 1"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 6, -0.1),
            "problem.WattsStrogatzNetworkArgs : beta is less than 0.0"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 6, 1.1),
            "problem.WattsStrogatzNetworkArgs : beta is more than 1.0"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 5, 0.5),
            "problem.WattsStrogatzNetworkArgs : degree is less than log_2(vertex) (  5.00000)"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 32, 0.5),
            "problem.WattsStrogatzNetworkArgs : degree is more than vertex (32)"
        )
    };

    for (TestCase tc : testCases) {
      String got = tc.in.valid();
      assertEquals(tc.want, got);
    }
  }
}