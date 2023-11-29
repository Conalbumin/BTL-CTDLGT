import java.util.LinkedList;
import java.util.Scanner;

public class EL extends Graph {

    LinkedList<Edge> el;
    @Override
    public void read() {
         Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();
        el = new LinkedList<>();
        int u,v,w;
        for(int i = 0; i < E; i++){
            u = sc.nextInt();
            v = sc.nextInt();
            w = sc.nextInt();
            el.add(new Edge(u, v, w));
        }
        sc.close();
    }

    @Override
    public void print() {
        System.out.println(V + ", "+ E);
        for(Edge e : el){
            System.out.println(e);
        }
    }

    @Override
    public boolean isAdjacent(int u, int v) {
        for(Edge e : el){
            if((e.src == u && e.dest == v) || (e.src == v && e.dest == u)){
                return true;
            }
        }
        return false;
    }
}
