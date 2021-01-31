package draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.Coloring;
import model.Coordinate;
import model.UndirectedGraph;

/**
 * Graph draw a graph.
 */
public class Graph extends JFrame {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private final DrawPanel dp;
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
  private final UndirectedGraph graph;
  private Coordinate[] coordinates;
  private Coloring coloring;

  /**
   * constructor.
   *
   * @param graph       model.UndirectedGraph
   * @param coordinates array of model.Coordinate
   * @param coloring    model.Coloring
   */
  public Graph(
      UndirectedGraph graph,
      Coordinate[] coordinates,
      Coloring coloring
  ) {
    super("graph coloring");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setResizable(true);

    this.graph = graph.copy();
    this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    this.coloring = coloring.copy();

    dp = new DrawPanel();
    add(dp, BorderLayout.CENTER);

    setVisible(true);
  }

  /**
   * draw is drawing again after updating coordinates and coloring.
   *
   * @param coordinates arrays of model.Coordinate
   * @param coloring    model.Coloring
   */
  public void draw(Coordinate[] coordinates, Coloring coloring) {
    this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    this.coloring = coloring.copy();
    dp.repaint();
    setVisible(true);
  }

  /**
   * DrawPanel define a panel.
   */
  public class DrawPanel extends JPanel {
    /**
     * paintComponent override.
     *
     * @param g Graphics
     */
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      // background
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());

      // edge
      g.setColor(Color.GRAY);
      for (int e = 0; e < graph.edge; e ++) {
        g.drawLine(
            (int) (coordinates[graph.tail[e]].getX() * (getWidth() - 40) + 20),
            (int) (coordinates[graph.tail[e]].getY() * (getHeight() - 40) + 20),
            (int) (coordinates[graph.head[e]].getX() * (getWidth() - 40) + 20),
            (int) (coordinates[graph.head[e]].getY() * (getHeight() - 40) + 20)
        );
      }

      // vertex
      for (int v = 0; v < coordinates.length; v++) {
        if (coloring.color < colors.length) {
          g.setColor(colors[coloring.vertexColors[v]]);
        }

        g.fillOval(
            (int) (coordinates[v].getX() * (getWidth() - 40)) - 7 + 20,
            (int) (coordinates[v].getY() * (getHeight() - 40)) - 7 + 20,
            14,
            14
        );

        g.setColor(Color.BLACK);
        g.drawOval(
            (int) (coordinates[v].getX() * (getWidth() - 40)) - 7 + 20,
            (int) (coordinates[v].getY() * (getHeight() - 40)) - 7 + 20,
            14,
            14
        );
      }
    }
  }
}
