package graph.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Prims {

    public static void main(String[] args) {
        int totalNodes = 5;
        ArrayList<ArrayList<Node>> adj = new ArrayList<>();

        // Initialize adjacency list for the graph
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList<>());
        }

        // Constructing the graph with edges
        adj.get(0).add(new Node(1, 2));
        adj.get(1).add(new Node(0, 2));

        adj.get(1).add(new Node(2, 3));
        adj.get(2).add(new Node(1, 3));

        adj.get(0).add(new Node(3, 6));
        adj.get(3).add(new Node(0, 6));

        adj.get(1).add(new Node(3, 8));
        adj.get(3).add(new Node(1, 8));

        adj.get(1).add(new Node(4, 5));
        adj.get(4).add(new Node(1, 5));

        adj.get(2).add(new Node(4, 7));
        adj.get(4).add(new Node(2, 7));

        Prims obj = new Prims();
        int mstCost = obj.primsAlgoMinCost(adj, totalNodes);
        obj.primsAlgoPrintMst(adj, totalNodes);
        System.out.println("Cost of minimum spanning tree: " + mstCost);
    }

    /**
     * Implements Prim's algorithm to find the minimum spanning tree (MST) cost.
     *
     * Overview:
     * Prim's algorithm is a greedy algorithm that finds the minimum spanning tree of a weighted undirected graph.
     * It starts with a single node and continuously adds the lowest-weight edge that connects a node in the MST to a node outside the MST.
     *
     * Intuition:
     * The algorithm uses a priority queue to always expand the MST using the minimum weight edge available.
     * This ensures that the resulting MST has the smallest possible total edge weight.
     *
     * Data Structures Used:
     * - Adjacency list to represent the graph
     * - Priority queue to select the next minimum weight edge
     * - Boolean array to keep track of nodes included in the MST
     *
     * Time complexity of Prim's algorithm is O((V + E) log V), where V is the number of vertices and E is the number of edges.
     *
     * This is derived as follows:
     * - Initializing the priority queue and the arrays takes O(V) time.
     * - Extracting the minimum element from the priority queue (which is a binary heap) takes O(log V) time.
     * - Each of the V vertices is extracted from the priority queue exactly once, leading to O(V log V) time for all extractions.
     * - Each edge can be added to the priority queue or its key updated at most once. Since there are E edges and each insertion or update takes O(log V) time, this results in O(E log V) for all such operations.
     *
     * Combining these, the total time complexity is O((V + E) log V).
     *
     * TODO: I feel time complexity of insertion or deletion operation on priority queue should be O(log E),
     * because at any moment the priority queue can contain max E elements representing every edge,
     * but everywhere it is mentioned O(log V), ignoring it for now.
     *
     * Space Complexity: O(V + E), for the adjacency list, priority queue, and additional arrays.
     *
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     * @return the cost of the minimum spanning tree
     */
    public int primsAlgoMinCost(ArrayList<ArrayList<Node>> adj, int totalNodes) {
        boolean[] mst = new boolean[totalNodes]; // To keep track of nodes included in the MST
        int minCost = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.w)); // Priority queue to select the next minimum weight edge

        pq.add(new Node(0, 0)); // Start with node 0
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (mst[node.v]) continue; // Skip if the node is already included in the MST

            mst[node.v] = true;
            minCost += node.w; // Add the edge weight to the total cost

            // Explore all adjacent nodes
            for (Node adjNode : adj.get(node.v)) {
                // Check if adjacent node is already part of MST
                if (!mst[adjNode.v]) {
                    pq.add(adjNode); // Add the edge to the priority queue
                }
            }
        }
        return minCost;
    }

    /**
     * Prints the edges of the minimum spanning tree (MST) using Prim's algorithm.
     *
     * Overview:
     * This method constructs the MST and prints the edges of the MST.
     * It uses a priority queue to always expand the MST using the minimum weight edge available.
     *
     * Intuition:
     * The algorithm starts from an arbitrary node (node 0 in this case) and grows the MST by adding the smallest edge
     * that connects a vertex in the MST with a vertex outside the MST. The key array keeps track of the minimum weight
     * required to add each vertex to the MST, and the parent array keeps track of the MST structure.
     *
     * Data Structures Used:
     * - mst[]: Boolean array to keep track of vertices included in the MST.
     * - key[]: Integer array to store the minimum weight to add each vertex to the MST.
     * - parent[]: Integer array to store the parent node for each vertex in the MST.
     * - PriorityQueue<Node>: A priority queue to select the next vertex to add to the MST based on the minimum weight edge.
     *
     * Role of key[] and parent[]:
     * - The key[] array helps in ensuring that the minimum weight edge is always chosen to expand the MST.
     * - The parent[] array works hand in hand with key[] to update the parent of each vertex whenever a lower weight edge is found.
     * - Together, they ensure that the parent[] array contains the correct parent-child relationships for the final MST.
     *
     * Steps:
     * 1. Initialize the key array with a large value (infinity) and the parent array with -1.
     * 2. Start from node 0: set key[0] to 0 and add it to the priority queue.
     * 3. While the priority queue is not empty, extract the minimum key vertex from the priority queue.
     * 4. Mark the extracted vertex as part of the MST.
     * 5. For each adjacent vertex of the extracted vertex, if it is not in the MST and the weight of the edge to it
     *    is less than its current key value, update its key value and parent, and add it to the priority queue.
     * 6. After the MST is constructed, print the edges of the MST.
     *
     * @param adj the adjacency list representing the graph
     * @param totalNodes the total number of nodes in the graph
     */
    public void primsAlgoPrintMst(ArrayList<ArrayList<Node>> adj, int totalNodes) {

        boolean[] mst = new boolean[totalNodes]; // To keep track of nodes included in the MST

        int[] key = new int[totalNodes]; // To store the minimum weight to add each node to the MST
        Arrays.fill(key, Integer.MAX_VALUE);

        int[] parent = new int[totalNodes]; // To store the parent node for each node in the MST
        Arrays.fill(parent, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.w)); // Priority queue to select the next minimum weight edge

        key[0] = 0;
        pq.add(new Node(0, 0)); // Start with node 0
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            mst[node.v] = true;

            // Explore all adjacent nodes
            for (Node adjNode : adj.get(node.v)) {
                if (!mst[adjNode.v] && key[adjNode.v] > adjNode.w) {
                    pq.add(adjNode); // Add the edge to the priority queue
                    key[adjNode.v] = adjNode.w; // Update the key value
                    parent[adjNode.v] = node.v; // Update the parent
                }
            }
        }

        // Print the edges in the MST
        System.out.println("Minimum spanning tree edges:");
        for (int i = 1; i < totalNodes; i++) {
            System.out.println(parent[i] + " -> " + i);
        }
    }

    /**
     * Represents a node in the graph with a destination node and edge weight.
     */
    static class Node {
        public int v; // Destination node
        public int w; // Edge weight

        Node(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}