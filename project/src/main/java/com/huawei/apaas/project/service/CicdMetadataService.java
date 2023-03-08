package com.huawei.apaas.project.service;

import com.huawei.apaas.project.domain.CicdMetadata;
import com.huawei.apaas.project.repository.CicdMetadataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CicdMetadata}.
 */
@Service
@Transactional
public class CicdMetadataService {

    private final Logger log = LoggerFactory.getLogger(CicdMetadataService.class);

    private final CicdMetadataRepository cicdMetadataRepository;

    public CicdMetadataService(CicdMetadataRepository cicdMetadataRepository) {
        this.cicdMetadataRepository = cicdMetadataRepository;
    }

    /**
     * Save a cicdMetadata.
     *
     * @param cicdMetadata the entity to save.
     * @return the persisted entity.
     */
    public CicdMetadata save(CicdMetadata cicdMetadata) {
        log.debug("Request to save CicdMetadata : {}", cicdMetadata);
        return cicdMetadataRepository.save(cicdMetadata);
    }

    /**
     * Update a cicdMetadata.
     *
     * @param cicdMetadata the entity to save.
     * @return the persisted entity.
     */
    public CicdMetadata update(CicdMetadata cicdMetadata) {
        log.debug("Request to update CicdMetadata : {}", cicdMetadata);
        return cicdMetadataRepository.save(cicdMetadata);
    }

    /**
     * Partially update a cicdMetadata.
     *
     * @param cicdMetadata the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CicdMetadata> partialUpdate(CicdMetadata cicdMetadata) {
        log.debug("Request to partially update CicdMetadata : {}", cicdMetadata);

        return cicdMetadataRepository
            .findById(cicdMetadata.getId())
            .map(existingCicdMetadata -> {
                if (cicdMetadata.getRepositoryType() != null) {
                    existingCicdMetadata.setRepositoryType(cicdMetadata.getRepositoryType());
                }
                if (cicdMetadata.getRepositoryName() != null) {
                    existingCicdMetadata.setRepositoryName(cicdMetadata.getRepositoryName());
                }
                if (cicdMetadata.getCiName() != null) {
                    existingCicdMetadata.setCiName(cicdMetadata.getCiName());
                }
                if (cicdMetadata.getCiUrl() != null) {
                    existingCicdMetadata.setCiUrl(cicdMetadata.getCiUrl());
                }
                if (cicdMetadata.getBuildPkg() != null) {
                    existingCicdMetadata.setBuildPkg(cicdMetadata.getBuildPkg());
                }
                if (cicdMetadata.getMirrorRepository() != null) {
                    existingCicdMetadata.setMirrorRepository(cicdMetadata.getMirrorRepository());
                }
                if (cicdMetadata.getCdType() != null) {
                    existingCicdMetadata.setCdType(cicdMetadata.getCdType());
                }
                if (cicdMetadata.getAutoIp() != null) {
                    existingCicdMetadata.setAutoIp(cicdMetadata.getAutoIp());
                }
                if (cicdMetadata.getAutoPort() != null) {
                    existingCicdMetadata.setAutoPort(cicdMetadata.getAutoPort());
                }
                if (cicdMetadata.getAutoUser() != null) {
                    existingCicdMetadata.setAutoUser(cicdMetadata.getAutoUser());
                }
                if (cicdMetadata.getAutoPwd() != null) {
                    existingCicdMetadata.setAutoPwd(cicdMetadata.getAutoPwd());
                }
                if (cicdMetadata.getAutoKey() != null) {
                    existingCicdMetadata.setAutoKey(cicdMetadata.getAutoKey());
                }
                if (cicdMetadata.getDeploy() != null) {
                    existingCicdMetadata.setDeploy(cicdMetadata.getDeploy());
                }
                if (cicdMetadata.getRentId() != null) {
                    existingCicdMetadata.setRentId(cicdMetadata.getRentId());
                }
                if (cicdMetadata.getUpdateTime() != null) {
                    existingCicdMetadata.setUpdateTime(cicdMetadata.getUpdateTime());
                }
                if (cicdMetadata.getCreateTime() != null) {
                    existingCicdMetadata.setCreateTime(cicdMetadata.getCreateTime());
                }

                return existingCicdMetadata;
            })
            .map(cicdMetadataRepository::save);
    }

    /**
     * Get all the cicdMetadata.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CicdMetadata> findAll() {
        log.debug("Request to get all CicdMetadata");
        return cicdMetadataRepository.findAll();
    }

    /**
     * Get one cicdMetadata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CicdMetadata> findOne(Long id) {
        log.debug("Request to get CicdMetadata : {}", id);
        return cicdMetadataRepository.findById(id);
    }

    /**
     * Delete the cicdMetadata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CicdMetadata : {}", id);
        cicdMetadataRepository.deleteById(id);
    }
}
