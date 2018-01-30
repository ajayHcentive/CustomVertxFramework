package com.ajay.blessed.vertx.framework.exception;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class JsonFailureHandler implements IFailureHandler {

	@Override
	public void handleError(final RoutingContext ctx) {

		final JsonObject error = new JsonObject().put("timestamp", System.nanoTime())
				.put("exception", ctx.failure().getClass().getName())
				.put("exceptionMessage", ctx.failure().getMessage()).put("path", ctx.request().path());

		ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
		ctx.response().end(error.encode());

	}

}
