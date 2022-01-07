package pers.cxd.corelibrary.util.reflection;

import androidx.collection.ArrayMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import pers.cxd.corelibrary.util.Pair;

/**
 * A util for reflection,
 * Cache is added to Constructor Field Method, memory is changed for speed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ReflectionUtil {

    private static final Map<ConstructorKey, Constructor<?>> sConstructors = new ArrayMap<>();
    private static final Map<FieldKey, Field> sFields = new ArrayMap<>();
    private static final Map<MethodKey, Method> sMethods = new ArrayMap<>();

    private static final Object[] sEmptyParameterTypesAndArgs = new Object[0];

    /**
     * Split parameter types and args, their size of the two is always the same,
     * otherwise the {@code IllegalArgumentException} will throw
     *
     * @param classLoader for load class by {@code String}
     * @param parameterTypesAndArgs their size of the two is always the same
     * @return A wrapped {@code Pair}, {@link Pair#first()} is parameter types, {@link Pair#second()} is args
     * @throws ClassNotFoundException when classloader load class failure
     */
    private static Pair<Class<?>[], Object[]> splitParameterTypesAndArgs(ClassLoader classLoader, Object... parameterTypesAndArgs) throws ClassNotFoundException {
        if (parameterTypesAndArgs.length % 2 != 0) {
            throw new IllegalArgumentException("check your parameterTypesAndArgs length -> " + parameterTypesAndArgs.length);
        }
        if (parameterTypesAndArgs.length == 0) {
            return Pair.obtain(null, null);
        }
        Object[] mixedParameterTypes = Arrays.copyOf(parameterTypesAndArgs, parameterTypesAndArgs.length / 2);
        Object[] args = Arrays.copyOfRange(parameterTypesAndArgs, parameterTypesAndArgs.length / 2, parameterTypesAndArgs.length);
        Class<?>[] parameterTypes = new Class[mixedParameterTypes.length];
        for (int i = 0; i < mixedParameterTypes.length; i++) {
            Object pt = mixedParameterTypes[i];
            if (pt instanceof String) {
                parameterTypes[i] = classLoader.loadClass((String) pt);
            } else if (pt instanceof Class) {
                parameterTypes[i] = (Class<?>) pt;
            } else {
                throw new IllegalArgumentException("check your parameterTypes at pos " + i + ", type is " + pt.getClass());
            }
        }
        return Pair.obtain(parameterTypes, args);
    }

    /**
     * Find in the cache or create {@code Constructor} by class and parameterTypes
     */
    private static <T> Constructor<T> findOrCreateConstructor(Class<T> clazz, Class<?>[] parameterTypes) throws ReflectiveOperationException{
        ConstructorKey constructorKey = ConstructorKey.obtain(clazz, parameterTypes);
        Constructor<T> constructor = (Constructor<T>) sConstructors.get(constructorKey);
        if (constructor == null){
            synchronized (sConstructors){
                if ((constructor = (Constructor<T>) sConstructors.get(constructorKey)) == null){
                    try {
                        constructor = clazz.getDeclaredConstructor(parameterTypes);
                    }catch (NoSuchMethodException ex){
                        constructorKey.recycle();
                        throw ex;
                    }
                    constructor.setAccessible(true);
                    constructorKey.markInUse();
                    sConstructors.put(constructorKey, constructor);
                }else {
                    constructorKey.recycle();
                }
            }
        }else {
            constructorKey.recycle();
        }
        return constructor;
    }

    /**
     * Recursive find in the cache or create {@code Field} by class and fieldName
     */
    private static Field findOrCreateField(Class<?> clazz, String fieldName) throws ReflectiveOperationException{
        FieldKey fieldKey = FieldKey.obtain(clazz, fieldName);
        Field field = sFields.get(fieldKey);
        if (field == null){
            synchronized (sFields){
                if ((field = sFields.get(fieldKey)) == null){
                    try {
                        field = clazz.getDeclaredField(fieldName);
                    }catch (NoSuchFieldException ex){
                        fieldKey.recycle();
                        Class<?> superClazz = clazz.getSuperclass();
                        if (superClazz != null && superClazz != Object.class){
                            return findOrCreateField(superClazz, fieldName);
                        }else {
                            throw ex;
                        }
                    }
                    field.setAccessible(true);
                    fieldKey.markInUse();
                    sFields.put(fieldKey, field);
                }else {
                    fieldKey.recycle();
                }
            }
        }else {
            fieldKey.recycle();
        }
        return field;
    }

    /**
     * Recursive find in the cache or create {@code Method} by class and methodName and parameterTypes
     */
    private static Method findOrCreateMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws ReflectiveOperationException{
        MethodKey methodKey = MethodKey.obtain(clazz, methodName, parameterTypes);
        Method method = sMethods.get(methodKey);
        if (method == null){
            synchronized (sMethods){
                if ((method = sMethods.get(methodKey)) == null){
                    try {
                        method = clazz.getDeclaredMethod(methodName, parameterTypes);
                    }catch (NoSuchMethodException ex){
                        methodKey.recycle();
                        Class<?> superClazz = clazz.getSuperclass();
                        if (superClazz != null && superClazz != Object.class){
                            return findOrCreateMethod(superClazz, methodName, parameterTypes);
                        }else {
                            throw ex;
                        }
                    }
                    method.setAccessible(true);
                    methodKey.markInUse();
                    sMethods.put(methodKey, method);
                }else {
                    methodKey.recycle();
                }
            }
        }else {
            methodKey.recycle();
        }
        return method;
    }

    public static <T> T newInstance(String className) throws ReflectiveOperationException{
        return (T) newInstance(className, ClassLoader.getSystemClassLoader(), sEmptyParameterTypesAndArgs);
    }

    public static <T> T newInstance(String className, ClassLoader classLoader) throws ReflectiveOperationException{
        return (T) newInstance(className, classLoader, sEmptyParameterTypesAndArgs);
    }

    public static <T> T newInstance(String className, Object... parameterTypesAndArgs) throws ReflectiveOperationException{
        return (T) newInstance(className, ClassLoader.getSystemClassLoader(), parameterTypesAndArgs);
    }

    public static <T> T newInstance(String className, ClassLoader classLoader, Object... parameterTypesAndArgs) throws ReflectiveOperationException{
        return (T) newInstance(classLoader.loadClass(className), classLoader, parameterTypesAndArgs);
    }

    public static <T> T newInstance(Class<T> clazz) throws ReflectiveOperationException{
        return newInstance(clazz, sEmptyParameterTypesAndArgs);
    }

    public static <T> T newInstance(Class<T> clazz, Object... parameterTypesAndArgs) throws ReflectiveOperationException {
        ClassLoader classLoader = clazz.getClassLoader() == null ? ClassLoader.getSystemClassLoader() : clazz.getClassLoader();
        return newInstance(clazz, classLoader, parameterTypesAndArgs);
    }

    public static <T> T newInstance(Class<T> clazz, ClassLoader classLoader, Object... parameterTypesAndArgs) throws ReflectiveOperationException{
        Pair<Class<?>[], Object[]> splitParameterTypesAndArgs = splitParameterTypesAndArgs(classLoader, parameterTypesAndArgs);
        T instance = findOrCreateConstructor(clazz, splitParameterTypesAndArgs.first()).newInstance(splitParameterTypesAndArgs.second());
        splitParameterTypesAndArgs.recycle();
        return instance;
    }

    public static <T> T getFieldValue(Object object, String fieldName) throws ReflectiveOperationException {
        return (T) findOrCreateField(object.getClass(), fieldName).get(object);
    }

    public static void setFieldValue(Object object, String fieldName, Object fieldValue) throws ReflectiveOperationException {
        findOrCreateField(object.getClass(), fieldName).set(object, fieldValue);
    }

    public static <T> T invokeMethod(Object object, String methodName) throws ReflectiveOperationException{
        return invokeMethod(object, methodName, sEmptyParameterTypesAndArgs);
    }

    public static <T> T invokeMethod(Object object, String methodName, Object... parameterTypesAndArgs) throws ReflectiveOperationException {
        Class<?> clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader() == null ? ClassLoader.getSystemClassLoader() : clazz.getClassLoader();
        Pair<Class<?>[], Object[]> splitParameterTypesAndArgs = splitParameterTypesAndArgs(classLoader, parameterTypesAndArgs);
        T result = (T) findOrCreateMethod(clazz, methodName, splitParameterTypesAndArgs.first()).invoke(object, splitParameterTypesAndArgs.second());
        splitParameterTypesAndArgs.recycle();
        return result;
    }

    public static <T> T getStaticFieldValue(String className, String fieldName) throws ReflectiveOperationException {
        return getStaticFieldValue(Class.forName(className), fieldName);
    }

    public static <T> T getStaticFieldValue(String className, ClassLoader classLoader, String fieldName) throws ReflectiveOperationException {
        return getStaticFieldValue(classLoader.loadClass(className), fieldName);
    }

    public static <T> T getStaticFieldValue(Class<?> clazz, String fieldName) throws ReflectiveOperationException {
        return (T) findOrCreateField(clazz, fieldName).get(null);
    }

    public static void setStaticFiledValue(String className, String fieldName, Object fieldValue) throws ReflectiveOperationException {
        setStaticFiledValue(Class.forName(className), fieldName, fieldValue);
    }

    public static void setStaticFiledValue(String className, ClassLoader classLoader, String fieldName, Object fieldValue) throws ReflectiveOperationException {
        setStaticFiledValue(classLoader.loadClass(className), fieldName, fieldValue);
    }

    public static void setStaticFiledValue(Class<?> clazz, String fieldName, Object fieldValue) throws ReflectiveOperationException {
        findOrCreateField(clazz, fieldName).set(null, fieldValue);
    }

    public static <T> T invokeStaticMethod(String className, String methodName) throws ReflectiveOperationException {
        return invokeStaticMethod(ClassLoader.getSystemClassLoader().loadClass(className), methodName, sEmptyParameterTypesAndArgs);
    }

    public static <T> T invokeStaticMethod(String className, ClassLoader classLoader, String methodName) throws ReflectiveOperationException {
        return invokeStaticMethod(classLoader.loadClass(className), methodName, sEmptyParameterTypesAndArgs);
    }

    public static <T> T invokeStaticMethod(Class<?> clazz, String methodName) throws ReflectiveOperationException {
        return invokeStaticMethod(clazz, methodName, sEmptyParameterTypesAndArgs);
    }

    public static <T> T invokeStaticMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndArgs) throws ReflectiveOperationException {
        return invokeStaticMethod(classLoader.loadClass(className), methodName, parameterTypesAndArgs);
    }

    public static <T> T invokeStaticMethod(Class<?> clazz, String methodName, Object... parameterTypesAndArgs) throws ReflectiveOperationException {
        ClassLoader classLoader = clazz.getClassLoader() == null ? ClassLoader.getSystemClassLoader() : clazz.getClassLoader();
        Pair<Class<?>[], Object[]> splitParameterTypesAndArgs = splitParameterTypesAndArgs(classLoader, parameterTypesAndArgs);
        T result = (T) findOrCreateMethod(clazz, methodName, splitParameterTypesAndArgs.first()).invoke(null, splitParameterTypesAndArgs.second());
        splitParameterTypesAndArgs.recycle();
        return result;
    }


}
