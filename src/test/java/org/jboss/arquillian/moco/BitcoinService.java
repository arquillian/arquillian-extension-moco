package org.jboss.arquillian.moco;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;


public class BitcoinService {

	public String rates() {
	    
	    WebClient client = WebClient.create("http://localhost:8080/mockserver-war-2.8");
	    return client.path("rates").accept(MediaType.APPLICATION_JSON).get(String.class);
	    
	}
	
}
