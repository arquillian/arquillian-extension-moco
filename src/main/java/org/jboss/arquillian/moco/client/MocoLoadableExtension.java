package org.jboss.arquillian.moco.client;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.spi.LoadableExtension;

public class MocoLoadableExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {

		builder.service(ApplicationArchiveProcessor.class,
				MocoDependencyArchiveProcessor.class);
		builder.service(AuxiliaryArchiveAppender.class,
				MocoArchiveAppender.class);
		
		builder.observer(MocoConfigurator.class);

	}

}
