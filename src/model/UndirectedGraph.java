package model;

import java.util.Arrays;

/**
 * UndirectedGraph is model of undirected graph.
 */
public class UndirectedGraph {
  public final int vertex;
  public final int edge;
  public final int[] tail;
  public final int[] head;

  /**
   * constructor.
   *
   * @param vertex int
   * @param edge int
   * @param tail array of int
   * @param head array of int
   */
  public UndirectedGraph(
      final int vertex,
      final int edge,
      final int[] tail,
      final int[] head
  ) {
    this.vertex = vertex;
    this.edge = edge;
    this.tail = Arrays.copyOf(tail, edge);
    this.head = Arrays.copyOf(head, edge);
  }

  /**
   * copy copy undirected graph.
   *
   * @return model.UndirectedGraph
   */
  public UndirectedGraph copy() {
    return new UndirectedGraph(
        vertex,
        edge,
        tail,
        head
    );
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(String.format("#vertexes : %d , #edges : %d\n", vertex, edge));
    stringBuilder.append("edge\n");
    for (int e = 0; e < edge; e++) {
      stringBuilder.append(String.format("   %4d : %4d - %4d\n", e, tail[e], head[e]));
    }

    return stringBuilder.toString();
  }
}
