package pers.cxd.corelibrary.util;

import java.util.Objects;

public class Pair<F, S> implements Recyclable {

    private F first;
    private S second;

    private Pair<?, ?> next;

    private Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    private static Pair<?, ?> sPool;
    private static int sPoolSize;
    private static final int sMaxPoolSize = 10;
    private static final Object sPoolLock = new Object();

    public static <K, V> Pair<K, V> obtain(K key, V value){
        synchronized (sPoolLock){
            if (sPool != null){
                Pair<K, V> pair = (Pair<K, V>) sPool;
                sPool = pair.next;
                pair.next = null;
                pair.first = key;
                pair.second = value;
                sPoolSize--;
                return pair;
            }
        }
        return new Pair<>(key, value);
    }

    @Override
    public void recycle(){
        recycle(true);
    }

    /**
     * recycle the pair
     * @param recycleIfCan if true will do recycle if key or value instance of Recyclable
     */
    public void recycle(boolean recycleIfCan){
        if (recycleIfCan){
            if (first instanceof Recyclable){
                ((Recyclable) first).recycle();
            }
            if (second instanceof Recyclable){
                ((Recyclable) second).recycle();
            }
        }
        first = null;
        second = null;
        synchronized (sPoolLock){
            if (sMaxPoolSize > sPoolSize){
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

}
