/**
 * 
 */
package org.cloud4log.server.dao;

import java.util.List;

import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>ILogMessageDao</b>
 * <p>
 * This interface provides data access to the Log Message data object, it can instantiated via the DaoProviderFactory
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public interface ILogMessageDao<T extends PersistedObject> extends IDao{
	
	/** The bean name to get access to the Dao Object. */
	public static final String BEANNAME = "logMessageDao";
	
	/**
     * Finds the messages by level and environment
     *
     * @return the list
     */
    List<T> findByLevelAndEnvironment(String level, String environment);
    
    /**
     * Persist.
     *
     * @param obj the obj
     * @return the persisted result
     * @throws TransactionSystemException the transaction system exception
     */
    @Transactional(rollbackFor = Exception.class,
            readOnly = false, 
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.DEFAULT)
    PersistedResult persist(PersistedObject obj)  throws TransactionSystemException;
    
}
