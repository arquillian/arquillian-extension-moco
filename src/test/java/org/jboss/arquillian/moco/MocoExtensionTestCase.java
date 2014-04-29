package org.jboss.arquillian.moco;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.xbean.finder.archive.JarArchive;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.moco.api.MockServer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;

@RunWith(Arquillian.class)
public class MocoExtensionTestCase {

	@Deployment(testable=false)
	public static JavaArchive deploy() {
		return ShrinkWrap.create(JavaArchive.class, "myapp")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@ArquillianResource
	URL url;
	
	@Test
	public void shouldGetBitcoinTradeRates() throws IOException {
		
	    System.out.println(url.toExternalForm());
	    System.out.println(url.getPort());
	    
	    int port = url.getPort();
	    
	    MockServerClient mockServer = new MockServerClient("localhost", url.getPort(), "mockserver-war-2.8");
	    
	    mockServer
        .when(
                request()
                        .withMethod("GET")
                        .withPath("/login")
        )
        .respond(
                response()
                        .withBody("{ message: 'incorrect username and password combination' }")
        );
        
        URL url = new URL("http://localhost:"+port+"/mockserver-war-2.8/login");
        InputStream openStream = url.openStream();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(openStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        System.out.println(out.toString());   //Prints the string content read from input stream
        reader.close();
        
        openStream.close();
		
	}
	
}
