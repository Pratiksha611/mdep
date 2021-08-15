package com.pratiket.connector;

import com.pratiket.connection.entity.ConnectionType;
import com.pratiket.connector.service.MongoDBConnectorService;
import com.pratiket.connector.service.MysqlConnectorService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
public class ConnectorServiceFactory
{

    public ConnectorService getConnectorService(ConnectionType connectionType)
    {
        ConnectorService connectorService = null;
        switch (connectionType)
        {
            case MONGODB:
                connectorService = new MongoDBConnectorService();
                break;
            case MYSQL:
            default:
                connectorService = new MysqlConnectorService();
                break;
        }
        return connectorService;
    }
}
