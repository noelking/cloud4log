/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.RollbackException;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.ILogMessageDao;
import org.cloud4log.server.mocks.LogMessageMocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;

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
@ContextConfiguration(locations={"classpath:META-INF/spring/applicationContext.xml","classpath:META-INF/spring/applicationTestContext-database.xml", "classpath:META-INF/spring/applicationContext-dao.xml"})
public class LogMessageDaoTest {
	
	/** The log message dao. */
	@Resource(name = "logMessageDaoFactory")
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
	 * Save the message
	 * 
	 * @param successMessage
	 * @return
	 */
	private LogMessage saveMessage(LogMessage successMessage) {

		//save the message and get the Id value
		try {
			//success persist result
			PersistedResult<LogMessage> successPersistResult = logMessageDao.persist(successMessage);
			
			//success message obj
			LogMessage savedMessage = successPersistResult.getObjectSaved();
			return savedMessage;
		} catch (RollbackException exception) {
			assertTrue(exception.getMessage(), false);
		}
		return new LogMessage();
	}
	
	/**
	 * Successfully saves a record to the database for testing purposes.
	 *
	 * @return the success saved message
	 */
	private LogMessage getSuccessSavedMessage(String errorMessage) {
		
		//Message mocks
		final LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		LogMessage successMessage = messageMocks.getSuccessLogMessage();
		successMessage.setErrorMessage(errorMessage);
		
		//save the message and get the Id value
		// saved message
		return saveMessage(successMessage);
	}
	

	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#findByLevelAndEnvironment(String level, String environemnt)}.
	 */
	@Test
	public void testFindByLevelAndEnvironment() {
		
		// timestamp message
		final String timeStampMessage = String.valueOf(System.currentTimeMillis());
		
		//get the saved message
		final LogMessage savedMessage = getSuccessSavedMessage(timeStampMessage);
		
		//find the value from the database
		final List<LogMessage> foundMessages = 
				(List<LogMessage>)logMessageDao.findByLevelAndEnvironment(savedMessage.getLevel(),savedMessage.getEnvironment());
		
		//do we find this message;
		boolean foundMessage = false;
		for(LogMessage message : foundMessages) {
			if(message.getErrorMessage().equals(timeStampMessage)) {
				foundMessage = true;
				break;
			}
		}
		
		//if saved error messages saved if found then we are good
		assertTrue(foundMessage);
	}
	
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#persist(org.cloud4log.domain.PersistedObject)}.
	 */
	@Test
	public void testPersistPass() {
		
		//Message mocks
		LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		LogMessage successMessage = messageMocks.getSuccessLogMessage();
		try {
			//success persist result
			PersistedResult successPersistResult = logMessageDao.persist(successMessage);
		
			//if stored successfully then the id should be greater than zero
			assertTrue(successPersistResult.getObjectSaved().getId()>0  
					&& successPersistResult.getPresistingErrorMessages().size() == 0);
		} catch (RollbackException exception) {
			assertTrue(exception.getMessage(), false);
		}
	}
	
	
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#persist(org.cloud4log.domain.PersistedObject)}.
	 */
	@Test
	public void testPersistFail() {
		
		// get the mocks
		LogMessageMocks messageMocks = new LogMessageMocks();
		
		//failed message
		LogMessage failedMessage = messageMocks.getNameFailureLogMessage();
		
		try{
			//save a failure object
			PersistedResult failPersistResult = logMessageDao.persist(failedMessage);
		} catch (TransactionSystemException exception) {
			//passed if sent back a rollback exception
			assertTrue(exception.getCause() instanceof RollbackException);
		}
	}
}
