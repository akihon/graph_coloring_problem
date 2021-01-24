package draw;

import model.DirectedGraph;
import javax.swing.*;

/**
 * Graph draw a graph.
 */
public class Graph extends JFrame {
  public Graph(DirectedGraph directedGraph) {
    super("graph coloring");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);
    setResizable(true);
  }
}
