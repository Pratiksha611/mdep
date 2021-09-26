package com.pratiket.connector.service;

import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.connection.model.JobExecution;
import com.pratiket.exception.ConnectionException;
import com.pratiket.exception.ConnectorException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.Map;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
public interface ConnectorService
{
    Map<String, String> testConnection(ConnectionConfiguration connectionConfiguration);
    List<String> getDatabaseList(String connectionConfigurationId) throws ConnectorException;
    List<String> getTableList(String connectionConfigurationId, String databaseName) throws ConnectorException;

    Map<String, String> executeJob(List<Map<String, String>> jobExecution);
    Dataset<Row> readDataset(ConnectionConfiguration connectionConfiguration);
    void writeDataset(Dataset<Row> dataset, ConnectionConfiguration connectionConfiguration);
    ConnectionConfiguration convertStringToConnectionConfiguration(String connectionConfiguration) throws ConnectionException;
}
