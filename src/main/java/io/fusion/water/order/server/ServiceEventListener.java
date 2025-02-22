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
 
import io.fusion.water.order.utils.Std;
import org.slf4j.MDC;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


//Logging System
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import static java.lang.invoke.MethodHandles.lookup;
import io.fusion.water.order.utils.CPU;

/**
 * 
 * @author arafkarsh
 * @version 1.0
 */
@Configuration
public class ServiceEventListener {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());
	
	// Autowired using the Constructor Injection
	private ServiceConfiguration  serviceConfig;

	/**
	 * Autowired using the Constructor Injection
	 * @param serviceConfig
	 */
	public ServiceEventListener(ServiceConfiguration  serviceConfig) {
		log.info("Service Event Listener is Ready!");
		this.serviceConfig = serviceConfig;
	}
	
	/**
	 * 
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		log.info("Order Service is getting ready...... ");
		String s = CPU.printCpuStats();
	    log.info(s);
	    Std.println(LocalDateTime.now()+"|Order Service is getting ready...... ");
		showLogo();
	}

	/**
	 * Shows the Service Logo and Version Details.
	 */
	public void showLogo() {
		String version="v0.9.0";
		String name="NoName";
		String javaVersion="21";
		String sbVersion="3.3.4";
		String buildNo = "";
		String buildDate = "";
		String counter = "";

		if(serviceConfig != null) {
			version = serviceConfig.getServerVersion();
			name =serviceConfig.getServiceName();
			javaVersion = System.getProperty("java.version");
			sbVersion = SpringBootVersion.getVersion();
			buildNo = "" +serviceConfig.getBuildNumber();
			buildDate = serviceConfig.getBuildDate();
			counter = "" +ServiceHelp.getCounter();
		}
		MDC.put("Service", name);
		String logo =ServiceHelp.LOGO
				.replace("SIGMA", name)
				.replace("MSVERSION", version)
				.replace("JAVAVERSION", javaVersion)
				.replace("SPRINGBOOTVERSION", sbVersion);

		log.info("{}  Service is ready! ... .. {} Build No. = {}   :: Build Date = {}  :: Mode = Testing :: Restart =  {} {} {} ",
				name, logo, buildNo, buildDate, counter ,  ServiceHelp.NL , ServiceHelp.DL);

		String url = serviceConfig.apiURL() + ServiceHelp.NL + ServiceHelp.DL;
		log.info("{} API URL : {}", ServiceHelp.NL , url);
	}
}