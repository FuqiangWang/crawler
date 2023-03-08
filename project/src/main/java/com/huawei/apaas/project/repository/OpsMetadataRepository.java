package com.huawei.apaas.project.repository;

import com.huawei.apaas.project.domain.OpsMetadata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OpsMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpsMetadataRepository extends JpaRepository<OpsMetadata, Long> {}
