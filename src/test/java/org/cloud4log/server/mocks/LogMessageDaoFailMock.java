/**
 * 
 */
package org.cloud4log.server.mocks;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.ILogMessageDao;
import org.springframework.transaction.TransactionSystemException;

// TODO Auto-generated Javadoc
/**
 * This class provides a error saving mocking for the log message dao object.
 *
 * @author nking
 */
public class LogMessageDaoFailMock extends LogMessageDaoMock implements ILogMessageDao{

	/**
	 * Tests by throwing an error message in the persistance DAO layer.
	 *
	 * @param obj the obj
	 * @return the persisted result
	 * @throws TransactionSystemException the transaction system exception
	 */
	@Override
	public PersistedResult persist(PersistedObject obj)
			throws TransactionSystemException {
		
		throw new TransactionSystemException("Error saving the record", new RollbackException());
	}

	@Override
	public List<LogMessage> findByLevelAndEnvironment(String level, String environment) {
		return new ArrayList<LogMessage>();
	}
	
}
