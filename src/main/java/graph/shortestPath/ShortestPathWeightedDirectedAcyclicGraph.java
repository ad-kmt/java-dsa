package graph.shortestPath;

import java.util.*;

public class ShortestPathWeightedDirectedAcyclicGraph {

    public static void main(String[] args) {
        int totalNodes = 6;
        ArrayList<ArrayList<Edge>> adj = new ArrayList<>();

        // Initialize adjacency list for the graph
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        // Constructing the graph with edges
        adj.get(0).add(new Edge(1, 2));
        adj.get(0).add(new Edge(4, 1));
        adj.get(1).add(new Edge(2, 3));
        adj.get(2).add(new Edge(3, 6));
        adj.get(4).add(new Edge(2, 2));
        adj.get(4).add(new Edge(5, 4));
        adj.get(5).add(new Edge(3, 1));

        ShortestPathWeightedDirectedAcyclicGraph obj = new ShortestPathWeightedDirectedAcyclicGraph();
        obj.shortestPath(0, adj, totalNodes);
    }

    /**
     * Implements the algorithm to find the shortest path in a weighted Directed Acyclic Graph (DAG) using topological sorting.
     *
     * Overview:
     * This method calculates the shortest paths from a given source node to all other nodes in a weighted Directed Acyclic Graph (DAG).
     *
     * Intuition:
     * In a DAG, the shortest path can be efficiently found using topological sorting. By processing nodes in topological order, we ensure that
     * when we process a node, all nodes reachable from it have already been processed. This allows us to update the shortest paths correctly.
     *
     * Why not Dijkstra ?
     * Dijkstra's algorithm is necessary for graphs that can contain cycles because they can't be topologically sorted.
     * In other cases, the topological sort would work fine as we start from the first node, and then move on to the others in a directed manner.
     *
     * Topological sort can be implemented in two ways- BFS and DFS. Here, we will be implementing using the DFS technique
     *
     * Data Structures Used:
     * - Adjacency list to represent the graph
     * - Distance array to store the shortest distance from the source to each node
     * - Stack to store the topological order of nodes
     * - Edge class to represent an edge with a destination node and a weight
     *
     * Algorithm Description:
     * 1. Perform topological sorting on the graph to get the processing order of nodes.
     * 2. Initialize the distance array with a large value (infinity) to represent untraversed nodes.
     * 3. Set the distance to the source node as 0.
     * 4. Process nodes in topological order, updating the shortest distance to each adjacent node.
     * 5. Nodes that remain untraversed (distance = infinity) are marked as unreachable (INF).
     *
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity: O(V + E), for the adjacency list, distance array, and stack.
     * Edge Cases:
     * - The graph may contain disconnected components.
     *
     * @param src the source node
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     */
    private void shortestPath(int src, ArrayList<ArrayList<Edge>> adj, int totalNodes) {
        // Stack to store the topological sort order
        Stack<Integer> stack = new Stack<>();
        // Visited array
        boolean[] visited = new boolean[totalNodes];

        // Perform topological sorting for each unvisited node
        for (int i = 0; i < totalNodes; i++) {
            if (!visited[i]) {
                topoSort(i, adj, visited, stack);
            }
        }

        // Distance array initialized with a large number to indicate untraversed nodes
        int[] dist = new int[totalNodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0; // Distance to the source node is 0

        // Process nodes in topological order
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (dist[node] != Integer.MAX_VALUE) {
                for (Edge adjNode : adj.get(node)) {
                    // If a shorter path to the adjacent node is found
                    if (dist[adjNode.v] > dist[node] + adjNode.weight) {
                        dist[adjNode.v] = dist[node] + adjNode.weight; // Update distance
                    }
                }
            }
        }

        // Print the shortest distances
        for (int i = 0; i < totalNodes; i++) {
            if (dist[i] == Integer.MAX_VALUE)
                System.out.print("INF ");
            else
                System.out.print(dist[i] + " ");
        }
    }

    /**
     * Helper method to perform topological sorting.
     *
     * @param node the current node
     * @param adj the adjacency list
     * @param visited the visited array
     * @param stack the stack to store the topological order
     */
    private void topoSort(int node, ArrayList<ArrayList<Edge>> adj, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true; // Mark the current node as visited

        // Recursively visit all adjacent nodes
        for (Edge adjNode : adj.get(node)) {
            // Check if the adjacent node is already visited
            if (!visited[adjNode.v]) {
                topoSort(adjNode.v, adj, visited, stack);
            }
        }
        stack.add(node);
    }

}

class Edge {
    int v;
    int weight;

    Edge(int v, int weight) {
        this.v = v;
        this.weight = weight;
    }
}
