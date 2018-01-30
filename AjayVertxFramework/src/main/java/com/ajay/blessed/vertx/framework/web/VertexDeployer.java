package com.ajay.blessed.vertx.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajay.blessed.vertx.framework.rest.BaseVerticle;

import io.vertx.core.Vertx;

public enum VertexDeployer {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(VertexDeployer.class);

	public void start(final Class<?> mainClass) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Starting verticle deplooyment for the project");
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new BaseVerticle(mainClass));
		long endTime = System.currentTimeMillis();
		LOGGER.info("Ending verticle deplooyment for the project");
		LOGGER.info("Total time in deployment of all the vertciles is " + (endTime - startTime));

	}

}
