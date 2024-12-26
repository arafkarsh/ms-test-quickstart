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

package test.fusion.water.order.junit.junit5.utils;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;
/**
 * 
 * @author arafkarsh
 *
 */

public class CustomerArgumentProvider implements ArgumentsProvider  {

	static final Logger log = getLogger(lookup().lookupClass());
	
	@Override
	public Stream<? extends Arguments> provideArguments(
			ExtensionContext context) throws Exception {
        log.debug("Customer Arguments provider to test {}",
                context.getTestMethod().get().getName());
        return Stream.of(
        		Arguments.of("UUID1", "John","Doe","0123456666"), 
        		Arguments.of("UUID2", "Jane","Doe","0123456777"), 
        		Arguments.of("UUID3", "John","Doe Jr.","0123456888"),
        		Arguments.of("UUID4", "Jane","Doe Jr.","0123456999")
        	);
	}
}

