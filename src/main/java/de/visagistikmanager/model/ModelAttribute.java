package de.visagistikmanager.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ModelAttribute {

	public String placeholder() default "";

	public int row() default 0;
	
	public int column() default 0;
	
}
