package superCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    public static enum СacheType {
        IN_MEMORY, FILE, SQLite
    }
    СacheType cacheType() default СacheType.IN_MEMORY;
    String fileNamePrefix() default  "data";

}
