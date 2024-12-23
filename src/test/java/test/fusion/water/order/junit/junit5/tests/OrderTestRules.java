package test.fusion.water.order.junit.junit5.tests;
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;

// JUnit 4 Rules.
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;

import org.junit.Rule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import io.fusion.water.order.domain.models.OrderEntity;
import test.fusion.water.order.junit.junit5.annotations.tools.Junit5;
import test.fusion.water.order.junit.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit.junit5.extensions.TestTimeExtension;

/**
 * Order Test Rules Suite
 * 
 * @author arafkarsh
 *
 */
@Junit5()
@Functional()
@TestMethodOrder(OrderAnnotation.class)
@EnableRuleMigrationSupport
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
class OrderTestRules {

	private OrderEntity order;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Order Test Rules Suite Execution Started...");
    }
    
    
	/**
	 * Following from Junit4 - As part of the Vintage Package
	 * Field Level Rules introduced in JUnit 4.7
	 * Class Level Rules introduced in JUnit 4.9
	 */
	@Rule
	private TemporaryFolder tmpFolder1 = new TemporaryFolder();
	
	/**
	 * From JUnit v4.13 assuredDeletion ensures that tmp files/folders 
	 * are removed and if that fails then you will get an
	 * assertion failure.
	 */
	@Rule
	private TemporaryFolder tmpFolder2 = TemporaryFolder.builder().assureDeletion().build();
	
	/**
	 * The Error Collector rule allows the execution of a test to 
	 * continue after one or more problems are found:
	 */
	@Rule
	private ErrorCollector collector = new ErrorCollector();
	
	/**
	 * This Rule is configured in anticipation of an Exception Thrown
	 * in the tests. 
	 * E**
	 */
	@Rule
	private ExpectedException thrown = ExpectedException.none();
	
	/**
	 * This Returns the currently running Test Method Name
	 * E**
	 */
	@Rule
	private TestName testName = new TestName();
	
	/**
	 * Rule Must Timed out after the specified seconds
	 */
	@Rule
	private Timeout timeout = Timeout.seconds(3);

    @BeforeEach
    public void setup() throws IOException {
        System.out.println("Setup Rules.....");
			tmpFolder1.create();
			tmpFolder2.create();
    }
	
	@Test
	@DisplayName("1. Rule Test - Temporary Folder")
	@Order(1)
	void folderTest()  {
    	try {
			File newFile = tmpFolder2.newFile("MyNewFile.txt");
			File newDir = tmpFolder2.newFolder("MyNewFolder");
			System.out.println(newFile.getAbsolutePath());
			System.out.println(newDir.getAbsolutePath());

			assertThat(newFile.exists(), is(equalTo(true)));
			assertThat(newDir.exists(), is(equalTo(true)));

    	} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("2. Rule Test - Error Collector")
	@Order(2)
	void test() {
        collector.checkThat("a", equalTo("b"));
        collector.checkThat(1, equalTo(2));
        collector.checkThat("c", equalTo("c"));
    }

    @Test
 	@DisplayName("3. Rule Test - ExpectedException : No Error")
	@Order(3)
	void throwsNothing() {
		// No Error Expected
		assertThat("No Error", is(equalTo("No Error")));
    }
     
    @Test
  	@DisplayName("4. Rule Test - ExpectedException : Error Thrown")
	@Order(4)
	void throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
        	throw new NullPointerException();
        } catch (Exception e) {
        	collector.addError(e);
        	collector.checkThat(e.getMessage(), is(equalTo("NullPointerException")));
        }
    }
    
    @Test
    @DisplayName("5. Rule Test - TestName") 
	@Order(5)
	void ruleTestName() {
    	if(testName.getMethodName() == null) {
        	System.out.println(">> TestName Rule Doesnt Work! TestName="+testName.getMethodName());
    		return;
    	}
    	System.out.println(">> TestName="+testName.getMethodName());
    	assertThat("ruleTestName",is(equalTo(testName.getMethodName())));
    }

    @Test
    @DisplayName("6. Rule Test - Timeout R1") 
	@Order(6)
	void testTimeout1() throws InterruptedException {
    	long start = System.currentTimeMillis();
    	Thread.sleep(1000);
    	System.out.println("Time Taken R1 = "+(System.currentTimeMillis()-start));
		assertThat("Timeout Test", is(equalTo("Timeout Test")));
    }
    
    @Test
    @DisplayName("7. Rule Test - Timeout R2") 
	@Order(7)
	void testTimeout2() throws InterruptedException {
    	long start = System.currentTimeMillis();
		// Simulating a Long Running Process
    	Thread.sleep(5000);
    	System.out.println("Time Taken R2 = "+(System.currentTimeMillis()-start));
		assertThat("Timeout Test", is(equalTo("Timeout Test")));
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Order Test Rules Suite Execution Completed...");
    }
}
