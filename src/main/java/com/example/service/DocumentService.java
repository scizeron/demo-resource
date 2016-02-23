package com.example.service;

import java.util.List;

import com.example.domain.Document;

public interface DocumentService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Document> getDocumentsByUser(String userId);

}