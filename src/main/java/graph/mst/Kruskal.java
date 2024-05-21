package graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    int[] parent;
    int[] rank;

    /**
     * Initializes the Kruskal's algorithm data structure.
     *
     * Overview:
     * Kruskal's algorithm is used to find the Minimum Spanning Tree (MST) of a graph. It works by sorting all the edges of the graph
     * by their weights and adding the smallest edge to the MST, ensuring no cycles are formed, until all nodes are connected.
     *
     * Intuition:
     * The algorithm uses the Disjoint Set Union (DSU) data structure to efficiently manage the merging of sets and ensure that no cycles are formed.
     * The edges are processed in increasing order of their weights, and only those edges that connect two different sets are added to the MST.
     *
     * In the case of a disconnected graph, the algorithm will produce a Minimum Spanning Forest (MSF),
     * which is a collection of MSTs for each connected component.
     *
     * Data Structures Used:
     * - parent array to store the parent of each node in the disjoint set.
     * - rank array to store the rank (or depth) of each node in the disjoint set.
     * - Edge class to represent an edge with a source node, destination node, and weight.
     *
     * Time Complexity:
     * - Sorting the edges: O(E log E), where E is the number of edges.
     * - Union-Find operations: O(V + E), where V is the number of vertices.
     * Overall time complexity O(E log E)
     *
     * The time complexity for Union-Find operations is derived as follows:
     * - Each Union and Find operation takes nearly constant time, O(α(n)), where α is the Inverse Ackermann function, which grows very slowly.
     * - Since we perform Union-Find operations for each edge, the total time complexity for these operations is O(E * α(V)).
     *   In practical scenarios, α(V) is very small and can be considered a constant.
     *
     * Space Complexity:
     * - The space complexity is O(V + E), where V is the number of vertices and E is the number of edges.
     *
     * This is derived from:
     * - The parent array, which stores the parent of each node, requiring O(V) space.
     * - The rank array, which stores the rank (or depth) of each node, also requiring O(V) space.
     * - The adjacency list to store the graph, which requires O(E) space.
     *
     * @param totalNodes the total number of nodes in the graph
     */
    Kruskal(int totalNodes) {
        parent = new int[totalNodes];
        rank = new int[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public static void main(String[] args) {
        int totalNodes = 5;
        ArrayList<Edge> edges = new ArrayList<>();

        // Constructing the graph with edges
        edges.add(new Edge(0, 1, 2));
        edges.add(new Edge(0, 3, 6));
        edges.add(new Edge(1, 3, 8));
        edges.add(new Edge(1, 2, 3));
        edges.add(new Edge(1, 4, 5));
        edges.add(new Edge(2, 4, 7));

        Kruskal obj = new Kruskal(totalNodes);
        obj.findMst(edges, totalNodes);
    }

    /**
     * Finds the Minimum Spanning Tree (MST) using Kruskal's algorithm.
     *
     * @param edges the list of edges in the graph
     * @param totalNodes the total number of nodes in the graph
     */
    public void findMst(ArrayList<Edge> edges, int totalNodes) {
        // Sort the edges by their weight in non-decreasing order
        edges.sort(Comparator.comparingInt(edge -> edge.w));

        int mstCost = 0;
        List<Edge> mstEdges = new ArrayList<>();

        // Process each edge in sorted order
        for (Edge edge : edges) {
            // If the edge connects two different sets, add it to the MST
            if (findParent(edge.u) != findParent(edge.v)) {
                mstCost += edge.w;
                union(edge.u, edge.v);
                mstEdges.add(edge);
            }
        }

        // Print the edges in the MST
        for (Edge mstEdge : mstEdges) {
            System.out.println(mstEdge.u + " -> " + mstEdge.v);
        }

        System.out.println("Cost of MST: " + mstCost);
    }

    /**
     * Finds the representative (or parent) of the set that the node belongs to.
     * Uses path compression to make the tree flatter and the operations faster.
     *
     * @param node the node to find the parent of
     * @return the representative of the set
     */
    public int findParent(int node) {
        if (parent[node] == node) return node;
        return parent[node] = findParent(parent[node]); // Path compression
    }

    /**
     * Unions the sets containing nodes u and v using union by rank.
     *
     * @param u the first node
     * @param v the second node
     */
    public void union(int u, int v) {
        u = findParent(u);
        v = findParent(v);

        if (rank[u] > rank[v]) {
            parent[v] = u;
        } else if (rank[u] < rank[v]) {
            parent[u] = v;
        } else {
            parent[v] = u;
            rank[u]++;
        }
    }

    /**
     * Represents an edge in the graph with a source node, destination node, and weight.
     */
    public static class Edge {
        public int u;
        public int v;
        public int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

    }
}
