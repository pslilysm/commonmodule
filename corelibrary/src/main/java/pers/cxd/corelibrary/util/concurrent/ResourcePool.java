package pers.cxd.corelibrary.util.concurrent;

import java.util.Queue;

public interface ResourcePool<R> {

    R getResource();

    void storeResource(R r);

    Queue<R> getResourceQueue();

}
