package pers.cxd.corelibrary.util.reflection;

import java.util.Arrays;
import java.util.Objects;

public class MethodKey {

    private static final int MAX_POOL_SIZE = 10;
    private static MethodKey sPool;
    private static int sPoolSize;
    Class<?> clazz;
    String methodName;
    Class<?>[] parameterTypes;
    private boolean inUse;
    private MethodKey next;

    private MethodKey(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    public static MethodKey obtain(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        synchronized (MethodKey.class) {
            if (sPool != null) {
                MethodKey mk = sPool;
                sPool = mk.next;
                mk.next = null;
                sPoolSize--;
                mk.clazz = clazz;
                mk.methodName = methodName;
                mk.parameterTypes = parameterTypes;
                return mk;
            }
        }
        return new MethodKey(clazz, methodName, parameterTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodKey methodKey = (MethodKey) o;
        return Objects.equals(clazz, methodKey.clazz) &&
                Objects.equals(methodName, methodKey.methodName) &&
                Arrays.equals(parameterTypes, methodKey.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(clazz, methodName);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }

    public void markInUse() {
        inUse = true;
    }

    public void recycle() {
        if (inUse) {
            throw new IllegalStateException(this + " is in use, can't recycle");
        }
        clazz = null;
        methodName = null;
        parameterTypes = null;
        synchronized (MethodKey.class) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

}
