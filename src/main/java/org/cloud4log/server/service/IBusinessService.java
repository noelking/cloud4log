/**
 * 
 */
package org.cloud4log.server.service;

import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;

// TODO Auto-generated Javadoc
/**
 * <b>IBusinessService</b>
 * <p>
 * This interface provides you with access to the business object service layer, all method available here will be stateless services
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public interface IBusinessService {
	
	/**
	 * Message save an object to the database.
	 *
	 * @param objectToSave - Persisted object you are looking to save
	 * @return - PersistedObjectTo
	 */
	public PersistedResult save(PersistedObject objectToSave);
}
