/**
 * (C) Copyright 2021 Araf Karsh Hamid 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.fusion.water.order.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * Test Client to Send Requests to Mock Server
 * 
 * @author arafkarsh
 *
 */
public abstract class AbstractTestClient {

	@Value("${remote.host}")
	private String host = "localhost";
	
	@Value("${remote.port}")
	private int port = 8080;

	/**
	 * Return Web Client
	 * @return
	 */
	/**
	public WebClient getClient() {
		if(client == null) {
			baseURL = "http://" + host + ":" + port;
			// client = WebClient.create(baseURL);
			client  = WebClient.builder()
	        .baseUrl(baseURL)
	        .defaultHeader(
	        		HttpHeaders.CONTENT_TYPE, 
	        		MediaType.APPLICATION_JSON_VALUE)
	        .build();
		}
		return client;
	}
	 */

	/**
	public UriSpec<RequestBodySpec> post() {
		return (UriSpec<RequestBodySpec>) getClient().post();
	}
	 */
	
	/**
	public WebClient getClient(int timeouts)  {
		HttpClient httpClient = HttpClient.create()
				  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				  .responseTimeout(Duration.ofMillis(5000))
				  .doOnConnected(conn -> 
				    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
				      .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

		client = WebClient.builder()
				  .clientConnector(new ReactorClientHttpConnector(httpClient))
				  .build();
	}
	*/

}
