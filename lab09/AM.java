import java.util.Scanner;

public class AM extends Graph {
    int[][] am;

    public AM() {
    }

    @Override
    public void read(){
            Scanner sc = new Scanner(System.in);
            V = sc.nextInt();
            E = sc.nextInt();
            am = new int[V][V];
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    am[u][v] = sc.nextInt();
                }
            }
            sc.close();
      

    }

    @Override
    public void print() {
        System.out.println();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.printf("%-3d", am[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public boolean isAdjacent(int u, int v) {
        return am[u][v] > 0;
    }

}
