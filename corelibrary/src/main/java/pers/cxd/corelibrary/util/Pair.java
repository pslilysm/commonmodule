package pers.cxd.corelibrary.util;

import java.util.Objects;

import pers.cxd.corelibrary.util.function.Recyclable;

/**
 * Container to ease passing around a tuple of two objects. This object provides a sensible
 * implementation of equals(), returning true if equals() is true on each of the contained
 * objects.
 * This object also implements a recyclable interface.
 * When it is reclaimed, if the tuple of two objects can be reclaimed, the tuple will also be reclaimed.
 *
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 * @author pslilysm
 * @since 1.0.0
 */
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

    public static <F, S> Pair<F, S> obtain(F first, S second){
        synchronized (sPoolLock){
            if (sPool != null){
                Pair<F, S> pair = (Pair<F, S>) sPool;
                sPool = pair.next;
                pair.next = null;
                pair.first = first;
                pair.second = second;
                sPoolSize--;
                return pair;
            }
        }
        return new Pair<>(first, second);
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
