package com.cash.flow.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InitActivity {
	static final boolean defaultValue = false;
	
	boolean withActionBar() default defaultValue;
}
