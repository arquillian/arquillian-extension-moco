package org.jboss.arquillian.moco.client;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.moco.api.Moco;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class MocoDependencyArchiveProcessor implements
		ApplicationArchiveProcessor {

	private static final String MOCO_CORE_COORDINATES = "com.github.dreamhead:moco-core";
	private static final String MOCO_RUNNER_COORDINATES = "com.github.dreamhead:moco-runner";

	@Override
	public void process(Archive<?> applicationArchive, TestClass testClass) {

		if (testClass.isAnnotationPresent(Moco.class)) {
			
			if (applicationArchive instanceof LibraryContainer) {

				Moco moco = testClass.getAnnotation(Moco.class);

				LibraryContainer<?> container = (LibraryContainer<?>) applicationArchive;

				container
						.addAsLibraries(Maven
								.resolver()
								.loadPomFromFile("pom.xml")
								.resolve(MOCO_CORE_COORDINATES,
										MOCO_RUNNER_COORDINATES)
								.withTransitivity().asFile());

				final JavaArchive additionalResources = ShrinkWrap.create(
						JavaArchive.class,
						"arquillian-moco-extension-additional-resources.jar");
				merge(additionalResources,
						createArchiveWithResources(moco.mocoFile()));

				addResources(applicationArchive, additionalResources);
				
			}
		}

	}

	private void addResources(Archive<?> applicationArchive,
			final JavaArchive dataArchive) {
		if (JavaArchive.class.isInstance(applicationArchive)) {
			applicationArchive.merge(dataArchive);
		} else {
			final LibraryContainer<?> libraryContainer = (LibraryContainer<?>) applicationArchive;
			libraryContainer.addAsLibrary(dataArchive);
		}
	}
	
	private void merge(final JavaArchive target,
			final JavaArchive... archivesToMerge) {
		for (JavaArchive archiveToMerge : archivesToMerge) {
			target.merge(archiveToMerge);
		}
	}


	private JavaArchive createArchiveWithResources(String... resourcePaths) {
		final JavaArchive dataSetsArchive = ShrinkWrap
				.create(JavaArchive.class);

		for (String path : resourcePaths) {
			dataSetsArchive.addAsResource(path);
		}

		return dataSetsArchive;
	}

}
