package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    /**
     * Performs Breadth First Search (BFS) on a graph starting from the given node.
     *
     * Intuition:
     * BFS explores all nodes at the present depth level before moving on to nodes at the next depth level.
     *
     * Data Structures Used:
     * - Queue to keep track of nodes to visit next
     * - Visited array to keep track of visited nodes
     *
     * Algorithm Description:
     * 1. Initialize the queue and mark the starting node as visited.
     * 2. While the queue is not empty, repeat steps 3-5.
     * 3. Remove the front node from the queue and add it to the BFS list.
     * 4. For each adjacent node of the current node, if it has not been visited, add it to the queue and mark it as visited.
     * 5. Return the BFS traversal list.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the queue and visited array.
     * Edge Cases:
     * - The graph may be disconnected, in which case the BFS should handle all components.
     *
     * Limitations:
     * - The graph is assumed to be undirected. For directed graphs, minor modifications are needed.
     *
     * @param startNode the starting node for BFS traversal
     * @param adj the adjacency list representing the graph
     * @param vis the visited array
     * @return the list of nodes in BFS order
     */
    public static ArrayList<Integer> bfs(int startNode, ArrayList<ArrayList<Integer>> adj, boolean[] vis) {
        ArrayList<Integer> bfsList = new ArrayList<>(); // Output list to store BFS traversal order

        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS

        // Start BFS traversal from the given node
        queue.add(startNode);
        vis[startNode] = true; // Mark the start node as visited

        // Continue the traversal until the queue is empty
        while (!queue.isEmpty()) {
            int currentNode = queue.poll(); // Dequeue the front node
            bfsList.add(currentNode); // Add the current node to the BFS list

            // Iterate through all adjacent nodes
            for (int adjNode : adj.get(currentNode)) {
                if (!vis[adjNode]) { // If the adjacent node is not visited
                    queue.add(adjNode); // Enqueue the adjacent node
                    vis[adjNode] = true; // Mark the adjacent node as visited
                }
            }
        }
        return bfsList; // Return the BFS traversal list
    }

    public static void main(String[] args) {
        int totalNodes = 5;

        // Initializing the adjacency list for the graph
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(totalNodes);
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        // Adding edges to the graph
        adj.get(0).add(1);
        adj.get(1).add(0);
        adj.get(0).add(2);
        adj.get(2).add(0);
        adj.get(0).add(3);
        adj.get(3).add(0);
        adj.get(2).add(4);
        adj.get(4).add(2);

        boolean[] visited = new boolean[totalNodes]; // Visited array to track visited nodes

        // Perform BFS for all components of the graph
        int totalComponents = 0;
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) { // If the node is not visited
                ArrayList<Integer> bfsComponent = bfs(i, adj, visited); // Perform BFS for the component
                printBFSResult(bfsComponent); // Print the BFS result for the component
                totalComponents++; // Increment the count of components
            }
        }

        System.out.println("Total components in graph: " + totalComponents); // Print the total number of components
    }

    /**
     * Prints the result of the BFS traversal.
     *
     * @param result the list of nodes in BFS order
     */
    static void printBFSResult(ArrayList<Integer> result) {
        for (int node : result) {
            System.out.print(node + " "); // Print each node in the BFS result
        }
        System.out.println(); // Print a newline after printing all nodes
    }
}
