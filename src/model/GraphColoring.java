package model;

import java.util.Arrays;

/**
 * GraphColoring is solution of graph coloring problem.
 */
public class GraphColoring {
  final int vertex;
  final int color;
  final int[] vertexColors;
  final Coordinate[] coordinates;

  public GraphColoring(
      final int vertex,
      final int color,
      final int[] vertexColors,
      final Coordinate[] coordinate
      ) {
    this.vertex = vertex;
    this.color = color;
    this.vertexColors = Arrays.copyOf(vertexColors, vertexColors.length);
    this.coordinates = Arrays.copyOf(coordinate, coordinate.length);
  }
}
