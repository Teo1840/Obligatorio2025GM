package um.edu.uy.tads;

public class MyNode<K extends Comparable<K>,T> {
    private K key;
    private T data;
    private MyNode<K,T> rightchild;

    MyNode(K key, T data) {
        this.key=key;
        this.data=data;
        this.rightchild=null;
    }

    public boolean find(K key) {
        if (this.key.compareTo(key)==0) {
            return true;
        } else if (this.rightchild!=null) {
            return this.rightchild.find(key);
        }
        return false;
    }

    public T get(K key) {
        if (this.key.compareTo(key) == 0) {
            return this.data;
        } else {
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

    public MyNode<K, T> getRightchild() {
        return rightchild;
    }
    public void setRightchild(MyNode<K, T> rightchild) {
        this.rightchild = rightchild;
    }

}
