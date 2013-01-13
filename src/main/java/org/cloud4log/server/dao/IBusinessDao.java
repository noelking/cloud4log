package org.cloud4log.server.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// TODO Auto-generated Javadoc
/**
 * <b>IBusinessDao</b>
 * <p>
 * This interface provides the common data access requirements for a given business object.
 * </p>
 *
 * @param <T> the generic type
 * @author Noel King
 * @since 1.0
 */
public interface IBusinessDao<T extends PersistedObject> extends IDao {
	
	/**
	 * Count.
	 *
	 * @return the long
	 */
	long count();
    
    /**
     * Find all.
     *
     * @return the list
     */
    List<T> findAll();
    
    /**
     * Find.
     *
     * @param id the id
     * @return the t
     */
    T find(Long id);
    
    /**
     * Find entries.
     *
     * @param firstResult the first result
     * @param maxResults the max results
     * @return the list
     */
    List<T> findEntries(int firstResult, int maxResults);
    
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
    
    /**
     * Removes the.
     *
     * @param obj the obj
     * @return the persisted result
     * @throws TransactionSystemException the transaction system exception
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    PersistedResult remove(PersistedObject obj)  throws TransactionSystemException;
    
}
