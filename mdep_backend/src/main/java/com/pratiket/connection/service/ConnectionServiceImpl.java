package com.pratiket.connection.service;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.repository.ConnectionRepository;
import com.pratiket.connection.dto.ConnectionDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ConnectionDTO createConnection(ConnectionDTO connection) {
        Connection connection1 = new Connection();
        BeanUtils.copyProperties(connection, connection1);
        connection1.setCreatedDate(new Date());
        connection1.setUpdatedDate(new Date());
        Connection connection2 = connectionRepository.save(connection1);
        return new ConnectionDTO(connection2);
    }
}
