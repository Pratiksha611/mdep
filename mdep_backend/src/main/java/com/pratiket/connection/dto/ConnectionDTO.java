package com.pratiket.connection.dto;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionType;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConnectionDTO
{
    private String id;
    private String connectionName;
    private ConnectionType connectionType;
    private String connectionConfiguration;
    private Date createdDate;
    private Date updatedDate;

    public ConnectionDTO(Connection connection)
    {
        BeanUtils.copyProperties(connection, this);
    }
}
