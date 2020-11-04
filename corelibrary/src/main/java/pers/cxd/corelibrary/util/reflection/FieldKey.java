package pers.cxd.corelibrary.util.reflection;

import java.util.Objects;

public class FieldKey {

    Class<?> clazz;
    String filedName;
    private boolean inUse;
    private FieldKey next;

    private FieldKey(Class<?> clazz, String filedName) {
        this.clazz = clazz;
        this.filedName = filedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldKey fieldKey = (FieldKey) o;
        return Objects.equals(clazz, fieldKey.clazz) &&
                Objects.equals(filedName, fieldKey.filedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, filedName);
    }

    public void markInUse() {
        inUse = true;
    }

    private static FieldKey sPool;
    private static int sPoolSize;
    private static final int MAX_POOL_SIZE = 10;

    public static FieldKey obtain(Class<?> clazz, String filedName){
        synchronized (FieldKey.class) {
            if (sPool != null) {
                FieldKey fk = sPool;
                sPool = fk.next;
                fk.next = null;
                sPoolSize--;
                fk.clazz = clazz;
                fk.filedName = filedName;
                return fk;
            }
        }
        return new FieldKey(clazz, filedName);
    }

    public void recycle(){
        if (inUse){
            throw new IllegalStateException(this + " is in use, can't recycle");
        }
        clazz = null;
        filedName = null;
        synchronized (FieldKey.class) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

}
