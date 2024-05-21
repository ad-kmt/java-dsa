package graph.mst;

public class DisjointSetUnionFind {
    int[] parent;
    int[] rank;
    int[] size;
    int totalNodes;

    /**
     * Initializes the Disjoint Set Union Find (DSU) data structure.
     *
     * Overview:
     * The DSU (or Union-Find) data structure is used to efficiently manage and merge disjoint sets.
     * It supports two primary operations: finding the representative (or parent) of a set and unioning two sets.
     *
     * Intuition:
     * The DSU uses two key optimizations:
     * 1. Path Compression: Flattens the structure of the tree whenever `findParent` is called, making future operations faster.
     * 2. Union by Rank/Size: Ensures that the smaller tree is always added under the root of the larger tree, keeping the tree's height small.
     *
     * Data Structures Used:
     * - parent array to store the parent of each node.
     * - rank array to store the rank (or depth) of each node.
     * - size array to store the size of each set.
     *
     * Time Complexity:
     * If we combine path compression with union by size / rank - we will reach nearly constant time queries
     *
     * The final amortized time complexity is:
     * - Find: O(αlpha(n)), where α is the Inverse Ackermann function, which grows very slowly, it doesn't exceed 5 for all values of n at the range of (n < 10^600)
     * - Union: O(αlpha(n)), due to the use of path compression and union by rank/size.
     *
     * Amortized Complexity:
     * Amortized Analysis is used for algorithms where an occasional operation is very slow, but most of the other operations are faster.
     * Amortized complexity is the total time per operation, evaluated over a sequence of multiple operations.
     * The idea is to guarantee the total time of the entire sequence, while allowing single operations to be much slower then the amortized time.
     * E.g. in our case a single call might take O(log n) in the worst case, but if we do 'm' such calls
     * back to back we will end up with an average time of O(αlpha(n))
     *
     * We will also not present a proof for this time complexity, since it is quite long and complicated.
     *
     * Also, DSU with union by size / rank, but without path compression works is O(log n) time per query.
     *
     * @param totalNodes the total number of nodes
     */
    DisjointSetUnionFind(int totalNodes) {
        this.totalNodes = totalNodes;
        this.parent = new int[totalNodes];
        this.rank = new int[totalNodes];
        this.size = new int[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    /**
     * Finds the representative (or parent) of the set that the node belongs to.
     * Uses path compression to make the tree flatter and the operations faster.
     *
     * @param node the node to find the parent of
     * @return the representative of the set
     */
    int findParent(int node) {
        if (node == parent[node]) return node;
        return parent[node] = findParent(parent[node]); // Path compression
    }

    /**
     * Unions the sets containing nodes u and v using union by rank.
     *
     * @param u the first node
     * @param v the second node
     */
    void union(int u, int v) {
        u = findParent(u);
        v = findParent(v);

        if (rank[u] > rank[v]) {
            parent[v] = u;
        } else if (rank[v] > rank[u]) {
            parent[u] = v;
        } else {
            parent[v] = u;
            rank[u]++;
        }
    }

    /**
     * Unions the sets containing nodes u and v using union by size.
     *
     * @param u the first node
     * @param v the second node
     */
    void unionBySize(int u, int v) {
        u = findParent(u);
        v = findParent(v);

        if (size[u] > size[v]) {
            parent[v] = u;
            size[u] += size[v];
        } else {
            parent[u] = v;
            size[v] += size[u];
        }
    }

    public static void main(String[] args) {
        int totalNodes = 7;

        DisjointSetUnionFind obj = new DisjointSetUnionFind(totalNodes);
        obj.union(0, 1);
        obj.union(1, 2);
        obj.union(3, 4);
        obj.union(5, 6);

        System.out.println("Parent of 2: " + obj.findParent(2));
        System.out.println("Parent of 4: " + obj.findParent(4));
        System.out.println("Parent of 6: " + obj.findParent(6));

        obj.union(3, 5);

        System.out.println("Parent of 2: " + obj.findParent(2));
        System.out.println("Parent of 4: " + obj.findParent(4));
        System.out.println("Parent of 6: " + obj.findParent(6));

        obj.union(2, 6);

        System.out.println("Parent of 2: " + obj.findParent(2));
        System.out.println("Parent of 4: " + obj.findParent(4));
        System.out.println("Parent of 6: " + obj.findParent(6));
    }
}
