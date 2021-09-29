package com.pratiket.connector.service.factory;

import com.pratiket.connection.entity.ConnectionType;
import com.pratiket.connector.service.ConnectorService;
import com.pratiket.connector.service.impl.nosql.MongoDBConnectorService;
import com.pratiket.connector.service.impl.rdbms.MysqlConnectorService;
import com.pratiket.connector.service.impl.rdbms.OracleConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Component
public class ConnectorServiceFactory implements ServiceFactory
{
    @Autowired
    private MongoDBConnectorService mongoDBConnectorService;

    @Autowired
    private MysqlConnectorService mysqlConnectorService;

    @Autowired
    private OracleConnectorService oracleConnectorService;

    @Override
    public ConnectorService getConnectorService(ConnectionType connectionType)
    {
        switch (connectionType)
        {
            case MONGODB:
                return mongoDBConnectorService;
            case MYSQL:
            default:
                return mysqlConnectorService;
        }
    }
}
