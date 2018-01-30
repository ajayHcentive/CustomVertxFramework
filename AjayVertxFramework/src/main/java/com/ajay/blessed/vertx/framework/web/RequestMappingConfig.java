package com.ajay.blessed.vertx.framework.web;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class RequestMappingConfig {

	private HttpMethod httpMethod;
	private String url;
	private Handler<RoutingContext> handler;

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "RequestMappingConfig [httpMethod=" + httpMethod + ", url=" + url + ", handler=" + handler + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMappingConfig other = (RequestMappingConfig) obj;
		if (httpMethod != other.httpMethod)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Handler<RoutingContext> getHandler() {
		return handler;
	}

	public void setHandler(Handler<RoutingContext> handler) {
		this.handler = handler;
	}

}
