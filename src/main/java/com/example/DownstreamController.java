package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class DownstreamController {

	@Autowired
	@LoadBalanced
	private OAuth2RestOperations restOperations;

	@RequestMapping(value = { "/downstream" })
	@PreAuthorize("#oauth2.hasScope('test')")
	public String downstreamHello() {
		return this.restOperations.exchange("http://demo-resource/secureHello", HttpMethod.GET, null,
				new ParameterizedTypeReference<String>() {
				}).getBody();
	}
}
