package um.edu.uy.tads;


import java.util.Arrays;

public class HeapImpl<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private boolean isMinHeap;

    @SuppressWarnings("unchecked")
    public HeapImpl(int capacity, boolean isMinHeap) {
        heap = (T[]) new Comparable[capacity];
        this.size = 0;
        this.isMinHeap = isMinHeap;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T item) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = item;
        upHeap(size);
        size++;
    }

    public T delete() {
        if (isEmpty()) return null;
        T result = heap[0];
        heap[0] = heap[size - 1];
        size--;
        downHeap(0);
        return result;
    }

    public T get() {
        return isEmpty() ? null : heap[0];
    }

    private void upHeap(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (compare(heap[idx], heap[parent])) {
                swap(idx, parent);
                idx = parent;
            } else {
                break;
            }
        }
    }

    private void downHeap(int idx) {
        while (2 * idx + 1 < size) {
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;
            int child = left;
            if (right < size && compare(heap[right], heap[left])) {
                child = right;
            }
            if (compare(heap[child], heap[idx])) {
                swap(child, idx);
                idx = child;
            } else {
                break;
            }
        }
    }

    private boolean compare(T a, T b) {
        return isMinHeap ? a.compareTo(b) < 0 : a.compareTo(b) > 0;
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
