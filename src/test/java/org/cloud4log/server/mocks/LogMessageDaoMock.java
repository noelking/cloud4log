/**
 * 
 */
package org.cloud4log.server.mocks;

import java.util.ArrayList;
import java.util.List;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.IBusinessDao;
import org.cloud4log.server.dao.ILogMessageDao;
import org.springframework.transaction.TransactionSystemException;

// TODO Auto-generated Javadoc
/**
 * The abstract implementation of the log message dao to provide success results for all methods.
 *
 * @author nking
 */
public abstract class LogMessageDaoMock implements  ILogMessageDao, IBusinessDao {

	/** Store the objects in a list for the log message unit testing. */
	private List<LogMessage> logMessages;
	
	/**
	 * Log message dao default constructor creates the list for storage of the logMessages.
	 */
	public LogMessageDaoMock() {
		logMessages = new ArrayList<LogMessage>();
	}

	/* (non-Javadoc)
	 * @see org.cloud4log.server.dao.IBusinessDao#count()
	 */
	@Override
	public long count() {
		return logMessages.size();
	}

	/* (non-Javadoc)
	 * @see org.cloud4log.server.dao.IBusinessDao#findAll()
	 */
	@Override
	public List findAll() {
		return logMessages;
	}

	/* (non-Javadoc)
	 * @see org.cloud4log.server.dao.IBusinessDao#find(java.lang.Long)
	 */
	@Override
	public PersistedObject find(Long id) {
		
		//find the log message
		for(LogMessage msg : logMessages) {
			if(msg.getId().equals(id))
				return msg;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cloud4log.server.dao.IBusinessDao#findEntries(int, int)
	 */
	@Override
	public List findEntries(int firstResult, int maxResults) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cloud4log.server.dao.IBusinessDao#persist(org.cloud4log.domain.PersistedObject)
	 */
	@Override
	public PersistedResult persist(PersistedObject obj)
			throws TransactionSystemException {
		
		//set the id
		LogMessage msg = (LogMessage)obj;
		msg.setId(Long.valueOf(logMessages.size()+1));
		
		//Log message added to list
		logMessages.add(msg);
		
		//log message saved successfully
		PersistedResult<LogMessage> result = new PersistedResult<LogMessage>(msg);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.cloud4log.server.dao.IBusinessDao#remove(org.cloud4log.domain.PersistedObject)
	 */
	@Override
	public PersistedResult remove(PersistedObject obj)
			throws TransactionSystemException {
		
		//Log message result
		PersistedResult<LogMessage> result;
		
		//Log message is there, then remove it
		if(logMessages.contains(obj)) {
			logMessages.remove(obj);
			
			//log message saved removed
			result = new PersistedResult<LogMessage>((LogMessage)obj);
		} else { //not found to remove
			//log message not removed
			result = new PersistedResult<LogMessage>((LogMessage)obj);
			result.add("Error object not found");
		}
		return result;
		
	}

	/**
	 * Gets the log messages.
	 *
	 * @return the logMessages
	 */
	public List<LogMessage> getLogMessages() {
		return logMessages;
	}

}
