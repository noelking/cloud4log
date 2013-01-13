/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import org.cloud4log.server.dao.IDao;
import org.cloud4log.server.dao.ILogMessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO Auto-generated Javadoc
/**
 * <b>DaoProviderFactory</b>
 * <p>
 *  This class is the factory for all Core Dao Objects it must be instantiated via this class, its declared via the getInstance method taking
 *  in the entity manager as a parameter, this insures this class can only exist with a entity manager.  It will then return the 
 *  interface for your required dao object.
 *  </p>
 * 
 * <b> Example how to instantiate this class </b>
 * <pre>
 *    IDaoProviderFactory daoFactory = DaoProviderFactory.getInstance(entityManager); 
 * </pre>
 * 
 * @author Noel King
 * @since 1.0
 */
public class DaoProviderFactory implements IDaoProviderFactory{
	
	/** Logger for business object. */
	private final static Logger LOG = LoggerFactory.getLogger(DaoProviderFactory.class);

	/** Log Message DAO object. */
	private IDao messageDao;
	
	/**
	 * Dao Consturctor provide everything we need here or you wont get access to the object.
	 *
	 * @param messageDao the message dao
	 */
	DaoProviderFactory(final IDao messageDao) {
		this.messageDao = messageDao;
	}
	
	/**
	 * Get the log message dao object.
	 *
	 * @return the log message dao
	 */
	public IDao getLogMessageDao() {
		LOG.debug(">> Log message getting the bean " + ILogMessageDao.BEANNAME);
		return messageDao;
	}
	
}
