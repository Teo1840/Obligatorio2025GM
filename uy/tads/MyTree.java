package um.edu.uy.tads;

public class MyTree<K extends Comparable<K>, T> {
    private MyNode<K, T> root;
    private int size;

    public MyTree() {
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
        if (this.root == null) {
            this.root = new MyNode<>(key, data);
            this.size++;
            return;
        }

        MyNode<K, T> temp = this.root;
        while (true) {
            int cmp = key.compareTo(temp.getKey());
            if (cmp == 0) {
                temp.setData(data); // reemplaza si ya existe
                return;
            } else if (cmp < 0) {
                if (temp.getLeftchild() == null) {
                    temp.setLeftchild(new MyNode<>(key, data));
                    this.size++;
                    return;
                }
                temp = temp.getLeftchild();
            } else {
                if (temp.getRightchild() == null) {
                    temp.setRightchild(new MyNode<>(key, data));
                    this.size++;
                    return;
                }
                temp = temp.getRightchild();
            }
        }
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
