package graph.bridgesAndArticulationPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class ArticulationPoint {

    public static void main(String[] args) {
        int totalNodes = 6;
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

        ArticulationPoint obj = new ArticulationPoint();
        obj.printArticulationPoints(adj, totalNodes);
    }

    /**
     * Finds and prints all articulation points in the given graph.
     *
     * OVERVIEW:
     * An articulation point (or cut vertex) in a graph is a vertex which, when removed, graph gets broken into multiple components.
     * This method uses a DFS-based algorithm to find all such articulation points.
     *
     * INTUITION:
     * The algorithm uses DFS traversal to assign discovery time for each node and lowest reachable time for the subtree rooted with each node.
     * A node is identified as an articulation point if:
     * - Condition for root node: It is the root of the DFS tree and has more than one child dfs subtree.
     * - Condition for nodes other than root node: It is not the root of the DFS tree and there is a child node v such that
     *   no vertex in the subtree rooted at v has a back edge to one of the ancestors of the node, i.e.
     *   no vertex in the subtree rooted at v has lowest reachable discovery time less than discovery time of v.
     *
     * SEPARATE LOGIC FOR ROOT NODE:
     * The root node requires separate logic because it does not have a parent or ancestors,
     * making the standard condition for articulation points (based on back edges) inapplicable.
     *
     * DATA STRUCTURES USED:
     * - visited[]: Boolean array to keep track of visited nodes.
     * - discoveryTime[]: Array to store the discovery time of each node.
     * - lowestDiscoveryTime[]: Array to store the lowest discovery time reachable from the subtree rooted with the node.
     * - AP[]: Boolean array to mark articulation points, because we can get a single node as an articulation point multiple times.
     * - int childCount: to determine if the root of the DFS tree is an articulation point
     *
     * ALGORITHM STEPS:
     * 1. Initialize arrays to keep track of visited nodes, discovery times, and lowest reachable discovery times.
     * 2. Iterate through all nodes. For each unvisited node, perform DFS:
     *    - Mark the node as visited and set its discovery time and lowest reachable discovery time.
     *    - For each adjacent node:
     *      - If the adjacent node is not visited, recursively perform DFS on it:
     *        - After returning from DFS, update the current node's lowest reachable time based on the adjacent node's lowest reachable time.
     *        - (IMPORTANT) If the current node [ IS NOT THE ROOT ] and the adjacent node's lowest reachable time is [ GREATER THAN OR EQUAL TO ] (>=) the current node's discovery time, mark the current node as an articulation point.
     *      - If the adjacent node is visited and is not the parent, update the current node's lowest reachable time based on the adjacent node's discovery time.
     *    - If the current node IS THE ROOT and has more than one child, mark it as an articulation point.
     * 3. Print all nodes that are marked as articulation points.
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V), for the arrays and recursive stack space.
     *
     *
     * COMPARISON OF CONDITIONS FOR ARTICULATION POINTS AND BRIDGES:*
     * Bridges:
     * - For bridges, we check if there exists any alternative path from the adjacent node to the current node, which would mean that removing the edge would disconnect the graph.
     * - The condition to identify a bridge during DFS is:
     *   if (lowestReachable[adjNode] > discoveryTime[node])
     *
     * Articulation Points:
     * - For articulation points, we check if there exists any path from the adjacent node to the PREVIOUS NODE OF THE CURRENT NODE. If such a path does not exist, and the current node is not the root of the DFS tree, then the current node is an articulation point.
     * - The condition to identify an articulation point during DFS is:
     *   if (low[adjNode] >= time[node] && parent != -1)
     *   This condition checks if there is no alternative path from the adjacent node (`adjNode`) to the previous node of the current node (`node`). Additionally, it ensures that the current node is not the starting node of the DFS.
     * - Additionally, if the current node is the root of the DFS tree and has more than one child, it is considered an articulation point because its removal would disconnect the graph.
     *
     * Summary:
     * - Bridge Condition: if (low[adjNode] > time[node])
     * - Articulation Point Condition: if (low[adjNode] >= time[node] && parent != -1)
     * - Root Condition for Articulation Point: if (parent == -1 && childCount > 1)
     *
     *
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     */
    private void printArticulationPoints(ArrayList<ArrayList<Integer>> adj, int totalNodes) {
        boolean[] visited = new boolean[totalNodes];
        int[] discoveryTime = new int[totalNodes]; // Discovery times of visited vertices
        int[] lowestReachable = new int[totalNodes];  // Lowest reachable vertex from subtree from each node
        boolean[] AP = new boolean[totalNodes]; // To mark articulation points

        // Initialize all nodes as unvisited
        Arrays.fill(visited, false);

        // Perform DFS from each unvisited node
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) {
                dfs(i, -1, adj, visited, discoveryTime, lowestReachable, 1, AP);
            }
        }

        // Print all articulation points
        System.out.println("Articulation Points:");
        for (int i = 0; i < totalNodes; i++) {
            if (AP[i]) {
                System.out.println(i);
            }
        }
    }

    /**
     * Recursive method to perform DFS and find articulation points.
     *
     * @param node the current node
     * @param parent the parent node in the DFS tree
     * @param adj the adjacency list representing the graph
     * @param visited the visited array
     * @param discoveryTime the discovery time array
     * @param lowestReachable the lowest discovery time array
     * @param timer the current time step
     * @param AP the articulation points array
     */
    private void dfs(int node, int parent, ArrayList<ArrayList<Integer>> adj, boolean[] visited, int[] discoveryTime, int[] lowestReachable, int timer, boolean[] AP) {
        int dfsChildrenCount = 0; // to determine if root node is an articulation point or not
        visited[node] = true; // mark current node as visited
        discoveryTime[node] = lowestReachable[node] = timer++; // Initialize discovery time and lowest reachable value

        // Iterate through all adjacent nodes
        for (int adjNode : adj.get(node)) {
            if (adjNode == parent) continue; // Skip the parent node

            // Check if adjacent node is visited or not
            if (!visited[adjNode]) {
                // Dfs on adjacent node
                dfs(adjNode, node, adj, visited, discoveryTime, lowestReachable, timer, AP);

                // Increment for the new child dfs subtree
                dfsChildrenCount++;

                // Update the lowest reachable value of the current node
                lowestReachable[node] = Math.min(lowestReachable[node], lowestReachable[adjNode]); // Update lowest reachable value of the current node

                // If node is NOT root and lowestReachable value of one of its child is greater
                // than or equal to discovery value of node, but essentially less than any previous node of the current node
                if (parent != -1 && lowestReachable[adjNode] >= discoveryTime[node]) {
                    AP[node] = true;
                }
            } else {
                // Update the lowest reachable value of the current node for parent function calls
                lowestReachable[node] = Math.min(lowestReachable[node], lowestReachable[adjNode]);
            }
        }

        // If node is root of DFS tree and has two or more children
        if (parent == -1 && dfsChildrenCount > 1) {
            AP[node] = true;
            System.out.println(parent + " " + node);
        }
    }
}
