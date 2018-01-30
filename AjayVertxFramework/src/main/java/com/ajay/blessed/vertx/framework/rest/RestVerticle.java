package com.ajay.blessed.vertx.framework.rest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajay.blessed.vertx.framework.rest.support.DefaultRequestMappingProvider;
import com.ajay.blessed.vertx.framework.rest.support.IRequestMappingProvider;
import com.ajay.blessed.vertx.framework.web.RequestMappingConfig;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RestVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestVerticle.class);

	private IRequestMappingProvider reqMappingProvider;

	public RestVerticle() {
		this.reqMappingProvider = new DefaultRequestMappingProvider();
	}

	@Override
	public void start(final Future<Void> startFuture) throws Exception {
		LOGGER.info("Initializing vertx configurations");

		/**
		 * This sets the default exception handler.
		 */
		configureExceptionHandler();
		LOGGER.info("Exception handler set.");
		final Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		LOGGER.info("BodyHandler set.");
		/**
		 * Failure handler is registered to do the processing after error has occured.
		 */
		router.route().failureHandler(ctx -> {

			final JsonObject error = new JsonObject().put("timestamp", System.nanoTime())
					.put("exception", ctx.failure().getClass().getName())
					.put("exceptionMessage", ctx.failure().getMessage()).put("path", ctx.request().path());

			ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
			ctx.response().end(error.encode());

		});

		final Set<RequestMappingConfig> allMappings = reqMappingProvider.getAllRequestMappings();
		LOGGER.info("All the mappings present in the project are {}", allMappings);
		for (RequestMappingConfig requestMappingConfig : allMappings) {
			router.route(requestMappingConfig.getHttpMethod(), requestMappingConfig.getUrl())
					.handler(requestMappingConfig.getHandler());
			LOGGER.info("Request handler set for the route {}", requestMappingConfig);
		}

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		LOGGER.info("Starting the httpserver on port 8080");
	}

	protected void configureExceptionHandler() {
		vertx.exceptionHandler();
	}

}
