import java.util.NoSuchElementException;

public class MaxHeap {
    int[] heap;
    int heapSize;
    int maxSize;

    public MaxHeap(int capacity) {
        heapSize = 0;
        this.maxSize = capacity + 1;
        heap = new int[maxSize];
        heap[0] = -1;
    }

    public MaxHeap() {
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return i * 2;
    }

    private int right(int i) {
        return i * 2 + 1;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void insert(int key) {
        if (heapSize == maxSize) {
            throw new NoSuchElementException("Overflow exception");
        }
        heapSize += 1;
        heap[heapSize] = key;
        shiftUp(heapSize);
    }

    private void shiftUp(int i) {
        while (i > 1 && heap[parent(i)] < heap[i]) {
            swap(parent(i), i); 
            i = parent(i);
        }
    }

    public int extractMax() {
        if (heapSize == 0) {
            throw new NoSuchElementException("Underflow exception");
        }
        int max = heap[1];
        heap[1] = heap[heapSize];
        heapSize -= 1;
        shiftDown(1);
        return max;
    }

    private void shiftDown(int i) {
        while (i <= heapSize) {
            int max = heap[i];
            int max_id = i;
            if (left(i) <= heapSize && max < heap[left(i)]) {
                max = heap[left(i)];
                max_id = left(i);
            }
            if (right(i) <= heapSize && max < heap[right(i)]) {
                max = heap[right(i)];
                max_id = right(i);
            }
            if (max_id != i) {
                swap(max_id, i);
                i = max_id;
            } else {
                break;
            }
        }
    }

    public static void heapSort(int[] arr) {
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.buildHeapFast(arr);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = maxHeap.extractMax();
        }
    }

    public void buildHeapFast(int[] arr) {
        this.heapSize = arr.length;
        this.maxSize = arr.length + 1;
        this.heap = new int[maxSize];

        for (int i = 0; i < arr.length; i++) {
            this.heap[i + 1] = arr[i];
        }

        for (int i = heapSize / 2; i >= 1; i--) {
            shiftDown(i);
        }
    }
    
    public void printHeap() {
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

}