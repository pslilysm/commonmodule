package pers.cxd.corelibrary.annotation

/**
 * A annotation support make field be ignore when use Gson to serialize or deserialize
 *
 * @author pslilysm
 * @since 1.0.0
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class GsonExclude