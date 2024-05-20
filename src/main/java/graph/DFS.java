package graph;

import java.util.ArrayList;

public class DFS {

    /**
     * Performs depth-first search (DFS) on a graph starting from a specific node.
     *
     * Intuition: DFS explores as far as possible along each branch before backtracking, ensuring deep traversal before level traversal.
     *
     * Data Structures Used: Recursive call stack for managing nodes to explore, boolean array for visited nodes.
     *
     * Algorithm Description:
     *   1. Mark the current node as visited.
     *   2. Print the current node (or perform other processing).
     *   3. Recursively visit all unvisited neighbors of the current node.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), primarily for the visited array and the recursive call stack.
     *
     * Edge Cases: Handles graphs with multiple disconnected components via external loop in main.
     *
     * @param node the starting node for DFS
     * @param adjacencyList the adjacency list representing the graph
     * @param visited the array to track visited nodes
     */
    public static void dfs(int node, ArrayList<ArrayList<Integer>> adjacencyList, boolean[] visited) {
        // Mark the current node as visited and print it.
        visited[node] = true;
        System.out.print(node + " ");

        // Explore each adjacent node of the current node.
        for (int neighbor : adjacencyList.get(node)) {
            // If the neighbor has not been visited, recursively visit it.
            if (!visited[neighbor]) {
                dfs(neighbor, adjacencyList, visited);
            }
        }
    }

    public static void main(String[] args) {
        int totalNodes = 10;
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < totalNodes; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        // Constructing a simple graph
        adjacencyList.get(0).add(1);
        adjacencyList.get(1).add(0);
        adjacencyList.get(0).add(2);
        adjacencyList.get(2).add(0);
        adjacencyList.get(0).add(3);
        adjacencyList.get(3).add(0);
        adjacencyList.get(2).add(4);
        adjacencyList.get(4).add(2);

        boolean[] visited = new boolean[totalNodes];
        int totalComponents = 0;

        // Perform DFS for each component of the graph.
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) {
                dfs(i, adjacencyList, visited);
                totalComponents++;
                System.out.println();
            }
        }

        System.out.println("Total components in graph: " + totalComponents);
    }
}
