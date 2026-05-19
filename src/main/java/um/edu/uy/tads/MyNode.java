package um.edu.uy.tads;

public class MyNode<K extends Comparable<K>, T> {
    private K key;
    private T data;
    private MyNode<K, T> leftchild;
    private MyNode<K, T> rightchild;

    MyNode(K key, T data) {
        this.key = key;
        this.data = data;
        this.leftchild = null;
        this.rightchild = null;
    }

    public boolean find(K key) {
        if (this.key.compareTo(key) == 0) return true;
        if (this.rightchild != null) return this.rightchild.find(key);
        return false;
    }

    public T get(K key) {
        if (this.key.compareTo(key) == 0) return this.data;
        if (this.rightchild != null && this.rightchild.find(key)) return this.rightchild.data;
        return null;
    }

    public void insert(K key, T data) {
        if (this.rightchild == null) {
            this.rightchild = new MyNode<>(key, data);
        } else {
            this.rightchild.insert(key, data);
        }
    }

    public void delete(K key) {
        if (this.rightchild == null) return;
        if (this.rightchild.key.compareTo(key) == 0) {
            if (this.rightchild.rightchild == null) {
                this.rightchild = null;
            } else {
                this.rightchild = this.rightchild.rightchild;
            }
        } else {
            this.rightchild.delete(key);
        }
    }

    public K getKey() { return key; }
    public void setKey(K key) { this.key = key; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public MyNode<K, T> getLeftchild() { return leftchild; }
    public void setLeftchild(MyNode<K, T> leftchild) { this.leftchild = leftchild; }

    public MyNode<K, T> getRightchild() { return rightchild; }
    public void setRightchild(MyNode<K, T> rightchild) { this.rightchild = rightchild; }
}
