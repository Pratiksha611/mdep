package com.pratiket.connection.repository;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@Transactional
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, String>, JpaSpecificationExecutor<Connection>
{
    Optional<Connection> findByConnectionType(ConnectionType connectionType);
}
