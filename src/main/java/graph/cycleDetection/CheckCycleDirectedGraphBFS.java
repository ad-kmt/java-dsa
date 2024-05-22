package graph.cycleDetection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CheckCycleDirectedGraphBFS {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int totalNodes = 6;

        // Adding new arraylists to 'adj' to add neighbour nodes
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(5).add(2);
        adj.get(0).add(5);
        adj.get(4).add(0);
        adj.get(1).add(4);
        adj.get(3).add(1);
        adj.get(2).add(3);

        if (new CheckCycleDirectedGraphBFS().isCyclic(totalNodes, adj)) {
            System.out.println("Cycle detected in Graph");
        } else {
            System.out.println("Cycle not detected in Graph");
        }
    }

    /**
     * Checks if the directed graph contains a cycle using BFS (Kahn's Algorithm).
     *
     * Overview:
     * This method detects cycles in a directed graph using Kahn's Algorithm, which is a BFS-based approach to topological sorting.
     * If the topological sort of the graph contains all the vertices, then the graph is acyclic. Otherwise, it contains a cycle.
     *
     * Intuition:
     * The algorithm calculates the in-degree of each vertex and repeatedly removes vertices with zero in-degrees.
     * If the number of vertices processed in this manner equals the number of vertices in the graph, the graph is acyclic.
     * If not, it contains a cycle.
     *
     * Data Structures Used:
     * - Adjacency list to represent the graph.
     * - In-degree array to store the number of incoming edges for each vertex.
     * - Queue for BFS traversal.
     *
     * Steps:
     * 1. Initialize the in-degree array with the in-degrees of all vertices.
     * 2. Add all vertices with zero in-degrees to the queue.
     * 3. Process vertices from the queue:
     *    a. Remove a vertex from the queue.
     *    b. Decrease the in-degree of all its adjacent vertices.
     *    c. If an adjacent vertex's in-degree becomes zero, add it to the queue.
     * 4. Track the number of vertices processed. If this number is less than the total number of vertices, a cycle exists.
     *
     * @param totalNodes the total number of nodes in the graph
     * @param adj the adjacency list representing the graph
     * @return true if the graph contains a cycle, false otherwise
     */
    private boolean isCyclic(int totalNodes, ArrayList<ArrayList<Integer>> adj) {
        // Initializing in-degree array
        int[] indegree = new int[totalNodes];
        for (ArrayList<Integer> adjNodes : adj) {
            for (Integer adjNode : adjNodes) {
                indegree[adjNode]++; // Increment in-degree for each adjacent node
            }
        }

        Queue<Integer> q = new LinkedList<>();
        int topoSortOrderCount = 0;

        // Add all vertices with zero in-degrees to the queue
        for (int i = 0; i < totalNodes; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        // Process vertices from the queue
        while (!q.isEmpty()) {
            int node = q.poll(); // Remove node from the queue
            topoSortOrderCount++; // increment topo sort order count

            // Decrease in-degree of adjacent nodes
            for (int adjNode : adj.get(node)) {
                indegree[adjNode]--;
                if (indegree[adjNode] == 0) { // If in-degree becomes zero, add to queue
                    q.add(adjNode);
                }
            }
        }

        // If the number of processed vertices is less than total nodes, a cycle exists
        return topoSortOrderCount != totalNodes;
    }
}
