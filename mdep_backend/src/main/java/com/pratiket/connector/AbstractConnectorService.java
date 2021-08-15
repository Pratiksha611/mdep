package com.pratiket.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.exception.ConnectionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
public abstract class AbstractConnectorService implements ConnectorService
{

    protected ConnectionConfiguration connectionConfiguration;

    private ObjectMapper objectMapper;


    protected void initialize()
    {

    }

    public void fetchConnectionConfiguration(String connectionConfigurationInfo) throws ConnectionException {
        objectMapper = new ObjectMapper();
        try {
            connectionConfiguration = objectMapper.readValue(connectionConfigurationInfo, ConnectionConfiguration.class);
        } catch (IOException e) {
            throw ConnectionException.builder()
                  .message(e.getMessage())
                  .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @Override
    public List<String> getDatabaseList() {
        return null;
    }

    @Override
    public List<String> getTableList() {
        return null;
    }

    public abstract boolean testConnection(String connectionConfigurationInfo);
}
