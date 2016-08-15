package de.schakko.samples.spring.boot.togglz.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.schakko.samples.spring.boot.togglz.business.UserStoryFeature;
import de.schakko.samples.spring.boot.togglz.business.domain.UserStoryRepository;

@Controller
public class UserStoryController {
	private UserStoryRepository userStoryRepository;

	public UserStoryController(UserStoryRepository userStoryRepository) {
		Assert.notNull(userStoryRepository, "userStoryRepository must not be null");
		
		this.userStoryRepository = userStoryRepository;
	}

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView r = new ModelAndView("index");
		
		r.addObject("stories", this.userStoryRepository.findAll());
		// assign the Thymeleaf variable "enable_jira_export" to the feature state
		r.addObject("enable_jira_export", UserStoryFeature.EXPORT_TO_JIRA.isActive());
		
		return r;
	}
}
