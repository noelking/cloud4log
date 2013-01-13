/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.IBusinessDao;
import org.cloud4log.server.dao.ILogMessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>LogMessageDao</b>
 * <p>
 * This class provides all data access to the LogMessage data object from the database, it extends BusinessObjectDao so provides you with the core information 
 * </p>
 * 
 * <b> Example how to instantiate this class </b>
 * <pre>
 *    DaoProviderFactory.getLogMessageDao();
 * </pre>
 * 
 * @author Noel King
 * @since 1.0
 */
@Transactional
public class LogMessageDao<T extends PersistedObject> extends BusinessObjectDao<LogMessage> implements ILogMessageDao, IBusinessDao{

	/** Logger for business object. */
	private final static Logger LOG = LoggerFactory.getLogger(LogMessageDao.class);
	
	/**
	 * Declares the LogMessage.
	 *
	 * @param entityManager - required for valid object, if you add a null value here it will throw a null pointer exception
	 * @param theBusinessObject the the business object
	 */
	LogMessageDao(final EntityManager entityManager, final Class theBusinessObject) {
		super(entityManager, theBusinessObject);
		
		//log message to say we are hitting this class
		LOG.info(">> Initalising the log message dao");
	}
	
	/**
     * Finds the messages by level and environment
     *
     * @return the list
     */
    public List<T> findByLevelAndEnvironment(final String level, final String environment) {
    	
    	//1 Log Query
    	final Query logQuery = getEntityManager().createQuery("SELECT msg FROM LogMessage msg where " +
    			"msg.level=:level and msg.environment=:environment", LogMessage.class);

    	//add params
    	logQuery.setParameter("level", level);
    	logQuery.setParameter("environment", environment);
    	
    	//returns the results
    	return logQuery.getResultList();
    }
}
