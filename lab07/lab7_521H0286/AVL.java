public class AVL {
    public Node root;

    public int height(Node node) {
        if (node == null)
            return -1;
        return node.height;
    }

    private int checkBalance(Node x) {
        return height(x.left) - height(x.right);
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node balance(Node x) {
        if (checkBalance(x) < -1) {
            if (checkBalance(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (checkBalance(x) > 1) {
            if (checkBalance(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    public Node insert(Node node, Integer key) {
        if (node == null) {
            return new Node(key);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
        } else {
            // Duplicate keys are not allowed, do nothing
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public void insert(Integer key) {
        root = insert(root, key);
    }

    public Node delete(Node node, Integer key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            // Found the node to delete
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // Node has two children, get the in-order successor
                Node successor = min(node.right);
                node.key = successor.key;
                node.right = delete(node.right, successor.key);
            }
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public void delete(Integer key) {
        root = delete(root, key);
    }

    private Node min(Node node) {
        if (node == null) {
            return null;
        }

        if (node.left == null) {
            return node;
        }

        return min(node.left);
    }

    public static void main(String[] args) {
        AVL avlTree = new AVL();

        // Insert elements into the AVL tree
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);

        // Delete an element
        avlTree.delete(20);
    }
}
