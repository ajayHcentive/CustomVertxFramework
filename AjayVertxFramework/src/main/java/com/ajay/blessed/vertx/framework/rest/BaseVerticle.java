package com.ajay.blessed.vertx.framework.rest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajay.blessed.vertx.framework.exception.IFailureHandler;
import com.ajay.blessed.vertx.framework.exception.JsonFailureHandler;
import com.ajay.blessed.vertx.framework.rest.support.DefaultRequestMappingProvider;
import com.ajay.blessed.vertx.framework.rest.support.IRequestMappingProvider;
import com.ajay.blessed.vertx.framework.web.RequestMappingConfig;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BaseVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseVerticle.class);

	private IRequestMappingProvider reqMappingProvider;

	private IFailureHandler failureHandler;

	private final Class<?> mainClass;

	public BaseVerticle(final Class<?> mainClass) {
		this.reqMappingProvider = new DefaultRequestMappingProvider();
		this.mainClass = mainClass;
		this.failureHandler = new JsonFailureHandler();
	}

	@Override
	public void start(final Future<Void> startFuture) throws Exception {
		LOGGER.info("Initializing vertx configurations");

		/**
		 * This sets the default exception handler.
		 */
		registerExceptionHanlder();
		LOGGER.info("Exception handler set.");
		final Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		LOGGER.info("BodyHandler set.");
		/**
		 * Failure handler is registered to do the processing after error has occured.
		 */
		router.route().failureHandler(ctx -> {
			failureHandler.handleError(ctx);
		});

		final Set<RequestMappingConfig> allMappings = reqMappingProvider
				.getAllRequestMappings(mainClass.getPackage().getName());
		LOGGER.info("All the mappings present in the project are {}", allMappings);
		for (RequestMappingConfig requestMappingConfig : allMappings) {
			router.route(requestMappingConfig.getHttpMethod(), requestMappingConfig.getUrl())
					.handler(requestMappingConfig.getHandler());
			LOGGER.info("Request handler set for the route {}", requestMappingConfig);
		}

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		LOGGER.info("Starting the httpserver on port 8080");
	}

	protected void registerFailureHandler(IFailureHandler failureHandler) {
		this.failureHandler = failureHandler;

	}

	protected void registerExceptionHanlder() {
		vertx.exceptionHandler();
	}

}
