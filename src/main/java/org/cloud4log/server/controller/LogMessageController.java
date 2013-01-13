/**
 * 
 */
package org.cloud4log.server.controller;

import java.util.List;

import javax.annotation.Resource;

import org.cloud4log.domain.LogMessage;
import org.cloud4log.domain.PersistedResult;
import org.cloud4log.server.service.ILogMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <b>LogMessageController</b>
 * <p>
 * A rest controller provide external sources an api for REST based interaction with the LogMessage framework.  This class is available for 
 * use over http requests and should not be instantiated in code.
 * </p>
 * 
 * <b> Example how to call this class </b>
 * <pre>
 *    
 * </pre>
 * 
 * @author Noel King
 * @since 1.0
 */
@Controller
@RequestMapping("/logmessage")
public class LogMessageController implements ILogMessageController {
	
	/** The Constant LOG. */
	private final static Logger LOG = LoggerFactory.getLogger(LogMessageController.class);
	
	/** Log Message Service */
	@Resource(name = "logMessageServiceFactory")
	private ILogMessageService logMessageService;
 
	/**
	 * Instantiates a new log message controller.
	 */
	public LogMessageController() {
	}

	/**
	 * This method returns all log messages for a given level.
	 *
	 * @param level the level
	 * @return The list of log messages, or HTTTP.NOT_FOUND status if none found
	 * @author Noel King
	 */
	@RequestMapping(value="/{environment}/{level}", 
			method=RequestMethod.GET, 
			headers="Accept=application/json")
	@ResponseBody
	public String getLogMessagesByLevel(final @PathVariable("environment") String environment, 
			final @PathVariable("level") String level) {
		
		final List<LogMessage> messages = logMessageService.findByLevelAndEnvironment(level, environment);
		
		//returns messages as json String
		return LogMessage.toJsonArray(messages);
	}
	
	/**
	 * Creates the log message.
	 *
	 * @param messageJson the message json
	 * @return the response entity
	 */
	@RequestMapping(value="/create", 
			method=RequestMethod.POST, 
			headers="Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> create(final @RequestBody String messageJson) {
		
		final LogMessage messageToSave = LogMessage.fromJsonToLogMessage(messageJson);
		
		//save the message
		final PersistedResult<LogMessage> result = logMessageService.save(messageToSave);
		
		//if does not save return bad request to client
		if(result.getPresistingErrorMessages().size()>0)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		//otherwise return success
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
}
