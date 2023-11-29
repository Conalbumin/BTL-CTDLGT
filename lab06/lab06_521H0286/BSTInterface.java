public interface BSTInterface {
    Node insert(Integer x);

    Node search(Integer x);

    Node delete(Integer x);

    void traverse();

    int count(Node n);

    int minOdd(Node n);

    int countLeaves(Node n);
}
