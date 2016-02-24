package com.example.service;

import com.example.domain.Document;

public interface DocumentService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Document findById(String id);

}