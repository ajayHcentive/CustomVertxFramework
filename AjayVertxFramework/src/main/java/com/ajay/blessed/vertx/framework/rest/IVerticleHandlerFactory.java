package com.ajay.blessed.vertx.framework.rest;

import java.lang.reflect.Method;

import io.vertx.core.Handler;

public interface IVerticleHandlerFactory<E> {

	Handler<E> buildHandler(Class<?> clazz, Method method);
}
