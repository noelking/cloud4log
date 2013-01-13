/**
 * 
 */
package org.cloud4log.server.dao.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedObject;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.dao.ILogMessageDao;
import org.cloud4log.server.dao.implementation.cassandra.ICassandraSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;

import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.serializers.StringSerializer;

/**
 * 
 * <b>LogMessageCassandraDao</b>
 * <p>
 * This class provides all data access to the LogMessage data object from Cassandra 
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
public class LogMessageCassandraDao  implements ILogMessageDao<LogMessage> {
	

	/** Logger for business object. */
	private final static Logger LOG = LoggerFactory.getLogger(LogMessageCassandraDao.class);
	
	/** Key space for accessing cassandra data */
	private Keyspace cassandraKeySpace; 
	
	//added the Log Message Cassandra info here 
	private final ColumnFamily<String, String> logMessageCassandraInfo = new ColumnFamily<String, String>(
		    "LogMessage", // Column Family Name
		    StringSerializer.get(), // Key Serializer
		    StringSerializer.get()); // Column Serializer
	
	/** Max number of rows to be returns from db */
	private final int maxRowsToReturn = 100;
	
	/**
	 * Default constructor 
     * 
	 * @param cassandraHost
	 * @param cassandraPort
	 */
	LogMessageCassandraDao(final ICassandraSession cassandraSession) {
		cassandraKeySpace = cassandraSession.getCassandraKeySpace();
	}
	
	/**
	 * Find the log messages by environment and level
	 * 
	 */
	@Override
	public List<LogMessage> findByLevelAndEnvironment(final String level, final String environment) {
		
		final List<LogMessage> foundMessages = new ArrayList<LogMessage>();
		
		try {
			
			//build up and execute the query
			final OperationResult<Rows<String, String>> result = cassandraKeySpace.prepareQuery(logMessageCassandraInfo)
			    .searchWithIndex()
			    .setRowLimit(maxRowsToReturn)   // Number of rows returned
			    .addExpression()
			        .whereColumn("level").equals().value(level)
			    .addExpression()
			        .whereColumn("environment").equals().value(environment)
			    .execute();
			
			//Get the log messages from row objects
			for (Row<String, String> row : result.getResult()) {
				foundMessages.add(getLogMessageFromRow(row));
			}
			
		} catch (Exception e) {
			//log the error message 
			LOG.error("Error retrieving records LogMessageCassandraDao:findByLevelAndEnvironment for {} : " + e.getMessage(),level+":"+environment, e);
		}
		
		return foundMessages;
	}
	
	/**
	 * LogMessage 
	 * @param row
	 * @return
	 */
	private LogMessage getLogMessageFromRow(final Row<String, String> row) {
		
		final LogMessage message = new LogMessage();
		
		final ColumnList<String> resultColumns = row.getColumns();
		
		//set the uuid
		message.setUuid(row.getKey());
		
		//Gets the value from column
		message.setErrorMessage(resultColumns.getStringValue("errorMessage", null));
		message.setHost(resultColumns.getStringValue("host", null));
		message.setEnvironment(resultColumns.getStringValue("environment", null));
		message.setStackTrace(resultColumns.getStringValue("stackTrace", null));
		message.setErrorCode(resultColumns.getStringValue("errorCode", null));
		message.setLoggingTimestamp(resultColumns.getDateValue("loggingTimestamp", null));
		message.setLogType(resultColumns.getStringValue("logType", null));
		message.setLevel(resultColumns.getStringValue("level", null));
		
		return message;
	}
	
	
	/**
	 * Save the log message object and returns the persisted result
	 * 
	 */
	@Override
	public PersistedResult<LogMessage> persist(final PersistedObject obj)
			throws TransactionSystemException {
		
		//Message to be saved
		final LogMessage messageToSave = (LogMessage)obj; 
		
		//the saved result
		final PersistedResult<LogMessage> saveResult = new PersistedResult<LogMessage>(messageToSave);
		
		//if no uuid then then has not been saved before
		if(messageToSave.getUuid() == null) {
			messageToSave.setUuid(UUID.randomUUID().toString());
		}
		
		//batch for saving
		final MutationBatch batch = cassandraKeySpace.prepareMutationBatch();
		
		//Create the column list
		final ColumnListMutation<String> columnList = batch.withRow(logMessageCassandraInfo, messageToSave.getUuid());
		
		//get the log messages info into the query
		putLogMessageIntoBatch(messageToSave,columnList);
		
		//execute
		try {
		    batch.execute();
		} catch (final ConnectionException e) {
			handeException(e, messageToSave, saveResult);
		}
		
		return saveResult;
	}

	/**
	 * Puts the log message into the batch
	 * 
	 * @param messageToSave
	 * @param batch
	 */
	private void putLogMessageIntoBatch(final LogMessage messageToSave , final ColumnListMutation<String> columnList) {
		
		//puts the value in for storage insures non of them are null
		if(messageToSave.getErrorMessage()!=null)
			columnList.putColumn("errorMessage", messageToSave.getErrorMessage(), null);
		if(messageToSave.getHost()!=null)
			columnList.putColumn("host", messageToSave.getHost(), null);
	    if(messageToSave.getEnvironment()!=null)
			columnList.putColumn("environment", messageToSave.getEnvironment(), null);
	    if(messageToSave.getStackTrace()!=null)
			columnList.putColumn("stackTrace", messageToSave.getStackTrace(), null);
	    if(messageToSave.getErrorCode()!=null)
			columnList.putColumn("errorCode", messageToSave.getErrorCode(), null);
	    if(messageToSave.getLoggingTimestamp()!=null)
			columnList.putColumn("loggingTimestamp", messageToSave.getLoggingTimestamp(), null);
	    if(messageToSave.getLogType()!=null)
			columnList.putColumn("logType", messageToSave.getLogType(), null);
	    if(messageToSave.getLevel()!=null)
			columnList.putColumn("level", messageToSave.getLevel(), null);
	}
	
	/**
	 * Handles the saving exception
	 * 
	 * @param ex
	 * @param object
	 */
	private void handeException(final Exception e, final LogMessage message, final PersistedResult<LogMessage> saveResult) {
		
		// go json with the info
		final String logMessageJson =  message.toJson();

		//log the error message 
		LOG.error("Error saving LogMessageCassandraDao:persist {} with error " + e.getMessage(),logMessageJson, e);
		
		//setting the failing save result
		saveResult.add("Error saving log message " + logMessageJson);
	}

}
