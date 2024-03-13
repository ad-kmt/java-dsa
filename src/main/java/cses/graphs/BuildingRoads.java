package cses.graphs;

import java.util.*;

public class BuildingRoads {

    public int[] parent;
    public int[] size;
    int n;

    public BuildingRoads(int n){
        this.n = n;
        this.parent = new int[n+1];
        for(int i=0; i<=n; i++){
            parent[i] = i;
        }
        this.size = new int[n+1];
        Arrays.fill(size, 1);
    }

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int edges = sc.nextInt();
        sc.nextLine();
        BuildingRoads buildingRoads = new BuildingRoads(n);

        for(int i=0; i<edges; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();
            sc.nextLine();
            buildingRoads.union(u , v);
        }

        List<List<Integer>> ans = buildingRoads.buildRoads();
        System.out.println(ans.size());
        for(List<Integer> road : ans){
            System.out.println(road.get(0) + " " + road.get(1));
        }
    }

    public List<List<Integer>> buildRoads(){
        TreeSet<Integer> set = new TreeSet<>();
        List<List<Integer>> list = new ArrayList<>();
        for(int i=1; i<=n; i++){
            int parent = findParent(i);
            if(!set.contains(parent)) set.add(findParent(i));
        }
        int u = set.first();
        set.remove(u);
        for(int v : set){
            list.add(List.of(u,v));
            u = v;
        }
        return list;
    }

    public void union(int u, int v){
        u = findParent(u);
        v = findParent(v);
        if(u == v) return;
        if(size[u] >= size[v]){
            parent[v] = u;
            size[u] += size[v];
        } else {
            parent[u] = v;
            size[v] += size[u];
        }
    }

    public int findParent(int node){
        if(node == parent[node]) return node;
        return parent[node] = findParent(parent[node]);
    }



}
