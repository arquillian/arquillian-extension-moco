package org.jboss.arquillian.moco;

import java.io.IOException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.moco.api.Moco;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit.RestAdapter;

@RunWith(Arquillian.class)
@Moco(mocoFile = "apirates/content.json")
public class MocoExtensionTestCase {

	@Deployment
	public static WebArchive deploy() {
		return ShrinkWrap.create(WebArchive.class)
				.addClass(BitcoinService.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(Maven
						.resolver()
						.loadPomFromFile("pom.xml")
						.resolve("com.squareup.retrofit:retrofit:1.4.1")
						.withTransitivity().asFile());
	}

	@Test
	public void shouldGetBitcoinTradeRates() throws IOException {
		
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				"http://localhost:12309").build();

		BitcoinService service = restAdapter.create(BitcoinService.class);
		System.out
				.println(convertStreamToString(service.rates().getBody().in()));
		
	}
	
	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
}
