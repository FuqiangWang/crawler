package com.huawei.apaas.project.service;

import com.huawei.apaas.project.domain.ArcTemplate;
import com.huawei.apaas.project.repository.ArcTemplateRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArcTemplate}.
 */
@Service
@Transactional
public class ArcTemplateService {

    private final Logger log = LoggerFactory.getLogger(ArcTemplateService.class);

    private final ArcTemplateRepository arcTemplateRepository;

    public ArcTemplateService(ArcTemplateRepository arcTemplateRepository) {
        this.arcTemplateRepository = arcTemplateRepository;
    }

    /**
     * Save a arcTemplate.
     *
     * @param arcTemplate the entity to save.
     * @return the persisted entity.
     */
    public ArcTemplate save(ArcTemplate arcTemplate) {
        log.debug("Request to save ArcTemplate : {}", arcTemplate);
        return arcTemplateRepository.save(arcTemplate);
    }

    /**
     * Update a arcTemplate.
     *
     * @param arcTemplate the entity to save.
     * @return the persisted entity.
     */
    public ArcTemplate update(ArcTemplate arcTemplate) {
        log.debug("Request to update ArcTemplate : {}", arcTemplate);
        return arcTemplateRepository.save(arcTemplate);
    }

    /**
     * Partially update a arcTemplate.
     *
     * @param arcTemplate the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArcTemplate> partialUpdate(ArcTemplate arcTemplate) {
        log.debug("Request to partially update ArcTemplate : {}", arcTemplate);

        return arcTemplateRepository
            .findById(arcTemplate.getId())
            .map(existingArcTemplate -> {
                if (arcTemplate.getName() != null) {
                    existingArcTemplate.setName(arcTemplate.getName());
                }
                if (arcTemplate.getPackageName() != null) {
                    existingArcTemplate.setPackageName(arcTemplate.getPackageName());
                }
                if (arcTemplate.getServiceDiscoveryType() != null) {
                    existingArcTemplate.setServiceDiscoveryType(arcTemplate.getServiceDiscoveryType());
                }
                if (arcTemplate.getDevDatabaseType() != null) {
                    existingArcTemplate.setDevDatabaseType(arcTemplate.getDevDatabaseType());
                }
                if (arcTemplate.getProdDatabaseType() != null) {
                    existingArcTemplate.setProdDatabaseType(arcTemplate.getProdDatabaseType());
                }
                if (arcTemplate.getCacheProvider() != null) {
                    existingArcTemplate.setCacheProvider(arcTemplate.getCacheProvider());
                }
                if (arcTemplate.getMessageBroker() != null) {
                    existingArcTemplate.setMessageBroker(arcTemplate.getMessageBroker());
                }
                if (arcTemplate.getServerPort() != null) {
                    existingArcTemplate.setServerPort(arcTemplate.getServerPort());
                }
                if (arcTemplate.getFrontMessage() != null) {
                    existingArcTemplate.setFrontMessage(arcTemplate.getFrontMessage());
                }
                if (arcTemplate.getClientFramework() != null) {
                    existingArcTemplate.setClientFramework(arcTemplate.getClientFramework());
                }
                if (arcTemplate.getLanguages() != null) {
                    existingArcTemplate.setLanguages(arcTemplate.getLanguages());
                }
                if (arcTemplate.getTestFramework() != null) {
                    existingArcTemplate.setTestFramework(arcTemplate.getTestFramework());
                }
                if (arcTemplate.getRentId() != null) {
                    existingArcTemplate.setRentId(arcTemplate.getRentId());
                }
                if (arcTemplate.getUpdateTime() != null) {
                    existingArcTemplate.setUpdateTime(arcTemplate.getUpdateTime());
                }
                if (arcTemplate.getCreateTime() != null) {
                    existingArcTemplate.setCreateTime(arcTemplate.getCreateTime());
                }

                return existingArcTemplate;
            })
            .map(arcTemplateRepository::save);
    }

    /**
     * Get all the arcTemplates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ArcTemplate> findAll() {
        log.debug("Request to get all ArcTemplates");
        return arcTemplateRepository.findAll();
    }

    /**
     * Get one arcTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArcTemplate> findOne(Long id) {
        log.debug("Request to get ArcTemplate : {}", id);
        return arcTemplateRepository.findById(id);
    }

    /**
     * Delete the arcTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArcTemplate : {}", id);
        arcTemplateRepository.deleteById(id);
    }
}
