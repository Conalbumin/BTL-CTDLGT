public class Test {
    public static void main(String[] args) {
        MyBST tree = new MyBST();

        int[] nums = new int[] {58, 88, 86, 56, 49, 29, 65, 100, 39, 64};
        for (int x: nums) {
            tree.insert(x);
            System.out.print(x + " ");
        }
        System.out.println();

        tree.traverse();
        System.out.println();

        Node n = tree.search(39);
        System.out.println(n.key);

        tree.delete(58);
        tree.traverse();
        System.out.println();

        tree.delete(86);
        tree.traverse();
        System.out.println();
    }
}
