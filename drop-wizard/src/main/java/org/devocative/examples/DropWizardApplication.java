package org.devocative.examples;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardApplication extends Application<DropWizardConfiguration> {

	public static void main(final String[] args) throws Exception {
		new DropWizardApplication().run(args);
	}

	@Override
	public String getName() {
		return "DropWizard";
	}

	@Override
	public void initialize(final Bootstrap<DropWizardConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final DropWizardConfiguration configuration,
					final Environment environment) {
		// TODO: implement application
	}

}
