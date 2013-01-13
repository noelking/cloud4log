/**
 * 
 */
package org.cloud4log.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


// TODO Auto-generated Javadoc
/**
 * <b>PersistedResult</b>
 * <p>
 * This class provides the presisted result for a saved entity, it contains the saved entity and the list of error messages if any is available.
 * </p>
 *
 * @param <T> the generic type
 * @author Noel King
 * @since 1.0
 */
@RooJavaBean
@RooToString
@RooJson
public class PersistedResult<T extends PersistedObject> {

	/** Object being saved. */
	private T objectSaved;
	
	/** Error messages if any. */
	private List<String> presistingErrorMessages = null;
	
	/**
	 * Persisted Result object must have a persistedObject to be valid.
	 *
	 * @param objectSaved the object saved
	 */
	public PersistedResult(T objectSaved) {
		this.objectSaved=objectSaved;
		this.presistingErrorMessages = new ArrayList<String>();
	}

	/**
	 * Gets the presisting error messages.
	 *
	 * @return the presistingErrorMessages
	 */
	public List<String> getPresistingErrorMessages() {
		return this.presistingErrorMessages;
	}
	
	/**
	 * Adds the error message.
	 *
	 * @param errorMessage the error message
	 */
	public void add(String errorMessage) {
		this.presistingErrorMessages.add(errorMessage);
	}

	/**
	 * Gets the object saved.
	 *
	 * @return the objectSaved
	 */
	public T getObjectSaved() {
		return this.objectSaved;
	}
	
	/**
	 * Sets the persisted object.
	 *
	 * @param objectSaved the new object saved
	 */
	public void setObjectSaved(T objectSaved) {
		this.objectSaved = objectSaved;
	}
	
    /**
     * From json to persisted result.
     *
     * @param json the json
     * @return the persisted result
     */
    public static PersistedResult fromJsonToPersistedResult(String json) {
        return new JSONDeserializer<PersistedResult>().use(null, PersistedResult.class).deserialize(json);
    }
    
    /**
     * To json array.
     *
     * @param collection the collection
     * @return the string
     */
    public static String toJsonArray(Collection<PersistedResult> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    /**
     * From json array to persisted results.
     *
     * @param json the json
     * @return the collection
     */
    public static Collection<PersistedResult> fromJsonArrayToPersistedResults(String json) {
        return new JSONDeserializer<List<PersistedResult>>().use(null, ArrayList.class).use("values", PersistedResult.class).deserialize(json);
    }
}
