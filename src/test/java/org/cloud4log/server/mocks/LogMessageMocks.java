/**
 * 
 */
package org.cloud4log.server.mocks;

import java.util.Date;

import org.cloud4log.domain.LogMessage;

// TODO Auto-generated Javadoc
/**
 * <b>LogMessageMocks</b>
 * <p>
 * Provides a set of LogMessage mocked scenarios
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public class LogMessageMocks {

	/**
	 * The Enum Application.
	 */
	private enum Application {
		
		/** The TESTAP p1. */
		TESTAPP1,
		
		/** The TESTAP p2. */
		TESTAPP2;
	}
	
	/**
	 * The Enum LogType.
	 */
	private enum LogType {
		
		/** The JAVA. */
		JAVA;
	}
	

	/**
	 * The Enum Log Environment.
	 */
	private enum LogEnvironment {
		
		/** The Prod env. */
		PRODUCTION;
	}
	

	
	/**
	 * The Enum Log Level
	 */
	private enum LogLevel {
		
		/** The Error Level. */
		ERROR;
	}
	
	/**
	 * Gets the basic log message.
	 *
	 * @return the basic log message
	 */
	private LogMessage getBasicLogMessage() {
		
		LogMessage message = new LogMessage();
		
		message.setApplication(Application.TESTAPP1.name());
		message.setHost("localhost");
		message.setLoggingTimestamp(new Date());
		message.setLogType(LogType.JAVA.name());
		message.setLevel(LogLevel.ERROR.name());
		message.setEnvironment(LogEnvironment.PRODUCTION.name());
		message.setErrorMessage("This is an error message test here");
		
		return message;
	}
	
	/**
	 * Gets the basic log message.
	 *
	 * @return the success log message
	 */
	public LogMessage getSuccessLogMessage() {
		return getBasicLogMessage();
	}
	
	/**
	 * Gets the name failure log message.
	 *
	 * @return the name failure log message
	 */
	public LogMessage getNameFailureLogMessage() {
		
		LogMessage message = getBasicLogMessage();
		message.setErrorMessage(null);
		
		return message;
	}
	
}
;