package org.cloud4log.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

// TODO Auto-generated Javadoc
/**
 * The Class LogMessage.
 */
@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findLogMessagesByErrorMessageEquals" })
@Entity
public class LogMessage extends PersistedObject {
	   
    /** The error message. */
    @NotNull
    private String errorMessage;
    
    /** The Uuid value. */
    private String uuid;
    
    /** The stack trace. */
    @Lob
    private String stackTrace;

    /** The application. */
    private String application;

    /** The error code. */
    private String errorCode;

    /** The host. */
    private String host;
    
    /** Level of the logged message */
    private String level;

    /** The logging timestamp. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date loggingTimestamp;

    /** The log type. */
    @NotNull
    private String logType;

    /** The environment. */
    private String environment;
    

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    /** The version. */
    @Version
    @Column(name = "version")
    private Integer version;
    
    /**
     * Gets the unique for the object.
     *
     * @return long value for the object id, 0 if not persisted
     */
    public Long getId() {
        return this.id;
    }
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the version number.
     *
     * @return current persisted version number
     */
    public Integer getVersion() {
        return this.version;
    }
    
    /**
     * Sets the version.
     *
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    /**
     * Sets the error message.
     *
     * @param errorMessage the new error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    /**
     * Gets the stack trace.
     *
     * @return the stack trace
     */
    @Lob
    public String getStackTrace() {
        return this.stackTrace;
    }
    
    /**
     * Sets the stack trace.
     *
     * @param stackTrace the new stack trace
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    
    /**
     * Gets the application.
     *
     * @return the application
     */
    public String getApplication() {
        return this.application;
    }
    
    /**
     * Sets the application.
     *
     * @param application the new application
     */
    public void setApplication(String application) {
        this.application = application;
    }
    
    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return this.errorCode;
    }
    
    /**
     * Sets the error code.
     *
     * @param errorCode the new error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    /**
     * Gets the host.
     *
     * @return the host
     */
    public String getHost() {
        return this.host;
    }
    
    /**
     * Sets the host.
     *
     * @param host the new host
     */
    public void setHost(String host) {
        this.host = host;
    }
    
    /**
     * Gets the logging timestamp.
     *
     * @return the logging timestamp
     */
    public Date getLoggingTimestamp() {
    	
    	//Malicious code vulnerability - May expose internal representation by incorporating reference to mutable object
    	if(loggingTimestamp!=null)
    		return new Date(this.loggingTimestamp.getTime());
    	return null;
    }
    
    /**
     * Sets the logging timestamp.
     *
     * @param loggingTimestamp the new logging timestamp
     */
    public void setLoggingTimestamp(Date loggingTimestamp) {
        this.loggingTimestamp = loggingTimestamp;
    }
    
    /**
     * Gets the log type.
     *
     * @return the log type
     */
    public String getLogType() {
        return this.logType;
    }
    
    /**
     * Sets the log type.
     *
     * @param logType the new log type
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }
    
    /**
     * Gets the environment.
     *
     * @return the environment
     */
    public String getEnvironment() {
        return this.environment;
    }
    
    /**
     * Sets the environment.
     *
     * @param environment the new environment
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    
    /**
     * Returns json view of this class
     * 
     * @return return the json representation
     */
    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    /**
     * From json to log message.
     *
     * @param json the json
     * @return the log message
     */
    public static LogMessage fromJsonToLogMessage(String json) {
        return new JSONDeserializer<LogMessage>().use(null, LogMessage.class).deserialize(json);
    }
    
    /**
     * To json array.
     *
     * @param collection the collection
     * @return the string
     */
    public static String toJsonArray(Collection<LogMessage> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    /**
     * From json array to log messages.
     *
     * @param json the json
     * @return the collection
     */
    public static Collection<LogMessage> fromJsonArrayToLogMessages(String json) {
        return new JSONDeserializer<List<LogMessage>>().use(null, ArrayList.class).use("values", LogMessage.class).deserialize(json);
    }

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    

}
