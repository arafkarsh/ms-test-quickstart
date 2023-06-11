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
package io.fusion.water.order.adapters.external;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * 
 * 
 * @author arafkarsh
 *
 */
@Service
public class RestClientService  extends RestTemplate {
	
    public RestClientService() {
    	// Set Object Mapper For Serialization
    	setMessageConverters(getDataConverters1());
    	// setMessageConverters(getDataConverters2());
        // Set Factory to RestTemplate
        super.setRequestFactory(getHttpFactory());
    }
    
    /**
     * Returns Converters 1
     * @return
     */
    public List<HttpMessageConverter<?>> getDataConverters1() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = 
				new MappingJackson2HttpMessageConverter(getObjectMapper());
		converter.setSupportedMediaTypes(
				Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		return messageConverters;
    }
    
    /**
     * Returns Converters 2
     * @return
     */
    public List<HttpMessageConverter<?>> getDataConverters2() {
    	return asList(
    			new MappingJackson2HttpMessageConverter(
				getObjectMapper())
    		);
    }
    
    /**
     * Return Object Mapper
     * @return
     */
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        .findAndRegisterModules();
    }
    
    /**
     * Return HttpComponentsClientHttpRequestFactory
     * @return
     */
    public HttpComponentsClientHttpRequestFactory getHttpFactory() {
        HttpComponentsClientHttpRequestFactory factory 
    		= new HttpComponentsClientHttpRequestFactory();

	    factory.setConnectTimeout(10000);
	    factory.setReadTimeout(10000);
	    return factory;
    }
}
