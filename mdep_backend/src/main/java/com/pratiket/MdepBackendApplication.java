package com.pratiket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratiket.connection.dto.ConnectionConfigDTO;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.model.JobExecution;
import com.pratiket.connection.repository.ConnectionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//        String str = "[{\"from\":{\"connectionConfig\":[{\"connectionConfigId\":\"ff8081817baa2ed6017baa2f95db0002\",\"connectionName\":\"mysql_con1\",\"connectionConfiguration\":\"{\\\"host\\\": \\\"localhost\\\",\\\"port\\\": \\\"3306\\\", \\\"username\\\": \\\"root\\\",\\\"password\\\": \\\"Newuser@123\\\",\\\"database\\\":\\\"exam\\\", \\\"table\\\":\\\"EMP\\\"}\"}],\"connectionType\":\"MYSQL\",\"connectionId\":\"ff8081817b96aca7017b96acccd40000\"},\"to\":{\"connectionConfig\":[{\"connectionConfigId\":\"ff8081817baa2ed6017baa2f95db0002\",\"connectionName\":\"mysql_con1\",\"connectionConfiguration\":\"{\\\"host\\\": \\\"localhost\\\",\\\"port\\\": \\\"3306\\\", \\\"username\\\": \\\"root\\\",\\\"password\\\": \\\"Newuser@123\\\",\\\"database\\\":\\\"exam\\\", \\\"table\\\":\\\"EMP2\\\"}\"}],\"connectionType\":\"MYSQL\",\"connectionId\":\"ff8081817b96aca7017b96acccd40000\"}}]";
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<LinkedHashMap<String, ConnectionDTO>> jobExecutionList = objectMapper.readValue(str, List.class);
//        System.out.println(jobExecutionList);
//        System.out.println(jobExecutionList.get(0));
//        System.out.println(jobExecutionList.get(0).get("from"));
//        System.out.println(jobExecutionList.get(0).get("to"));


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
    /*
    MYSQL -
    {
  "connectionConfig": [
    {
      "connectionName": "mysql_con1",
      "connectionConfiguration": "{\"host\": \"localhost\",\"port\": \"3306\", \"username\": \"root\",\"password\": \"Newuser@123\"}"
    }
  ],
  "connectionType": "MYSQL"
}

MONGO -
{
  "connectionConfig": [
    {
      "connectionName": "mongo_con1",
      "connectionConfiguration": "{\"host\": \"localhost\",\"port\": \"27017\", \"username\": \"root\",\"password\": \"root\"}"
    }
  ],
  "connectionType": "MONGODB"
}
     */
}
