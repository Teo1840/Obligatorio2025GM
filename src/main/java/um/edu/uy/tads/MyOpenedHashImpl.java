package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoNoExiste;

// Tabla hash con encadenamiento (open hashing / chaining).
// Se redimensiona automáticamente cuando el factor de carga supera 0.75.
// La capacidad siempre es un número primo para distribuir mejor las claves.
public class MyOpenedHashImpl<K extends Comparable<K>, V> implements MyHash<K, V> {
    private MyList<K, V>[] tabla;
    private int capacidad;
    private int size;
    private static final float FACTOR_CARGA = 0.75f;

    public MyOpenedHashImpl() {
        this(101);
    }

    @SuppressWarnings("unchecked")
    public MyOpenedHashImpl(int capacidad) {
        this.capacidad = siguientePrimo(capacidad);
        this.tabla = new MyList[this.capacidad];
        this.size = 0;
        for (int i = 0; i < this.capacidad; i++) tabla[i] = new MyList<>();
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacidad);
    }

    @Override
    public void insert(K key, V value) {
        if ((float)(size + 1) / capacidad > FACTOR_CARGA) rehashing();
        int idx = hash(key);
        if (tabla[idx].pertenece(key)) {
            tabla[idx].replace(key, key, value);
        } else {
            tabla[idx].insert(key, value);
            size++;
        }
    }

    @Override
    public V get(K key) throws ElementoNoExiste {
        int idx = hash(key);
        if (!tabla[idx].pertenece(key)) throw new ElementoNoExiste();
        return tabla[idx].get(key);
    }

    @Override
    public void replace(K oldKey, K newKey, V newValue) throws ElementoNoExiste {
        int idx = hash(oldKey);
        if (!tabla[idx].pertenece(oldKey)) throw new ElementoNoExiste();
        tabla[idx].replace(oldKey, newKey, newValue);
    }

    @Override
    public boolean pertenece(K key) {
        return tabla[hash(key)].pertenece(key);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public K getKey(int index) {
        int count = 0;
        for (MyList<K, V> lista : tabla) {
            if (index < count + lista.getSize()) return lista.getKeyNumber(index - count);
            count += lista.getSize();
        }
        return null;
    }

    @Override
    public V getValue(int index) {
        int count = 0;
        for (MyList<K, V> lista : tabla) {
            if (index < count + lista.getSize()) return lista.getNumber(index - count);
            count += lista.getSize();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void rehashing() {
        int nuevaCapacidad = siguientePrimo(capacidad * 2);
        MyList<K, V>[] nuevaTabla = new MyList[nuevaCapacidad];
        for (int i = 0; i < nuevaCapacidad; i++) nuevaTabla[i] = new MyList<>();
        for (MyList<K, V> lista : tabla) {
            for (int i = 0; i < lista.getSize(); i++) {
                K k = lista.getKeyNumber(i);
                V v = lista.getNumber(i);
                int idx = Math.abs(k.hashCode() % nuevaCapacidad);
                nuevaTabla[idx].insert(k, v);
            }
        }
        this.tabla = nuevaTabla;
        this.capacidad = nuevaCapacidad;
    }

    private int siguientePrimo(int n) {
        int candidate = n + 1;
        while (!esPrimo(candidate)) candidate++;
        return candidate;
    }

    private boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }
}
