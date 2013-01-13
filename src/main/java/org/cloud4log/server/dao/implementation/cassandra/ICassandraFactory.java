/**
 * 
 */
package org.cloud4log.server.dao.implementation.cassandra;

/**
 * <b>ICassandraFactory</b>
 * 
 * This factory provides access to cassandra session object
 * @author nking
 *
 */
public interface ICassandraFactory {
	ICassandraSession getCassandraSession();
}
