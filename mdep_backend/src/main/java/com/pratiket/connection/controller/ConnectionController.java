package com.pratiket.connection.controller;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionConfig;
import com.pratiket.connection.entity.ConnectionType;
import com.pratiket.connection.service.ConnectionService;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.exception.ConnectionException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@RestController
@RequestMapping(ConnectionController.ROOT)
@Log4j2
@CrossOrigin("*")
public class ConnectionController {

    static final String ROOT = "/api/connections/";

    @Autowired
    private ConnectionService connectionService;

    @GetMapping(value = "/v1/getAllConnections", headers = "Accept=application/json")
    public ResponseEntity<List<Connection>> getAllConnections() {
        try {
            return new ResponseEntity<List<Connection>>(connectionService.getAllConnections(), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @GetMapping(value = "/v1/getConnectionByConnectionId/connectionId={connectionId}", headers = "Accept=application/json")
    public ResponseEntity<Connection> getConnectionByConnectionId(@PathVariable("connectionId") String connectionId) {
        try {
            return new ResponseEntity<Connection>(connectionService.getConnectionByConnectionId(connectionId), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @GetMapping(value = "/v1/getConnectionByConnectionType/connectionType={connectionType}", headers = "Accept=application/json")
    public ResponseEntity<Connection> getConnectionByConnectionType(@PathVariable("connectionType") ConnectionType connectionType) {
        try {
            return new ResponseEntity<Connection>(connectionService.getConnectionByConnectionType(connectionType), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @PostMapping(value = "/v1/createConnections", headers = "Accept=application/json")
    public ResponseEntity<ConnectionDTO> createConnections(@Valid @RequestBody ConnectionDTO connection)
    {
        try
        {
            return new ResponseEntity<ConnectionDTO>(connectionService.createConnection(connection), HttpStatus.OK);
        }
        catch (ConnectionException e) {
            log.error("Error occurred while creating connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    @GetMapping(value = "/v1/getConnectionByConnectionConfigId/connectionConfigId={connectionConfigId}", headers = "Accept=application/json")
    public ResponseEntity<ConnectionConfig> getConnectionByConnectionConfigId(@PathVariable("connectionConfigId") String connectionConfigId) {
        try {
            return new ResponseEntity<ConnectionConfig>(connectionService.getConnectionByConnectionConfigId(connectionConfigId), HttpStatus.OK);
        } catch (ConnectionException e) {
            log.error("Error occurred while retrieving connections - " + e);
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

}
