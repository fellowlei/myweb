package com.mark.proxy.frame;

import java.lang.annotation.*;

/**
 * Created by lulei on 2017/6/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
