package com.pratiket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratiket.connection.dto.ConnectionConfigDTO;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.repository.ConnectionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Pratiksha Deshmukh
 * created on 06-08-2021
 */
@SpringBootApplication
@Service
public class MdepBackendApplication
{
    @Autowired
    private ConnectionRepository connectionRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MdepBackendApplication.class, args);
    }

    private void test() throws Exception {
        /*ObjectMapper objectMapper = new ObjectMapper();
        String str = "{\"connectionConfig\":[{\"connectionName\":\"pratu_con\",\"connectionConfiguration\":\"{\\\"ip\\\": \\\"192.32.22.32\\\",\\\"port\\\": \\\"3306\\\"}\"}],\"connectionType\":\"MYSQL\"}";
        ConnectionDTO connectionDTO = objectMapper.readValue(str, ConnectionDTO.class);
        System.out.println(connectionDTO);
        Connection connection1 = new Connection();
        BeanUtils.copyProperties(connectionDTO, connection1);
        Connection connection2 = connectionRepository.save(connection1);
        System.out.println(connection2);*/
    }
}
