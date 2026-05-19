package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoNoExiste;

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
            if (key.compareTo(temp.getKey()) == 0) return temp.getData();
            temp = temp.getRightchild();
        }
        return null;
    }

    public MyNode<K, T> getNode(K key) {
        MyNode<K, T> temp = this.root;
        while (temp != null) {
            if (key.compareTo(temp.getKey()) == 0) return temp;
            temp = temp.getRightchild();
        }
        throw new ElementoNoExiste();
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

    public K getKeyNumber(Integer number) {
        MyNode<K, T> temp = this.root;
        if (number < this.size) {
            for (int i = 0; i < number; i++) {
                if (temp != null) temp = temp.getRightchild();
            }
            return temp != null ? temp.getKey() : null;
        }
        return null;
    }

    public void insert(K key, T data) {
        MyNode<K, T> existing = this.root;
        while (existing != null) {
            if (key.compareTo(existing.getKey()) == 0) {
                existing.setData(data);
                return;
            }
            existing = existing.getRightchild();
        }
        if (this.root == null) {
            this.root = new MyNode<>(key, data);
        } else {
            this.root.insert(key, data);
        }
        this.size++;
    }

    public void delete(K key) {
        if (this.root != null) {
            if (this.root.getKey().compareTo(key) == 0) {
                this.root = this.root.getRightchild();
            } else {
                this.root.delete(key);
            }
            this.size--;
        }
    }

    public boolean pertenece(K key) {
        MyNode<K, T> temp = this.root;
        while (temp != null) {
            if (key.compareTo(temp.getKey()) == 0) return true;
            temp = temp.getRightchild();
        }
        return false;
    }

    public void replace(K oldKey, K newKey, T newData) {
        MyNode<K, T> actual = this.root;
        while (actual != null) {
            if (actual.getKey().equals(oldKey)) {
                actual.setKey(newKey);
                actual.setData(newData);
                return;
            }
            actual = actual.getRightchild();
        }
        throw new ElementoNoExiste();
    }

    public void swap(int i, int j) {
        if (i == j) return;
        MyNode<K, T> nodeI = null, nodeJ = null;
        MyNode<K, T> temp = this.root;
        int idx = 0;
        while (temp != null) {
            if (idx == i) nodeI = temp;
            if (idx == j) nodeJ = temp;
            temp = temp.getRightchild();
            idx++;
        }
        if (nodeI == null || nodeJ == null) return;
        K tmpKey = nodeI.getKey();
        T tmpData = nodeI.getData();
        nodeI.setKey(nodeJ.getKey());
        nodeI.setData(nodeJ.getData());
        nodeJ.setKey(tmpKey);
        nodeJ.setData(tmpData);
    }

    public void setRoot(MyNode<K, T> root) { this.root = root; }
    public MyNode<K, T> getRoot() { return this.root; }
    public int getSize() { return this.size; }

    public void bubbleSort() {
        int n = this.getSize();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (this.getKeyNumber(j).compareTo(this.getKeyNumber(j + 1)) > 0) {
                    this.swap(j, j + 1);
                }
            }
        }
    }
}
