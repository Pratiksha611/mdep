package com.pratiket.job;

import com.pratiket.common.utils.MdepUtils;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connector.service.ConnectorService;
import com.pratiket.connector.service.factory.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Pratiksha Deshmukh
 * created on 24-09-2021
 */
@Log4j2
public class JobExecutionClient implements Runnable
{

    private final ServiceFactory serviceFactory;

    private final Map<String, String> jobExecutionMap;

    public JobExecutionClient(ServiceFactory serviceFactory, Map<String, String> jobExecutionMap) {
        this.serviceFactory = serviceFactory;
        this.jobExecutionMap = jobExecutionMap;
    }

    @Override
    public void run()
    {
        log.info("serviceFactory -> "+ serviceFactory);
        ConnectionDTO fromConnectionDTO = MdepUtils.getClass(jobExecutionMap.get("from"), ConnectionDTO.class);
        log.info("fromConnectionDTO -> "+ fromConnectionDTO);
        ConnectorService connectorService = serviceFactory.getConnectorService(fromConnectionDTO.getConnectionType());
        Dataset<Row> dataset = connectorService.readDataset(connectorService.convertStringToConnectionConfiguration(fromConnectionDTO.getConnectionConfig().get(0).getConnectionConfiguration()));

        ConnectionDTO toConnectionDTO = MdepUtils.getClass(jobExecutionMap.get("to"), ConnectionDTO.class);
        ConnectorService toConnectorService = serviceFactory.getConnectorService(toConnectionDTO.getConnectionType());
        toConnectorService.writeDataset(dataset, connectorService.convertStringToConnectionConfiguration(toConnectionDTO.getConnectionConfig().get(0).getConnectionConfiguration()));
    }
}
