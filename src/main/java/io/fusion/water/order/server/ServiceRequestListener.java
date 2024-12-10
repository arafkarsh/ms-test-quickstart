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

import java.util.UUID;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;

import org.slf4j.MDC;

/**
 * A ServletRequest is defined as coming into scope of a web application 
 * when it is about to enter the first servlet or filter of the web 
 * application, and as going out of scope as it exits the last servlet or 
 * the first filter in the chain
 * 
 * Source: https://docs.oracle.com/javaee/7/api/javax/servlet/ServletRequestListener.html
 * 
 * @author arafkarsh
 *
 */
@WebListener
public class ServiceRequestListener implements ServletRequestListener {
	
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		MDC.put("RequestId", UUID.randomUUID().toString());
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		MDC.clear();
	}
}