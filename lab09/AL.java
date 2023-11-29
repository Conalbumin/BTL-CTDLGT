import java.util.LinkedList;
import java.util.Scanner;

public class AL extends Graph {

    LinkedList<Edge>[] al;
    
    @Override
    public void read() {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();
        al = new LinkedList[V];
        for(int i = 0; i < V; i++){
            al[i] = new LinkedList<>();
        }
        int u,v,w;
        for(int i = 0; i < E; i++){
            u = sc.nextInt();
            v = sc.nextInt();
            w = sc.nextInt();
            al[u].add(new Edge(u, v, w));
            al[v].add(new Edge(u, v, w));
        }
        sc.close();
    }

    @Override
    public void print() {
        System.out.println(V +", " + E);
        for(int i = 0; i < V; i++){
            System.out.print(i+ ": ");
            System.out.println(al[i]);
        }
    }

    @Override
    public boolean isAdjacent(int u, int v) {
        for(Edge e : al[u]){
            if(e.dest == V){
                return true;
            }
        }
        return false;
    }
    
}
