package com.ajay.blessed.vertx.framework.classes;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

public interface IClassFinder {

	Optional<Set<Class<?>>> findClassesWithAnnotationInPackage(Class<? extends Annotation> annotation,
			String packageName);

}
