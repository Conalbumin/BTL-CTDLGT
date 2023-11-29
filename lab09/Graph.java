
public abstract class Graph {
    protected int V;
    protected int E;

    public abstract void read();
    public abstract void print();
    public abstract boolean isAdjacent(int u, int v);
    
}