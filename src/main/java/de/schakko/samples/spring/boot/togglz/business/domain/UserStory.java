package de.schakko.samples.spring.boot.togglz.business.domain;

import org.springframework.util.Assert;

public class UserStory {
	private String content;
	private Double cost;
	
	public UserStory(String content) {
		this(content, null);
	}

	public UserStory(String content, Double cost) {
		Assert.hasLength(content, "content must not be empty");
		
		this.content = content;
		this.cost = cost;
	}
	
	public String getContent() {
		return content;
	}
	
	public Double getCost() {
		return cost;
	}
}
