package pers.cxd.corelibrary.util;

import java.util.LinkedList;

public class FIFOQueue<E> extends LinkedList<E> {

    private final int limit;

    public FIFOQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean offer(E e) {
        if (size() >= limit){
            onPoll(poll());
        }
        return super.offer(e);
    }

    public void onPoll(E e){

    }

}
