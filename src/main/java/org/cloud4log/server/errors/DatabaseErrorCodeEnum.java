package org.cloud4log.server.errors;

// TODO Auto-generated Javadoc
/**
 * <b>ErrorCodeEnum</b>
 * <p>
 * Stores the code error codes for the application database related errors, it contains a friendly name, number and description.
 * The error codes for the database will be between 1000 and 2000
 * </p>
 * 
 * 
 * @author Noel King
 * @since 1.0
 */
public enum DatabaseErrorCodeEnum implements IErrorCode {
	
	/** The DATABAS e_ failur e_ saving. */
	DATABASE_FAILURE_SAVING(1000, "Problem at the database level saving the object", 
			"A failure has occurred at the database level, you will need to review the database logs.");

	/** The error code. */
	private int errorCode;
	
	/** The error message. */
	private String errorMessage;
	
	/** The resolution message. */
	private String resolutionMessage;
	
	/**
	 * Error code enum requires these params to help error resolution.
	 *
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 * @param proposedResolution the proposed resolution
	 */
	private DatabaseErrorCodeEnum(int errorCode, String errorMessage, String proposedResolution) {
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
		this.resolutionMessage = proposedResolution;
	}

	/**
	 * Returns the error code.
	 *
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Returns the error message.
	 *
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Gets the resolution message.
	 *
	 * @return the resolution
	 */
	public String getResolutionMessage() {
		return resolutionMessage;
	}

}
