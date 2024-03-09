package cses.graphs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Labyrinth {

    public static int[][] A;
    public static char[][] matrix;
    public static boolean[][] visited;
    public static int m,n;
    public static Node start;
    public static Node end;
    public static int[][] moves = new int[][]{{1,0,'D'}, {-1,0,'U'}, {0,1,'R'}, {0,-1,'L'}};

    public static String path;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();
        sc.nextLine();
        A = new int[m][n];
        visited = new boolean[m][n];
        matrix = new char[m][n];

        for(int i=0; i<m; i++){
            String s = sc.nextLine();
            int j=0;
            for(char c: s.toCharArray()){
                matrix[i][j] = c;
                if(c == '.') A[i][j] = 1;
                else if(c == 'A') {
                    A[i][j] = 1;
                    start = new Node(i,j, 0);
                } else if(c == 'B') {
                    A[i][j] = 1;
                    end = new Node(i,j, 0);
                }
                j++;
            }
        }
        int result = labyrinth();
        if(result == -1) {
            System.out.println("NO");
            return;
        }
        System.out.println("YES");
        System.out.println(result);
        System.out.println(path);
    }

    public static int labyrinth(){
        Queue<Node> q = new LinkedList<>();

        q.add(start);

        while(!q.isEmpty()){
            Node node = q.poll();
            if(node.equals(end)) {
                path = node.s;
                return node.level;
            }
            for(int[] move : moves) {
                int r = node.r + move[0], c = node.c + move[1];
                if (checkNode(r, c) && !visited[r][c]) {
                    q.add(new Node(r, c, node.level +1,node.s, (char)move[2]));
                    visited[r][c] = true;
                }
            }
        }

        return -1;
    }

    public static boolean checkNode(int r, int c){
        if(r<0 || r>=m) return false;
        if(c<0 || c>=n) return false;
        if (A[r][c] == 0) return false;
        return true;
    }

    public static class Node {
        int r;
        int c;
        String s;
        int level;
        public Node(int r, int c, int level){
            this.r = r;
            this.c = c;
            this.level = level;
            this.s = "";
        }
        public Node(int r, int c, int level, String path, char direction){
            this.r = r;
            this.c = c;
            this.level = level;
            this.s = path + direction;
        }
        public boolean equals(Node node) {
            return (r == node.r && c == node.c);
        }
    }

}
