package org.jboss.arquillian.moco.client;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.event.container.AfterUnDeploy;
import org.jboss.arquillian.container.spi.event.container.BeforeDeploy;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class MockServerDeployer {

    private WebArchive mockServerWebArchive;
	
    public void executeBeforeDeploy(@Observes BeforeDeploy event) throws DeploymentException {
       
        if(mockServerWebArchive == null){
            resolveMockServerArchive();
        }
        
        DeployableContainer<?> deployableContainer = event.getDeployableContainer();
        deployableContainer.deploy(this.mockServerWebArchive);
        
    }

    public void executeAfterUnDeploy(@Observes AfterUnDeploy event) throws DeploymentException {
        
        if(mockServerWebArchive == null){
            resolveMockServerArchive();
        }
        
        DeployableContainer<?> deployableContainer = event.getDeployableContainer();
        deployableContainer.undeploy(this.mockServerWebArchive);
        
    }
    
    private void resolveMockServerArchive() {
        this.mockServerWebArchive = Maven.resolver().resolve("org.mock-server:mockserver-war:war:2.8").withoutTransitivity().asSingle(WebArchive.class);
    }

}
