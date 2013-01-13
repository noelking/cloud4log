/**
 * 
 */
package org.cloud4log.domain;

import org.cloud4log.server.errors.IErrorCode;

// TODO Auto-generated Javadoc
/**
 * <b>ValidationError</b>
 * <p>
 * Stores the error message from a validation failure in the application
 * </p>.
 *
 * @author Noel King
 * @since 1.0
 */
public class ValidationError {

	/** The validation error message to describe the reason for validation failures. */
	private String validationErrorMessage;
	
	/** The error code. */
	private IErrorCode errorCode = null;
	
	/**
	 * Default constructor, takes in the validationErrorMessage, this class is
	 * useless without this error message, so compulsory it has one.
	 *
	 * @param validationErrorMessage - the error message
	 * @param errorCode the error code
	 */
	public ValidationError(String validationErrorMessage, IErrorCode errorCode) {
		this.validationErrorMessage = validationErrorMessage;
	}
	
	/**
	 * Returns the validation failure message.
	 *
	 * @return string message with reason for validation failure
	 */
	public String getValidationErrorMessage() {
		return this.validationErrorMessage;
	}
	
	/**
	 * Returns the error code corresponding to this validation error.
	 *
	 * @return error code with number and message details
	 */
	public IErrorCode getErrorCode() {
		return this.errorCode;
	}
}
