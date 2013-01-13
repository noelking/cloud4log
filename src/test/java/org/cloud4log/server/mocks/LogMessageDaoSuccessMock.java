/**
 * 
 */
package org.cloud4log.server.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.ILogMessageDao;
import org.springframework.transaction.TransactionSystemException;

// TODO Auto-generated Javadoc
/**
 * This class provides a successful saving mocking for the log message dao object.
 *
 * @author nking
 */
public class LogMessageDaoSuccessMock extends LogMessageDaoMock implements ILogMessageDao{

	/* (non-Javadoc)
	 * @see org.cloud4log.server.mocks.LogMessageDaoMock#persist(org.cloud4log.domain.PersistedObject)
	 */
	@Override
	public PersistedResult persist(PersistedObject obj)
			throws TransactionSystemException {
		return super.persist(obj);
	}

	@Override
	public List<LogMessage> findByLevelAndEnvironment(String level, String environment) {
		
		List<LogMessage> messages = new ArrayList<LogMessage>();
		
		LogMessage message = new LogMessage();
		message.setLevel(level);
		message.setEnvironment(environment);
		message.setErrorMessage(String.valueOf(System.currentTimeMillis()));
		message.setApplication("Test App");
		message.setLoggingTimestamp(new Date());
		message.setHost("localhost");
		message.setLogType("JAVA");
		
		messages.add(message);
		return messages;
	}
	
}
