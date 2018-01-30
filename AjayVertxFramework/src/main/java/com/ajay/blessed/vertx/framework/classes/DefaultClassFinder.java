package com.ajay.blessed.vertx.framework.classes;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultClassFinder implements IClassFinder {

	private static final Logger logger = LoggerFactory.getLogger(DefaultClassFinder.class);

	@Override
	public Optional<Set<Class<?>>> findClassesWithAnnotationInPackage(final Class<? extends Annotation> annotation,
			final String packageName) {
		logger.info("Finding all classes in packagge {} with annotation {}", packageName, annotation);
		final Reflections reflections = new Reflections(packageName);
		final Set<Class<?>> matchedClasses = reflections.getTypesAnnotatedWith(annotation);
		if (CollectionUtils.isEmpty(matchedClasses)) {
			logger.debug("No class found with annotation {} in package {}", annotation, packageName);
			return Optional.empty();

		}
		logger.debug("Classes {} found with annotation {} in package {}", matchedClasses.toString(), annotation,
				packageName);
		return Optional.of(matchedClasses);
	}

}
