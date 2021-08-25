package com.pratiket.connection.dto;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionType;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

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
    private String connectionId;
    private ConnectionType connectionType;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
    private List<ConnectionConfigDTO> connectionConfig;

    public ConnectionDTO(Connection connection)
    {
        BeanUtils.copyProperties(connection, this);
    }
}
