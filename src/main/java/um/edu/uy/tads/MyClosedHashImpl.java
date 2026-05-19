package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoNoExiste;

// Tabla hash con direccionamiento cerrado (closed hashing / open addressing).
// Resuelve colisiones por sondeo lineal (linear=true) o cuadrático (linear=false).
// Se redimensiona cuando el factor de carga supera 0.5 (más bajo que chaining
// porque el clustering degrada más rápido en direccionamiento abierto).
public class MyClosedHashImpl<K extends Comparable<K>, V> implements MyHash<K, V> {
    private Object[] keys;
    private Object[] values;
    private boolean[] deleted;
    private int capacidad;
    private int size;
    private final boolean linear;
    private static final float FACTOR_CARGA = 0.5f;

    public MyClosedHashImpl() {
        this(101, true);
    }

    public MyClosedHashImpl(int capacidad, boolean linear) {
        this.capacidad = siguientePrimo(capacidad);
        this.keys = new Object[this.capacidad];
        this.values = new Object[this.capacidad];
        this.deleted = new boolean[this.capacidad];
        this.size = 0;
        this.linear = linear;
    }

    private int sondeo(int keyHash, int i) {
        int base = Math.abs(keyHash % capacidad);
        return linear ? (base + i) % capacidad : (base + i * i) % capacidad;
    }

    @Override
    public void insert(K key, V value) {
        if ((float)(size + 1) / capacidad > FACTOR_CARGA) rehashing();
        int keyHash = key.hashCode();
        for (int i = 0; i < capacidad; i++) {
            int pos = sondeo(keyHash, i);
            if (keys[pos] == null || deleted[pos]) {
                keys[pos] = key;
                values[pos] = value;
                deleted[pos] = false;
                size++;
                return;
            }
            if (keys[pos].equals(key)) {
                values[pos] = value;
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws ElementoNoExiste {
        int keyHash = key.hashCode();
        for (int i = 0; i < capacidad; i++) {
            int pos = sondeo(keyHash, i);
            if (keys[pos] == null) throw new ElementoNoExiste();
            if (!deleted[pos] && keys[pos].equals(key)) return (V) values[pos];
        }
        throw new ElementoNoExiste();
    }

    @Override
    public void replace(K oldKey, K newKey, V newValue) throws ElementoNoExiste {
        int keyHash = oldKey.hashCode();
        for (int i = 0; i < capacidad; i++) {
            int pos = sondeo(keyHash, i);
            if (keys[pos] == null) throw new ElementoNoExiste();
            if (!deleted[pos] && keys[pos].equals(oldKey)) {
                keys[pos] = newKey;
                values[pos] = newValue;
                return;
            }
        }
        throw new ElementoNoExiste();
    }

    @Override
    public boolean pertenece(K key) {
        try { get(key); return true; } catch (ElementoNoExiste e) { return false; }
    }

    @Override
    public int getSize() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public K getKey(int index) {
        int count = 0;
        for (int i = 0; i < capacidad; i++) {
            if (keys[i] != null && !deleted[i]) {
                if (count == index) return (K) keys[i];
                count++;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getValue(int index) {
        int count = 0;
        for (int i = 0; i < capacidad; i++) {
            if (keys[i] != null && !deleted[i]) {
                if (count == index) return (V) values[i];
                count++;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void rehashing() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        boolean[] oldDeleted = deleted;
        int oldCapacidad = capacidad;
        capacidad = siguientePrimo(capacidad * 2);
        keys = new Object[capacidad];
        values = new Object[capacidad];
        deleted = new boolean[capacidad];
        size = 0;
        for (int i = 0; i < oldCapacidad; i++) {
            if (oldKeys[i] != null && !oldDeleted[i]) {
                insert((K) oldKeys[i], (V) oldValues[i]);
            }
        }
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
