package com.ajay.blessed.vertx.framework.rest.support;

import java.lang.reflect.Method;

import com.ajay.blessed.vertx.framework.web.RequestMappingConfig;

public interface IRequestMappingAnnotationProcessor {

	<T> RequestMappingConfig processRequestMapping(Class<T> clazz, Method method);

}
