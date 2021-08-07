package com.pratiket.connection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pratiket.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.htrace.fasterxml.jackson.annotation.JsonIgnore;
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
public class Connection extends BaseEntity
{

    @Column(name = "CONNECTION_NAME", nullable = false)
    private String connectionName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "CONNECTION_CONFIGURATION", nullable = false)
    private String connectionConfiguration;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONNECTION_TYPE", nullable = false)
    @NotNull(message = "CONNECTION_TYPE can be either MYSQL, ORACLE OR MONGODB")
    private ConnectionType connectionType;

}
