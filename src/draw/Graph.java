package draw;

import model.Coordinate;
import model.DirectedGraph;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Graph draw a graph.
 */
public class Graph extends JFrame {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private final DirectedGraph directedGraph;
  private final Coordinate[] coordinates;

  public Graph(DirectedGraph directedGraph, Coordinate[] coordinates) {
    super("graph coloring");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(WIDTH, HEIGHT);
    //setLocationRelativeTo(null);
    setResizable(true);

    Container container = getContentPane();
    container.setBackground(Color.WHITE);

    this.directedGraph = directedGraph;
    this.coordinates = Arrays.copyOf(coordinates, coordinates.length);

    this.setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    Graphics graphics = getContentPane().getGraphics();

    for (Coordinate coordinate : coordinates) {
      graphics.fillOval(
          (int) (coordinate.getX() * Graph.WIDTH) - 7,
          (int) (coordinate.getY() * Graph.HEIGHT) - 7,
          14,
          14
      );
    }

    for (int e = 0; e < directedGraph.edge; e += 2) {
      graphics.drawLine(
          (int) (coordinates[directedGraph.tail[e]].getX() * Graph.WIDTH),
          (int) (coordinates[directedGraph.tail[e]].getY() * Graph.HEIGHT),
          (int) (coordinates[directedGraph.head[e]].getX() * Graph.WIDTH),
          (int) (coordinates[directedGraph.head[e]].getY() * Graph.HEIGHT)
      );
    }
  }
}
