package um.edu.uy.tads;

public class MyList<K extends Comparable<K>,T> {
    private MyNode<K,T> root;
    private int size;

    public MyList() {
        root=null;
        size=0;
    }

    public T get(K key) {
        MyNode<K,T> temp = this.root;
        while (temp != null) {
            if (key.compareTo(temp.getKey()) == 0) {
                return temp.getData();
            } else if (key.compareTo(temp.getKey()) < 0) {
                temp = temp.getLeftchild();
            } else {
                temp = temp.getRightchild();
            }
        }
        return null; // No se encontró
    }


    public void insert(K key, T data) {
        if (this.root==null) {
            this.root=new MyNode<>(key,data);
        } else {
            this.root.insert(key, data);
        }
        this.size++;
    }

    public void insertInOrder(K key, T data) {
        if (this.root==null) {
            this.root=new MyNode<>(key,data);
        } else if (this.root.getKey().compareTo(key) < 0) {
            MyNode<K,T> temp = this.root;
            this.root=new MyNode<K,T>(key, data);
            this.root.setRightchild(temp);
        } else {
            this.root.insertInOrder(key, data);
        }
    }

    public void delete(K key) {
        if (this.root.getKey().compareTo(key)==0) { //es la raiz?
            if (this.root.getRightchild()==null) { //hoja?
                this.root = null;
            } else {
                this.root=this.root.getRightchild();
            }
        } else {
            this.root.delete(key); //será el proximo?
        }
        this.size--;
    }

    public boolean pertenece(K key) {
        if (this.root==null) {
            return false;
        } else {
            return this.root.find(key);
        }
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