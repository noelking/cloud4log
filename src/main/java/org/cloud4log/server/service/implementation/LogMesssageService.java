/**
 * 
 */
package org.cloud4log.server.service.implementation;

import java.util.List;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.ILogMessageDao;
import org.cloud4log.server.service.ILogMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>LogMesssageService</b>
 * <p>
 * Log Message Service provides you with necessary services for interacting with the LogMessages in the application, 
 * It inherits two core interfaces IBusinessService, will provide the common business service functionality, while the ILogMessageService
 * provides the additional behaviour required for LogMessage management
 * </p>
 * 
 * <p>
 * You can only instantiate this class via its Cloud4LogServiceFactory class
 * </p>
 * 
 * <b> Example how to instantiate this class </b>
 * <pre>
 *    IBusinessService logService = Cloud4LogServiceFactory.getLogMessageService();
 *    ILogMessageService logService = Cloud4LogServiceFactory.getLogMessageService();
 * </pre>
 * 
 * 
 * 
 * @author Noel King
 * @since 1.0
 */
public class LogMesssageService extends BusinessObjectService implements ILogMessageService{
	
	/** The core dao object used for data access. */
	private ILogMessageDao logMessageDao;
	
	/** Logger for business object. */
	private final static Logger LOG = LoggerFactory.getLogger(LogMesssageService.class);
	
	/**
	 * Log Message Service default constructor takes in the necessary logMessageDao object.
	 *
	 * @param logMessageDao the log message dao
	 */
	LogMesssageService(final ILogMessageDao logMessageDao) {
		this.logMessageDao = logMessageDao;
	}
	
	/**
	 * Saves the persisted log message to the database.
	 *
	 * @param objectToSave the object to save
	 * @return the persisted result
	 * @author nking
	 */
	@Override
	public PersistedResult save(PersistedObject objectToSave) {
		try {
			return logMessageDao.persist(objectToSave);
		} catch (TransactionSystemException ex) {
			LOG.error("LogMesssageService Error saving object " + ex.getMessage(), ex);
			
			//now send back the result
			final PersistedResult result = new PersistedResult(objectToSave);
			result.add("Error saving message to the database");
			return result;
		}
	}
	
	
	/**
	 * Find the logging level by environment
	 * 
	 * @param level logging level
	 * @param environment environment level
	 * 
	 * @return Listof log messages
	 */
	@Override
	public List<LogMessage> findByLevelAndEnvironment(final String level,
			final String environment) {
		return logMessageDao.findByLevelAndEnvironment(level, environment);
	}

}
