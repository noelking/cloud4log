/**
 * 
 */
package org.cloud4log.server.service.implementation;

import org.cloud4log.server.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>BusinessObjectService</b>
 * <p>
 * BusinessObject Service provides a gateway to the business objects services available by the application, most common 
 * business object services i.e. CRUD are available through this service, however specific behaviour by
 * certain BO will have their own services exposed.
 * </p>
 * 
 * @author Noel King
 * @since 1.0
 */
public abstract class BusinessObjectService implements IBusinessService{


	/** Logger for business object service. */
	private final static Logger LOG = LoggerFactory.getLogger(BusinessObjectService.class);
	
}
