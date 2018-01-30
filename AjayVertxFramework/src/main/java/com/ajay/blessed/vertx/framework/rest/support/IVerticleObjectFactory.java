package com.ajay.blessed.vertx.framework.rest.support;

public interface IVerticleObjectFactory {

	<T> Object getRestVerticleObj(Class<T> clazz);

}
