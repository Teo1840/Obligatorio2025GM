package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoNoExiste;

public interface MyHash<K, V> {

    void insert(K key, V value);

    V get(K key) throws ElementoNoExiste;

    void replace(K oldKey, K newKey, V newValue) throws ElementoNoExiste;

    int getSize();

    K getKey(int index);

    V getValue(int index);

    boolean pertenece(K key);
}
