package org.jboss.arquillian.moco.client;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.moco.ReflectionHelper;
import org.jboss.arquillian.moco.api.Moco;
import org.jboss.arquillian.moco.container.MocoRemoteExtension;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class MocoArchiveAppender implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		
		 return ShrinkWrap.create(JavaArchive.class, "arquillian-moco.jar")
		            .addClass(ReflectionHelper.class)
		            .addPackage(Moco.class.getPackage())
		            .addPackage(MocoRemoteExtension.class.getPackage())
		            .addAsServiceProvider(RemoteLoadableExtension.class, MocoRemoteExtension.class);
		
	}

}
