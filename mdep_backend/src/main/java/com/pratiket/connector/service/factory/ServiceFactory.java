package com.pratiket.connector.service.factory;

import com.pratiket.connection.entity.ConnectionType;
import com.pratiket.connector.service.ConnectorService;

/**
 * @author Pratiksha Deshmukh
 * created on 30-08-2021
 */
public interface ServiceFactory
{
    ConnectorService getConnectorService(ConnectionType connectionType);
}
