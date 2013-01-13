/**
 * 
 */
package org.cloud4log.server.dao.implementation.cassandra;

/**
 * <b>CassandraFactory</b>
 * 
 * The cassandra factory is the only way you can access the cassandra session for interaction with the cassandra storage model
 * 
 * @author nking
 *
 */
public class CassandraFactory implements ICassandraFactory {

	private ICassandraSession cassandraSession;
	
	/**
	 * Constructor providing the session object 
	 * 
	 * @param cassandraSession
	 */
	CassandraFactory(ICassandraSession cassandraSession) {
		this.cassandraSession = cassandraSession;
	}
	
	/**
	 * Returns the application cassandra session
	 */
	@Override
	public ICassandraSession getCassandraSession() {
		return cassandraSession;
	}
	
}
