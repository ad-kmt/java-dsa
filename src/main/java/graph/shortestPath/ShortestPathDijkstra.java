package graph.shortestPath;

import java.util.*;

public class ShortestPathDijkstra {

    public static void main(String[] args) {
        int totalNodes = 6;
        ArrayList<ArrayList<Node>> adj = new ArrayList<>();

        // Initialize adjacency list for the graph
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        // Constructing the graph with edges
        adj.get(0).add(new Node(1, 5));
        adj.get(1).add(new Node(5, -3));
        adj.get(1).add(new Node(2, -2));
        adj.get(3).add(new Node(2, 6));
        adj.get(3).add(new Node(4, -2));
        adj.get(5).add(new Node(3, 1));
        adj.get(2).add(new Node(4, 3));

        // Creating an instance of ShortestPathDijkstra and finding shortest paths
        ShortestPathDijkstra obj = new ShortestPathDijkstra();
        obj.dijkstra(adj, totalNodes, 0);
    }

    /**
     * Implements Dijkstra's algorithm to find the shortest path in a weighted graph.
     *
     * Overview:
     * This method calculates the shortest paths from a given source node to all other nodes in a weighted graph.
     *
     * Intuition:
     *  * Dijkstra's algorithm uses a greedy approach to find the shortest paths from a source node to all other nodes in a graph with non-negative edge weights.
     *  *
     *  * - The algorithm starts by assigning a distance of 0 to the source node and infinity to all other nodes.
     *  * - It uses a priority queue (min-heap) to always process the node with the smallest known distance.
     *  * - For each processed node, the algorithm updates the distances to its adjacent nodes if a shorter path is found. This process is known as "relaxation."
     *  * - By always extending the shortest known path, the algorithm efficiently finds the shortest paths to all nodes.
     *
     * Data Structures Used:
     * - Distance array to store the shortest distance from the source to each node
     * - Priority queue to efficiently get the next node with the smallest known distance
     *
     * Algorithm Description:
     * 1. Initialize the distance array with infinity for all nodes except the source.
     * 2. Use a priority queue to keep track of the next node to process based on the smallest known distance.
     * 3. For the current node, update the distances to its adjacent nodes if a shorter path is found.
     * 4. Repeat until all nodes have been processed.
     *
     * Time Complexity: O((V + E) log V), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the distance array and priority queue.
     * Edge Cases:
     * - The graph should not contain negative weight edges for Dijkstra's algorithm to work correctly.
     *
     * Limitations:
     * - The algorithm does not handle negative weight edges.
     *
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     * @param src the source node
     */
    private void dijkstra(ArrayList<ArrayList<Node>> adj, int totalNodes, int src) {
        int[] dist = new int[totalNodes]; // Distance array to store shortest distances
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize distances to infinity
        dist[src] = 0; // Distance to the source node is 0

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.w)); // Priority queue to select the next node with the smallest distance
        pq.add(new Node(src, 0)); // Add the source node to the priority queue

        while (!pq.isEmpty()) {
            Node current = pq.poll(); // Get the node with the smallest distance
            int u = current.u;

            // Process each adjacent node
            for (Node neighbor : adj.get(u)) {
                int v = neighbor.u;
                int weight = neighbor.w;

                // If a shorter path to the adjacent node is found
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight; // Update the distance
                    pq.add(new Node(v, dist[v])); // Add the adjacent node to the priority queue
                }
            }
        }

        // Print the shortest distances
        for (int i = 0; i < totalNodes; i++) {
            System.out.print(dist[i] + " ");
        }
    }

    // Node class to represent an edge in the graph
    static class Node {
        int u; // Node identifier
        int w; // Weight or distance to this node

        Node(int u, int w) {
            this.u = u;
            this.w = w;
        }
    }
}
