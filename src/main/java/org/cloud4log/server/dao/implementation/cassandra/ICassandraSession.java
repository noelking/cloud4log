/**
 * 
 */
package org.cloud4log.server.dao.implementation.cassandra;

import com.netflix.astyanax.Keyspace;

/**
 * <b>CassandraSession</b>
 * 
 * Provide access to the cassandra session for interacting with your cassandra data
 * 
 * @author nking
 *
 */
public interface ICassandraSession {

	/**
	 * Returns the keyspace used for interacting with cassandra
	 * 
	 * 
	 * @return the cassandraKeySpace
	 */
	Keyspace getCassandraKeySpace();
}
