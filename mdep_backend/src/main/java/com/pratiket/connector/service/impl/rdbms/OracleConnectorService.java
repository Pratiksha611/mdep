package com.pratiket.connector.service.impl.rdbms;

import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.connector.service.AbstractConnectorService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Pratiksha Deshmukh
 * created on 30-08-2021
 */
@Service
public class OracleConnectorService extends AbstractConnectorService
{

    @Override
    public Map<String, String> testConnection(ConnectionConfiguration connectionConfiguration) {
        return null;
    }

    @Override
    public List<String> getDatabaseList(String connectionId) {
        return null;
    }

    @Override
    public List<String> getTableList(String connectionId, String databaseName) {
        return null;
    }

    @Override
    public Dataset<Row> readDataset(ConnectionConfiguration connectionConfiguration) {
        return null;
    }

    @Override
    public void writeDataset(Dataset<Row> dataset, ConnectionConfiguration connectionConfiguration) {

    }
}
