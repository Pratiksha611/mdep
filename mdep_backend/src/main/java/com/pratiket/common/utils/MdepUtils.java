package com.pratiket.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratiket.connection.dto.ConnectionDTO;
import com.pratiket.exception.ConnectionException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Pratiksha Deshmukh
 * created on 24-09-2021
 */
public class MdepUtils
{
    public static long generateRandomNumber()
    {
        return ThreadLocalRandom.current().nextLong(1, 10000000);
    }

    public static <T> T getClass(String content, Class<T> classType)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, classType);
        } catch (IOException e) {
            throw ConnectionException.builder()
                    .message(e.getMessage())
                    .detailMessage(ExceptionUtils.getStackTrace(e)).build();
        }
    }

    public static void main(String[] args) {
        String str = "{       \"connectionId\": \"ff8081817baa2ed6017baa2f18140000\",       \"connectionType\": \"MONGODB\",       \"connectionConfig\": [         {           \"connectionConfigId\": \"ff8081817baa2ed6017baa2f18400001\",           \"connectionName\": \"mongo_con1\",           \"connectionConfiguration\": \"{\\\"host\\\": \\\"localhost\\\",\\\"port\\\": \\\"27017\\\", \\\"username\\\": \\\"root\\\",\\\"password\\\": \\\"root\\\", \\\"database\\\":\\\"video\\\", \\\"collection\\\": \\\"test\\\"}\",           \"createdOn\": 1630647818000,           \"updatedOn\": 1630647818000         }       ],       \"createdOn\": 1630647818000,       \"updatedOn\": 1630647818000     }";
        ConnectionDTO connectionDTO = getClass(str, ConnectionDTO.class);
        System.out.println(connectionDTO);
    }
}
