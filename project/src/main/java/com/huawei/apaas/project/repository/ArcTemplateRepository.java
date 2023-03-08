package com.huawei.apaas.project.repository;

import com.huawei.apaas.project.domain.ArcTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArcTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArcTemplateRepository extends JpaRepository<ArcTemplate, Long> {}
