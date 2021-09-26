package com.pratiket.connection.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pratiket.connection.entity.Connection;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Pratiksha Deshmukh
 * created on 25-08-2021
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConnectionConfigDTO
{
    private String connectionConfigId;
    private String connectionName;
    private String connectionConfiguration;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();

}
