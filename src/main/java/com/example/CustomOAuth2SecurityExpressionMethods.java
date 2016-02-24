package com.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods;

/**
 * 
 * @author 212552868
 *
 */
public class CustomOAuth2SecurityExpressionMethods extends OAuth2SecurityExpressionMethods {

	private HttpServletRequest httpServletRequest;
	
	private OAuth2Authentication oAuth2Authentication;
	
	/**
	 * 
	 * @param authentication
	 */
	public CustomOAuth2SecurityExpressionMethods(Authentication authentication, HttpServletRequest httpServletRequest) {
		super(authentication);
		this.oAuth2Authentication = (OAuth2Authentication) authentication;
		this.httpServletRequest = httpServletRequest;
	}
	
	/**
	 * 
	 * @param docId
	 * @return
	 */
	public boolean canRequestThis(String entityType) {
		int pos = this.httpServletRequest.getRequestURI().lastIndexOf('/');
		String docId = this.httpServletRequest.getRequestURI().substring(pos+1);
		String userId = this.oAuth2Authentication.getPrincipal().toString();
		
		// useful to handle complex 'allow/deny' access test
		
		return true;
	
	}
}
