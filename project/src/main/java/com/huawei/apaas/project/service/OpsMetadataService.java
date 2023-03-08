package com.huawei.apaas.project.service;

import com.huawei.apaas.project.domain.OpsMetadata;
import com.huawei.apaas.project.repository.OpsMetadataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OpsMetadata}.
 */
@Service
@Transactional
public class OpsMetadataService {

    private final Logger log = LoggerFactory.getLogger(OpsMetadataService.class);

    private final OpsMetadataRepository opsMetadataRepository;

    public OpsMetadataService(OpsMetadataRepository opsMetadataRepository) {
        this.opsMetadataRepository = opsMetadataRepository;
    }

    /**
     * Save a opsMetadata.
     *
     * @param opsMetadata the entity to save.
     * @return the persisted entity.
     */
    public OpsMetadata save(OpsMetadata opsMetadata) {
        log.debug("Request to save OpsMetadata : {}", opsMetadata);
        return opsMetadataRepository.save(opsMetadata);
    }

    /**
     * Update a opsMetadata.
     *
     * @param opsMetadata the entity to save.
     * @return the persisted entity.
     */
    public OpsMetadata update(OpsMetadata opsMetadata) {
        log.debug("Request to update OpsMetadata : {}", opsMetadata);
        return opsMetadataRepository.save(opsMetadata);
    }

    /**
     * Partially update a opsMetadata.
     *
     * @param opsMetadata the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpsMetadata> partialUpdate(OpsMetadata opsMetadata) {
        log.debug("Request to partially update OpsMetadata : {}", opsMetadata);

        return opsMetadataRepository
            .findById(opsMetadata.getId())
            .map(existingOpsMetadata -> {
                if (opsMetadata.getOpsSystem() != null) {
                    existingOpsMetadata.setOpsSystem(opsMetadata.getOpsSystem());
                }
                if (opsMetadata.getRentId() != null) {
                    existingOpsMetadata.setRentId(opsMetadata.getRentId());
                }
                if (opsMetadata.getCreateTime() != null) {
                    existingOpsMetadata.setCreateTime(opsMetadata.getCreateTime());
                }
                if (opsMetadata.getUpdateTime() != null) {
                    existingOpsMetadata.setUpdateTime(opsMetadata.getUpdateTime());
                }

                return existingOpsMetadata;
            })
            .map(opsMetadataRepository::save);
    }

    /**
     * Get all the opsMetadata.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OpsMetadata> findAll() {
        log.debug("Request to get all OpsMetadata");
        return opsMetadataRepository.findAll();
    }

    /**
     * Get one opsMetadata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpsMetadata> findOne(Long id) {
        log.debug("Request to get OpsMetadata : {}", id);
        return opsMetadataRepository.findById(id);
    }

    /**
     * Delete the opsMetadata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpsMetadata : {}", id);
        opsMetadataRepository.deleteById(id);
    }
}
