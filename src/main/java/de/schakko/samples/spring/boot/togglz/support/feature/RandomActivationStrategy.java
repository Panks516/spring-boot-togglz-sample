package de.schakko.samples.spring.boot.togglz.support.feature;

import java.util.Random;

import org.springframework.util.StringUtils;
import org.togglz.core.activation.Parameter;
import org.togglz.core.activation.ParameterBuilder;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.spi.ActivationStrategy;
import org.togglz.core.user.FeatureUser;

/**
 * Based upon the value of {@link #FREQUENCY_KEY} the feature is enabled or
 * disabled. As described in <a href=
 * "http://www.togglz.org/documentation/activation-strategies.html#custom">http://www.togglz.org/documentation/activation-strategies.html#custom</a>
 * this activation strategy has been activated by adding it to
 * <em>META-INF/services/org.togglz.core.spi.ActivationStrategy</em>.
 * 
 * @author ckl
 *
 */
public class RandomActivationStrategy implements ActivationStrategy {
	private static int DEFAULT_FREQUENCY = 2;
	private static String FREQUENCY_KEY = "frequency";

	private ThreadLocal<Integer> randomValueHolder = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return null;
		}
	};

	@Override
	public String getId() {
		return "random";
	}

	@Override
	public String getName() {
		return "Random strategy";
	}

	@Override
	public boolean isActive(FeatureState featureState, FeatureUser user) {
		int frequency = DEFAULT_FREQUENCY;
		String configuredFrequeny = featureState.getParameter(FREQUENCY_KEY);

		if (StringUtils.hasText(configuredFrequeny)) {
			try {
				frequency = Integer.valueOf(configuredFrequeny);
			} catch (Exception e) {
				// ignore
			}
		}

		// isActive is called on *every* request. This means that on multiple
		// isActive() calls on the same Thymeleaf page a different result could
		// be reproduced. To have a consistent result during an HTTP request we
		// use a ThreadLocal instance to keep the value in the current
		// request/thread.
		if (randomValueHolder.get() == null) {
			randomValueHolder.set((new Random()).nextInt());
		}

		return (randomValueHolder.get() % frequency) == 0;
	}

	@Override
	public Parameter[] getParameters() {
		// these parameters are exported to the togglz console
		return new Parameter[] { ParameterBuilder.create(FREQUENCY_KEY).label("Frequency of activation").description(
				"a higher value means lower frequency. Try with 2 and 3, defaults to " + DEFAULT_FREQUENCY) };
	}

}
