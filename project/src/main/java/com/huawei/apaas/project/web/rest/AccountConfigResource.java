package com.huawei.apaas.project.web.rest;

import com.huawei.apaas.project.domain.AccountConfig;
import com.huawei.apaas.project.repository.AccountConfigRepository;
import com.huawei.apaas.project.service.AccountConfigService;
import com.huawei.apaas.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.huawei.apaas.project.domain.AccountConfig}.
 */
@RestController
@RequestMapping("/api")
public class AccountConfigResource {

    private final Logger log = LoggerFactory.getLogger(AccountConfigResource.class);

    private static final String ENTITY_NAME = "projectAccountConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountConfigService accountConfigService;

    private final AccountConfigRepository accountConfigRepository;

    public AccountConfigResource(AccountConfigService accountConfigService, AccountConfigRepository accountConfigRepository) {
        this.accountConfigService = accountConfigService;
        this.accountConfigRepository = accountConfigRepository;
    }

    /**
     * {@code POST  /account-configs} : Create a new accountConfig.
     *
     * @param accountConfig the accountConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountConfig, or with status {@code 400 (Bad Request)} if the accountConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-configs")
    public ResponseEntity<AccountConfig> createAccountConfig(@Valid @RequestBody AccountConfig accountConfig) throws URISyntaxException {
        log.debug("REST request to save AccountConfig : {}", accountConfig);
        if (accountConfig.getId() != null) {
            throw new BadRequestAlertException("A new accountConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountConfig result = accountConfigService.save(accountConfig);
        return ResponseEntity
            .created(new URI("/api/account-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-configs/:id} : Updates an existing accountConfig.
     *
     * @param id the id of the accountConfig to save.
     * @param accountConfig the accountConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountConfig,
     * or with status {@code 400 (Bad Request)} if the accountConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-configs/{id}")
    public ResponseEntity<AccountConfig> updateAccountConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccountConfig accountConfig
    ) throws URISyntaxException {
        log.debug("REST request to update AccountConfig : {}, {}", id, accountConfig);
        if (accountConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountConfig result = accountConfigService.update(accountConfig);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-configs/:id} : Partial updates given fields of an existing accountConfig, field will ignore if it is null
     *
     * @param id the id of the accountConfig to save.
     * @param accountConfig the accountConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountConfig,
     * or with status {@code 400 (Bad Request)} if the accountConfig is not valid,
     * or with status {@code 404 (Not Found)} if the accountConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountConfig> partialUpdateAccountConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccountConfig accountConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountConfig partially : {}, {}", id, accountConfig);
        if (accountConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountConfig> result = accountConfigService.partialUpdate(accountConfig);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /account-configs} : get all the accountConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountConfigs in body.
     */
    @GetMapping("/account-configs")
    public List<AccountConfig> getAllAccountConfigs() {
        log.debug("REST request to get all AccountConfigs");
        return accountConfigService.findAll();
    }

    /**
     * {@code GET  /account-configs/:id} : get the "id" accountConfig.
     *
     * @param id the id of the accountConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-configs/{id}")
    public ResponseEntity<AccountConfig> getAccountConfig(@PathVariable Long id) {
        log.debug("REST request to get AccountConfig : {}", id);
        Optional<AccountConfig> accountConfig = accountConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountConfig);
    }

    /**
     * {@code DELETE  /account-configs/:id} : delete the "id" accountConfig.
     *
     * @param id the id of the accountConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-configs/{id}")
    public ResponseEntity<Void> deleteAccountConfig(@PathVariable Long id) {
        log.debug("REST request to delete AccountConfig : {}", id);
        accountConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
