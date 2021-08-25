package com.pratiket.connection.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pratiket.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author Prtiksha Deshmukh
 * created on 25-08-2021
 */
@Entity
@Table(name = "CONNECTIONS_CONFIG")
@NoArgsConstructor
@Log4j2
@Setter
@Getter
@ToString(exclude = {"connection"})
public class ConnectionConfig
{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "CONNECTION_CONFIG_ID", unique = true, nullable = false, updatable = false)
    @NotBlank(message = "CONNECTION_CONFIG_ID can not be empty.")
    private String connectionConfigId;

    @Column(name = "CONNECTION_NAME", nullable = false)
    private String connectionName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "CONNECTION_CONFIGURATION", nullable = false)
    private String connectionConfiguration;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONNECTION_ID", nullable = false)
    private Connection connection;

    @Column(name = "CREATED_ON", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdOn = new Date();

    @Column(name = "UPDATED_ON", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedOn = new Date();
}
