package com.ajay.blessed.vertx.framework.rest.support;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajay.blessed.vertx.framework.annotation.RequestMapping;
import com.ajay.blessed.vertx.framework.rest.DefaultVerticleHandlerFactory;
import com.ajay.blessed.vertx.framework.rest.IVerticleHandlerFactory;
import com.ajay.blessed.vertx.framework.web.RequestMappingConfig;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class DefaultRequestMappingnnotationProcessor implements IRequestMappingAnnotationProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultVerticleHandlerFactory.class);

	private IVerticleHandlerFactory<RoutingContext> factory;

	public DefaultRequestMappingnnotationProcessor() {
		this.factory = new DefaultVerticleHandlerFactory();
	}

	@Override
	public <T> RequestMappingConfig processRequestMapping(final Class<T> clazz, final Method method) {
		final RequestMapping annotation = method.getAnnotation(RequestMapping.class);
		final String url = annotation.value();
		if (StringUtils.isEmpty(url)) {
			throw new RuntimeException("No Mapping found");
		}
		final Handler<RoutingContext> handler = factory.buildHandler(clazz, method);
		final RequestMappingConfig newConfig = new RequestMappingConfig();
		newConfig.setHttpMethod(annotation.method());
		newConfig.setUrl(annotation.value());
		newConfig.setHandler(handler);
		LOGGER.info("config object created for url {} for http method {}", url, annotation.method());
		return newConfig;

	}

}
