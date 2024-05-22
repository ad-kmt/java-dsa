package graph.cycleDetection;

import java.util.ArrayList;

public class CheckCycleDirectedGraphDFS {

    public static void main(String[] args) {
        int totalNodes = 6;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(totalNodes);

        // Initializing the adjacency list
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        // Adding edges to the graph
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(1).add(5);
        adj.get(2).add(3);
        adj.get(3).add(4);
        adj.get(4).add(0);
        adj.get(4).add(1);

        if (isCyclic(totalNodes, adj)) {
            System.out.println("Cycle detected");
        } else {
            System.out.println("No cycles detected");
        }
    }

    /**
     * Checks if the directed graph contains a cycle using DFS.
     *
     * Overview:
     * This method detects cycles in a directed graph using Depth-First Search (DFS).
     * It uses two boolean arrays to keep track of visited nodes and the recursion stack.
     *
     * Intuition:
     * The algorithm explores each node recursively using DFS. If it encounters a node that is already on the current DFS path,
     * a cycle is detected. The `vis` array keeps track of all visited nodes, while the `dfsVis` array keeps track of the nodes
     * currently in the recursion stack.
     *
     * Data Structures Used:
     * - visited[]: Boolean array to keep track of visited nodes.
     * - recursionStack[]: Boolean array to keep track of nodes in the current DFS path.
     *
     * Steps:
     * 1. Initialize the `visited` and `recursionStack` arrays to false.
     * 2. For each unvisited node, perform a DFS and check for cycles.
     * 3. During DFS, if a node is found in the recursion stack, a cycle is detected.
     * 4. If a cycle is detected during the DFS, return true.
     * 5. If no cycles are detected after checking all nodes, return false.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the visited and recursionStack array.
     *
     * @param totalNodes the total number of nodes in the graph
     * @param adj the adjacency list representing the graph
     * @return true if the graph contains a cycle, false otherwise
     */
    private static boolean isCyclic(int totalNodes, ArrayList<ArrayList<Integer>> adj) {
        // Visited array
        boolean[] visited = new boolean[totalNodes];
        // Recursion stack array for cycle detection
        boolean[] recursionStack = new boolean[totalNodes];

        // Perform DFS for each unvisited node
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) {
                if (checkCycleDfs(i, adj, visited, recursionStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to perform DFS and check for cycles.
     *
     * @param node the current node
     * @param adj the adjacency list
     * @param visited the visited array
     * @param recursionStack the recursion stack array
     * @return true if a cycle is detected, false otherwise
     */
    private static boolean checkCycleDfs(int node, ArrayList<ArrayList<Integer>> adj, boolean[] visited, boolean[] recursionStack) {
        // Mark the current node as visited
        visited[node] = true;
        // Add the current node to the recursion stack
        recursionStack[node] = true;

        // Recursively visit all adjacent nodes
        for (int adjNode : adj.get(node)) {
            if (!visited[adjNode]) {
                if (checkCycleDfs(adjNode, adj, visited, recursionStack)) {
                    return true; // Cycle detected
                }
            } else if (recursionStack[adjNode]) {
                return true; // Cycle detected
            }
        }

        // Remove the current node from the recursion stack
        recursionStack[node] = false;
        return false;
    }
}
