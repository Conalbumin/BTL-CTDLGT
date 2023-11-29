public class Edge{
    int src;
    int dest;
    int weight;

    public Edge(int u, int v, int w) {
        this.src = u;
        this.dest = v;
        this.weight = w;
    }

    @Override
    public String toString() {
        return String.format("%d%d%d",src,dest,weight);
    }
    
}
