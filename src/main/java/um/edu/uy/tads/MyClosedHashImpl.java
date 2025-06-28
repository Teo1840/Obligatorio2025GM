package um.edu.uy.tads;

import um.edu.uy.adt.list.MyList;
import um.edu.uy.adt.list.linked.MyLinkedListImpl;

public class MyClosedHashImpl<K,T> implements MyHash<K,T> {



    private HashEntry<K,T>[] hashArray;
    private int size;
    private boolean linear;
    private float chargeFactor;


    public MyClosedHashImpl(int size, boolean linear){
        this.hashArray = (HashEntry<K,T>[]) new HashEntry[getNextPrimeNumber(size-1)];
        this.linear = linear;
        this.size = 0;
        this.chargeFactor = 0.5f;
    }


    public MyClosedHashImpl(int size, boolean linear, float chargeFactor) {
        this.hashArray = (HashEntry<K,T>[]) new HashEntry[getNextPrimeNumber(size-1)];
        this.size = 0;
        this.linear = linear;
        this.chargeFactor = chargeFactor;
    }


    @Override
    public synchronized void put(K key, T value) {

        if (((float) this.size + 1) / ((float) hashArray.length) > this.chargeFactor ){
            this.reHashing();
        }


        int iterations = 0;
        int keyHash = key.hashCode();

        int position = getPosition(keyHash, iterations);

        while (!(hashArray[position] == null || hashArray[position].getKey().equals(key) || iterations  > hashArray.length || hashArray[position].isDeleted() ) ) {
            iterations++;
            position = getPosition(keyHash,iterations);

        }

        if (iterations <= hashArray.length) {
            if (hashArray[position] != null && !hashArray[position].isDeleted()) {
                this.size--;
            }
            hashArray[position] = new HashEntry<>(key, value);
            this.size++;
        }




    }

    @Override
    public synchronized  T remove(K key) {
        T valueToReturn = null;
        int iterations = 0;
        int keyHash = key.hashCode();

        int position = getPosition(keyHash, iterations);

        while (!(hashArray[position] == null || hashArray[position].getKey().equals(key) || iterations  > hashArray.length || hashArray[position].isDeleted())) {
            iterations++;
            position = getPosition(keyHash, iterations);
        }

        if (iterations <= hashArray.length && !(hashArray[position] == null) && !(hashArray[position].isDeleted())){
            valueToReturn = hashArray[position].getValue();
            hashArray[position].setDeleted(true);
            this.size--;
        }
        return valueToReturn;
    }

    @Override
    public synchronized T get(K key) {
        T valueToReturn = null;
        int iterations = 0;
        int keyHash = key.hashCode();

        int position = getPosition(keyHash, iterations);


        if (hashArray[position] == null){
            return null;
        }

        while (!(hashArray[position] == null || hashArray[position].getKey().equals(key) || iterations  > hashArray.length)) {
            iterations++;
            position = getPosition(keyHash, iterations);
        }

        if (hashArray[position] != null && iterations < hashArray.length && !(hashArray[position].isDeleted())){
            valueToReturn = hashArray[position].getValue();
        }
        return valueToReturn;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public MyList<T> getValues() {
        int updates = 0;
        MyList<T> list = new MyLinkedListImpl<>();
        for (int i = 0; i<this.hashArray.length; i++){
            if (hashArray[i] != null && !hashArray[i].isDeleted()){
                list.add(hashArray[i].getValue());
                updates++;
            }
            if (updates == this.size){
                break;
            }
        }
        return list;
    }

    @Override
    public T[] getValuesArray() {
        int updates = 0;
        T[] vector = (T[]) new Object[size];

        for (int i = 0; i< this.hashArray.length; i++){
            if (hashArray[i] != null && !hashArray[i].isDeleted()){
                vector[updates] = hashArray[i].getValue();
                updates++;
            }
            if (updates == this.size){
                break;
            }
        }

        return vector;
    }



    private int getPosition(int keyHash, int iterations){
        int position = 0;
        if (linear){
            position = (keyHash % hashArray.length + iterations) % hashArray.length;
        }else{
            position = (keyHash % hashArray.length + (int) Math.pow(iterations,2)) % hashArray.length;
        }
        return position;
    }

    private void reHashing(){

        int newLength = getNextPrimeNumber(this.hashArray.length * 2);

        HashEntry<K,T>[] oldHash = hashArray;
        this.hashArray = (HashEntry<K,T>[]) new HashEntry[newLength];
        int oldSize = size;

        int updates = 0;

        for(int i = 0; i<oldHash.length;i++){
            if (oldHash[i] != null && !oldHash[i].isDeleted() ){
                put(oldHash[i].getKey(),oldHash[i].getValue());
                updates++;
            }
            if (updates >= oldSize){
                break;
            }

        }

        this.size = oldSize;
    }

    private int getNextPrimeNumber(int number){
        int numberToReturn = number + 1;

        while(isNotPrime(numberToReturn)){
            numberToReturn++;
        }

        return numberToReturn;
    }

    private boolean isNotPrime(int number){

        boolean isNotPrime = false;

        for (int i=2; i<number-1; i++){
            if (number % i == 0){
                isNotPrime = true;
                break;
            }
        }

        return isNotPrime;
    }


}
