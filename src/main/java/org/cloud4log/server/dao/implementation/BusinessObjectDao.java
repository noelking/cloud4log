/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.IBusinessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>BusinessobjectDao</b>
 * <p>
 * The class provides abstract functionality to the underlying Dao object
 * </p>.
 *
 * @param <T> the generic type
 * @author Noel King
 * @since 1.0
 */
public abstract class BusinessObjectDao<T extends PersistedObject> implements IBusinessDao {
	
	/** Logger for business object. */
	private final static Logger LOG = LoggerFactory.getLogger(BusinessObjectDao.class);

	/** The entity manager. */
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private transient EntityManager entityManager;
	
	/** The bo class. */
	private Class<T> businessObject;
	
	 /**
 	 * Declares the LogMessage Dao object.
 	 *
 	 * @param entityManager - required for valid object, if you add a null value here it will throw a null pointer exception
 	 * @param businessObject the the bo class
 	 */
	BusinessObjectDao(final EntityManager entityManager, final Class< T > businessObject) {
		this.entityManager = entityManager;
		this.businessObject = businessObject;
		if(entityManager == null) 
			throw new NullPointerException(this.getClass().getCanonicalName() + " invalid constructor access");
	}
		
	/**
	 * Returns the number of Log messages in the database.
	 *
	 * @return the count value or zero if none found
	 */
	public long count() {
		long records = entityManager.createQuery("SELECT COUNT(o) FROM "+businessObject.getName()+" o", Long.class).getSingleResult();
        return records;
	}
	
	/**
	 * Finds all the records.
	 *
	 * @return the list
	 */
    public List<T> findAll() {
    	return entityManager.createQuery("SELECT o FROM "+businessObject.getName()+" o", businessObject).getResultList();
    }
    
    /**
     * Finds a given value for a id of the record.
     *
     * @param id the id
     * @return the t
     */
    public T find(final Long id) {
    	 if (id == null) 
    		 return null;
         return entityManager.find(businessObject, id);
    }
    
    /**
     * Finds the entries within a certain range.
     *
     * @param firstResult the first result
     * @param maxResults the max results
     * @return the list
     */
    public List<T> findEntries(final int firstResult, final  int maxResults) {
    	return getEntityManager().createQuery("SELECT o FROM "+ businessObject.getName() +" o", businessObject)
        		.setFirstResult(firstResult)
        		.setMaxResults(maxResults).getResultList();
    }
    
   /**
    * Saves the object.
    *
    * @param businessObject the business object
    * @return results
    * @throws TransactionSystemException the transaction system exception
    */
    @Transactional(readOnly = false, timeout = 30,
            propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.DEFAULT)
    public PersistedResult persist(final PersistedObject businessObject) throws TransactionSystemException {
    	
    	final PersistedResult objectToSave = new PersistedResult(businessObject);
    	try{
    		//save the object to the database and handle the exception
    		save(businessObject);
    	} catch (PersistenceException exception) {
    		handleError(objectToSave, exception, "Error saving object " + businessObject.toJson() + " to the database ");
    	} catch (ConstraintViolationException exception) {
    		handleError(objectToSave, exception, "Error saving object " + businessObject.toJson() + " to the database ");
    	}
    	return objectToSave;
    }
    
    /**
     * Handles the processing of an error message.
     *
     * @param result the result
     * @param exception the exception
     * @param message the message
     */
    protected void handleError(final PersistedResult result, final Exception exception, final String message) {
    	result.add(message + exception.getMessage());
		LOG.error(message + " " + exception.getMessage(), exception);
    }
    
    /**
     * Save a persisted object to the database.
     *
     * @param businessObject the business object
     */
    private void save(final PersistedObject businessObject) {
    	this.entityManager.persist(businessObject);
    	flush();
    }
    
    /**
     * Removes the object.
     *
     * @param businessObject the business object
     * @return persisted result with outcome
     * @throws TransactionSystemException the transaction system exception
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public PersistedResult remove(final PersistedObject businessObject)  throws TransactionSystemException {
    	final PersistedResult objectToRemove = new PersistedResult(businessObject);
    	try {
    		
	        if (this.entityManager.contains(businessObject)) {
	            this.entityManager.remove(businessObject);
	        } else {
	            PersistedObject attached = find(businessObject.getId());
	            this.entityManager.remove(attached);
	        }
	        
    	} catch (javax.validation.ConstraintViolationException exception) {
    		objectToRemove.add("Error removing object from the database " + exception.getMessage());
    		LOG.error("Error removing " + businessObject.toJson(), exception);
    	} catch (NullPointerException exception) {
    		objectToRemove.add("Error removing object you have added an null object " + exception.getMessage());
    		LOG.error("Error removing an null object ", exception);
    	}
        return objectToRemove;
    }
    
   /**
    * Flush the entity manager.
    */
    public void flush() {
        this.entityManager.flush();
    }
    
   /**
    * Clear the entity manager.
    */
    public void clear() {
        entityManager.clear();
    }
    
    /**
     * Merge the object.
     *
     * @param businessObject the business object
     * @return Persisted result
     * @throws TransactionSystemException the transaction system exception
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public PersistedResult merge(final PersistedObject businessObject)  throws TransactionSystemException {
    	final PersistedResult result = new PersistedResult(businessObject);
    	
    	try {
	    	final PersistedObject merged = (PersistedObject)entityManager.merge(businessObject);
	    	result.setObjectSaved(merged);
    	}  catch (javax.validation.ConstraintViolationException exception) {
    		result.add("Error removing object from the database " + exception.getMessage());
    		LOG.error("Error removing " + businessObject.toJson(), exception);
    	} 
        
        return result;
    }

	/**
	 * Gets the entity manager.
	 *
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
