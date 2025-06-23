package um.edu.uy.tads;

public class MyNode<K extends Comparable<K>,T> {
    private K key;
    private T data;
    private MyNode<K,T> leftchild;
    private MyNode<K,T> rightchild;

    MyNode(K key, T data) {
        this.key=key;
        this.data=data;
        this.leftchild=null;
        this.rightchild=null;
    }

    public boolean find(K key) {
        if (this.key.compareTo(key)==0) {
            return true;
        } else {
            if (this.leftchild!=null) {
                if (this.leftchild.find(key)) {
                    return true;
                }
            }
            if (this.rightchild!=null) {
                if (this.rightchild.find(key)) {
                    return true;
                }
            }
        }
        return false;
    } //sirve para Lista y Arbol

    public T get(K key) {
        if (this.key.compareTo(key) == 0) {
            return this.data;
        } else {
            if (this.leftchild != null) {
                if (this.leftchild.find(key)) {
                    return this.leftchild.data;
                }
            }
            if (this.rightchild != null) {
                if (this.rightchild.find(key)) {
                    return this.rightchild.data;
                }
            }
        }
        return null;
    }

    public void insert(K key, T data) {
        if (this.rightchild==null) {
            this.rightchild=new MyNode<>(key, data);
        } else {
            this.rightchild.insert(key, data);
        }
    }

    public void delete(K key) {
        if (this.rightchild.key.compareTo(key)==0) {
            if (this.rightchild.rightchild==null) {
                this.rightchild=null;
            } else {
                this.rightchild=this.rightchild.rightchild;
            }
        } else {
            this.rightchild.delete(key);
        }
    }

    public void insertInOrder(K key, T data) {
        if (this.rightchild.key.compareTo(key)<0) {
            MyNode<K,T> temp = this.rightchild;
            this.rightchild=new MyNode<K,T>(key, data);
            this.rightchild.setRightchild(temp);
        }
    }

    public void treeInsert(K key, T data) {
        if (this.key.compareTo(key)<0) {
            if (this.leftchild==null) {
                this.leftchild = new MyNode<>(key, data);
            } else {
                this.leftchild.treeInsert(key,data);
            }
        } else {
            if (this.rightchild==null) {
                this.rightchild = new MyNode<>(key, data);
            } else {
                this.rightchild.treeInsert(key,data);
            }
        }
    }

    public MyNode<K,T> findMinimum() {
        if (this.leftchild==null) {
            return this;
        } else {
            return this.leftchild.findMinimum();
        }
    }
    public MyNode<K,T> findMaximum() {
        if (this.rightchild==null) {
            return this;
        } else {
            return this.rightchild.findMaximum();
        }
    }

    public void treeDelete(K key) {
        if (this.rightchild.key.compareTo(key)==0) { //hijo derecho?
            if (this.rightchild.rightchild==null && this.rightchild.leftchild==null) { //hoja?
                this.rightchild=null;
            } else if (this.rightchild.leftchild==null){
                this.rightchild=this.rightchild.rightchild;
            } else if (this.rightchild.rightchild==null){
                this.rightchild=this.rightchild.leftchild;
            } else {
                MyNode<K,T> temp = this.rightchild.findMinimum();
                this.rightchild.treeDelete(temp.key); //como es una hoja entonces lo borran en la parte de arriba del if
                temp.rightchild=this.rightchild.rightchild;
                temp.leftchild=this.rightchild.leftchild;
                this.rightchild=temp;
            }
        } else if (this.leftchild.key.compareTo(key)==0) { //hijo izquierdo?
            if (this.leftchild.rightchild == null && this.leftchild.leftchild == null) { //hoja?
                this.leftchild = null;
            } else if (this.leftchild.rightchild == null) {
                this.leftchild = this.leftchild.rightchild;
            } else if (this.leftchild.rightchild == null) {
                this.leftchild = this.leftchild.leftchild;
            } else {
                MyNode<K, T> temp = this.leftchild.findMaximum();
                this.leftchild.treeDelete(temp.key); //como es una hoja entonces lo borran en la parte de arriba del if
                temp.rightchild = this.leftchild.rightchild;
                temp.leftchild = this.leftchild.leftchild;
                this.leftchild = temp;
            }
        } else {
            if (this.rightchild.rightchild != null) {
                this.rightchild.delete(key);
            }
            if (this.rightchild.leftchild != null) {
                this.leftchild.delete(key);
            }
        }
    }

    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public MyNode<K, T> getLeftchild() {
        return leftchild;
    }
    public void setLeftchild(MyNode<K, T> leftchild) {
        this.leftchild = leftchild;
    }

    public MyNode<K, T> getRightchild() {
        return rightchild;
    }
    public void setRightchild(MyNode<K, T> rightchild) {
        this.rightchild = rightchild;
    }

}
