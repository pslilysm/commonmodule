package pers.cxd.corelibrary;

import androidx.collection.ArrayMap;

import java.lang.reflect.Constructor;
import java.util.Map;

public class SingletonFactory {

    private static Map<Class<?>, Object> sSingletonCache;

    public static <T> T findOrCreate(Class<T> clazz) {
        checkSingletonCache();
        T t = (T) sSingletonCache.get(clazz);
        if (t == null){
            synchronized (clazz){
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

    private static void checkSingletonCache(){
        if (sSingletonCache == null){
            synchronized (SingletonFactory.class){
                if (sSingletonCache == null){
                    sSingletonCache = new ArrayMap<>();
                }
            }
        }
    }

}
