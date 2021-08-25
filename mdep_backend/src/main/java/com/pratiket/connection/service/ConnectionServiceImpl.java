package com.pratiket.connection.service;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionConfig;
import com.pratiket.connection.repository.ConnectionRepository;
import com.pratiket.connection.dto.ConnectionDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@Service
@Log4j2
public class ConnectionServiceImpl extends AbstractConnectionService
{
    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public ConnectionDTO createConnection(ConnectionDTO connectionDTO) {
        log.info("connection - "+connectionDTO);
        Connection connection = new Connection();
        List<ConnectionConfig> connectionConfigList = new ArrayList<>();
        connection.setConnectionType(connectionDTO.getConnectionType());
        connectionDTO.getConnectionConfig().stream().forEach(element -> {
            ConnectionConfig connectionConfig = new ConnectionConfig();
            connectionConfig.setConnectionConfiguration(element.getConnectionConfiguration());
            connectionConfig.setConnectionName(element.getConnectionName());
            connectionConfig.setConnection(connection);
            connectionConfigList.add(connectionConfig);
        });
        connection.setConnectionConfig(connectionConfigList);
        log.info("connection1 -> "+connection);
        //BeanUtils.copyProperties(connection, connection1, "connectionConfigId");
        Connection connection2 = connectionRepository.save(connection);
        return new ConnectionDTO(connection2);
    }
}
