package de.schakko.samples.spring.boot.togglz.business.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserStoryRepository {
	/**
	 * Get existing user stories
	 * 
	 * @return
	 */
	public List<UserStory> findAll() {
		List<UserStory> r = new ArrayList<>();
		r.add(new UserStory("As a user I want to get a higher pay", 10d));
		r.add(new UserStory("As a manager I want to reduce payments", 100d));

		return r;
	}
}
