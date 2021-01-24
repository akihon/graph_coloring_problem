package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
  @Test
  public void TestCoordinate() {
    class TestCase {
      final double x;
      final double y;

      TestCase(double x, double y) {
        this.x = x;
        this.y = y;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(0, 0),
        new TestCase(1.0, -1.0),
        new TestCase(-1.0, 1.0),
        new TestCase(Double.MAX_VALUE, Double.MAX_VALUE),
        new TestCase(Double.MIN_VALUE, Double.MIN_VALUE)
    };

    for (TestCase tc : testCases) {
      Coordinate got = new Coordinate(tc.x, tc.y);

      assertEquals(tc.x, got.getX());
      assertEquals(tc.y, got.getY());
    }
  }

  @Test
  public void TestDistance() {
    class TestCase {
      final Coordinate c1;
      final Coordinate c2;
      final double want;

      TestCase(final Coordinate c1, final Coordinate c2, double want) {
        this.c1 = c1;
        this.c2 = c2;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new Coordinate(1, 2),
            new Coordinate(4, 6),
            5.0
        ),
        new TestCase(
            new Coordinate(-1, 0),
            new Coordinate(11, 5),
            13.0
        ),
        new TestCase(
            new Coordinate(-1, -1),
            new Coordinate(10, -1),
            11.0
        ),
        new TestCase(
            new Coordinate(-1, -10),
            new Coordinate(-1, -4),
            6.0
        ),
        new TestCase(
            new Coordinate(-1, 1),
            new Coordinate(-1, 1),
            0.0
        ),
    };

    for (TestCase tc : testCases) {
      double got = tc.c1.distance(tc.c2);

      assertEquals(tc.want, got);
    }
  }

  @Test
  public void TestDistanceFromOrigin() {
    class TestCase {
      final Coordinate c;
      final double want;

      TestCase(final Coordinate c, double want) {
        this.c = c;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new Coordinate(3, 4),
            5.0
        ),
        new TestCase(
            new Coordinate(5, 12),
            13.0
        ),
        new TestCase(
            new Coordinate(0, -1),
            1.0
        ),
        new TestCase(
            new Coordinate(-1, 0),
            1.0
        ),
        new TestCase(
            new Coordinate(0, 0),
            0.0
        ),
    };

    for (TestCase tc : testCases) {
      double got = tc.c.distanceFromOrigin();

      assertEquals(tc.want, got);
    }
  }

  @Test
  public void TestCalcDirectionUnitVector() {
    class TestCase {
      final Coordinate c1;
      final Coordinate c2;
      final Coordinate want;

      TestCase(final Coordinate c1, final Coordinate c2, final Coordinate want) {
        this.c1 = c1;
        this.c2 = c2;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            new Coordinate(1, 2),
            new Coordinate(4, 6),
            new Coordinate(0.6, 0.8)
        ),
        new TestCase(
            new Coordinate(-1, 0),
            new Coordinate(11, 5),
            new Coordinate(12 / 13.0, 5 / 13.0)
        ),
        new TestCase(
            new Coordinate(-1, -1),
            new Coordinate(10, -1),
            new Coordinate(1, 0)
        ),
        new TestCase(
            new Coordinate(-1, 10),
            new Coordinate(-1, 4),
            new Coordinate(0, -1)
        ),
        new TestCase(
            new Coordinate(-1, 1),
            new Coordinate(-1, 1),
            new Coordinate(0, 0)
        ),
    };

    for (TestCase tc : testCases) {
      Coordinate got = tc.c1.calcDirectionUnitVector(tc.c2);

      assertEquals(tc.want.getX(), got.getX());
      assertEquals(tc.want.getY(), got.getY());
    }
  }
}