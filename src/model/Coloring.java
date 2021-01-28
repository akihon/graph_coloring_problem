package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Coloring is solution of graph coloring problem.
 */
public class Coloring {
  public int color;
  public int[] vertexColors;

  public Coloring(int vertex) {
    this.color = 0;
    this.vertexColors = new int[vertex];
  }

  public void swapVertexColors(final int v1, final int v2) {
    int c = vertexColors[v1];
    vertexColors[v1] = vertexColors[v2];
    vertexColors[v2] = c;
  }

  public Coloring copy() {
    Coloring c = new Coloring(this.vertexColors.length);
    c.color = this.color;
    c.vertexColors = Arrays.copyOf(this.vertexColors, this.vertexColors.length);

    return c;
  }

  public void updateColor() {
    ArrayList<Integer> usedColor = new ArrayList<>();
    for (int c : vertexColors) {
      if (!usedColor.contains(c)) {
        usedColor.add(c);
      }
    }

    color = usedColor.size();
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("colors : %s\n", color));

    for (int v = 0; v < vertexColors.length; v++) {
      stringBuilder.append(String.format("   %3d : %2d\n", v, vertexColors[v]));
    }

    return stringBuilder.toString();
  }
}
