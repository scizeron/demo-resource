package com.example.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.domain.*;

@Service
public class DoocumentServiceImpl implements DocumentService {

	/* (non-Javadoc)
	 * @see com.example.service.DocumentService#getDocumentsByUser(java.lang.String)
	 */
	@Override
	public List<Document> getDocumentsByUser(String userId) {
		if ("demo-user".equals(userId)) {
			return Arrays.asList(new Document("123", userId));
		}
		return null;
	}
}
