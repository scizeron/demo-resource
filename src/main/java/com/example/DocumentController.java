package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Document;
import com.example.service.DocumentService;

@RestController
@RequestMapping(value={"/documents"}, produces={MediaType.APPLICATION_JSON_VALUE})
public class DocumentController {
	
	@Autowired
	private DocumentService documentService;
			
	/**
	 * The client has the scopes 'documents.*.read'
	 * The user has the authorities 'documents.123.read'
	 * 
	 * Despite the user has only the authority documents.123.read, a request /documents/124 is allowed
	 * - request /authorize with scope=documents.*.read vs documents.123.read : ok => token
	 * - incomming request /documents/124 : 
	 * 			#oauth2.hasScopeMatching('documents.*.read') is ok 
	 * 			because 'documents.*.read' matches the authority user 'documents.123.read'
	 * The id 124 doesn't take part in the permission evaluation
	 *  
	 * @see org.springframework.security.oauth2.provider.expression.OAuth2ExpressionUtils#hasAnyScopeMatching(org.springframework.security.core.Authentication, String[])
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}")
	@PostAuthorize("returnObject.owner == principal")
	public Document getDocumentById(@PathVariable("id") String id) {
		return this.documentService.findById(id);
	}
}
