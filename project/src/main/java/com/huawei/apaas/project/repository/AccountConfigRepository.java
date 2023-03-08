package com.huawei.apaas.project.repository;

import com.huawei.apaas.project.domain.AccountConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountConfigRepository extends JpaRepository<AccountConfig, Long> {}
