public class MyBST implements BSTInterface{
    private Node root;

    public MyBST() {
    }

    public MyBST(Integer x) {
        this.root = new Node(x);
    }

    //in-order asc
    public void LNR(Node x) {
        if (x != null) {
            LNR(x.left);
            System.out.print(x.key + " ");
            LNR(x.right);
        }
    }

    //in-order desc
    public void RNL(Node x) {
        if (x != null) {
            RNL(x.right);
            System.out.print(x.key + " ");
            RNL(x.left);
        }
    }

    //pre-order
    public void NLR(Node x) {
        if (x != null) {
            System.out.print(x.key + " ");
            NLR(x.left);
            NLR(x.right);
        }
    }

    public void LRN(Node x) {
        if (x != null) {
            LRN(x.left);
            LRN(x.right);
            System.out.print(x.key + " ");
        }
    }
    

    private Node insert(Node n, Integer key) {
        if (n == null) {
            return new Node(key);
        }

        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = insert(n.left, key);
        }
        if (cmp > 0) {
            n.right = insert(n.right, key);
        }
        return n;
    }

    private Node search(Node n, Integer key) {
        if (n == null) {
            return null;
        }

        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            return search(n.left, key);
        }
        if (cmp > 0) {
            return search(n.right, key);
        }
        return n;
    }

    //4
    public boolean contains(Integer key) {
        return search(root, key) != null;
    }
    

    private Node min(Node n) {
        if (n == null) {
            return null;
        }

        if (n.left == null) {
            return n;
        }
        return min(n.left);
    }
    
    private Node max(Node x) {
        if (x == null) {
            return null;
        }
        
        while (x.right != null) {
            x = x.right;
        }
        
        return x;
    }
    

    private Node deleteMin(Node n) {
        if (n.left == null) {
            return n.right;
        }
        n.left = deleteMin(n.left);
        return n;
    }

    //5
    public void deleteMax() {
        if (root != null) {
            root = deleteMax(root);
        }
    }
    
    private Node deleteMax(Node node) {
        if (node == null) {
            return null;
        }
    
        if (node.right == null) {
            // Node with the maximum key found
            return node.left;
        }
    
        node.right = deleteMax(node.right);
        return node;
    }
    

    private Node delete(Node n, Integer k) {
        if (n == null) {
            return null;
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = delete(n.left, k);
        } else {
            if (cmp > 0) {
                n.right = delete(n.right, k);
            } else {
                // k = n.key
                // node with only one child or no child
                if (n.right == null) {
                    return n.left;
                }
                if (n.left == null) {
                    return n.right;
                }

                // node with two children: Get the successor (smallest in the right subtree)
                Node t = n;

                n = min(t.right);

                // Delete the successor
                n.right = deleteMin(t.right);

                // re-link left subtree to the node which was replaced
                n.left = t.left;
            }
        }
        return n;
    }

    //6
    public void delete_pre(Integer key) {
        root = deletePredecessor(root, key);
    }
    
    private Node deletePredecessor(Node node, Integer key) {
        if (node == null) {
            return null; //the key is not found in the tree.
        }
    
        int cmp = key.compareTo(node.key);
    
        if (cmp < 0) {
            //the key to be deleted is in the left subtree.
            node.left = deletePredecessor(node.left, key);
        } else if (cmp > 0) {
            //the key to be deleted is in the right subtree.
            node.right = deletePredecessor(node.right, key);
        } else {
            //key to delete is found in this node.
            if (node.left == null) {
                // Node has no or only right child.
                return node.right;
            } else if (node.right == null) {
                //node has only left child.
                return node.left;
            } else {
                //node has both left and right children.
                Node predecessor = findPredecessor(node.left);
                node.key = predecessor.key;
                node.left = deletePredecessor(node.left, predecessor.key);
            }
        }
    
        return node;
    }
    
    private Node findPredecessor(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    

    //7
    public int height() {
        return calculateHeight(root);
    }
    
    private int calculateHeight(Node node) {
        if (node == null) {
            return -1; // Height of an empty tree is -1
        } else {
            int leftHeight = calculateHeight(node.left);
            int rightHeight = calculateHeight(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    //8
    public Integer sum(Node x) {
        return calculateSubtreeSum(x);
    }
    
    private Integer calculateSubtreeSum(Node x) {
        if (x == null) {
            return 0;
        }

        int leftSum = calculateSubtreeSum(x.left);
        int rightSum = calculateSubtreeSum(x.right);

        return x.key + leftSum + rightSum;
    }
    
    public Integer sum() {
        return calculateBSTSum(root);
    }
    
    private Integer calculateBSTSum(Node x) {
        return calculateSubtreeSum(x);
    }
    
    //9
    public Integer sumEven() {
        return calculateSumEven(root);
    }
    
    private Integer calculateSumEven(Node node) {
        if (node == null) {
            return 0;
        }
    
        int sum = 0;
    
        if (node.key % 2 == 0) {
            sum += node.key;
        }

        sum += calculateSumEven(node.left);
        sum += calculateSumEven(node.right);
    
        return sum;
    }
    

    //3
    public void descendingOrder(Node x) {
        if (x != null) {
            descendingOrder(x.right);
            System.out.print(x.key + " ");
            descendingOrder(x.left);
        }
    }

    public void printDescendingOrder() {
        descendingOrder(root);
        System.out.println();
    }

    @Override
    public Node insert(Integer x) {
        return root = insert(root, x);
    }

    @Override
    public Node search(Integer x) {
        return search(root, x);
    }

    @Override
    public Node delete(Integer x) {
        return root = delete(root, x);
    }

    @Override
    public void traverse() {
        RNL(root);
    }

    @Override
    public int count(Node n) {
        if (n == null)
            return 0;

        return 1 + count(n.right)+ count(n.left);
    }

    @Override
    public int minOdd(Node n) {
        if (n == null)
            return -1;
        if (min(n).key % 2 == 1)
            return min(n).key;
        Node tmp = n,tmp1 = n;
        int min = -1;
        while (tmp.left != null){
            if (tmp.left.key % 2 == 1)
                min = tmp.left.key;
            tmp = tmp.left;
        }
        while (tmp1.left != null){
            if (tmp.right.key % 2 == 1 && tmp.right.key < min && tmp.right.key > min(n).key)
                return tmp.right.key;
            tmp1 = tmp1.left;
        }
        return min;
    }

    //10
    @Override
    public int countLeaves(Node n) {
        if (n == null)
            return 0;
        if (n.left == null && n.right == null){
            return 1;
        }
        return countLeaves(n.right)+ countLeaves(n.left);
    }

    //11
    public int sumEvenKeysAtLeaves() {
        return calculateSumEvenKeysAtLeaves(root);
    }
    
    private int calculateSumEvenKeysAtLeaves(Node node) {
        if (node == null) {
            return 0; // Base case: sum of an empty subtree is 0
        }
    
        if (node.left == null && node.right == null) {
            // Check if the current node is a leaf (no children)
            if (node.key % 2 == 0) {
                return node.key; // Return the even key for the leaf
            } else {
                return 0; // Return 0 for odd keys in leaves
            }
        }
    
        // Recursively calculate the sum of even keys in the left and right subtrees
        int leftSum = calculateSumEvenKeysAtLeaves(node.left);
        int rightSum = calculateSumEvenKeysAtLeaves(node.right);
    
        return leftSum + rightSum;
    }
    
}