package com.pratiket.connection.service;

import com.pratiket.common.constants.MdepConstants;
import com.pratiket.connection.dto.ConnectionConfigDTO;
import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionConfig;
import com.pratiket.connection.entity.ConnectionType;
import com.pratiket.connection.repository.ConnectionConfigRepository;
import com.pratiket.connection.repository.ConnectionRepository;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.exception.ConnectionException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @Autowired
    private ConnectionConfigRepository connectionConfigRepository;

    @Override
    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public ConnectionDTO createConnection(ConnectionDTO connectionDTO) {
        log.info("connection - "+connectionDTO);
        Optional<Connection> existingConnectionOptional = connectionRepository.findByConnectionType(connectionDTO.getConnectionType());
        log.info("existingConnectionOptional -> "+ existingConnectionOptional);
        Connection existingConnection = existingConnectionOptional.orElse(null);
        Connection connection = existingConnection != null ? existingConnection : new Connection();
        connection.setConnectionType(connectionDTO.getConnectionType());
        List<ConnectionConfig> connectionConfigList = new ArrayList<>();
        ConnectionConfigDTO element = connectionDTO.getConnectionConfig().get(0);
        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setConnectionConfiguration(element.getConnectionConfiguration());
        connectionConfig.setConnectionName(element.getConnectionName());
        connectionConfig.setConnection(connection);
        connectionConfigList.add(connectionConfig);
        connection.setConnectionConfig(connectionConfigList);
        log.info("connection  -> "+connection);
        Connection connection2 = connectionRepository.save(connection);
        return new ConnectionDTO(connection2);
    }

    @Override
    public Connection getConnectionByConnectionId(String connectionId) {
        if(StringUtils.isEmpty(connectionId))
            throw ConnectionException.builder()
                    .message(MdepConstants.ErrorMessageConstants.CONNECTION_ID_NULL)
                    .detailMessage(MdepConstants.ErrorMessageConstants.CONNECTION_ID_NULL)
                    .build();
        return connectionRepository.findById(connectionId).orElse(null);
    }

    @Override
    public Connection getConnectionByConnectionType(ConnectionType connectionType) {
        return connectionRepository.findByConnectionType(connectionType).orElse(null);
    }

    @Override
    public ConnectionConfig getConnectionByConnectionConfigId(String connectionConfigId) {
        return connectionConfigRepository.findById(connectionConfigId).orElse(null);
    }
}
