/**
 * 
 */
package org.cloud4log.server.service.implementation;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.mocks.LogMessageMocks;
import org.cloud4log.server.service.ILogMessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <b>LogMessageServiceTest</b>
 * <p>
 * Core test class for log message service layer
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from files specified below
@ContextConfiguration(locations={"classpath:META-INF/spring/applicationContext.xml","classpath:META-INF/spring/applicationTestContext-dao.xml", "classpath:META-INF/spring/applicationContext-coreServices.xml"})
public class LogMessageServiceSuccessTest {
	
	/** Log Message Service to be tested. */
	@Resource(name = "logMessageServiceFactory")
	private ILogMessageService logMessageService;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * Test method for {@link org.cloud4log.server.service.implementation.LogMessageService#save()}.
	 */
	@Test
	public void saveTest() {

		//Message mocks
		final LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		final LogMessage successMessage = messageMocks.getSuccessLogMessage();
		
		//save the object
		final PersistedResult<LogMessage> result = logMessageService.save(successMessage);
		
		//now see if there was no errors
		assertTrue(result.getPresistingErrorMessages().size()==0);
		
	}
	
	/**
	 * Test to find by level and environments
	 */
	@Test
	public void testFindByLevelAndEnvironment() {
		
		//Message mocks
		final LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		final LogMessage successMessage = messageMocks.getSuccessLogMessage();
		
		//save the object
		final List<LogMessage> results = logMessageService.
				findByLevelAndEnvironment(successMessage.getLevel(), 
						successMessage.getEnvironment());
		
		//is the value available
		boolean foundMessage  = false;
		
		//find the message
		if(results.size()>0) {
			for(final LogMessage message : results) {
				if(message.getEnvironment().equals(successMessage.getEnvironment())
						&& message.getLevel().equals(successMessage.getLevel())) {
					foundMessage = true;
				}
			}
		}
		
		//Found assert message
		assertTrue(foundMessage);
		
	}

}
