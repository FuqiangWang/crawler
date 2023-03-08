package com.huawei.apaas.project.repository;

import com.huawei.apaas.project.domain.ProjectMetadata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectMetadataRepository extends JpaRepository<ProjectMetadata, Long> {}
