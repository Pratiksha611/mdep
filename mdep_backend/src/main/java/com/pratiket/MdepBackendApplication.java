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
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MdepBackendApplication.class, args);
    }
}
