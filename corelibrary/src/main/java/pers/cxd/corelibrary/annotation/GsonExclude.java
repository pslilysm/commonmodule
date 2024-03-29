package pers.cxd.corelibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A annotation support make field be ignore when use Gson to serialize or deserialize
 *
 * @author pslilysm
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface GsonExclude {

}
