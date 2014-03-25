package org.jboss.arquillian.moco;

import retrofit.client.Response;
import retrofit.http.GET;

public interface BitcoinService {

	@GET("/currencies/exchange_rates")
	Response rates();
	
}
