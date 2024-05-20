package graph.shortestPath;

import java.util.*;

public class ShortestPathBellmanFord {

    public static void main(String[] args) {
        int totalNodes = 6;
        ArrayList<Node> adj = new ArrayList<>();

        // Constructing the graph with edges
        adj.add(new Node(3, 2, 6));
        adj.add(new Node(5, 3, 1));
        adj.add(new Node(0, 1, 5));
        adj.add(new Node(1, 5, -3));
        adj.add(new Node(1, 2, -2));
        adj.add(new Node(3, 4, -2));
        adj.add(new Node(2, 4, 3));

        // Creating an instance of ShortestPathBellmanFord and finding shortest paths
        ShortestPathBellmanFord obj = new ShortestPathBellmanFord();
        obj.bellmanFord(adj, totalNodes, 0);
    }

    /**
     * Implements the Bellman-Ford algorithm to find the shortest path in a weighted graph.
     *
     * Overview:
     * This method calculates the shortest paths from a given source node to all other nodes in a weighted graph.
     *
     * Intuition:
     * The Bellman-Ford algorithm finds the shortest paths from a source node to all other nodes in a graph by iteratively relaxing all edges.
     * "Relaxing" an edge means checking if the shortest known distance to the destination node can be improved by taking a path through the source node of that edge.
     * If it can, we update the shortest known distance.
     * This process is repeated (V-1) times, where V is the number of vertices in the graph, to ensure that the shortest paths are found even when they involve multiple edges.
     * Additionally, one more iteration is performed to check for the presence of negative weight cycles by attempting to relax the edges again.
     * If any distance can still be improved, it indicates a negative weight cycle.
     *
     * Note: Points to remember since, in a graph of V nodes we will take at most V-1 edges to reach from the first to the last node, that's why we need exact N-1 iterations.
     * It is impossible to draw a graph that takes more than N-1 edges to reach any node.
     *
     * Data Structures Used:
     * - Distance array to store the shortest distance from the source to each node
     *
     * Algorithm Description:
     * 1. Initialize the distance array with infinity for all nodes except the source.
     * 2. Relax all edges (V-1) times, where V is the number of vertices.
     * 3. Check for negative weight cycles by trying to relax the edges one more time.
     * 4. If no negative weight cycle is found, print the shortest distances.
     *
     * Time Complexity: O(V * E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the distance array.
     * Edge Cases:
     * - The graph may contain negative weight cycles.
     *
     * Limitations:
     * - The algorithm may not work correctly if there are negative weight cycles in the graph.
     *
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     * @param src the source node
     */
    private void bellmanFord(ArrayList<Node> adj, int totalNodes, int src) {
        int[] dist = new int[totalNodes]; // Distance array to store shortest distances
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize distances to infinity
        dist[src] = 0; // Distance to the source node is 0

        // Relax all edges (totalNodes-1) times
        for (int i = 1; i <= totalNodes - 1; i++) {
            for (Node node : adj) {
                if (dist[node.u] != Integer.MAX_VALUE && dist[node.v] > dist[node.u] + node.w) {
                    dist[node.v] = dist[node.u] + node.w; // Relax the edge
                }
            }
        }

        // Check for negative weight cycles by relaxing edges once more
        boolean isNegativeCycle = false;
        for (Node node : adj) {
            // check if another shortest path to a destination node is found
            if (dist[node.v] > dist[node.u] + node.w) {
                isNegativeCycle = true; // negative cycle found
                System.out.println("Negative Cycle found");
                break;
            }
        }

        // Print the shortest distances if no negative weight cycle is found
        if (!isNegativeCycle) {
            for (int i = 0; i < totalNodes; i++) {
                System.out.print(dist[i] + " ");
            }
        }
    }

    // Node class to represent an edge in the graph
    static class Node {
        int u; // Start node
        int v; // End node
        int w; // Weight of the edge

        Node(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}
