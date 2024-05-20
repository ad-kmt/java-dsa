package graph.toposort;

import java.util.*;

public class TopologicalSortBFS {

    /**
     * Checks if a graph contains a cycle using topological sort with BFS (Kahn's Algorithm).
     *
     * Intuition:
     * Kahn's Algorithm for topological sorting uses the in-degree of nodes. If all nodes are processed
     * in the topological sort, the graph is acyclic; otherwise, it contains a cycle.
     *
     * Data Structures Used:
     * - Queue to manage nodes with zero in-degree
     * - In-degree array to track the number of incoming edges for each node
     *
     * Algorithm Description:
     * 1. Calculate the in-degree of each node.
     * 2. Initialize a queue with all nodes having zero in-degree.
     * 3. While the queue is not empty, remove a node, add it to the topological sort list, and decrease the in-degree of its adjacent nodes.
     * 4. If the in-degree of an adjacent node becomes zero, add it to the queue.
     * 5. If the topological sort list contains all nodes, the graph is acyclic; otherwise, it contains a cycle.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the queue and in-degree array.
     * Edge Cases:
     * - The graph may be disconnected.
     * - The graph may contain self-loops.
     *
     * Limitations:
     * - The graph is assumed to be directed and acyclic for a valid topological sort.
     *
     * @param totalNodes the total number of nodes in the graph
     * @param adj the adjacency list representing the graph
     * @return true if the graph contains a cycle, false otherwise
     */
    private boolean isCyclic(int totalNodes, ArrayList<ArrayList<Integer>> adj) {
        int[] inDegree = new int[totalNodes]; // Initialize in-degree array

        // Calculate in-degree of each node
        for (ArrayList<Integer> adjNodes : adj) {
            for (Integer adjNode : adjNodes) {
                inDegree[adjNode]++; // Increment in-degree for each adjacent node
            }
        }

        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS
        ArrayList<Integer> topoSortList = new ArrayList<>(); // List to store the topological sort

        // Add nodes with zero in-degree to the queue
        for (int i = 0; i < totalNodes; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Process nodes until the queue is empty
        while (!queue.isEmpty()) {
            int currentNode = queue.poll(); // Remove node from the queue
            topoSortList.add(currentNode); // Add node to the topological sort list

            // Decrease in-degree of adjacent nodes
            for (int adjNode : adj.get(currentNode)) {
                inDegree[adjNode]--;
                if (inDegree[adjNode] == 0) { // If in-degree becomes zero, add to queue
                    queue.add(adjNode);
                }
            }
        }

        // Print the topological sort order
        topoSortList.forEach(node -> System.out.print(node + " "));
        System.out.println();

        // Check if all nodes are included in the topological sort
        return topoSortList.size() != totalNodes;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        // Adding new ArrayLists to 'adj' to add neighbor nodes
        for (int i = 0; i < 6; i++) {
            adj.add(new ArrayList<>());
        }

        // Constructing the graph
        adj.get(5).add(2);
        adj.get(5).add(0);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(3).add(1);
        adj.get(2).add(3);

        // Checking for cycles in the graph
        if (new TopologicalSortBFS().isCyclic(6, adj)) {
            System.out.println("Cycle detected in Graph");
        } else {
            System.out.println("Cycle not detected in Graph");
        }
    }
}
