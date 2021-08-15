package com.pratiket.connector.service;

import com.pratiket.connector.AbstractConnectorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Service
public class MongoDBConnectorService extends AbstractConnectorService
{

    @Override
    public boolean testConnection(String connectionConfigurationInfo) {
        return false;
    }
}
