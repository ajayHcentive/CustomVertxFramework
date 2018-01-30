package com.ajay.blessed.vertx.framework.rest.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryVerticleObjectFactory implements IVerticleObjectFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryVerticleObjectFactory.class);

	private static final Map<String, Object> objectHolderMap = new ConcurrentHashMap<>();

	@Override
	public <T> Object getRestVerticleObj(final Class<T> clazz) {
		LOGGER.info("Creating object of class {}", clazz);
		if (null != objectHolderMap.get(clazz.getName())) {
			LOGGER.info("Object of class {} found in cache ", clazz);
			return objectHolderMap.get(clazz.getName());
		}
		LOGGER.info("Object of class {} not found in cache ", clazz);
		try {
			final Object newObj = clazz.newInstance();
			LOGGER.info("New object of class {} created ", clazz);
			objectHolderMap.put(clazz.getName(), newObj);
			return newObj;
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("Error whille creating object of class " + clazz, e);
			throw new RuntimeException("Error creating new object for class " + clazz.getName());
		}

	}

}
