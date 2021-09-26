package com.pratiket.connector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;
import com.pratiket.common.utils.MdepUtils;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.entity.ConnectionConfig;
import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.connection.service.ConnectionService;
import com.pratiket.connector.service.factory.ServiceFactory;
import com.pratiket.exception.ConnectionException;
import com.pratiket.job.JobExecutionClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Component
@Log4j2
public abstract class AbstractConnectorService implements ConnectorService
{
    protected MongoClient mongoClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ServiceFactory serviceFactory;

    protected ConnectionConfiguration fetchConnectionConfiguration(String connectionConfigurationId) throws ConnectionException {
        ConnectionConfig ConnectionConfig = connectionService.getConnectionByConnectionConfigId(connectionConfigurationId);
        return convertStringToConnectionConfiguration(ConnectionConfig.getConnectionConfiguration());
    }

    protected List<String> getObjectList(MongoIterable<String> mongoIterable)
    {
        List<String> stringList = new ArrayList<>();
        if(null != mongoIterable)
        {
            Iterator<String> iterator = mongoIterable.iterator();
            while(iterator.hasNext())
                stringList.add(iterator.next());
        }
        return stringList;
    }

    @Override
    public Map<String, String> executeJob(List<Map<String, String>> jobExecutionList) {
        log.info("jobExecution -> "+ jobExecutionList);
        Map<String, String> responseMap = new HashMap<>();
        jobExecutionList.stream().forEach(jobExecution -> {
            JobExecutionClient jobExecutionClient = new JobExecutionClient(serviceFactory, jobExecution);
            Thread thread = new Thread(jobExecutionClient);
            thread.start();
            responseMap.put(jobExecution.get("jobName"), String.valueOf(MdepUtils.generateRandomNumber()));
        });
        return responseMap;
    }

    public ConnectionConfiguration convertStringToConnectionConfiguration(String connectionConfiguration) throws ConnectionException
    {
        try
        {
            return objectMapper.readValue(connectionConfiguration, ConnectionConfiguration.class);
        } catch (IOException e) {
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }
}
