package org.jboss.arquillian.moco;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.moco.api.Moco;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Moco(mocoFile = "apirates/content.json")
public class MocoExtensionTestCase {

	@Deployment
	public static WebArchive deploy() {
		return ShrinkWrap.create(WebArchive.class).addClass(BitcoinService.class);
	}

	@Test
	public void shouldGetBitcoinTradeRates() {
		
		System.out.println("Hello");
		
	}
	
}
