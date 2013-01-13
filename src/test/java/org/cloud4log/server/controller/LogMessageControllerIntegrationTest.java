/**
 * 
 */
package org.cloud4log.server.controller;

import static org.junit.Assert.*;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.server.mocks.LogMessageMocks;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Assert;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

/**
 * <b>LogMessageControllerTest</b>
 * <p>
 * Core test class for log message rest controller
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
@RunWith(HttpJUnitRunner.class )
public class LogMessageControllerIntegrationTest {

	@Rule
	public Destination destination = new Destination( "http://localhost:9999/Cloud4Log/" ); 
	 
	@Context
	private Response response; // will be injected after every request
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}
	
	/**
	 * Test method for {@link org.cloud4log.server.controller.LogMessageController#getLogMessagesByLevel(java.lang.String)}.
	 */
	@HttpTest(method = Method.GET, path = "/logmessage/PRODUCTION/ERROR" )
	public final void testGetLogMessagesByLevelInfo() {
		
		String json = response.getBody(String.class);
		
		if(json.indexOf("errorMessage") > -1)
			assertTrue(true);
		else
			//no json returned then failed
			assertTrue(false);
	}

}
