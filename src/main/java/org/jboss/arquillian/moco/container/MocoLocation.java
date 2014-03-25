package org.jboss.arquillian.moco.container;

import java.net.URL;

public class MocoLocation {

	private URL mocoUrl;
	
	public MocoLocation(URL mocoUrl) {
		this.mocoUrl = mocoUrl;
	}
	
	public URL getMocoUrl() {
		return mocoUrl;
	}
	
}
