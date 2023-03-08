package com.huawei.apaas.project.service;

import com.huawei.apaas.project.domain.ProjectMetadata;
import com.huawei.apaas.project.repository.ProjectMetadataRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectMetadata}.
 */
@Service
@Transactional
public class ProjectMetadataService {

    private final Logger log = LoggerFactory.getLogger(ProjectMetadataService.class);

    private final ProjectMetadataRepository projectMetadataRepository;

    public ProjectMetadataService(ProjectMetadataRepository projectMetadataRepository) {
        this.projectMetadataRepository = projectMetadataRepository;
    }

    /**
     * Save a projectMetadata.
     *
     * @param projectMetadata the entity to save.
     * @return the persisted entity.
     */
    public ProjectMetadata save(ProjectMetadata projectMetadata) {
        log.debug("Request to save ProjectMetadata : {}", projectMetadata);
        return projectMetadataRepository.save(projectMetadata);
    }

    /**
     * Update a projectMetadata.
     *
     * @param projectMetadata the entity to save.
     * @return the persisted entity.
     */
    public ProjectMetadata update(ProjectMetadata projectMetadata) {
        log.debug("Request to update ProjectMetadata : {}", projectMetadata);
        return projectMetadataRepository.save(projectMetadata);
    }

    /**
     * Partially update a projectMetadata.
     *
     * @param projectMetadata the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectMetadata> partialUpdate(ProjectMetadata projectMetadata) {
        log.debug("Request to partially update ProjectMetadata : {}", projectMetadata);

        return projectMetadataRepository
            .findById(projectMetadata.getId())
            .map(existingProjectMetadata -> {
                if (projectMetadata.getName() != null) {
                    existingProjectMetadata.setName(projectMetadata.getName());
                }
                if (projectMetadata.getType() != null) {
                    existingProjectMetadata.setType(projectMetadata.getType());
                }
                if (projectMetadata.getLanguage() != null) {
                    existingProjectMetadata.setLanguage(projectMetadata.getLanguage());
                }
                if (projectMetadata.getLangVersion() != null) {
                    existingProjectMetadata.setLangVersion(projectMetadata.getLangVersion());
                }
                if (projectMetadata.getBuildTool() != null) {
                    existingProjectMetadata.setBuildTool(projectMetadata.getBuildTool());
                }
                if (projectMetadata.getBuildToolVersion() != null) {
                    existingProjectMetadata.setBuildToolVersion(projectMetadata.getBuildToolVersion());
                }
                if (projectMetadata.getBanner() != null) {
                    existingProjectMetadata.setBanner(projectMetadata.getBanner());
                }
                if (projectMetadata.getFavicon() != null) {
                    existingProjectMetadata.setFavicon(projectMetadata.getFavicon());
                }
                if (projectMetadata.getVersion() != null) {
                    existingProjectMetadata.setVersion(projectMetadata.getVersion());
                }
                if (projectMetadata.getRentId() != null) {
                    existingProjectMetadata.setRentId(projectMetadata.getRentId());
                }
                if (projectMetadata.getUpdateTime() != null) {
                    existingProjectMetadata.setUpdateTime(projectMetadata.getUpdateTime());
                }
                if (projectMetadata.getCreateTime() != null) {
                    existingProjectMetadata.setCreateTime(projectMetadata.getCreateTime());
                }

                return existingProjectMetadata;
            })
            .map(projectMetadataRepository::save);
    }

    /**
     * Get all the projectMetadata.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectMetadata> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectMetadata");
        return projectMetadataRepository.findAll(pageable);
    }

    /**
     *  Get all the projectMetadata where ArcMetadata is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectMetadata> findAllWhereArcMetadataIsNull() {
        log.debug("Request to get all projectMetadata where ArcMetadata is null");
        return StreamSupport
            .stream(projectMetadataRepository.findAll().spliterator(), false)
            .filter(projectMetadata -> projectMetadata.getArcMetadata() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the projectMetadata where CicdMetadata is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectMetadata> findAllWhereCicdMetadataIsNull() {
        log.debug("Request to get all projectMetadata where CicdMetadata is null");
        return StreamSupport
            .stream(projectMetadataRepository.findAll().spliterator(), false)
            .filter(projectMetadata -> projectMetadata.getCicdMetadata() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the projectMetadata where OpsMetadata is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectMetadata> findAllWhereOpsMetadataIsNull() {
        log.debug("Request to get all projectMetadata where OpsMetadata is null");
        return StreamSupport
            .stream(projectMetadataRepository.findAll().spliterator(), false)
            .filter(projectMetadata -> projectMetadata.getOpsMetadata() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one projectMetadata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectMetadata> findOne(Long id) {
        log.debug("Request to get ProjectMetadata : {}", id);
        return projectMetadataRepository.findById(id);
    }

    /**
     * Delete the projectMetadata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectMetadata : {}", id);
        projectMetadataRepository.deleteById(id);
    }
}
