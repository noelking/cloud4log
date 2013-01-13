/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.ILogMessageDao;
import org.cloud4log.server.mocks.LogMessageMocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <b>LogMessageDaoTest</b>
 * <p>
 * Core test class for log message dao tests
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from "/applicationTestContext-database.xml" and the applicationContext-dao.xml in the root of the classpath
@ContextConfiguration(locations={"classpath:META-INF/spring/applicationContext.xml","classpath:META-INF/spring/applicationContext-cassandra.xml"})
@ActiveProfiles(profiles = "cassandra")
public class LogMessageCassandraDaoTest {
	
	/** The log message dao. */
	@Resource(name = "logMessageDao")
	private ILogMessageDao<LogMessage> logMessageDao;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	


	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#persist(org.cloud4log.domain.PersistedObject)}.
	 */
	@Test
	public void persistPass() {
		
		//success persist result
		final PersistedResult<LogMessage> successPersistResult =  saveStandardErrorMessage();
		final LogMessage returnedMessage = successPersistResult.getObjectSaved();
		
		//if stored successfully then the id should be greater than zero
		assertTrue(returnedMessage.getUuid() != null
				&& successPersistResult.getPresistingErrorMessages().size() == 0);
		
	}
	

	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#findByLevelAndEnvironment(String level, String environment)}.
	 */
	@Test
	public void findByLevelAndEnvironment() {
		
		//setup and save message for this unit test
		final PersistedResult<LogMessage> successPersistResult =  saveStandardErrorMessage();
		
		//messages returned
		List<LogMessage> messages = logMessageDao.findByLevelAndEnvironment("ERROR", "PRODUCTION");
	
		//if stored successfully then the id should be greater than zero
		assertTrue(messages.size() > 0);
		
	}
	
	private PersistedResult<LogMessage> saveStandardErrorMessage() {
		
		//Message mocks
		final LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		final LogMessage successMessage = messageMocks.getSuccessLogMessage();
		
		//success persist result
		final PersistedResult<LogMessage> successPersistResult = logMessageDao.persist(successMessage);
		
		return successPersistResult;
	}
	
}
