package pers.cxd.corelibrary;

import androidx.collection.ArrayMap;

import java.lang.reflect.Constructor;
import java.util.Map;

public class SingletonFactory {

    private static final Map<Class<?>, Object> sSingletonCache = new ArrayMap<>();;

    public static <T> T findOrCreate(Class<T> clazz) {
        T t;
        synchronized (sSingletonCache){
            t = (T) sSingletonCache.get(clazz);
        }
        if (t == null){
            synchronized (sSingletonCache){
                if ((t = (T) sSingletonCache.get(clazz)) == null){
                    try {
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        t = (T) constructor.newInstance();
                        sSingletonCache.put(clazz, t);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return t;
    }

}
