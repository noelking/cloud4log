/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import org.cloud4log.server.dao.IDao;

/**
 * <b>IDaoProviderFactory</b>
 * <p>
 * 
 * </p>
 * 
 * <b> Example how to instantiate this class </b>
 * <pre>
 * 
 * </pre>.
 *
 * @author Noel King
 * @since 1.0
 */
public interface IDaoProviderFactory {
	
	/**
	 * Gets the log message dao.
	 *
	 * @return the log message dao
	 */
	IDao getLogMessageDao();
}
