/**
 * 
 */
package org.cloud4log.server.service.implementation;

import static org.junit.Assert.*;

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

// TODO Auto-generated Javadoc
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
@ContextConfiguration(locations={"classpath:META-INF/spring/applicationContext.xml","classpath:META-INF/spring/applicationTestContext-daoFail.xml", "classpath:META-INF/spring/applicationContext-coreServices.xml"})
public class LogMessageServiceFailTest {
	
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
	public void save() {

		//Message mocks
		LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		LogMessage failMessage = messageMocks.getNameFailureLogMessage();
		
		//save the object
		PersistedResult<LogMessage> result = logMessageService.save(failMessage);
		
		//now see if there was no errors
		assertTrue(result.getPresistingErrorMessages().size()==1);
		
	}

}
