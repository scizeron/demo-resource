package com.example.domain;

public class Document {
	
	private String id;
	
	private String owner;

	public Document(String id, String owner) {
		super();
		this.id = id;
		this.owner = owner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	
}
