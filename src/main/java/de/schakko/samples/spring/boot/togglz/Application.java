package de.schakko.samples.spring.boot.togglz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.NoOpUserProvider;
import org.togglz.core.user.UserProvider;

import de.schakko.samples.spring.boot.togglz.business.UserStoryFeature;

@SpringBootApplication(scanBasePackageClasses = Application.class)
public class Application {
	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public FeatureProvider featureProvider() {
		return new EnumBasedFeatureProvider(UserStoryFeature.class);
	}
	
	@Bean
	public UserProvider userProvider() {
		return new NoOpUserProvider();
	}
}
