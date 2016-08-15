package de.schakko.samples.spring.boot.togglz.business;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum UserStoryFeature implements Feature {
	@EnabledByDefault
	@Label("Export to JIRA")
	EXPORT_TO_JIRA,

	@Label("Cost is viewable")
	COST_IS_VIEWABLE,

	@Label("Cost ist editable")
	COST_IS_EDITABLE;

	public boolean isActive() {
		return FeatureContext.getFeatureManager().isActive(this);
	}
}
