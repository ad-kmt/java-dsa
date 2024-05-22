package graph.cycleDetection;

import java.util.ArrayList;

public class CheckCycleUndirectedGraphDFS {

    public static void main(String[] args) {
        int totalNodes = 5;

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }
        // edge 0---1
        adj.get(0).add(1);
        adj.get(1).add(0);

        // edge 1---2
        adj.get(1).add(2);
        adj.get(2).add(1);

        // edge 2---3
        adj.get(2).add(3);
        adj.get(3).add(2);

        // edge 3---4
        adj.get(3).add(4);
        adj.get(4).add(3);

        // edge 1---4
        adj.get(1).add(4);
        adj.get(4).add(1);

        CheckCycleUndirectedGraphDFS obj = new CheckCycleUndirectedGraphDFS();
        boolean ans = obj.isCycle(totalNodes, adj);
        if (ans) {
            System.out.println("Cycle Detected");
        } else {
            System.out.println("No Cycle Detected");
        }
    }

    /**
     * Determines if there is a cycle in an undirected graph using DFS.
     *
     * Overview:
     * This method iterates through all the nodes of the graph and uses DFS to check for cycles.
     *
     * Intuition:
     * In an undirected graph, a cycle exists if we revisit a node that is not the direct parent of the current node during a DFS traversal.
     *
     * The algorithm starts DFS from an unvisited node and explores all its adjacent nodes. For each adjacent node:
     * - If the node is not visited, it is marked as visited, and DFS is recursively called for that node.
     * - If the node is already visited and is not the parent of the current node, a cycle is detected.
     *
     * By iterating through all the nodes and performing DFS, the algorithm ensures that all components of the graph are checked for cycles.
     * Data Structures Used:
     * - vis[]: Boolean array to keep track of visited nodes.
     * - Recursive call stack for managing nodes to explore
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the visited array and recursive stack space.
     *
     * @param totalNodes the total number of nodes in the graph
     * @param adj the adjacency list representing the graph
     * @return true if there is a cycle, false otherwise
     */
    public boolean isCycle(int totalNodes, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[totalNodes]; // To keep track of visited nodes

        // Check for cycles in each component of the graph
        for (int i = 0; i < totalNodes; i++) {
            if (!vis[i]) { // If the node is not visited
                if (checkForCycleDFS(i, -1, adj, vis)) return true; // Perform DFS and check for cycles
            }
        }
        return false; // Return false if no cycle is found
    }

    /**
     * Helper method to perform DFS and check for cycles starting from a given node.
     *
     * @param node the current node
     * @param parentNode the parent of the current node
     * @param adj the adjacency list representing the graph
     * @param vis the visited array
     * @return true if a cycle is found, false otherwise
     */
    public boolean checkForCycleDFS(int node, int parentNode, ArrayList<ArrayList<Integer>> adj, boolean[] vis) {
        // Mark the current node as visited
        vis[node] = true;

        // Iterate through all adjacent nodes
        for (int adjNode : adj.get(node)) {

            // Check if the adjacent node is not visited
            if (!vis[adjNode]) {
                // Recursively check for the adjacent node
                if (checkForCycleDFS(adjNode, node, adj, vis))
                    return true; // If a cycle is detected in recursion, return true
            } else if (adjNode != parentNode) { // If the adjacent node is visited and is not the parent of the current node
                return true; // A cycle is detected
            }
        }
        return false; // Return false if no cycle is found
    }
}
