package com.pratiket.connection.controller;

import com.pratiket.connection.entity.Connection;
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

    static final String ROOT = "/api";

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

}
