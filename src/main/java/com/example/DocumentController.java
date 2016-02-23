package com.example;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/documents"}, produces={MediaType.APPLICATION_JSON_VALUE})
public class DocumentController {
			
	/**
	 * The client has the scopes 'documents.*.read'
	 * The user has the authorities 'documents.123.read'
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}")
	public String getDocumentById(@PathVariable("id") String id) {
		return String.format("doc: %s",  id);
	}
	
}
