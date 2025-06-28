package um.edu.uy.tads;


public class HashEntry<K,T> {

    private K key;
    private boolean deleted;
    private T value;


    public HashEntry(K key, T value) {
        this.key = key;
        this.value = value;
        this.deleted = false;
    }

    public K getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equalToReturn = false;
        if (obj instanceof HashEntry) {
            equalToReturn = this.key.equals(((HashEntry) obj).getKey());
        }
        return equalToReturn;
    }
}
