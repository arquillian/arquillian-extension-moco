package org.jboss.arquillian.moco.container;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.test.spi.TestEnricher;

public class MocoRemoteExtension implements RemoteLoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {
		builder.service(TestEnricher.class, MocoTestEnricher.class);
		builder.observer(MocoLifecycleExecuter.class);
	}

}
