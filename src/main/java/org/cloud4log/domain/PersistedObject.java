/**
 * 
 */
package org.cloud4log.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import flexjson.JSONSerializer;

// TODO Auto-generated Javadoc
/**
 * <b>PersistedObject</b>
 * <p>
 * This abstract class provides a gateway to all Persisted Pojo object, only objects which you would want to be saved in the database
 * should extend this class.
 * </p>
 * 
 * 
 * @author Noel King
 * @since 1.0
 */
public abstract class PersistedObject {
	
	/**
	 * Returns the Id value.
	 *
	 * @return the id
	 */
	public abstract Long getId();
    
    /**
     * Converts the object to JSON.
     *
     * @return the string
     */
    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

}
