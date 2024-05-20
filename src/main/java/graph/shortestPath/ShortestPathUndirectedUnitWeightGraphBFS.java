package graph.shortestPath;

import java.util.*;

public class ShortestPathUndirectedUnitWeightGraphBFS {

    public static void main(String[] args) {
        int n = 9, m = 10;
        int[][] edge = {
                {0, 1},
                {0, 3},
                {3, 4},
                {4, 5},
                {5, 6},
                {1, 2},
                {2, 6},
                {6, 7},
                {7, 8},
                {6, 8}
        };

        ShortestPathUndirectedUnitWeightGraphBFS obj = new ShortestPathUndirectedUnitWeightGraphBFS();
        int res[] = obj.shortestPath(edge, n, m, 0);
        for (int i = 0; i < n; i++) {
            System.out.print(res[i] + " ");
        }
        System.out.println();
    }

    /**
     * Overview:
     * This method calculates the shortest paths from a given source node to all other nodes in an undirected graph with unit weights.
     *
     * Intuition:
     * In an undirected graph with unit weights, the shortest path can be found using BFS. The BFS algorithm explores nodes level by level.
     * Since all edges have the same weight, the first time a node is reached in BFS at a particular level.
     * That level is the shortest path to that node.
     *
     * Data Structures Used:
     * - Adjacency list to represent the graph
     * - Distance array to store the shortest distance from the source to each node
     * - Queue for BFS traversal
     *
     * Note: A visited array is not required because the distance array inherently serves the purpose of marking nodes as visited.
     * Once a node's distance is updated from infinity, it effectively means the node has been visited.
     *
     * Algorithm Description:
     * 1. Initialize the adjacency list for the graph.
     * 2. Initialize the distance array with a large value (infinity) to represent untraversed nodes.
     * 3. Set the distance to the source node as 0.
     * 4. Perform BFS traversal, updating the shortest distance to each node.
     * 5. Nodes that remain untraversed (distance = infinity) are marked as -1.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V + E), for the adjacency list, distance array, and queue.
     * Edge Cases:
     * - The graph may contain disconnected components.
     *
     * @param edges the edges of the graph
     * @param n the total number of nodes in the graph
     * @param m the total number of edges in the graph
     * @param src the source node
     * @return an array of shortest distances from the source to each node
     */
    public int[] shortestPath(int[][] edges, int n, int m, int src) {
        // Create an adjacency list of size n for storing the undirected graph.
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        // Populate the adjacency list with the edges.
        for (int i = 0; i < m; i++) {
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }

        // Distance array initialized with a large number to indicate untraversed nodes.
        int dist[] = new int[n];
        Arrays.fill(dist, (int) 1e9);
        dist[src] = 0; // Distance to the source node is 0.

        // BFS Implementation
        Queue<Integer> q = new LinkedList<>();
        q.add(src); // Start BFS from the source node.
        while (!q.isEmpty()) {
            int node = q.poll();
            // Traverse all adjacent nodes.
            for (int neighbor : adj.get(node)) {
                // Check if a shorter path to the neighbor is found through the current node
                if (dist[node] + 1 < dist[neighbor]) {
                    dist[neighbor] = 1 + dist[node]; // Update distance.
                    q.add(neighbor); // Add neighbor to the queue.
                }
            }
        }

        // Mark unreachable nodes as -1.
        for (int i = 0; i < n; i++) {
            if (dist[i] == (int) 1e9) {
                dist[i] = -1;
            }
        }
        return dist;
    }
}
