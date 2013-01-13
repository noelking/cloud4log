package org.cloud4log.server.dao.implementation;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.RollbackException;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.IBusinessDao;
import org.cloud4log.server.dao.ILogMessageDao;
import org.cloud4log.server.mocks.LogMessageMocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;

/**
 * <b>LogMessageBusinessDaoTest</b>
 * 
 * This tests the IBusinessDao implmentation for LogMessageDao
 * 
 * @author nking
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from "/applicationTestContext-database.xml" and the applicationContext-dao.xml in the root of the classpath
@ContextConfiguration(locations={"classpath:META-INF/spring/applicationContext.xml","classpath:META-INF/spring/applicationTestContext-database.xml", "classpath:META-INF/spring/applicationContext-dao.xml"})
@ActiveProfiles("database")
public class LogMessageBusinessDaoTest {

	/** The log message dao. */
	@Resource(name = "logMessageDaoFactory")
	private IBusinessDao<LogMessage> logMessageDao;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * Successfully saves a record to the database for testing purposes.
	 *
	 * @return the success saved message
	 */
	private LogMessage getSuccessSavedMessage() {
		
		//Message mocks
		final LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		final LogMessage successMessage = messageMocks.getSuccessLogMessage();
		
		// saved message
		return saveMessage(successMessage);
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
			final PersistedResult<LogMessage> successPersistResult = logMessageDao.persist(successMessage);
			
			//success message obj
			final LogMessage savedMessage = successPersistResult.getObjectSaved();
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
		final LogMessage successMessage = messageMocks.getSuccessLogMessage();
		successMessage.setErrorMessage(errorMessage);
		
		//save the message and get the Id value
		// saved message
		return saveMessage(successMessage);
	}
	

	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#count()}.
	 */
	@Test
	public void testCount() {
		
		//get the current list of records
		long currentCount = logMessageDao.count();
		
		//get the saved message
		final LogMessage savedMessage = getSuccessSavedMessage();
	
		//is the current value there and is our count + 1
		assertTrue(logMessageDao.count()==(currentCount+1));
	}

	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#findAll()}.
	 */
	@Test
	public void testFindAll() {
		
		//get the current list of records
		long currentCount = logMessageDao.count();
		
		//get the saved message
		final LogMessage savedMessage = getSuccessSavedMessage();
	
		//get the messages
		final List<LogMessage> messages = logMessageDao.findAll();
		
		boolean foundMessage = false;
		for(LogMessage message : messages) {
			
			if(message.getId().equals(savedMessage.getId())) {
				foundMessage = true;
			}
		}
		
		//is the current value there and is our count + 1
		assertTrue(foundMessage && messages.size()==(currentCount+1));
	}
	
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#findAll()}.
	 */
	@Test
	public void testFindEntries() {
		
		//get the current list of records
		long currentCount = logMessageDao.count();
		
		//get the saved message
		final LogMessage savedMessage = getSuccessSavedMessage();
	
		//get the messages
		final List<LogMessage> messages = logMessageDao.findEntries(0, (int)currentCount+1);
		
		boolean foundMessage = false;
		for(LogMessage message : messages) {
			if(message.getId().equals(savedMessage.getId())) {
				foundMessage = true;
			}
		}
		
		//is the current value there and is our count + 1
		assertTrue(foundMessage && messages.size()==currentCount+1);
	}
	 
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#find(java.lang.Long)}.
	 */
	@Test
	public void testFindLong() {
		
		//get the saved message
		final LogMessage savedMessage = getSuccessSavedMessage();
		
		//find the value from the database
		final LogMessage foundMessage = (LogMessage)logMessageDao.find(savedMessage.getId());
		
		//if saved error messages are same and same id then we should be good
		assertTrue(savedMessage.getErrorMessage().equals(foundMessage.getErrorMessage())
				&& foundMessage.getId().equals(savedMessage.getId()));
	}
	
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#find(java.lang.Long)}.
	 */
	@Test
	public void testFindNull() {
		
		//find a null id
		final LogMessage foundMessage = (LogMessage)logMessageDao.find(null);
		
		//should return a null object
		assertTrue(foundMessage==null);
	}
 
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#persist(org.cloud4log.domain.PersistedObject)}.
	 */
	@Test
	public void testPersistPass() {
		
		//Message mocks
		final LogMessageMocks messageMocks = new LogMessageMocks();
		
		//successful message
		final LogMessage successMessage = messageMocks.getSuccessLogMessage();
		try {
			//success persist result
			final PersistedResult successPersistResult = logMessageDao.persist(successMessage);
		
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
			final PersistedResult failPersistResult = logMessageDao.persist(failedMessage);
		} catch (TransactionSystemException exception) {
			//passed if sent back a rollback exception
			assertTrue(exception.getCause() instanceof RollbackException);
		}
	}

	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#remove(org.cloud4log.domain.PersistedObject)}.
	*/
	@Test
	public void testRemove() {
		
		//get the saved message
		final LogMessage savedMessage = getSuccessSavedMessage();
		
		try {
			final Long idRemoved = savedMessage.getId();
			
			//remove the log message
			logMessageDao.remove(savedMessage);
			
			//There should be no values in the database now, thus count is zero
			assertTrue(logMessageDao.find(idRemoved)==null);
		} catch (RollbackException exception) {
			assertTrue(exception.getMessage(), false);
		}
	}
	
	/**
	 * Test method for {@link org.cloud4log.server.dao.implementation.LogMessageDao#remove(org.cloud4log.domain.PersistedObject)}.
	*/
	@Test
	public void testRemoveNull() {
		
		try {
			//remove the log message
			final PersistedResult<LogMessage> result = logMessageDao.remove(null);
			
			//There should be no values in the database now, thus count is zero
			assertTrue(result.getPresistingErrorMessages().size()>0 );
		} catch (RollbackException exception) {
			assertTrue(exception.getMessage(), false);
		}
	}
}

