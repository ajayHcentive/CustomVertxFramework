package com.ajay.blessed.vertx.framework.rest.support;

import java.util.Set;

import com.ajay.blessed.vertx.framework.web.RequestMappingConfig;

public interface IRequestMappingProvider {

	Set<RequestMappingConfig> getAllRequestMappings();

}
