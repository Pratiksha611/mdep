package com.pratiket.connector.service.impl.nosql;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.config.WriteConfig;
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
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Service
@Log4j2
public class MongoDBConnectorService extends AbstractConnectorService
{
    @Override
    public Map<String, String> testConnection(ConnectionConfiguration connectionConfiguration) {
        Map<String, String> testResponse = new HashMap<>();
        String testResult = testMongoDBConnection(connectionConfiguration);
        if(null == mongoClient)
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
    public List<String> getDatabaseList(String connectionConfigurationId)
    {
        List<String> databaseList = new ArrayList<>();
        createMongoDBConnection(connectionConfigurationId);
        return getObjectList(mongoClient.listDatabaseNames());
    }

    @Override
    public List<String> getTableList(String connectionConfigurationId, String databaseName) throws ConnectorException {
        createMongoDBConnection(connectionConfigurationId);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        return getObjectList(mongoDatabase.listCollectionNames());
    }

    @Override
    public Dataset<Row> readDataset(ConnectionConfiguration connectionConfiguration)
    {
        final String CONNECTION_URL = String.format(MdepConstants.MongodbConstants.CONNECTION_URL, connectionConfiguration.getUsername(), connectionConfiguration.getPassword(), connectionConfiguration.getHost(), connectionConfiguration.getPort());
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put(MdepConstants.MongodbConstants.URI, CONNECTION_URL);
        readOverrides.put(MdepConstants.MongodbConstants.DATABASE, connectionConfiguration.getDatabase());
        readOverrides.put(MdepConstants.MongodbConstants.IP_COLLECTION, connectionConfiguration.getCollection());
        ReadConfig readConfig = ReadConfig.create(readOverrides);
        Dataset<Row> dataset = MongoSpark.load(SparkSessionFactory.getJavaSparkContext(), readConfig).toDF();
        dataset.show();
        return dataset;
    }

    @Override
    public void writeDataset(Dataset<Row> dataset, ConnectionConfiguration connectionConfiguration)
    {
        final String CONNECTION_URL = String.format(MdepConstants.MongodbConstants.CONNECTION_URL, connectionConfiguration.getUsername(), connectionConfiguration.getPassword(), connectionConfiguration.getHost(), connectionConfiguration.getPort());
        Map<String, String> writeOverrides = new HashMap<>();
        writeOverrides.put(MdepConstants.MongodbConstants.URI, CONNECTION_URL);
        writeOverrides.put(MdepConstants.MongodbConstants.DATABASE, connectionConfiguration.getDatabase());
        writeOverrides.put(MdepConstants.MongodbConstants.OP_COLLECTION, connectionConfiguration.getCollection());
        WriteConfig writeConfig = WriteConfig.create(writeOverrides);
        //MongoSpark.write(dataset).options(writeOverrides).mode(SaveMode.Overwrite);
        MongoSpark.save(dataset.write().mode(SaveMode.Overwrite), writeConfig);
    }

    private void createMongoDBConnection(String connectionConfigurationId)
    {
        ConnectionConfiguration connectionConfiguration = fetchConnectionConfiguration(connectionConfigurationId);
        String connectionURL = String.format(MdepConstants.MongodbConstants.CONNECTION_URL, connectionConfiguration.getUsername(), connectionConfiguration.getPassword(), connectionConfiguration.getHost(), connectionConfiguration.getPort());
        MongoClientURI mongoClientURI = new MongoClientURI(connectionURL);
        this.mongoClient = new MongoClient(mongoClientURI);
    }

    private String testMongoDBConnection(ConnectionConfiguration connectionConfiguration)
    {
        String connectionURL = String.format(MdepConstants.MongodbConstants.CONNECTION_URL, connectionConfiguration.getUsername(), connectionConfiguration.getPassword(), connectionConfiguration.getHost(), connectionConfiguration.getPort());
        log.info("before uri");
        MongoClientURI mongoClientURI = new MongoClientURI(connectionURL);
        log.info("before try");
        try
        {
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            log.info("In try block");
            this.mongoClient = mongoClient;
            log.info(getObjectList(mongoClient.listDatabaseNames()));
            return MdepConstants.ErrorMessageConstants.CONNECTION_SUCCESS_MSG;
        } catch (Exception e)
        {
            log.info("In catch block");
            return ExceptionUtils.getStackTrace(e);
        }
    }
}
