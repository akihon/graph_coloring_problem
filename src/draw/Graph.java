package draw;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JFrame;
import model.Coloring;
import model.Coordinate;
import model.DirectedGraph;

/**
 * Graph draw a graph.
 */
public class Graph extends JFrame {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private final DirectedGraph directedGraph;
  private final Coordinate[] coordinates;
  private final Coloring coloring;
  private final Color[] colors = new Color[]{
      Color.RED,
      Color.GREEN,
      Color.BLUE,
      Color.YELLOW,
      Color.CYAN,
      Color.PINK,
      Color.MAGENTA,
      Color.ORANGE,
      Color.GRAY,
      Color.LIGHT_GRAY,
      Color.DARK_GRAY,
      Color.BLACK
    };

  /**
   * constructor.
   *
   * @param directedGraph model.DirectedGraph
   * @param coordinates   array of model.Coordinate
   * @param coloring      model.Coloring
   */
  public Graph(DirectedGraph directedGraph, Coordinate[] coordinates, Coloring coloring) {
    super("graph coloring");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setResizable(true);

    Container container = getContentPane();
    container.setBackground(Color.WHITE);

    this.directedGraph = directedGraph;
    this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    this.coloring = coloring;

    this.setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    Graphics graphics = getContentPane().getGraphics();

    for (int e = 0; e < directedGraph.edge; e += 2) {
      graphics.drawLine(
          (int) (coordinates[directedGraph.tail[e]].getX() * (Graph.WIDTH - 16) + 8),
          (int) (coordinates[directedGraph.tail[e]].getY() * (Graph.HEIGHT - 16) + 8),
          (int) (coordinates[directedGraph.head[e]].getX() * (Graph.WIDTH - 16) + 8),
          (int) (coordinates[directedGraph.head[e]].getY() * (Graph.HEIGHT - 16) + 8)
      );
    }

    for (int v = 0; v < coordinates.length; v++) {
      if (coloring.color < colors.length) {
        graphics.setColor(colors[coloring.vertexColors[v]]);
      }

      graphics.fillOval(
          (int) (coordinates[v].getX() * (Graph.WIDTH - 16)) - 7 + 8,
          (int) (coordinates[v].getY() * (Graph.HEIGHT - 16)) - 7 + 8,
          14,
          14
      );
    }
  }
}
