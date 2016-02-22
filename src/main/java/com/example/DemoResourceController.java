package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE})
public class DemoResourceController {
	
	@Autowired
	private OAuth2RestOperations restOperations;
	
	/**
	 * No scope concern, just a valid token.
	 * Previously, the granted scopes have been validated against 
	 * the client scopes and the authorities user previously
	 * 
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/secureHello")
	@PreAuthorize("#oauth2.hasScope('test')")
	public String secureHello() {
		return "hello";
	}

	@RequestMapping(value={"/principal"})
	public Authentication protectedArea() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@RequestMapping(value={"/annotatedprincipal"})
	@PreAuthorize("#oauth2.hasScope('test')")
	public Authentication annotatedPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@RequestMapping(value={"/missingscope"})
	public Authentication missingScope() {
		// return 403 FORBIDDEN with {"error":"insufficient_scope","error_description":"Insufficient scope for this resource","scope":"sdfsfsfskfhsjdfnsqdnjndqkjn"}
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * Should return the root cause as above 'insufficient_scope' and not only 'access_denied'
	 * @return
	 */
	@RequestMapping(value={"/annotatedprincipalmissingscope"})
	@PreAuthorize("#oauth2.hasScope('sdfsfsfskfhsjdfnsqdnjndqkjn')")
	public Authentication annotatedPrincipalWithMissingScope() {
		// return 403 FORBIDDEN with {"error":"access_denied","error_description":"Access is denied"}
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	
	@RequestMapping(value={"/downstream"})
	@PreAuthorize("#oauth2.hasScope('test')")
	public String downstreamHello() {
		return this.restOperations.exchange("http://localhost:8083/secureHello", HttpMethod.GET, null, new  ParameterizedTypeReference<String>() {}).getBody();
	}
	
	/**
	 * The client has the scopes 'documents.*.read'
	 * The user has the authorities 'documents.123.read'
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/documents/{id}")
	@PreAuthorize("#oauth2.hasScope('documents.*.read')")
	public String getDocument(@PathVariable("id") String id) {
		return String.format("doc: %s",  id);
	}
	
}
