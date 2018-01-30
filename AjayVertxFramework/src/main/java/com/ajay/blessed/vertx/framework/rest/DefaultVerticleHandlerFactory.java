package com.ajay.blessed.vertx.framework.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajay.blessed.vertx.framework.exception.VertxException;
import com.ajay.blessed.vertx.framework.rest.support.IVerticleObjectFactory;
import com.ajay.blessed.vertx.framework.rest.support.InMemoryVerticleObjectFactory;
import com.ajay.blessed.vertx.framework.web.Response;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class DefaultVerticleHandlerFactory implements IVerticleHandlerFactory<RoutingContext> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultVerticleHandlerFactory.class);

	private IVerticleObjectFactory objectFactory;

	public DefaultVerticleHandlerFactory() {
		this.objectFactory = new InMemoryVerticleObjectFactory();
	}

	@Override
	public Handler<RoutingContext> buildHandler(Class<?> clazz, Method method) {
		LOGGER.info("Builder Handler for class {} for method {}", clazz, method);
		return (rc -> {
			processRequest(clazz, method, rc);
		});
	}

	private void processRequest(Class<?> clazz, Method method, final RoutingContext rc) {

		try {
			final Object instance = objectFactory.getRestVerticleObj(clazz);
			final Object response = method.invoke(instance, rc);
			LOGGER.debug("Response returned form executing method {} of class {}", response, clazz);
			if (!(response instanceof Response)) {
				throw new RuntimeException("Response returned in not supported by framework.");
			}
			final Response verticleResponse = (Response) response;
			rc.response().setStatusCode(verticleResponse.getStatus());
			rc.response().end(Json.encode(verticleResponse.getEntity()));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error("Error while executing method {} of class {}", method, clazz, e);
			throw new VertxException("Error while executing code.");
		}

	}

}
