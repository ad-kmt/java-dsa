package graph.bridgesAndArticulationPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class Bridges {

    public static void main(String[] args) {
        int totalNodes = 5;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < totalNodes; i++)
            adj.add(new ArrayList<>());

        // Adding edges
        adj.get(0).add(1);
        adj.get(1).add(0);

        adj.get(0).add(2);
        adj.get(2).add(0);

        adj.get(1).add(2);
        adj.get(2).add(1);

        adj.get(1).add(3);
        adj.get(3).add(1);

        adj.get(3).add(4);
        adj.get(4).add(3);

        Bridges obj = new Bridges();
        obj.printBridges(adj, totalNodes);
    }

    /**
     * Finds and prints all bridges in the given graph.
     *
     * Overview:
     * A bridge (or cut-edge) in a graph is an edge that, when removed, makes the graph disconnected or increases the number of separate connected components.
     * This method uses a DFS-based algorithm to find all such bridges.
     *
     * Data Structures Used:
     * - visited[]: Boolean array to keep track of visited nodes.
     * - time[]: Array to store the discovery time of each node.
     * - lowestReachable[]: Array to store the lowest discovery time reachable from the subtree rooted with the node.
     *
     * Intuition:
     * The algorithm uses Depth-First Search (DFS) to find bridges by maintaining discovery times and the earliest reachable vertex
     * (lowestReachable value) for each vertex. During the DFS, for each edge (u, v):
     * - If v is not visited, it is visited recursively, and the low value of u is updated based on the lowestReachable value of v.
     * - If v is already visited and v is not the parent of u, the low value of u is updated based on the lowestReachable time of v.
     *
     * An edge (u, v) is identified as a bridge if the low value of v is greater than the discovery time of u,
     * indicating that there is no back edge from the subtree rooted at v to one of the ancestors of u.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the arrays and recursive stack space.
     *
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     */
    private void printBridges(ArrayList<ArrayList<Integer>> adj, int totalNodes) {
        boolean[] visited = new boolean[totalNodes];
        int[] discoveryTime = new int[totalNodes]; // Discovery times of visited vertices
        int[] lowestReachable = new int[totalNodes];  // Earliest visited vertex reachable from subtree

        // Initialize all nodes as unvisited
        Arrays.fill(visited, false);

        // Perform DFS from each unvisited node
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) {
                dfs(i, -1, adj, visited, discoveryTime, lowestReachable, 1);
            }
        }
    }

    /**
     * Recursive method to perform DFS and find bridges.
     *
     * @param node the current node
     * @param parent the parent node in the DFS tree
     * @param adj the adjacency list representing the graph
     * @param visited the visited array
     * @param discoveryTime the discovery time array
     * @param lowestReachable the lowest discovery time array
     * @param timer the current time step
     */
    private void dfs(int node, int parent, ArrayList<ArrayList<Integer>> adj, boolean[] visited, int[] discoveryTime, int[] lowestReachable, int timer) {
        visited[node] = true;
        discoveryTime[node] = lowestReachable[node] = timer++; // Initialize discovery time and lowest reachable value

        // Iterate through all adjacent nodes
        for (int adjNode : adj.get(node)) {
            // Skip the parent node
            if (adjNode == parent) continue;

            if (!visited[adjNode]) {
                // If the adjacent node is not visited, perform DFS
                dfs(adjNode, node, adj, visited, discoveryTime, lowestReachable, timer);
                // Update lowest reachable value of the current node
                lowestReachable[node] = Math.min(lowestReachable[node], lowestReachable[adjNode]);

                // If the lowest vertex reachable from subtree under adjNode is
                // below the current node in DFS tree, then the current node to adjNode is a bridge
                if (lowestReachable[adjNode] > discoveryTime[node]) {
                    System.out.println(node + " -> " + adjNode); // Print the bridge
                }
            } else {
                // Update lowest reachable value of the current node for parent function calls
                lowestReachable[node] = Math.min(lowestReachable[node], lowestReachable[adjNode]);
            }
        }
    }
}
