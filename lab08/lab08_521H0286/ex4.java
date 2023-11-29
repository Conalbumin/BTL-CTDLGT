import java.util.Arrays;
import java.util.PriorityQueue;
public class ex4 {
    public static void main(String[] args) {
        PriorityQueue<Person> enqueueHeap = new PriorityQueue<>(
            new PersonComparator()
        ); 
        PriorityQueue<Person> dequeueHeap = new PriorityQueue<>(
            new PersonComparator()
        ); 

        // ex3
        MinHeap minHeap = new MinHeap();
        MaxHeap maxHeap = new MaxHeap();
        int[] arr = {15, 23, 18, 63, 21, 35, 36, 21, 66, 12, 42, 35, 75, 23, 64, 78, 39};

        minHeap.buildHeapFast(arr);
        System.out.print("Min Heap: ");
        minHeap.printHeap();
        maxHeap.buildHeapFast(arr);
        System.out.print("Max Heap: ");
        maxHeap.printHeap();
        System.out.println(minHeap.extractMin());
        System.out.println(maxHeap.extractMax());

        minHeap.heapSort(arr);
        System.out.println(Arrays.toString(arr));
        maxHeap.heapSort(arr);

        // enqueue
        System.out.println(Arrays.toString(arr));
        enqueueHeap.offer(new Person("Alex", 3));
        enqueueHeap.offer(new Person("Bob", 2));
        enqueueHeap.offer(new Person("David", 6));
        enqueueHeap.offer(new Person("Susan", 1));
        dequeueHeap.offer(enqueueHeap.poll());
        enqueueHeap.offer(new Person("Mike", 5));
        enqueueHeap.offer(new Person("Kevin", 4));
        dequeueHeap.offer(enqueueHeap.poll());
        dequeueHeap.offer(enqueueHeap.poll());
        enqueueHeap.offer(new Person("Helen", 0));
        enqueueHeap.offer(new Person("Paul", 8));
        enqueueHeap.offer(new Person("Iris", 7));
        dequeueHeap.offer(enqueueHeap.poll());
       
        System.out.println("List persons will be dequeued:");
        for (Person p : dequeueHeap) {
            System.out.print(p.toString());
        }
        
    }
}
