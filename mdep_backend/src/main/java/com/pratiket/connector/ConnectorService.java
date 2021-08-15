package com.pratiket.connector;

import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.exception.ConnectionException;

import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
public interface ConnectorService
{
    boolean testConnection(String connectionConfigurationInfo);
    List<String> getDatabaseList();
    List<String> getTableList();
}
