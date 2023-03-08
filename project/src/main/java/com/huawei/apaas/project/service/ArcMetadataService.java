package com.huawei.apaas.project.service;

import com.huawei.apaas.project.domain.ArcMetadata;
import com.huawei.apaas.project.repository.ArcMetadataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArcMetadata}.
 */
@Service
@Transactional
public class ArcMetadataService {

    private final Logger log = LoggerFactory.getLogger(ArcMetadataService.class);

    private final ArcMetadataRepository arcMetadataRepository;

    public ArcMetadataService(ArcMetadataRepository arcMetadataRepository) {
        this.arcMetadataRepository = arcMetadataRepository;
    }

    /**
     * Save a arcMetadata.
     *
     * @param arcMetadata the entity to save.
     * @return the persisted entity.
     */
    public ArcMetadata save(ArcMetadata arcMetadata) {
        log.debug("Request to save ArcMetadata : {}", arcMetadata);
        return arcMetadataRepository.save(arcMetadata);
    }

    /**
     * Update a arcMetadata.
     *
     * @param arcMetadata the entity to save.
     * @return the persisted entity.
     */
    public ArcMetadata update(ArcMetadata arcMetadata) {
        log.debug("Request to update ArcMetadata : {}", arcMetadata);
        return arcMetadataRepository.save(arcMetadata);
    }

    /**
     * Partially update a arcMetadata.
     *
     * @param arcMetadata the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArcMetadata> partialUpdate(ArcMetadata arcMetadata) {
        log.debug("Request to partially update ArcMetadata : {}", arcMetadata);

        return arcMetadataRepository
            .findById(arcMetadata.getId())
            .map(existingArcMetadata -> {
                if (arcMetadata.getType() != null) {
                    existingArcMetadata.setType(arcMetadata.getType());
                }
                if (arcMetadata.getPackageName() != null) {
                    existingArcMetadata.setPackageName(arcMetadata.getPackageName());
                }
                if (arcMetadata.getServiceDiscoveryType() != null) {
                    existingArcMetadata.setServiceDiscoveryType(arcMetadata.getServiceDiscoveryType());
                }
                if (arcMetadata.getDevDatabaseType() != null) {
                    existingArcMetadata.setDevDatabaseType(arcMetadata.getDevDatabaseType());
                }
                if (arcMetadata.getProdDatabaseType() != null) {
                    existingArcMetadata.setProdDatabaseType(arcMetadata.getProdDatabaseType());
                }
                if (arcMetadata.getCacheProvider() != null) {
                    existingArcMetadata.setCacheProvider(arcMetadata.getCacheProvider());
                }
                if (arcMetadata.getMessageBroker() != null) {
                    existingArcMetadata.setMessageBroker(arcMetadata.getMessageBroker());
                }
                if (arcMetadata.getServerPort() != null) {
                    existingArcMetadata.setServerPort(arcMetadata.getServerPort());
                }
                if (arcMetadata.getFrontMessage() != null) {
                    existingArcMetadata.setFrontMessage(arcMetadata.getFrontMessage());
                }
                if (arcMetadata.getClientFramework() != null) {
                    existingArcMetadata.setClientFramework(arcMetadata.getClientFramework());
                }
                if (arcMetadata.getLanguages() != null) {
                    existingArcMetadata.setLanguages(arcMetadata.getLanguages());
                }
                if (arcMetadata.getTestFramework() != null) {
                    existingArcMetadata.setTestFramework(arcMetadata.getTestFramework());
                }
                if (arcMetadata.getRentId() != null) {
                    existingArcMetadata.setRentId(arcMetadata.getRentId());
                }
                if (arcMetadata.getUpdateTime() != null) {
                    existingArcMetadata.setUpdateTime(arcMetadata.getUpdateTime());
                }
                if (arcMetadata.getCreateTime() != null) {
                    existingArcMetadata.setCreateTime(arcMetadata.getCreateTime());
                }

                return existingArcMetadata;
            })
            .map(arcMetadataRepository::save);
    }

    /**
     * Get all the arcMetadata.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ArcMetadata> findAll() {
        log.debug("Request to get all ArcMetadata");
        return arcMetadataRepository.findAll();
    }

    /**
     * Get all the arcMetadata with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ArcMetadata> findAllWithEagerRelationships(Pageable pageable) {
        return arcMetadataRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one arcMetadata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArcMetadata> findOne(Long id) {
        log.debug("Request to get ArcMetadata : {}", id);
        return arcMetadataRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the arcMetadata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArcMetadata : {}", id);
        arcMetadataRepository.deleteById(id);
    }
}
