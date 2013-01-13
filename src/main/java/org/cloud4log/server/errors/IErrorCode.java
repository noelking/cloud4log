package org.cloud4log.server.errors;

// TODO Auto-generated Javadoc
/**
 * <b>IErrorCode</b>
 * <p>
 * All error codes must have a number and an error description
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public interface IErrorCode {

	/**
	 * Returns the error code.
	 *
	 * @return the errorCode
	 */
	int getErrorCode();
	
	/**
	 * Returns the error message.
	 *
	 * @return the error message
	 */
	String getErrorMessage();
	
	/**
	 * Returns a suggested resolution.
	 *
	 * @return the resolution message
	 */
	String getResolutionMessage();
}
