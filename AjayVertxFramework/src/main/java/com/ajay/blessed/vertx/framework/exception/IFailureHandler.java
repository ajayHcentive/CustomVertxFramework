package com.ajay.blessed.vertx.framework.exception;

import io.vertx.ext.web.RoutingContext;

public interface IFailureHandler {

	void handleError(RoutingContext rc);

}
