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
package io.fusion.water.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Jakarta
import jakarta.annotation.PostConstruct;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletRequest;

import io.fusion.water.order.server.ServiceConfiguration;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
// Faster XML
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
// Swagger Open API
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

// Logging System
import org.slf4j.Logger;
import org.springdoc.core.GroupedOpenApi;

import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;

/**
 * Order Service - Spring Boot Application
 * 
 * @author arafkarsh
 *
 */
@Controller
@EnableScheduling
@ServletComponentScan
@RestController
@SpringBootApplication(scanBasePackages = { "io.fusion.water.order" })
public class OrderApplication {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());
	
	private static final String TITLE = "<h1>Welcome to (Ms-Test-Quickstart) Order Service<h1/>"
								+"<h3>Copyright (c) Araf Karsh Hamid, 2021-24</h3>";
	
	private static ConfigurableApplicationContext context;

	// Autowired using Constructor Injection
	@Autowired
	private ServiceConfiguration serviceConfig;

	// Get the Service Name from the properties file
	@Value("${service.name:NameNotDefined}")
	private String serviceName = "Unknown";

	/**
	 * Autowired using Constructor Injection
	 * @param serviceConfig
	 */
	/**
	@Autowired
	private OrderApplication(ServiceConfiguration serviceConfig) {
		this.serviceConfig = serviceConfig;
	}
	*/

	/**
	 * Start the Order Service
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Start the Server
		start(args);
		// API URL : http://localhost:9081/test-service/api/v1/swagger-ui.html
	}
	
	/**
	 * Start the Server
	 * @param args
	 */
	public static void start(String[] args) {
		log.info("Booting (MS-Test-QuickStart) Order Service ..... ..");
		try {
			context = SpringApplication.run(OrderApplication.class, args);
			log.info("Booting (MS-Test-QuickStart) Order Service ..... ...Startup completed!");
		} catch (Exception e) {
			log.trace(e.getMessage());
		}
	}
	
	/**
	 * Restart the Server
	 */
    public static void restart() {
		log.info("Restarting Order Service ..... .. 1");
        ApplicationArguments args = context.getBean(ApplicationArguments.class);
		log.info("Restarting Order Service ..... .. 2");

        Thread thread = new Thread(() -> {
            context.close();
            start(args.getSourceArgs());
        });
		log.info("Restarting Order Service ..... .. 3");

        thread.setDaemon(false);
        thread.start();
    }
	
	/**
	 * Load the Configuration 
	 */
	@PostConstruct
	public void configure() {
		// Load the Configuration
	}

	/**
	 * Micro Service - Home Page
	 * @return
	 */
	@GetMapping("/root")
	public String home(HttpServletRequest request) {
		String str = printRequestURI(request);
		log.info("Request to Home Page of Service... {} ", str );
		return (serviceConfig == null) ? TITLE :
				TITLE.replace("MICRO", serviceConfig.getServiceName())
						.replace("COMPANY", serviceConfig.getServiceOrg())
						.replace("BN", "" + serviceConfig.getBuildNumber())
						.replace("BD", serviceConfig.getBuildDate());
	}

	/**
	 * Print the Request
	 *
	 * @param request
	 * @return
	 */
	public static String printRequestURI(final HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("URI: ").append(request.getRequestURI());
		String[] req = request.getRequestURI().split("/");
		sb.append("Params Size = "+req.length+" : ");
		for(int x=0; x < req.length; x++) {
			sb.append(req[x]).append("|");
		}
		sb.append("\n");
		String str = sb.toString();
		log.info("HttpServletRequest: [{}]",str);
		return str;
	}

	/**
	 * CommandLineRunner Prints all the Beans defined ...
	 * @param ctx
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.debug(beanName);
			}
		};
	}
	
	/**
	 * Open API v3 Docs - All
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public GroupedOpenApi allPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/**")
			.build();
	}
	
	/**
	 * Open API v3 Docs - Order
	 * @return
	 */
	@Bean
	public GroupedOpenApi orderPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-core")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/**")
				.pathsToExclude(serviceConfig.getServiceApiPath()+"/service/**", serviceConfig.getServiceApiPath()+"/config/**")
				.build();
	}

	/**
	 * Open API v3 Docs - Core Service
	 * Reference: https://springdoc.org/faq.html
	 * Change the Resource Mapping in HealthController
	 *
	 */
	@Bean
	public GroupedOpenApi configPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-config")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/config/**")
				.build();
	}

	@Bean
	public GroupedOpenApi systemPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-health")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/service/**")
				.build();
	}

	/**
	 * Open API v3
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public OpenAPI buildOpenAPI() {
		return new OpenAPI()
				.servers(getServers())
				.info(new Info()
						.title(serviceConfig.getServiceName()+" Service")
						.description(serviceConfig.getServiceDetails())
						.version(serviceConfig.getServerVersion())
						.license(new License().name("License: "+serviceConfig.getServiceLicense())
								.url(serviceConfig.getServiceUrl()))
				)
				.externalDocs(new ExternalDocumentation()
						.description(serviceConfig.getServiceName()+" Service Source Code")
						.url(serviceConfig.getServiceApiRepository())
				)
				.components(new Components().addSecuritySchemes("bearer-key",
						new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT"))
				);
	}

	/**
	 * Get the List of Servers for Open API Docs - Swagger
	 * @return
	 */
	private List<Server> getServers() {
		List<Server> serverList = new ArrayList<>();

		Server dev = new Server();
		dev.setUrl(serviceConfig.getServerHostDev());
		dev.setDescription(serviceConfig.getServerHostDevDesc());
		Server uat = new Server();
		uat.setUrl(serviceConfig.getServerHostUat());
		uat.setDescription(serviceConfig.getServerHostUatDesc());
		Server prod = new Server();
		prod.setUrl(serviceConfig.getServerHostProd());
		prod.setDescription(serviceConfig.getServerHostProdDesc());

		serverList.add(dev);
		serverList.add(uat);
		serverList.add(prod);

		return serverList;
	}
	
	/**
	 * Returns the REST Template
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Returns the Object Mapper
	 * @return
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.findAndRegisterModules();
	}
}
