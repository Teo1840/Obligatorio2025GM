package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoNoExiste;

public class MyHashImpl<K extends Comparable<K>, V> implements MyHash<K, V> {
    private final int CAPACIDAD = 100;
    private MyList<K, V>[] tabla;

    @SuppressWarnings("unchecked")
    public MyHashImpl() {
        tabla = new MyList[CAPACIDAD];
        for (int i = 0; i < CAPACIDAD; i++) {
            tabla[i] = new MyList<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % CAPACIDAD);
    }

    @Override
    public void insert(K key, V value) {
        int index = hash(key);
        if (tabla[index].pertenece(key)) {
            tabla[index].replace(key, key, value);
        } else {
            tabla[index].insert(key, value);
        }
    }

    @Override
    public V get(K key) throws ElementoNoExiste {
        int index = hash(key);
        if (!tabla[index].pertenece(key)) throw new ElementoNoExiste();
        return tabla[index].get(key);
    }

    @Override
    public void replace(K oldKey, K newKey, V newValue) throws ElementoNoExiste {
        int index = hash(oldKey);
        if (!tabla[index].pertenece(oldKey)) throw new ElementoNoExiste();
        tabla[index].replace(oldKey, newKey, newValue);
    }

    @Override
    public int getSize() {
        int total = 0;
        for (MyList<K, V> lista : tabla) {
            total += lista.getSize();
        }
        return total;
    }

    @Override
    public K getKey(int index) {
        int count = 0;
        for (MyList<K, V> lista : tabla) {
            if (index < count + lista.getSize()) {
                return lista.getKeyNumber(index - count);
            }
            count += lista.getSize();
        }
        return null;
    }

    @Override
    public V getValue(int index) {
        int count = 0;
        for (MyList<K, V> lista : tabla) {
            if (index < count + lista.getSize()) {
                return lista.getNumber(index - count);
            }
            count += lista.getSize();
        }
        return null;
    }

    @Override
    public boolean pertenece(K key) {
        int index = hash(key);
        if (tabla[index] == null) return false;
        return tabla[index].pertenece(key);
    }

}
