package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoNoExiste;
import um.edu.uy.exeptions.ElementoYaExiste;

public class MyHashImpl<T> implements MyHash<T>{
    MyNode[] hashtable;
    int size;
    int filledBuckets;
    boolean quadraticColition;

    MyHashImpl(int size, boolean quadraticColition) {
        this.hashtable=new MyNode[size];
        this.size=size;
        this.filledBuckets=0;
        this.quadraticColition=quadraticColition;
    }

    public void reestructurar() {
        int nextPrime = 2*this.size+1;
        int i=3;
        while (i<=Math.sqrt(nextPrime)) {
            if (nextPrime%i==0) {
                nextPrime=nextPrime+2;
                i=3;
            }
            i++;
        }
        this.size = nextPrime;
        MyNode[] temp = this.hashtable;
        this.hashtable = new MyNode[nextPrime];
        for (MyNode bucket : temp) {    //reinserto todo
            if (quadraticColition) {
                for (int j = 0; j < this.size; j++) {
                    if (hashtable[(bucket.getKey().hashCode() + j*j) % this.size] == null) {
                        hashtable[(bucket.getKey().hashCode() + j*j) % this.size] = new MyNode(bucket.getKey(),bucket.getData());
                        return;
                    }
                }
            } else {
                for (int j = 0; j < this.size; j++) {
                    if (hashtable[(bucket.getKey().hashCode() + j) % this.size] == null) {
                        hashtable[(bucket.getKey().hashCode() + j) % this.size] = new MyNode(bucket.getKey(),bucket.getData());
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void insertar(String clave, Object valor) throws ElementoYaExiste {
        if (this.filledBuckets==this.size) {
            this.reestructurar();
        } else if (this.pertenece(clave)){
            throw new ElementoYaExiste();
        } else {
            if (quadraticColition) {
                for (int j = 0; j < this.size; j++) {
                    if (hashtable[(clave.hashCode() + j*j) % this.size] == null) {
                        hashtable[(clave.hashCode() + j*j) % this.size] = new MyNode(clave,valor);
                        return;
                    }
                }
            } else {
                for (int j = 0; j < this.size; j++) {
                    if (hashtable[(clave.hashCode() + j) % this.size] == null) {
                        hashtable[(clave.hashCode() + j) % this.size] = new MyNode(clave,valor);
                        return;
                    }
                }
            }
        }
    }
    @Override
    public boolean pertenece(String clave) {
        if (quadraticColition) {
            for (int j = 0; j < this.size; j++) {
                if (hashtable[(clave.hashCode() + j*j) % this.size].getKey() == clave) {
                    return true;
                }
            }
        } else {
            for (int j = 0; j < this.size; j++) {
                if (hashtable[(clave.hashCode() + j) % this.size].getKey() == clave) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void borrar(String clave) throws ElementoNoExiste {
        if (this.pertenece(clave)) {
            if (quadraticColition) {
                for (int j = 0; j < this.size; j++) {
                    if (hashtable[(clave.hashCode() + j*j) % this.size].getKey() == clave) {
                        hashtable[(clave.hashCode() + j*j) % this.size] = null;
                        return;
                    }
                }
            } else {
                for (int j = 0; j < this.size; j++) {
                    if (hashtable[(clave.hashCode() + j) % this.size].getKey() == clave) {
                        hashtable[(clave.hashCode() + j) % this.size] = null;
                        return;
                    }
                }
            }
        } else {
            throw new ElementoNoExiste();
        }
    }
}
