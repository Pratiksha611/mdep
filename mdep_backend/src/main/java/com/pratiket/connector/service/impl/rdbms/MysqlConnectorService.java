package com.pratiket.connector.service.impl.rdbms;

import com.pratiket.common.configuration.SparkSessionFactory;
import com.pratiket.common.constants.MdepConstants;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.connector.service.AbstractConnectorService;
import com.pratiket.exception.ConnectorException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Primary
@Service
@Log4j2
public class MysqlConnectorService extends AbstractConnectorService
{

    private Connection connection;

    private Statement statement;

    @Override
    public Map<String, String> testConnection(ConnectionConfiguration connectionConfiguration)
    {
        Map<String, String> testResponse = new HashMap<>();
        String testResult = testMYSQLConnection(connectionConfiguration);
        if(null == connection)
        {
            testResponse.put(MdepConstants.ErrorMessageConstants.STATUS, String.valueOf(false));
            testResponse.put(MdepConstants.ErrorMessageConstants.MESSAGE, MdepConstants.ErrorMessageConstants.CONNECTION_FAILED_MSG);
            testResponse.put(MdepConstants.ErrorMessageConstants.DETAIL_MESSAGE, testResult);
        }
        else
        {
            testResponse.put(MdepConstants.ErrorMessageConstants.STATUS, String.valueOf(true));
            testResponse.put(MdepConstants.ErrorMessageConstants.MESSAGE, testResult);
        }
        return testResponse;
    }

    @Override
    public List<String> getDatabaseList(String connectionConfigurationId) throws ConnectorException
    {
        createMYSQLConnection(connectionConfigurationId);
        return executeQuery(MdepConstants.MysqlConstants.SHOW_DATABASE_QUERY);
    }

    @Override
    public List<String> getTableList(String connectionConfigurationId, String databaseName)
    {
        createMYSQLConnection(connectionConfigurationId);
        return executeQuery(String.format(MdepConstants.MysqlConstants.GET_TABLE_QUERY, databaseName));
    }

    public static void main(String[] args) {
        new MysqlConnectorService().executeJob(Collections.emptyList());
    }

    @Override
    public Dataset<Row> readDataset(ConnectionConfiguration connectionConfiguration)
    {
        log.info("connectionConfiguration -> "+connectionConfiguration);
        final String CONNECTION_URL = String.format(MdepConstants.MysqlConstants.SPARK_CONNECTION_URL,
                connectionConfiguration.getHost(), connectionConfiguration.getPort(), connectionConfiguration.getDatabase());
        Dataset<Row> dataSet = SparkSessionFactory.getSparkSession().read()
                .format(MdepConstants.MysqlConstants.JDBC)
                .option(MdepConstants.MysqlConstants.URL, CONNECTION_URL)
                .option(MdepConstants.MysqlConstants.USER, connectionConfiguration.getUsername())
                .option(MdepConstants.MysqlConstants.PASSWORD, connectionConfiguration.getPassword())
                .option(MdepConstants.MysqlConstants.DRIVER, MdepConstants.MysqlConstants.DRIVER_NAME)
                .option(MdepConstants.MysqlConstants.DBTABLE, connectionConfiguration.getTable())
                .load();
        dataSet.show();
        return dataSet;
    }

    public void writeDataset(Dataset<Row> dataset, ConnectionConfiguration connectionConfiguration)
    {
        log.info("connectionConfiguration -> "+ connectionConfiguration);
        if(Arrays.asList(dataset.columns()).contains("_id"))
        {
            dataset = dataset.drop(dataset.col("_id"));
        }
        final String CONNECTION_URL = String.format(MdepConstants.MysqlConstants.SPARK_CONNECTION_URL,
                connectionConfiguration.getHost(), connectionConfiguration.getPort(), connectionConfiguration.getDatabase());
        dataset.write()
                .format(MdepConstants.MysqlConstants.JDBC)
                .option(MdepConstants.MysqlConstants.URL, CONNECTION_URL)
                .option(MdepConstants.MysqlConstants.USER, connectionConfiguration.getUsername())
                .option(MdepConstants.MysqlConstants.PASSWORD, connectionConfiguration.getPassword())
                .option(MdepConstants.MysqlConstants.DRIVER, MdepConstants.MysqlConstants.DRIVER_NAME)
                .option(MdepConstants.MysqlConstants.DBTABLE, connectionConfiguration.getTable())
                .mode(SaveMode.Overwrite)
                .save();
    }

    private List<String> executeQuery(String query)
    {
        List<String> stringList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
                stringList.add(resultSet.getString(1));
            statement.close();
            connection.close();
        }
        catch (Exception e)
        {
            throw ConnectorException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e))
                    .build();
        }
        return stringList;
    }

    private void createMYSQLConnection(String connectionConfigurationId)
    {
        try {
            ConnectionConfiguration connectionConfiguration = fetchConnectionConfiguration(connectionConfigurationId);
            log.info("connectionConfiguration - "+ connectionConfiguration);
            String connectionURL = String.format(MdepConstants.MysqlConstants.CONNECTION_URL,
                    connectionConfiguration.getHost(), connectionConfiguration.getPort());
            log.info("connectionURL - "+ connectionURL);
            connection = DriverManager.getConnection(
                    connectionURL, connectionConfiguration.getUsername(), connectionConfiguration.getPassword());
            statement = connection.createStatement();
        }
        catch (Exception e)
        {
            throw ConnectorException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e))
                    .build();
        }
    }

    private String testMYSQLConnection(ConnectionConfiguration connectionConfiguration)
    {
            log.info("connectionConfiguration - "+ connectionConfiguration);
            String connectionURL = String.format(MdepConstants.MysqlConstants.CONNECTION_URL,
                    connectionConfiguration.getHost(), connectionConfiguration.getPort());
            log.info("connectionURL - "+ connectionURL);
        try (Connection connection = DriverManager.getConnection(
                    connectionURL, connectionConfiguration.getUsername(), connectionConfiguration.getPassword()))
        {
            this.connection = connection;
            return MdepConstants.ErrorMessageConstants.CONNECTION_SUCCESS_MSG;
        }
        catch (Exception e)
        {
            return ExceptionUtils.getStackTrace(e);
        }
    }
}
