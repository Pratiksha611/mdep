package com.pratiket.connection.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pratiket.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@Entity
@Table(name = "CONNECTIONS")
@NoArgsConstructor
@Log4j2
@Setter
@Getter
@ToString
public class Connection
{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "CONNECTION_ID", unique = true, nullable = false, updatable = false)
    @NotBlank(message = "CONNECTION_ID can not be empty.")
    private String connectionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONNECTION_TYPE", nullable = false)
    @NotNull(message = "CONNECTION_TYPE can be either MYSQL, ORACLE OR MONGODB")
    private ConnectionType connectionType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "connection")
    private List<ConnectionConfig> connectionConfig;

    @Column(name = "CREATED_ON", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdOn = new Date();

    @Column(name = "UPDATED_ON", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedOn = new Date();
}
