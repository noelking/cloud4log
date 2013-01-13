/**
 * 
 */
package org.cloud4log.server.dao.implementation.cassandra;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

/**
 * <b>CassandraSessionFactory</b>
 * 
 * <p> 
 * This class provides the protocol to the cassandra storage layer
 * </p>
 * @author nking
 *
 */
public class CassandraSession implements ICassandraSession {
	
	/** The cassandra host */
	private String host;
	
	/** The cassandra port*/
	private int port;
	
	/** String cassandra cluster name */
	private String clusterName;
	
	/** The key space for objects */
	private String keySpaceName;
	
	/** The max number of connections per host */
	private int maxConnectionsPerHost;
	
	/** Key space for accessing cassandra data */
	private Keyspace cassandraKeySpace; 
	
	/**
	 * Default constructor 
     * 
	 * @param cassandraHost
	 * @param cassandraPort
	 */
	CassandraSession(String cassandraHost, int cassandraPort, String keySpace,
		String clusterName, int maxConnectionsPerHost) {
		this.host = cassandraHost;
		this.port = cassandraPort;
		this.keySpaceName = keySpace;
		this.clusterName = clusterName;
		this.maxConnectionsPerHost = maxConnectionsPerHost;
		
		//start the cassandra connection
		start();
	}
	
	/**
	 * Start the cassandra connection
	 * 
	 */
	private void start() {
		
		//connection pool name
		final String connectionPoolName = "Cloud4LogConnectionPool";
		
		//Create the context
		final AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
	    .forCluster(clusterName)
	    .forKeyspace(keySpaceName)
	    .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()      
	        .setDiscoveryType(NodeDiscoveryType.NONE)
	    )
	    .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl(connectionPoolName)
	        .setPort(port)
	        .setMaxConnsPerHost(maxConnectionsPerHost)
	        .setSeeds(host+":"+port)
	    )
	    .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
	    .buildKeyspace(ThriftFamilyFactory.getInstance());
		
		//start the context
		context.start();
		
		//create the key space
		cassandraKeySpace = context.getEntity();
	}

	/**
	 * Returns the keyspace used for interacting with cassandra
	 * 
	 * 
	 * @return the cassandraKeySpace
	 */
	public Keyspace getCassandraKeySpace() {
		return cassandraKeySpace;
	}

}
