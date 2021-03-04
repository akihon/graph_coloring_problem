package model;

import utils.exceptions.InvalidArgument;

/**
 * TravelingVertex is the solution model of traveling salesman problem.
 */
public class TravelingVertex {
  public final int[] travelingOrder;  // [order] = index of vertex
  private final int[] orderFromVertex; // [index of vertex] = order

  public TravelingVertex(final int vertex) throws InvalidArgument {
    if (vertex < 1) {
      throw new InvalidArgument("vertex is less than 0");
    }

    travelingOrder = new int[vertex];
    orderFromVertex = new int[vertex];
    for (int i = 0; i < travelingOrder.length; i++) {
      travelingOrder[i] = i;
      orderFromVertex[i] = i;
    }
  }

  public void swap(final int v1, final int v2) throws InvalidArgument {
    if (v1 < 0 || v1 >= travelingOrder.length) {
      throw new InvalidArgument("out of range");
    }
    if (v2 < 0 || v2 >= travelingOrder.length) {
      throw new InvalidArgument("out of range");
    }

    int o1 = orderFromVertex[v1];
    int o2 = orderFromVertex[v2];
    orderFromVertex[v1] = o2;
    orderFromVertex[v2] = o1;

    travelingOrder[o1] = v2;
    travelingOrder[o2] = v1;
  }

  public int[] getOrderFromVertex() {
    return orderFromVertex;
  }
}
