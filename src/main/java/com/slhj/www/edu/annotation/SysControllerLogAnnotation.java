package com.slhj.www.edu.annotation;

import java.lang.annotation.*;

@Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysControllerLogAnnotation {
	String desc() default "";
}
