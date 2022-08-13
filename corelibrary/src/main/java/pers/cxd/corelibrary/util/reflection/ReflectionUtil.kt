package pers.cxd.corelibrary.util.reflection

import androidx.collection.ArrayMap
import pers.cxd.corelibrary.util.Pair
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

/**
 * A util for reflection,
 * Cache is added to Constructor Field Method, memory is changed for speed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object ReflectionUtil {
    private val sConstructors: MutableMap<ConstructorKey, Constructor<*>> = ArrayMap()
    private val sFields: MutableMap<FieldKey, Field> = ArrayMap()
    private val sMethods: MutableMap<MethodKey, Method> = ArrayMap()
    private val sEmptyParameterTypesAndArgs = emptyArray<Any>()

    /**
     * Split parameter types and args, their size of the two is always the same,
     * otherwise the `IllegalArgumentException` will throw
     *
     * @param classLoader           for load class by `String`
     * @param parameterTypesAndArgs their size of the two is always the same
     * @return A wrapped `Pair`, [Pair.first] is parameter types, [Pair.second] is args
     * @throws ClassNotFoundException when classloader load class failure
     */
    @Throws(ClassNotFoundException::class)
    private fun splitParameterTypesAndArgs(
        classLoader: ClassLoader,
        vararg parameterTypesAndArgs: Any?
    ): Pair<Array<out Class<*>?>, Array<Any?>> {
        require(parameterTypesAndArgs.size % 2 == 0) { "check your parameterTypesAndArgs length -> " + parameterTypesAndArgs.size }
        if (parameterTypesAndArgs.isEmpty()) {
            return Pair.obtain(emptyArray(), emptyArray())
        }
        val mixedParameterTypes =
            Arrays.copyOf(parameterTypesAndArgs, parameterTypesAndArgs.size / 2)
        val args = Arrays.copyOfRange(
            parameterTypesAndArgs,
            parameterTypesAndArgs.size / 2,
            parameterTypesAndArgs.size
        )
        val parameterTypes: Array<Class<*>?> = arrayOfNulls(mixedParameterTypes.size)
        for (i in mixedParameterTypes.indices) {
            when (val pt = mixedParameterTypes[i]) {
                is String -> {
                    parameterTypes[i] = classLoader.loadClass(pt)
                }
                is Class<*> -> {
                    parameterTypes[i] = pt
                }
                else -> {
                    throw IllegalArgumentException("check your parameterTypes at pos " + i + ", type is " + pt!!.javaClass)
                }
            }
        }
        return Pair.obtain(parameterTypes, args)
    }

    /**
     * Find in the cache or create `Constructor` by class and parameterTypes
     */
    @Throws(ReflectiveOperationException::class)
    private fun <T> findOrCreateConstructor(
        clazz: Class<T>,
        vararg parameterTypes: Class<*>?
    ): Constructor<T> {
        val constructorKey: ConstructorKey = ConstructorKey.obtain(clazz, *parameterTypes)
        var constructor = sConstructors[constructorKey] as Constructor<T>?
        if (constructor == null) {
            synchronized(sConstructors) {
                if ((sConstructors[constructorKey] as Constructor<T>?).also {
                        constructor = it
                    } == null) {
                    constructor = try {
                        clazz.getDeclaredConstructor(*parameterTypes)
                    } catch (ex: NoSuchMethodException) {
                        constructorKey.recycle()
                        throw ex
                    }
                    constructor!!.isAccessible = true
                    constructorKey.markInUse()
                    sConstructors.put(constructorKey, constructor!!)
                } else {
                    constructorKey.recycle()
                }
            }
        } else {
            constructorKey.recycle()
        }
        return constructor!!
    }

    /**
     * Recursive find in the cache or create `Field` by class and fieldName
     */
    @Throws(ReflectiveOperationException::class)
    private fun findOrCreateField(clazz: Class<*>, fieldName: String): Field {
        val fieldKey: FieldKey = FieldKey.obtain(clazz, fieldName)
        var field = sFields[fieldKey]
        if (field == null) {
            synchronized(sFields) {
                if (sFields[fieldKey].also { field = it } == null) {
                    field = try {
                        clazz.getDeclaredField(fieldName)
                    } catch (ex: NoSuchFieldException) {
                        fieldKey.recycle()
                        val superClazz = clazz.superclass
                        return if (superClazz != null && superClazz != Any::class.java) {
                            findOrCreateField(superClazz, fieldName)
                        } else {
                            throw ex
                        }
                    }
                    field!!.isAccessible = true
                    fieldKey.markInUse()
                    sFields.put(fieldKey, field!!)
                } else {
                    fieldKey.recycle()
                }
            }
        } else {
            fieldKey.recycle()
        }
        return field!!
    }

    /**
     * Recursive find in the cache or create `Method` by class and methodName and parameterTypes
     */
    @Throws(ReflectiveOperationException::class)
    private fun findOrCreateMethod(
        clazz: Class<*>,
        methodName: String,
        vararg parameterTypes: Class<*>?
    ): Method {
        val methodKey: MethodKey = MethodKey.obtain(clazz, methodName, *parameterTypes)
        var method = sMethods[methodKey]
        if (method == null) {
            synchronized(sMethods) {
                if (sMethods[methodKey].also { method = it } == null) {
                    method = try {
                        clazz.getDeclaredMethod(methodName, *parameterTypes)
                    } catch (ex: NoSuchMethodException) {
                        methodKey.recycle()
                        val superClazz = clazz.superclass
                        return if (superClazz != null && superClazz != Any::class.java) {
                            findOrCreateMethod(superClazz, methodName, *parameterTypes)
                        } else {
                            throw ex
                        }
                    }
                    method!!.isAccessible = true
                    methodKey.markInUse()
                    sMethods.put(methodKey, method!!)
                } else {
                    methodKey.recycle()
                }
            }
        } else {
            methodKey.recycle()
        }
        return method!!
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(className: String?): T {
        return newInstance(
            className,
            *sEmptyParameterTypesAndArgs
        )
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(className: String?, classLoader: ClassLoader): T {
        return newInstance(className, classLoader, *sEmptyParameterTypesAndArgs)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(className: String?, vararg parameterTypesAndArgs: Any?): T {
        return newInstance(className, ClassLoader.getSystemClassLoader(), *parameterTypesAndArgs)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(
        className: String?,
        classLoader: ClassLoader,
        vararg parameterTypesAndArgs: Any?
    ): T {
        return newInstance(
            classLoader.loadClass(className),
            *parameterTypesAndArgs
        ) as T
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(clazz: Class<T>): T {
        return newInstance(clazz, *sEmptyParameterTypesAndArgs)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(clazz: Class<T>, vararg parameterTypesAndArgs: Any?): T {
        val classLoader =
            if (clazz.classLoader == null) ClassLoader.getSystemClassLoader() else clazz.classLoader
        return newInstance(clazz, classLoader, *parameterTypesAndArgs)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> newInstance(
        clazz: Class<T>,
        classLoader: ClassLoader,
        vararg parameterTypesAndArgs: Any?
    ): T {
        val splitParameterTypesAndArgs =
            splitParameterTypesAndArgs(classLoader, *parameterTypesAndArgs)
        val instance =
            splitParameterTypesAndArgs.first()!!.let { paramTypes ->
                splitParameterTypesAndArgs.second()!!.let { args ->
                    findOrCreateConstructor(clazz, *paramTypes)
                        .newInstance(*args)
                }
            }
        splitParameterTypesAndArgs.recycle()
        return instance
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> getFieldValue(`object`: Any?, fieldName: String): T {
        return findOrCreateField(`object`!!.javaClass, fieldName)[`object`] as T
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun setFieldValue(`object`: Any, fieldName: String, fieldValue: Any?) {
        findOrCreateField(`object`.javaClass, fieldName)[`object`] = fieldValue
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> invokeMethod(`object`: Any, methodName: String): T {
        return invokeMethod(`object`, methodName, *sEmptyParameterTypesAndArgs)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> invokeMethod(`object`: Any, methodName: String, vararg parameterTypesAndArgs: Any?): T {
        val clazz: Class<*> = `object`.javaClass
        val classLoader =
            if (clazz.classLoader == null) ClassLoader.getSystemClassLoader() else clazz.classLoader
        val splitParameterTypesAndArgs =
            splitParameterTypesAndArgs(classLoader, *parameterTypesAndArgs)
        val result =
            splitParameterTypesAndArgs.first()!!.let { paramTypes ->
                splitParameterTypesAndArgs.second()!!.let { args ->
                    findOrCreateMethod(clazz, methodName, *paramTypes)
                        .invoke(`object`, *args) as T
                }
            }
        splitParameterTypesAndArgs.recycle()
        return result
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> getStaticFieldValue(className: String, fieldName: String): T {
        return getStaticFieldValue(Class.forName(className), fieldName)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> getStaticFieldValue(
        className: String,
        classLoader: ClassLoader,
        fieldName: String
    ): T {
        return getStaticFieldValue(classLoader.loadClass(className), fieldName)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> getStaticFieldValue(clazz: Class<*>, fieldName: String): T {
        return findOrCreateField(clazz, fieldName)[null] as T
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun setStaticFiledValue(className: String, fieldName: String, fieldValue: Any?) {
        setStaticFiledValue(Class.forName(className), fieldName, fieldValue)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun setStaticFiledValue(
        className: String?,
        classLoader: ClassLoader,
        fieldName: String,
        fieldValue: Any?
    ) {
        setStaticFiledValue(classLoader.loadClass(className), fieldName, fieldValue)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun setStaticFiledValue(clazz: Class<*>, fieldName: String, fieldValue: Any?) {
        findOrCreateField(clazz, fieldName)[null] = fieldValue
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T : Any> invokeStaticMethod(className: String, methodName: String): T {
        return invokeStaticMethod(
            ClassLoader.getSystemClassLoader().loadClass(className),
            methodName,
            *sEmptyParameterTypesAndArgs
        )
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> invokeStaticMethod(
        className: String,
        classLoader: ClassLoader,
        methodName: String
    ): T {
        return invokeStaticMethod(
            classLoader.loadClass(className),
            methodName,
            *sEmptyParameterTypesAndArgs
        )
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> invokeStaticMethod(clazz: Class<*>, methodName: String): T {
        return invokeStaticMethod(clazz, methodName, *sEmptyParameterTypesAndArgs)
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> invokeStaticMethod(
        className: String,
        classLoader: ClassLoader,
        methodName: String,
        vararg parameterTypesAndArgs: Any?
    ): T {
        return invokeStaticMethod(
            classLoader.loadClass(className),
            methodName,
            *parameterTypesAndArgs
        )
    }

    @kotlin.jvm.JvmStatic
    @Throws(ReflectiveOperationException::class)
    fun <T> invokeStaticMethod(
        clazz: Class<*>,
        methodName: String,
        vararg parameterTypesAndArgs: Any?
    ): T {
        val classLoader =
            if (clazz.classLoader == null) ClassLoader.getSystemClassLoader() else clazz.classLoader
        val splitParameterTypesAndArgs =
            splitParameterTypesAndArgs(classLoader, *parameterTypesAndArgs)
        val result =
            splitParameterTypesAndArgs.first()!!.let { paramTypes ->
                splitParameterTypesAndArgs.second()!!.let { args ->
                    findOrCreateMethod(clazz, methodName, *paramTypes)
                        .invoke(null, *args) as T
                }
            }
        splitParameterTypesAndArgs.recycle()
        return result
    }
}