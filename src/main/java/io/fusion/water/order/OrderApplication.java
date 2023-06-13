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

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;

import io.fusion.water.order.server.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	private final String title = "<h1>Welcome to Order Service<h1/>"
								+"<h3>Copyright (c) MetaArivu Pvt Ltd, 2021</h3>";
	
	private static ConfigurableApplicationContext context;

	@Autowired
	private ServiceConfiguration serviceConfig;
	
	/**
	 * Start the Order Service
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Start the Server
		start(args);
	}
	
	/**
	 * Start the Server
	 * @param args
	 */
	public static void start(String[] args) {
		log.info("Booting Order Service ..... ..");
		System.out.println("Booting Order Service ..... ..");

		try {
			context = SpringApplication.run(OrderApplication.class, args);
			log.info("Booting Order Service ..... ...Startup completed!");
			System.out.println(LocalDateTime.now()+"|Booting Order Service ..... ...Startup completed!");

		} catch (Exception e) {
			e.printStackTrace();
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
	}
	
	/**
	 * Order Service - Home Page
	 * @return
	 */
	// @GetMapping("/")
	public String home() {
		System.out.println(LocalDateTime.now()+"|Request to Home Page of Order Service... ");
		return this.title;
	}

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
	 * @return
	 */
	@Bean
	public GroupedOpenApi allPublicApi() {
		return GroupedOpenApi.builder()
			.group("ms-order-service")
			.pathsToMatch("/api/**")
			.build();
	}
	
	/**
	 * Open API v3 Docs - Order
	 * @return
	 */
	@Bean
	public GroupedOpenApi orderPublicApi() {
		return GroupedOpenApi.builder()
			.group("ms-order-service-order")
			.pathsToMatch("/api/v1/order/**")
			.build();
	}
	
	/**
	 * Open API v3 Docs - Core Service
	 * @return
	 */
	@Bean
	public GroupedOpenApi servicePublicApi() {
		return GroupedOpenApi.builder()
			.group("ms-order-service-core")
			.pathsToMatch("/api/v1/order/service/**")
			.build();
	}
	
	@Bean
	public OpenAPI orderOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Order Microservice")
				.description("Microservices Testing Strategies - Guide")
				.version(getServerVersion())
				.license(new License().name("License: Apache 2.0")
					.url("http://www.metarivu.com"))
				)
			.externalDocs(new ExternalDocumentation()
				.description("Order Service Source Code")
				.url("https://github.com/MetaArivu/ms-order-service"));
	}

	private String getServerVersion() {
		return (serviceConfig != null) ? serviceConfig.getServerVersion() : "v0.0.0";
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
	
	/**
	 * All file upload till 512 MB
	 * returns MultipartConfigElement
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(500000000L));
		return factory.createMultipartConfig();
	}
}
