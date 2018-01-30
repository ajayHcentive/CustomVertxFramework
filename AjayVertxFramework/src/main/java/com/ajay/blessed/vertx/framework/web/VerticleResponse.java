package com.ajay.blessed.vertx.framework.web;

public class VerticleResponse implements Response {

	private Object entity;

	private int status;

	public VerticleResponse(final Object entity, final int status) {
		this.entity = entity;
		this.status = status;
	}

	@Override
	public Object getEntity() {
		return this.entity;
	}

	@Override
	public int getStatus() {
		return this.status;
	}

}
