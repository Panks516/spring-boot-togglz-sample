# Feature Toggles Using Spring Boot with Togglz
This repository has been added for the [http://hackcamp-wolfsburg.de/](Wolfsburger Hack-Talk).

## What are feature toggles?
Feature toggles can be easily applied to enable or disable specific features of an application. [Martin Fowler blogged about it in 2010](http://martinfowler.com/bliki/FeatureToggle.html), a more detailed blog post has been [written by Pete Hodgson](http://martinfowler.com/articles/feature-toggles.html).

## Togglz
Togglz is an easy-to-use framework for Java which allows you to implement feature toggles. It comes with a simple but sufficient UI to enable or disable features on the fly.
Togglz can be easily integrated into JSF or Spring Boot application.

## About this sample
This sample contains a very simple Spring Boot web application showing user stories and their costs. The source code is documented so you can dig through it.

### Features
Features can be defined in different ways. I use enumerations which can be found in the `UserStoryFeature` class.
The features are registered by using the 

	@Bean
	public FeatureProvider featureProvider() {
		return new EnumBasedFeatureProvider(UserStoryFeature.class);
	}

in the entry point `Application` class.

### Access a feature from Java
The activation state of a feature can be checked by using

		UserStoryFeature.EXPORT_TO_JIRA.isActive()
		
see `UserStoryController`.

### Access a feature from a Thymeleaf view
By using 

		<dependency>
			<groupId>com.github.heneke.thymeleaf</groupId>
			<artifactId>thymeleaf-extras-togglz</artifactId>
			<version>1.0.1.RELEASE</version>
		</dependency>

in your pom.xml the Thymeleaf dialect is extended with the directives `togglz:active` and `togglz:inactive`. The last one is not mentioned in the documentation.

You can see the usage in `index.html`

### Access a feature by using Spring Expression Language (SpEL)
The Thymeleaf template `index.html` does also contain the following SpEL construct:

		<li>Cost edit: <span
			th:text="${T(de.schakko.samples.spring.boot.togglz.business.UserStoryFeature).COST_IS_EDITABLE.isActive() == true} ? 'Yes' : 'No'"></span>
		</li>

A feature enumeration has access to its state so you can use the feature from your whole Spring application. In conjunction with [Spring Data REST's projections](http://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts.projections) and Spring Security you could for example provide different versions of your REST API.

### The console
Below the URL `/togglz-console` you can enable or disable the features this application has.

### Feature groups
By using the `@FeatureGroup` annotation on your custom annotation you can define groups of features. The annotations `@CostPlanning` and `@Exporting` are used for grouping the features on different pages in the Togglz console.

### Activation strategies
For A/B testing, security, beta testing or other purposes you can enable assign one activation strategy for a feature in the console. For example you can add a host entry to your `/etc/hosts` 

	prod	127.0.0.1
	beta	127.0.0.1
	
and use the activation strategy `Server names (vhosts)` do enable a feature only for specific host.

### Custom activation strategy
I added a `RandomActivationStrategy` which returns, well, random results. If you want to add a custom activation strategy you have to add your strategy class to `META-INF/services/org.togglz.core.spi.ActivationStrategy`.

### Using ECMAScript to run a custom strategy
For simple usage you can define a custom strategy by using ECMAScript.
The `RandomActivationStrategy` can be replaced by this one-liner:

	(Math.floor(Math.random() * 100) % 2) == 0

Please note that you will receive different results with __each__ *isActive* call. This means that you randomly get returned true/false on the same request. In the `RandomActivationStrategy` is resolved this issue by using a `ThreadLocal` instance.
