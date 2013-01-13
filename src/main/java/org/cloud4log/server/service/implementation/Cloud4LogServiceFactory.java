/**
 * Cloud4Log - Remote Logging solution
 */
package org.cloud4log.server.service.implementation;

import org.cloud4log.server.service.ICloud4LogServiceFactory;
import org.cloud4log.server.service.ILogMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <b>Cloud4LogServiceFactory</b>
 * <p>
 * This class is the factory class for all service interactions in the cloud4log application.  This allows different interfaces expose
 * these services, however they must all interact with this service layer for functionality.  Its imperative for test driven development to 
 * work here you must ensure that there is no logic in constructors.  This is a given rule and you must enforce this for validity of the underlying
 * tests
 * </p>
 * 
 * <p>
 * You can only instantiate this class via its getInstance method
 * </p>
 * 
 * <b> Example how to instantiate this class </b>
 * <pre>
 *    ICloud4LogServiceFactory serviceFactory = Cloud4LogServiceFactory.getInstance();
 * </pre>
 * 
 * @author Noel King
 * @since 1.0
 */
public class Cloud4LogServiceFactory implements ICloud4LogServiceFactory {

	/** Logger for business object. */
	private final static Logger LOG = LoggerFactory.getLogger(Cloud4LogServiceFactory.class);
	
	/** Contains the log message service interface. */
	private ILogMessageService messageService;
	
	/**
	 * Ensures this object cannot be instantiated without using the getInstance() method.
	 *
	 * @param messageService the message service
	 * @author Noel King
	 */
	public Cloud4LogServiceFactory(final ILogMessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * Log message service.
	 *
	 * @return The log message service
	 * @author Noel King
	 */
	public ILogMessageService getLogMessageService() {
		return messageService;
	}
	
}
