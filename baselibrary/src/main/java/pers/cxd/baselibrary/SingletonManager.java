package pers.cxd.baselibrary;

import androidx.collection.ArrayMap;

import java.lang.reflect.Constructor;
import java.util.Map;

public class SingletonManager {

    private static Map<Class<?>, Object> sSingletonCache;

    public static <T> T getInstance(Class<T> clazz) {
        checkSingletonCache();
        T t = (T) sSingletonCache.get(clazz);
        if (t == null){
            synchronized (clazz){
                if ((t = (T) sSingletonCache.get(clazz)) == null){
                    try {
                        // why declared and no argument?
                        // because for singleton we must make constructor modifies to private and no argument
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        t = (T) constructor.newInstance();
                        sSingletonCache.put(clazz, t);
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }

    private static void checkSingletonCache(){
        if (sSingletonCache == null){
            synchronized (SingletonManager.class){
                if (sSingletonCache == null){
                    sSingletonCache = new ArrayMap<>();
                }
            }
        }
    }

}
