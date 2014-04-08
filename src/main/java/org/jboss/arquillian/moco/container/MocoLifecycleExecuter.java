package org.jboss.arquillian.moco.container;

import static com.google.common.base.Optional.of;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.moco.api.Moco;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.annotation.ClassScoped;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import com.github.dreamhead.moco.runner.JsonRunner;
import com.google.common.io.Resources;

public class MocoLifecycleExecuter {

    
	@Inject @ClassScoped
	private InstanceProducer<MocoLocation> mocoLocationInstanceProducer;
	
	JsonRunner jsonRunner = null;
	
	public void executeBeforeClass(@Observes(precedence = 25) BeforeClass beforeClass, TestClass testClass) {

		if(testClass.isAnnotationPresent(Moco.class)) {
		
			Moco moco = testClass.getAnnotation(Moco.class);
			
			final String mocoFile = moco.mocoFile();

			try {
				List<InputStream> streams = new ArrayList<InputStream>();
				streams.add(Resources.getResource(mocoFile).openStream());
				jsonRunner = JsonRunner.newJsonRunnerWithStreams(
						streams, of(12306));
				
				mocoLocationInstanceProducer.set(new MocoLocation(new URL("http://localhost:12306")));
				
				jsonRunner.run();
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public void executeAfterClass(@Observes(precedence = 25) AfterClass afterClass) {

		if(jsonRunner != null) {
		    try {
		        jsonRunner.stop();
		        TimeUnit.SECONDS.sleep(3);
		    } catch(Exception e) {
		        throw new IllegalStateException(e);
		    }
		}
	}
	
}
