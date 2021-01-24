package model;

import java.util.Arrays;

/**
 * DirectedGraph model is directed graph.
 */
public class DirectedGraph {
  public final int vertex;
  public final int edge;
  public final int[] tail;
  public final int[] head;
  public final int[] first;
  public final int[] adjList;

  /**
   * constructor.
   *
   * @param vertex         int
   * @param noDirectedEdge array of int
   * @param noDirectedTail array of int
   * @param noDirectedHead array of int
   */
  public DirectedGraph(
      final int vertex,
      final int noDirectedEdge,
      final int[] noDirectedTail,
      final int[] noDirectedHead
  ) {
    this.vertex = vertex;
    edge = noDirectedEdge * 2;
    tail = new int[edge];
    head = new int[edge];

    for (int e = 0; e < noDirectedEdge; e++) {
      tail[e * 2] = noDirectedTail[e];
      head[e * 2] = noDirectedHead[e];

      tail[e * 2 + 1] = noDirectedHead[e];
      head[e * 2 + 1] = noDirectedTail[e];
    }

    first = new int[vertex];
    adjList = new int[edge];

    setAdjacencyList();
  }

  /**
   * constructor (copy)
   *
   * @param vertex int
   * @param edge int
   * @param tail array of int
   * @param head array of int
   * @param first array of int
   * @param adjList array of int
   */
  public DirectedGraph(
      final int vertex,
      final int edge,
      final int[] tail,
      final int[] head,
      final int[] first,
      final int[] adjList
  ) {
    this.vertex = vertex;
    this.edge = edge;
    this.tail = Arrays.copyOf(tail, tail.length);
    this.head = Arrays.copyOf(head, head.length);
    this.first = Arrays.copyOf(first, first.length);
    this.adjList = Arrays.copyOf(adjList, adjList.length);
  }

  @Override
  public String toString() {
    return String.format(
        "vertex = %d, edge = %d\n%s%s",
        vertex, edge, toStringEdge(), toStringAdjacencyList()
    );
  }

  private void setAdjacencyList() {
    Arrays.fill(first, -1);
    Arrays.fill(adjList, -1);

    for (int e = edge - 1; e >= 0; e--) {
      int v = tail[e];
      adjList[e] = first[v];
      first[v] = e;
    }
  }

  private String toStringEdge() {
    StringBuilder stringBuilder = new StringBuilder("edge\n");
    for (int e = 0; e < edge; e+=2) {
      stringBuilder.append(String.format("   %4d - %4d\n", tail[e], head[e]));
    }

    return stringBuilder.toString();
  }

  private String toStringAdjacencyList() {
    StringBuilder stringBuilder = new StringBuilder("adjacency list\n");

    for (int v = 0; v < vertex; v++) {
      stringBuilder.append(String.format("   vertex %4d : ", v));

      int e = first[v];
      int c = 1;

      while (e >= 0) {
        stringBuilder.append(String.format("%4d", head[e]));
        e = adjList[e];

        if (e > 0) {
          if ((c++) % 5 == 0) {
            stringBuilder.append("\n                 ");
          } else {
            stringBuilder.append(" ");
          }
        }
      }

      stringBuilder.append("\n");
    }

    return stringBuilder.toString();
  }
}
