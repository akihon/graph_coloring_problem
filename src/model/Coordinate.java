package model;

/**
 * Coordination express coordination.
 */
public class Coordinate {
  private double coordinateX;
  private double coordinateY;

  /**
   * constructor.
   *
   * @param coordinateX double
   * @param coordinateY double
   */
  public Coordinate(final double coordinateX, final double coordinateY) {
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
  }

  /**
   * distance calc the distance between this and argument coordinate.
   *
   * @param c Coordinate
   * @return double
   */
  public double distance(final Coordinate c) {
    double squareX = (coordinateX - c.getX()) * (coordinateX - c.getX());
    double squareY = (coordinateY - c.getY()) * (coordinateY - c.getY());

    return Math.sqrt(squareX + squareY);
  }

  /**
   * distanceFromOrigin calc distance from (0, 0).
   *
   * @return double
   */
  public double distanceFromOrigin() {
    return this.distance(new Coordinate(0, 0));
  }

  /**
   * calcDirectionUnitVector calc direction vector (unit).
   * if this is A, argument is B, then direction vector returned this method is A -> B.
   *
   * @param c Coordinate
   * @return Coordinate (unit vector)
   */
  public Coordinate calcDirectionUnitVector(final Coordinate c) {
    double d = this.distance(c);

    if (d == 0) {
      return new Coordinate(0, 0);
    }

    return new Coordinate(
        (c.getX() - coordinateX) / d,
        (c.getY() - coordinateY) / d
    );
  }

  /**
   * equal check the same coordinate of argument.
   *
   * @param c Coordinate
   * @param err double
   * @return boolean
   */
  public boolean equal(final Coordinate c, final double err) {
    return Math.abs(coordinateX - c.getX()) < err && Math.abs(coordinateY - c.getY()) < err;
  }

  public void add(final Coordinate c) {
    coordinateX += c.getX();
    coordinateY += c.getY();
  }

  public void sub(final Coordinate c) {
    coordinateX -= c.getX();
    coordinateY -= c.getY();
  }

  public double getX() {
    return coordinateX;
  }

  public double getY() {
    return coordinateY;
  }

  @Override
  public String toString() {
    return String.format("(%9.3f, %9.3f)", coordinateX, coordinateY);
  }
}
