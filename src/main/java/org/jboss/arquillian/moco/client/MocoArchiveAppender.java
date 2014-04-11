package org.jboss.arquillian.moco.client;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.moco.ReflectionHelper;
import org.jboss.arquillian.moco.api.Moco;
import org.jboss.arquillian.moco.container.MocoRemoteExtension;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class MocoArchiveAppender implements AuxiliaryArchiveAppender {

    @Inject
    private Instance<MocoConfiguration> config;
    
    @Override
    public Archive<?> createAuxiliaryArchive() {

        return ShrinkWrap.create(JavaArchive.class, "arquillian-moco.jar").addClass(ReflectionHelper.class)
                .addPackage(Moco.class.getPackage()).addPackage(MocoRemoteExtension.class.getPackage())
                .addClass(MocoConfiguration.class)
                .merge(createArchiveWithConfigurationParameters())
                .addAsServiceProvider(RemoteLoadableExtension.class, MocoRemoteExtension.class);

    }

    private JavaArchive createArchiveWithConfigurationParameters() {

        final JavaArchive configurationArchive = ShrinkWrap.create(JavaArchive.class);
        configurationArchive.add(new StringAsset(config.get().toProperties()), MocoConfiguration.MOCO_PROPERTIES_FILENAME);

        return configurationArchive;
    }

}
