package com.example.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.domain.Document;
import com.google.common.collect.ImmutableMap;

@Service
public class DoocumentServiceImpl implements DocumentService {

	private static Map<String,Document> ALL_DOCS = ImmutableMap.<String,Document>builder()
			.put("123", new Document("123", "demo-user"))
			.put("124", new Document("124", "demo-user2"))
			.put("125", new Document("125", "demo-user2"))
			.build();

	@Override
	public Document findById(String id) {
		return ALL_DOCS.get(id);
	}
}
