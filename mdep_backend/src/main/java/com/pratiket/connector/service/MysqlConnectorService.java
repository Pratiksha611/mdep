package com.pratiket.connector.service;

import com.pratiket.connection.model.ConnectionConfiguration;
import com.pratiket.connector.AbstractConnectorService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * @author Pratiksha Deshmukh
 * created on 14-08-2021
 */
@Service
public class MysqlConnectorService extends AbstractConnectorService
{

    private Connection connection;

    private Statement statement;

    private Environment environment;

    public MysqlConnectorService()
    {
        initialize();
    }

    public void test() throws SQLException, ClassNotFoundException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306?enabledTLSProtocols=TLSv1.2","root","Newuser@123");
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("select count(*) from exam.EMP");
        while(rs.next())
            System.out.println(rs.getInt(1));*/
        //con.close();


        fetchConnectionConfiguration("{\"host\":\"localhost\",\"port\":\"3306\",\"database\":\"exam\",\"schema\":\"\"}");
        System.out.println(connectionConfiguration);
    }

    public static void main(String[] args) throws Exception {
        new MysqlConnectorService().test();
    }

    @Override
    public boolean testConnection(String connectionConfigurationInfo)
    {
        fetchConnectionConfiguration(connectionConfigurationInfo);
        //Class.forName("com.mysql.cj.jdbc.Driver");
        return false;
    }
}
