package um.edu.uy.tads;

public class MyList<K extends Comparable<K>, T> {
    private MyNode<K, T> root;
    private int size;

    public MyList() {
        root = null;
        size = 0;
    }

    public T get(K key) {
        MyNode<K, T> temp = this.root;
        while (temp != null) {
            int cmp = key.compareTo(temp.getKey());
            if (cmp == 0) return temp.getData();
            else if (cmp < 0) temp = temp.getLeftchild();
            else temp = temp.getRightchild();
        }
        return null;
    }

    public MyNode<K, T> getNode(K key) {
        MyNode<K, T> temp = this.root;
        while (temp != null) {
            int cmp = key.compareTo(temp.getKey());
            if (cmp == 0) return temp;
            else if (cmp < 0) temp = temp.getLeftchild();
            else temp = temp.getRightchild();
        }
        return null;
    }

    public T getNumber(Integer number) {
        MyNode<K, T> temp = this.root;
        if (number < this.size) {
            for (int i = 0; i < number; i++) {
                if (temp != null) temp = temp.getRightchild();
            }
            return temp != null ? temp.getData() : null;
        }
        return null;
    }

    public void insert(K key, T data) {
        if (this.root==null) {
            this.root=new MyNode<>(key,data);
        } else {
            this.root.insert(key, data);
        }
        this.size++;
    }

    public void delete(K key) {
        if (this.root != null) {
            if (this.root.getKey().compareTo(key) == 0) {
                this.root = this.root.getRightchild(); // elimina raíz
            } else {
                this.root.delete(key); // delega al hijo
            }
            this.size--;
        }
    }

    public boolean pertenece(K key) {
        MyNode<K, T> temp = this.root;
        while (temp != null) {
            int cmp = key.compareTo(temp.getKey());
            if (cmp == 0) return true;
            else if (cmp < 0) temp = temp.getLeftchild();
            else temp = temp.getRightchild();
        }
        return false;
    }

    public void setRoot(MyNode<K, T> root) {
        this.root = root;
    }

    public MyNode<K, T> getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }

}