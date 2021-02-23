package model;

/**
 * Coordination express coordination.
 */
public class Coordinate {
  private static final double ERR = 1.0e-9;

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
  public boolean equals(Object obj) {
    if (obj instanceof Coordinate) {
      Coordinate other = (Coordinate) obj;
      return Math.abs(coordinateX - other.coordinateX) < ERR
          && Math.abs(coordinateY - other.coordinateY) < ERR;
    }

    return false;
  }

  @Override
  public String toString() {
    return String.format("(%9.3f, %9.3f)", coordinateX, coordinateY);
  }
}
