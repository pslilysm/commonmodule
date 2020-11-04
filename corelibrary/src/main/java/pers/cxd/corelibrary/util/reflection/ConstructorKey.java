package pers.cxd.corelibrary.util.reflection;

import java.util.Arrays;
import java.util.Objects;

public class ConstructorKey {

    Class<?> clazz;
    Class<?>[] parameterTypes;
    private boolean inUse;
    private ConstructorKey next;

    private ConstructorKey(Class<?> clazz, Class<?>[] parameterTypes) {
        this.clazz = clazz;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructorKey that = (ConstructorKey) o;
        return Objects.equals(clazz, that.clazz) &&
                Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(clazz);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }

    public void markInUse() {
        inUse = true;
    }

    private static ConstructorKey sPool;
    private static int sPoolSize;
    private static final int MAX_POOL_SIZE = 10;

    public static ConstructorKey obtain(Class<?> clazz, Class<?>[] parameterTypes){
        synchronized (ConstructorKey.class) {
            if (sPool != null) {
                ConstructorKey m = sPool;
                sPool = m.next;
                m.next = null;
                sPoolSize--;
                m.clazz = clazz;
                m.parameterTypes = parameterTypes;
                return m;
            }
        }
        return new ConstructorKey(clazz, parameterTypes);
    }

    public void recycle(){
        if (inUse){
            throw new IllegalStateException(this + " is in use, can't recycle");
        }
        clazz = null;
        parameterTypes = null;
        synchronized (ConstructorKey.class) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

}
