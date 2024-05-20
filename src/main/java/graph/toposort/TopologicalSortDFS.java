package graph.toposort;

import java.util.*;

public class TopologicalSortDFS {

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int totalNodes = 6;

        // Initialize adjacency list for the graph
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        // Constructing the graph
        adj.get(5).add(2);
        adj.get(5).add(0);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);

        // Perform topological sort
        try {
            int[] result = topoSort(totalNodes, adj);

            // Print the topological sort result
            System.out.println("Topological sort of the given graph is:");
            for (int node : result) {
                System.out.print(node + " ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Performs topological sort on a directed graph using DFS.
     *
     * Overview:
     * This method uses Depth First Search (DFS) to perform a topological sort on a directed graph.
     *
     * Intuition:
     * In DFS, when we visit a node, we recursively visit all its adjacent nodes. Once all adjacent nodes are processed,
     * we add the node to a stack. The stack will then contain nodes in the topologically sorted order.
     *
     * Data Structures Used:
     * - Stack to store the topological sort order
     * - Visited array to keep track of visited nodes
     * - Recursion stack array to detect cycles
     *
     * Algorithm Description:
     * 1. Initialize a stack to store the topological sort order.
     * 2. Initialize a visited array and a recursion stack array.
     * 3. For each unvisited node, perform DFS and push the nodes to the stack in post-order.
     * 4. During DFS, if a node is found in the recursion stack, a cycle is detected.
     * 5. Pop all nodes from the stack to get the topological sort order.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the stack and visited array.
     * Edge Cases:
     * - The graph may be disconnected.
     * - The graph may contain self-loops.
     *
     * Limitations:
     * - The graph is assumed to be directed and acyclic for a valid topological sort.
     *
     * @param totalNodes the total number of nodes in the graph
     * @param adj the adjacency list representing the graph
     * @return an array of nodes in topologically sorted order
     * @throws Exception if a cycle is detected in the graph
     */
    private static int[] topoSort(int totalNodes, ArrayList<ArrayList<Integer>> adj) throws Exception {
        Stack<Integer> stack = new Stack<>(); // Stack to store the topological sort order
        boolean[] visited = new boolean[totalNodes]; // Visited array
        boolean[] recursionStack = new boolean[totalNodes]; // Recursion stack array for cycle detection

        // Perform DFS for each unvisited node
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) {
                if (findTopoSort(i, adj, visited, stack, recursionStack)) {
                    throw new Exception("Graph contains a cycle");
                }
            }
        }

        // Pop all nodes from the stack to get the topological sort order
        int[] topoSortArray = new int[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            topoSortArray[i] = stack.pop();
        }
        return topoSortArray;
    }

    /**
     * Helper method to perform DFS and find topological sort order.
     *
     * @param node the current node
     * @param adj the adjacency list
     * @param visited the visited array
     * @param stack the stack to store the topological sort order
     * @param recursionStack the recursion stack array for cycle detection
     * @return true if a cycle is detected, false otherwise
     */
    private static boolean findTopoSort(int node, ArrayList<ArrayList<Integer>> adj, boolean[] visited, Stack<Integer> stack, boolean[] recursionStack) {
        visited[node] = true; // Mark the current node as visited
        recursionStack[node] = true; // Add the current node to the recursion stack

        // Recursively visit all adjacent nodes
        for (int adjNode : adj.get(node)) {
            if (!visited[adjNode]) {
                if (findTopoSort(adjNode, adj, visited, stack, recursionStack)) {
                    return true; // Cycle detected
                }
            } else if (recursionStack[adjNode]) {
                return true; // Cycle detected
            }
        }

        // Push the current node to the stack after all adjacent nodes are visited
        stack.push(node);
        recursionStack[node] = false; // Remove the current node from the recursion stack
        return false;
    }
}
