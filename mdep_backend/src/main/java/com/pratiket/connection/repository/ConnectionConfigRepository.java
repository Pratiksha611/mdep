package com.pratiket.connection.repository;

import com.pratiket.connection.entity.Connection;
import com.pratiket.connection.entity.ConnectionConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pratiksha Deshmukh
 * created on 30-08-2021
 */
@Transactional
@Repository
public interface ConnectionConfigRepository extends JpaRepository<ConnectionConfig, String>, JpaSpecificationExecutor<ConnectionConfig>
{

}
