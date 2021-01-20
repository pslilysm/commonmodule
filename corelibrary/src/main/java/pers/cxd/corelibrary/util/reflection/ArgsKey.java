package pers.cxd.corelibrary.util.reflection;

import java.util.Arrays;

public class ArgsKey {

    Object[] args;

    private ArgsKey next;

    private ArgsKey(Object[] args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArgsKey argsKey = (ArgsKey) o;
        return Arrays.equals(args, argsKey.args);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(args);
    }

    private static ArgsKey sPool;
    private static int sPoolSize;
    private static final int sMaxPoolSize = 10;
    private static final Object sPoolLock = new Object();

    public static ArgsKey obtain(Object[] args){
        synchronized (sPoolLock){
            if (sPool != null){
                ArgsKey argsKey = sPool;
                sPool = argsKey.next;
                argsKey.next = null;
                argsKey.args = args;
                sPoolSize--;
                return argsKey;
            }
        }
        return new ArgsKey(args);
    }

    public void recycle(){
        args = null;
        synchronized (sPoolLock){
            if (sMaxPoolSize > sPoolSize){
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

}
