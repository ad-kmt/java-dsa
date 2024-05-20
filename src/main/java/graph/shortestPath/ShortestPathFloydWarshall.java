package graph.shortestPath;

public class ShortestPathFloydWarshall {

    /**
     * Implements the Floyd-Warshall algorithm to find the shortest paths in a weighted graph.
     *
     * OVERVIEW:
     * - Dijkstra's Shortest Path algorithm and Bellman-Ford algorithm are single-source shortest path algorithms
     * where we are given a single source node and are asked to find out the shortest path to every other node from that given source.
     * But in the Floyd Warshall algorithm, we need to figure out the shortest distance from each node to every other node.
     * - Floyd-Warshall algorithm can detect negative cycles as well
     *
     * Intuition:
     * The Floyd-Warshall algorithm uses dynamic programming to find the shortest paths between all pairs of nodes in a weighted graph.
     * The key idea is to iteratively improve the shortest path estimates by considering each node as an intermediate point.
     *
     * Initially, the direct distances between nodes are used. Then, for each pair of nodes (i, j), we check if a path through a third node (k)
     * offers a shorter route. This means that for each pair (i, j), we update the distance as:
     *
     * matrix[i][j] = min(matrix[i][j], matrix[i][k] + matrix[k][j])
     *
     * This update is done for every pair of nodes (i, j) for each possible intermediate node k. By doing this iteratively for all nodes,
     * the algorithm ensures that all possible paths are considered, and the shortest paths are found.
     *
     * The process can be visualized as expanding the set of intermediate nodes that can be used to form the shortest path:
     * - In the first iteration, no intermediate nodes are used.
     * - In the second iteration, the first node is used as an intermediate point.
     * - In the third iteration, the first two nodes are used as intermediate points.
     * - This continues until all nodes are used as intermediate points potentially covering all opportunities for shortest path.
     *
     * The algorithm is not much intuitive as the other ones’.
     * It is more of a brute force, where all combinations of paths have been tried to get the shortest paths.
     *
     * NEGATIVE CYCLE
     * A negative cycle exists if, after running the algorithm, any distance from a node to itself becomes negative.
     * This is because the distance from a node to itself should always be zero,
     * and a negative value indicates the presence of a cycle with a negative total weight.
     *
     * Data Structures Used:
     * - 2D matrix to store the shortest distances between all pairs of nodes
     *
     * Algorithm Description:
     * 1. Initialize the distance matrix, setting the distance to itself as 0 and -1 (representing no path) as a large value (infinity).
     * 2. Iteratively update the distance matrix by checking if a path through an intermediate node offers a shorter route.
     * 3. Convert large values (infinity) back to -1 to indicate no path exists.
     *
     * The order of the loops is crucial:
     * - The outermost loop iterates over each node k, treating it as an intermediate point.
     * - The inner loops iterate over each pair of nodes (i, j).
     *
     * This order ensures that when updating matrix[i][j], the values matrix[i][k] and matrix[k][j] are already the shortest distances considering
     * all previous nodes (0 -> k-1) as intermediates. If the order of the loops is changed, the algorithm would incorrectly use incomplete or outdated path information,
     * leading to incorrect shortest path calculations.
     *
     * Time Complexity: O(V^3), where V is the number of vertices.
     * Space Complexity: O(V^2), for the distance matrix.
     * Edge Cases:
     * - The graph may contain no edges (all distances remain large values or -1).
     *
     * Limitations:
     * - The algorithm assumes no negative weight cycles.
     *
     * @param matrix the adjacency matrix representing the graph
     */
    public void shortest_distance(int[][] matrix) {
        int n = matrix.length;

        // Initialize the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) {
                    matrix[i][j] = (int) (1e9); // Treat -1 as a large value (infinity)
                }
                if (i == j) {
                    matrix[i][j] = 0; // Distance to self is 0
                }
            }
        }

        // Iteratively update the matrix to find the shortest paths by considering each node as an intermediate point.
        // For each pair of nodes (i, j), check if a path through a third node (k) offers a shorter route.
        // If yes then update the distance matrix
        // This ensures that all possible paths are considered, and the shortest paths are found.
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        // Check for negative weight cycles
        boolean hasNegativeCycle = false;
        for (int i = 0; i < n; i++) {
            // If distance of a node to itself is negative then negative cycle exists
            if (matrix[i][i] < 0) {
                hasNegativeCycle = true;
                System.out.println("Negative weight cycle detected");
                break;
            }
        }

        // Convert large values back to -1 to indicate no path exists
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == (int) (1e9)) {
                    matrix[i][j] = -1;
                }
            }
        }

        // If no negative cycle is found, print the shortest distances
        if (!hasNegativeCycle) {
            System.out.println("Shortest distances matrix:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        int V = 4;
        int[][] matrix = new int[V][V];

        // Initialize the matrix with -1 indicating no path
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                matrix[i][j] = -1;
            }
        }

        // Adding edges
        matrix[0][1] = 2;
        matrix[1][0] = 1;
        matrix[1][2] = 3;
        matrix[3][0] = 3;
        matrix[3][1] = 5;
        matrix[3][2] = 4;

        ShortestPathFloydWarshall obj = new ShortestPathFloydWarshall();
        obj.shortest_distance(matrix);
    }
}