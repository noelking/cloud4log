package org.cloud4log.server.service;

// TODO Auto-generated Javadoc
/**
 * <b>ICloud4LogServiceFactory</b>
 * <p>
 * This interface provides you with access to the cloud service layer, all method available here will be services provided by the tool
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public interface ICloud4LogServiceFactory {
	
	/**
	 * Log message service.
	 *
	 * @return the log message service
	 */
	ILogMessageService getLogMessageService();
}
