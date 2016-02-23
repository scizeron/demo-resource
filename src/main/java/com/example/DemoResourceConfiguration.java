package com.example;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableEurekaClient
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class DemoResourceConfiguration extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/hello").permitAll()
				.antMatchers("/principal").access("#oauth2.hasScope('test')")
				.antMatchers("/missingscope").access("#oauth2.hasScope('sdfsfsfskfhsjdfnsqdnjndqkjn')")
				.antMatchers("/documents/**").access("#oauth2.hasScopeMatching('documents.*.read') and #oauth2c.canRequestThis('doc')")
				.anyRequest().authenticated();
	}


	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// disable the resourceId check => OAuth2AuthenticationManager authenticate
//		Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();
//		if (resourceId != null && resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(resourceId)) {
//			throw new OAuth2AccessDeniedException("Invalid token does not contain resource id (" + resourceId + ")");
//		}
        resources.resourceId(null);
        
        resources.expressionHandler(new CustomOAuth2WebSecurityExpressionHandler());
    }
}
