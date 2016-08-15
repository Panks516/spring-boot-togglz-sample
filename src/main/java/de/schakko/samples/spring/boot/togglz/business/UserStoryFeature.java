package de.schakko.samples.spring.boot.togglz.business;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

/**
 * {@link Label} is used in the Togglz console to describe the feature.
 * 
 * @author ckl
 *
 */
public enum UserStoryFeature implements Feature {
	@Exporting
	@EnabledByDefault
	@Label("Export to JIRA")
	EXPORT_TO_JIRA,

	@CostPlanning
	@Label("Cost is viewable")
	COST_IS_VIEWABLE,

	@CostPlanning
	@Label("Cost ist editable")
	COST_IS_EDITABLE;

	public boolean isActive() {
		return FeatureContext.getFeatureManager().isActive(this);
	}
}
