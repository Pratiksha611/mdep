package com.pratiket.connector.controller;

import com.pratiket.connection.controller.ConnectionController;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionType;
import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.connection.model.JobExecution;
import com.pratiket.connector.service.AbstractConnectorService;
import com.pratiket.connector.service.ConnectorService;
import com.pratiket.connector.service.factory.ServiceFactory;
import com.pratiket.exception.ConnectionException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Pratiksha Deshmukh
 * created on 30-08-2021
 */
@RestController
@RequestMapping(ConnectorController.ROOT)
@Log4j2
@CrossOrigin("*")
public class ConnectorController
{
    static final String ROOT = "/api/connector/";

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private AbstractConnectorService abstractConnectorService;

    @PostMapping(value = "/v1/testConnection/connectionType={connectionType}", headers = "Accept=application/json")
    public ResponseEntity<Map<String, String>> testConnection(@RequestBody ConnectionConfiguration connectionConfiguration, @PathVariable("connectionType") ConnectionType connectionType) {
        try {
            return new ResponseEntity<Map<String, String>>(serviceFactory.getConnectorService(connectionType).testConnection(connectionConfiguration), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @GetMapping(value = "/v1/getDatabaseList/connectionConfigurationId={connectionConfigurationId}/connectionType={connectionType}", headers = "Accept=application/json")
    public ResponseEntity<List<String>> getDatabaseList(@PathVariable("connectionConfigurationId") String connectionConfigurationId, @PathVariable("connectionType") ConnectionType connectionType) {
        try {
            return new ResponseEntity<List<String>>(serviceFactory.getConnectorService(connectionType).getDatabaseList(connectionConfigurationId), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @GetMapping(value = "/v1/getTableList/connectionConfigurationId={connectionConfigurationId}/connectionType={connectionType}/databaseName={databaseName}", headers = "Accept=application/json")
    public ResponseEntity<List<String>> getTableList(@PathVariable("connectionConfigurationId") String connectionConfigurationId, @PathVariable("connectionType") ConnectionType connectionType, String databaseName) {
        try {
            return new ResponseEntity<List<String>>(serviceFactory.getConnectorService(connectionType).getTableList(connectionConfigurationId, databaseName), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @PostMapping(value = "/v1/executeJob", headers = "Accept=application/json")
    public ResponseEntity<Map<String, String>> executeJob(@Valid @RequestBody List<Map<String, String>> jobExecutionList) {
        try {
            return new ResponseEntity<Map<String, String>>(abstractConnectorService.executeJob(jobExecutionList), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }
}
