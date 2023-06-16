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

package test.fusion.water.order.junit.junit5.extensions;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * Test Time Extension
 * 
 * @author arafkarsh
 *
 */
public class TestTimeExtension implements InvocationInterceptor {

	/**
	 * Intercept Test Method to calculate the Time
	 * (it took to run the test).
	 */
	public void interceptTestMethod(Invocation<Void> invocation,
            ReflectiveInvocationContext<Method> classContext,
            ExtensionContext extensionContext) throws Throwable {
		// Start Time
        long startTime = System.currentTimeMillis();
        try {
        	// Execute the Test
            invocation.proceed();
        } finally {
        	// Duration of the Test
            long duration = System.currentTimeMillis() - startTime;
            // Print Details
            System.out.println(String.format("%s.%s(), Time=%d ms", 
            		classContext.getTargetClass().getSimpleName(), 
            		classContext.getExecutable().getName(), 
            		duration));
        }
    }
}
