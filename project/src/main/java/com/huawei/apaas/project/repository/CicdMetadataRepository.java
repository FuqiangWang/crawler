package com.huawei.apaas.project.repository;

import com.huawei.apaas.project.domain.CicdMetadata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CicdMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CicdMetadataRepository extends JpaRepository<CicdMetadata, Long> {}
