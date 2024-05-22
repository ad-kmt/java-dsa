package graph.cycleDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CheckCycleUndirectedGraphBFS {

    public static void main(String[] args) {
        int totalNodes = 5;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < totalNodes; i++) {
            adj.add(i, new ArrayList<Integer>());
        }
        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(2).add(3);
        adj.get(1).add(3);
        adj.get(2).add(4);

        CheckCycleUndirectedGraphBFS obj = new CheckCycleUndirectedGraphBFS();

        boolean ans = obj.isCycle(totalNodes, adj);
        if (ans) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    /**
     * Determines if there is a cycle in an undirected graph using BFS.
     *
     * Overview:
     * This method iterates through all the nodes of the graph and uses BFS to check for cycles.
     *
     * Data Structures Used:
     * - visited[]: Boolean array to keep track of visited nodes.
     * - Queue<Node>: A queue to perform BFS, where each node is represented by its value and its parent.
     *
     * Intuition:
     * In an undirected graph, a cycle exists if we revisit a node that is not the direct parent of the current node during a BFS traversal.
     *
     * The algorithm starts BFS from an unvisited node and explores all its adjacent nodes. For each adjacent node:
     * - If the node is not visited, it is marked as visited, added to the queue, and its parent is set as the current node.
     * - If the node is already visited and is not the parent of the current node, a cycle is detected.
     *
     * By iterating through all the nodes and performing BFS, the algorithm ensures that all components of the gr
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the visited array and queue.
     *
     * @param totalNodes the total number of nodes in the graph
     * @param adj the adjacency list representing the graph
     * @return true if there is a cycle, false otherwise
     */
    public boolean isCycle(int totalNodes, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[totalNodes]; // To keep track of visited nodes
        Arrays.fill(visited, false); // Initialize all nodes as unvisited

        // Check for cycles in each component of the graph
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) { // If the node is not visited
                if (checkForCycleBfs(i, adj, visited)) return true; // Perform BFS and check for cycles
            }
        }
        return false; // Return false if no cycle is found
    }

    /**
     * Helper method to perform BFS and check for cycles starting from a given node.
     *
     * @param start the starting node for BFS
     * @param adj the adjacency list representing the graph
     * @param visited the visited array
     * @return true if a cycle is found, false otherwise
     */
    public boolean checkForCycleBfs(int start, ArrayList<ArrayList<Integer>> adj, boolean[] visited) {
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(start, -1)); // Add the starting node to the queue with no parent
        visited[start] = true; // Mark the starting node as visited

        while (!q.isEmpty()) {
            Node node = q.poll(); // Dequeue a node from the queue

            ArrayList<Integer> adjNodes = adj.get(node.val); // Get the adjacent nodes of the current node

            // Iterate through all adjacent nodes
            for (int adjNode : adjNodes) {
                if (!visited[adjNode]) { // If the adjacent node is not visited
                    visited[adjNode] = true; // Mark the adjacent node as visited
                    q.add(new Node(adjNode, node.val)); // Add the adjacent node to the queue with the current node as its parent
                } else if (node.parent != adjNode) { // If the adjacent node is visited and is not the parent of the current node
                    return true; // A cycle is detected
                }
            }
        }
        return false; // Return false if no cycle is found
    }

    /**
     * Class representing a node in the graph for BFS.
     * It includes the value of the node and the value of its parent node.
     */
    class Node {
        int val;
        int parent;

        Node(int val, int parent) {
            this.val = val;
            this.parent = parent;
        }
    }
}
