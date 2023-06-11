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
package io.fusion.water.order.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Service Configuration
 * 
 * @author arafkarsh
 *
 */
@Component
@Configuration
@PropertySource(
		name = "serviceConfig", 
		value = "classpath:application.properties")
public class ServiceConfiguration {
	
	@Value("${build.number:13}")
	private int buildNumber;
	
	@Value("${build.date}")
	private String buildDate;
	
	@Value("${server.version:0.5.0}")
	private String serverVersion;
	
	@Value("${server.port:9080}")
	private int serverPort;
	
	@Value("${payment.gateway.host:localhost}")
	private String paymentGWHost;
	@Value("${payment.gateway.port:9091}")
	private int paymentGWPort;
	
	@Value("${remote.host:localhost}")
	private String remoteHost;
	@Value("${remote.port:9091}")
	private int remotePort;
	
	@Value("${server.restart:false}")
	private boolean serverRestart;
	
	// @Value("${logging.level}")
	private String loggingLevel;
	
	@Value("${spring.codec.max-in-memory-size:3MB}")
	private String springCodecMaxMemory;
	
	@Value("${token.key:sigmaEpsilon6109871597}")
	private String tokenKey;

	// Get All the System Properties
	@Value("#{systemProperties}")
	private HashMap<String, String> systemProperties;
	
	// Deployed App Property List
	@Value("${app.property.list}")
	private ArrayList<String> appPropertyList;
	
	// Deployed App Properties Map
	@Value("#{${app.property.map}}")
	private HashMap<String, String> appPropertyMap;
	
	/**
	 * To be used outside SpringBoot Context
	 * For WireMock Testing the External Services
	 */
	public ServiceConfiguration() {
		this("localhost", 8080);
	}
	
	/**
	 * To be used outside SpringBoot Context
	 * For WireMock Testing the External Services
	 * 
	 * @param rHost
	 * @param rPort
	 */
	public ServiceConfiguration(String rHost, int rPort) {
		this.remoteHost = rHost;
		this.remotePort = rPort;
	}
	
	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @return the paymentGWHost
	 */
	public String getPaymentGWHost() {
		return paymentGWHost;
	}

	/**
	 * @return the paymentGWPort
	 */
	public int getPaymentGWPort() {
		return paymentGWPort;
	}

	/**
	 * @return the remoteHost
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * @return the remotePort
	 */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * @return the springCodecMaxMemory
	 */
	public String getSpringCodecMaxMemory() {
		return springCodecMaxMemory;
	}

	/**
	 * @return the tokenKey
	 */
	public String getTokenKey() {
		return tokenKey;
	}
	
	/**
	 * @return the serverVersion
	 */
	public String getServerVersion() {
		return serverVersion;
	}

	/**
	 * @return the serverRestart
	 */
	public boolean isServerRestart() {
		return serverRestart;
	}

	/**
	 * @return the loggingLevel
	 */
	public String getLoggingLevel() {
		return loggingLevel;
	}

	/**
	 * @return the buildNumber
	 */
	public int getBuildNumber() {
		return buildNumber;
	}

	/**
	 * @return the buildDate
	 */
	public String getBuildDate() {
		return buildDate;
	}

	/**
	 * @return the systemProperties
	 */
	public HashMap<String, String> getSystemProperties() {
		return systemProperties;
	}

	/**
	 * @return the appPropertyList
	 */
	public ArrayList<String> getAppPropertyList() {
		return appPropertyList;
	}

	/**
	 * @return the appPropertyMap
	 */
	public HashMap<String, String> getAppPropertyMap() {
		return appPropertyMap;
	}
}
