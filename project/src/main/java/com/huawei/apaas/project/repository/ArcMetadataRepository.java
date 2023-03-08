package com.huawei.apaas.project.repository;

import com.huawei.apaas.project.domain.ArcMetadata;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArcMetadata entity.
 */
@Repository
public interface ArcMetadataRepository extends JpaRepository<ArcMetadata, Long> {
    default Optional<ArcMetadata> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ArcMetadata> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ArcMetadata> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct arcMetadata from ArcMetadata arcMetadata left join fetch arcMetadata.arcTemplate",
        countQuery = "select count(distinct arcMetadata) from ArcMetadata arcMetadata"
    )
    Page<ArcMetadata> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct arcMetadata from ArcMetadata arcMetadata left join fetch arcMetadata.arcTemplate")
    List<ArcMetadata> findAllWithToOneRelationships();

    @Query("select arcMetadata from ArcMetadata arcMetadata left join fetch arcMetadata.arcTemplate where arcMetadata.id =:id")
    Optional<ArcMetadata> findOneWithToOneRelationships(@Param("id") Long id);
}
