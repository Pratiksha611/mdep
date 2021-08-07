package com.pratiket.connection.service;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.dto.ConnectionDTO;

import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
public interface ConnectionService
{
    List<Connection> getAllConnections();

    ConnectionDTO createConnection(ConnectionDTO connectionDTO);
}
