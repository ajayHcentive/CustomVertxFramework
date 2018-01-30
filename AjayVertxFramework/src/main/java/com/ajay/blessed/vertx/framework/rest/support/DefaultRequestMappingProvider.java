package com.ajay.blessed.vertx.framework.rest.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajay.blessed.vertx.framework.annotation.RequestMapping;
import com.ajay.blessed.vertx.framework.annotation.RestVerticle;
import com.ajay.blessed.vertx.framework.classes.DefaultClassFinder;
import com.ajay.blessed.vertx.framework.classes.IClassFinder;
import com.ajay.blessed.vertx.framework.web.RequestMappingConfig;
import com.google.common.base.Predicates;

public class DefaultRequestMappingProvider implements IRequestMappingProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRequestMappingProvider.class);

	private IClassFinder classFinder;

	private IRequestMappingAnnotationProcessor reqMappingAnnotationProcessor;

	public DefaultRequestMappingProvider() {
		this.classFinder = new DefaultClassFinder();
		this.reqMappingAnnotationProcessor = new DefaultRequestMappingnnotationProcessor();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<RequestMappingConfig> getAllRequestMappings(final String packageName) {

		LOGGER.info("Search all the RestVerticle annotated classes in package {}", packageName);

		final Set<RequestMappingConfig> allReqMappingConfigs = new HashSet<>();
		final Optional<Set<Class<?>>> matchedClasses = classFinder
				.findClassesWithAnnotationInPackage(RestVerticle.class, packageName);
		LOGGER.info("All the classes with RestVerticle annotation found {}", matchedClasses);

		if (matchedClasses.isPresent()) {
			for (Class<?> clazz : matchedClasses.get()) {
				final Set<Method> matchedMethods = ReflectionUtils.getAllMethods(clazz,
						Predicates.and(ReflectionUtils.withAnnotations(RequestMapping.class),
								ReflectionUtils.withModifier(Modifier.PUBLIC)));
				if (!CollectionUtils.isEmpty(matchedMethods)) {
					for (Method method : matchedMethods) {
						final RequestMappingConfig reqMappingConfig = reqMappingAnnotationProcessor
								.processRequestMapping(clazz, method);
						if (allReqMappingConfigs.contains(reqMappingConfig)) {
							throw new RuntimeException("Two mappings of same url can't be exist");
						}
						allReqMappingConfigs.add(reqMappingConfig);
					}
				}
			}
		}
		return allReqMappingConfigs;
	}

}
