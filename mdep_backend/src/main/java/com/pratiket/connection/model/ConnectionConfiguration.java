package com.pratiket.connection.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Getter
@Setter
@ToString
public class ConnectionConfiguration
{
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;
    private String schema;
    private String collection;
    private String table;

}
