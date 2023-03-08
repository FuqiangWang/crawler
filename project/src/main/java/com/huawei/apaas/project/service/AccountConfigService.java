package com.huawei.apaas.project.service;

import com.huawei.apaas.project.domain.AccountConfig;
import com.huawei.apaas.project.repository.AccountConfigRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountConfig}.
 */
@Service
@Transactional
public class AccountConfigService {

    private final Logger log = LoggerFactory.getLogger(AccountConfigService.class);

    private final AccountConfigRepository accountConfigRepository;

    public AccountConfigService(AccountConfigRepository accountConfigRepository) {
        this.accountConfigRepository = accountConfigRepository;
    }

    /**
     * Save a accountConfig.
     *
     * @param accountConfig the entity to save.
     * @return the persisted entity.
     */
    public AccountConfig save(AccountConfig accountConfig) {
        log.debug("Request to save AccountConfig : {}", accountConfig);
        return accountConfigRepository.save(accountConfig);
    }

    /**
     * Update a accountConfig.
     *
     * @param accountConfig the entity to save.
     * @return the persisted entity.
     */
    public AccountConfig update(AccountConfig accountConfig) {
        log.debug("Request to update AccountConfig : {}", accountConfig);
        return accountConfigRepository.save(accountConfig);
    }

    /**
     * Partially update a accountConfig.
     *
     * @param accountConfig the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccountConfig> partialUpdate(AccountConfig accountConfig) {
        log.debug("Request to partially update AccountConfig : {}", accountConfig);

        return accountConfigRepository
            .findById(accountConfig.getId())
            .map(existingAccountConfig -> {
                if (accountConfig.getGitHubUser() != null) {
                    existingAccountConfig.setGitHubUser(accountConfig.getGitHubUser());
                }
                if (accountConfig.getGitHubPwd() != null) {
                    existingAccountConfig.setGitHubPwd(accountConfig.getGitHubPwd());
                }
                if (accountConfig.getGitLabUser() != null) {
                    existingAccountConfig.setGitLabUser(accountConfig.getGitLabUser());
                }
                if (accountConfig.getGitLabPwd() != null) {
                    existingAccountConfig.setGitLabPwd(accountConfig.getGitLabPwd());
                }
                if (accountConfig.getGiteeUser() != null) {
                    existingAccountConfig.setGiteeUser(accountConfig.getGiteeUser());
                }
                if (accountConfig.getGiteePwd() != null) {
                    existingAccountConfig.setGiteePwd(accountConfig.getGiteePwd());
                }
                if (accountConfig.getDockerHubUser() != null) {
                    existingAccountConfig.setDockerHubUser(accountConfig.getDockerHubUser());
                }
                if (accountConfig.getDockHubPwd() != null) {
                    existingAccountConfig.setDockHubPwd(accountConfig.getDockHubPwd());
                }
                if (accountConfig.getRendId() != null) {
                    existingAccountConfig.setRendId(accountConfig.getRendId());
                }

                return existingAccountConfig;
            })
            .map(accountConfigRepository::save);
    }

    /**
     * Get all the accountConfigs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AccountConfig> findAll() {
        log.debug("Request to get all AccountConfigs");
        return accountConfigRepository.findAll();
    }

    /**
     * Get one accountConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountConfig> findOne(Long id) {
        log.debug("Request to get AccountConfig : {}", id);
        return accountConfigRepository.findById(id);
    }

    /**
     * Delete the accountConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountConfig : {}", id);
        accountConfigRepository.deleteById(id);
    }
}
