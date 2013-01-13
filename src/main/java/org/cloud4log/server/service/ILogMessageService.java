package org.cloud4log.server.service;

import java.util.List;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;

// TODO Auto-generated Javadoc
/**
 * <b>ILogMessageService</b>
 * <p>
 * This interface provides services to the Log Message data, it can instantiated via the Cloud4LogServiceFactory
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public interface ILogMessageService {
	
	/**
	 * Saves the persisted log message to the database.
	 *
	 * @param objectToSave the object to save
	 * @return the persisted result
	 * @author nking
	 */
	PersistedResult save(PersistedObject objectToSave); 
	
	/**
     * Finds the messages by level and environment
     *
     * @return the list
     */
    List<LogMessage> findByLevelAndEnvironment(String level, String environment);
}
