package problem;

import org.junit.jupiter.api.Test;
import utils.exceptions.InvalidArgument;
import static org.junit.jupiter.api.Assertions.*;

class WattsStrogatzNetworkArgsTest {
  @Test
  public void TestWattsStrogatzNetworkArgs() {
    class TestCase {
      final int degree;
      final double beta;
      final WattsStrogatzNetworkArgs want;

      TestCase(final int degree, final double beta, final WattsStrogatzNetworkArgs want) {
        this.degree = degree;
        this.beta = beta;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            1,
            0.5,
            new WattsStrogatzNetworkArgs(1, 0.5)
        ),
        new TestCase(
            0,
            0.0,
            new WattsStrogatzNetworkArgs(0, 0.0)
        ),
        new TestCase(
            -1,
            -0.5,
            new WattsStrogatzNetworkArgs(-1, -0.5)
        )
    };

    for (TestCase tc : testCases) {
      WattsStrogatzNetworkArgs got = new WattsStrogatzNetworkArgs(tc.degree, tc.beta);

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
            new WattsStrogatzNetworkArgs(6, 0.5),
            ""
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(6, 0.0),
            ""
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(6, 1.0),
            ""
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(6, -0.1),
            "Invalid Argument : beta is less than 0.0"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(6, 1.1),
            "Invalid Argument : beta is more than 1.0"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(5, 0.5),
            "Invalid Argument : degree is less than log_2(vertex) (  5.00000)"
        ),
        new TestCase(
            new WattsStrogatzNetworkArgs(32, 0.5),
            "Invalid Argument : degree is more than vertex (32)"
        )
    };

    for (TestCase tc : testCases) {
      try {
        tc.in.valid(32);
      } catch (InvalidArgument e) {
        assertEquals(tc.want, e.getMessage());
      }
    }
  }
}