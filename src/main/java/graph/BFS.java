package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    public static ArrayList<Integer> bfs(int startNode, ArrayList < ArrayList < Integer >> adj, boolean[] vis){

        //Output
        ArrayList<Integer> bfsList = new ArrayList<>();

        //Data structures we need:
        // 1. Queue
        // 2. Visited array
        Queue<Integer> q = new LinkedList<>();

        //Starting BFS traversal with given node
        q.add(startNode);
        vis[startNode] = true;

        while(!q.isEmpty()){

            //Visiting a node from top of queue
            Integer node = q.poll();

            bfsList.add(node);
            //Checking Adjacent Nodes
            ArrayList<Integer> adjNodes = adj.get(node);
            for(int adjNode : adjNodes){
                if(!vis[adjNode]){
                    q.add(adjNode); // add in queue
                    vis[adjNode] = true;// mark visited
                }
            }
        }
        return bfsList;
    }

    public static void main(String[] args) {
        ArrayList < ArrayList < Integer >> adj = new ArrayList < > ();
        int totalNodes = 5;
        for (int i = 0; i < totalNodes; i++) {
            adj.add(new ArrayList < > ());
        }
        adj.get(0).add(1);
        adj.get(1).add(0);
        adj.get(0).add(2);
        adj.get(2).add(0);
        adj.get(0).add(3);
        adj.get(3).add(0);
        adj.get(2).add(4);
        adj.get(4).add(2);

        boolean[] vis = new boolean[totalNodes];

        // A graph can have multiple components
        // do traversal on all components of Graph.
        int totalComponents = 0;
        for (int i = 0; i < totalNodes; i++) {
            if(!vis[i]) {
                ArrayList<Integer> bfsGraphComponent = bfs(i, adj, vis);
                printAns(bfsGraphComponent);
                totalComponents++;
            }
        }

        System.out.println("Total components in graph: " + totalComponents);
    }

    static void printAns(ArrayList < Integer > ans) {
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i) + " ");
        }
        System.out.println(" ");
    }
}
