package com.huawei.apaas.project.web.rest;

import com.huawei.apaas.project.domain.OpsMetadata;
import com.huawei.apaas.project.repository.OpsMetadataRepository;
import com.huawei.apaas.project.service.OpsMetadataService;
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
 * REST controller for managing {@link com.huawei.apaas.project.domain.OpsMetadata}.
 */
@RestController
@RequestMapping("/api")
public class OpsMetadataResource {

    private final Logger log = LoggerFactory.getLogger(OpsMetadataResource.class);

    private static final String ENTITY_NAME = "projectOpsMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpsMetadataService opsMetadataService;

    private final OpsMetadataRepository opsMetadataRepository;

    public OpsMetadataResource(OpsMetadataService opsMetadataService, OpsMetadataRepository opsMetadataRepository) {
        this.opsMetadataService = opsMetadataService;
        this.opsMetadataRepository = opsMetadataRepository;
    }

    /**
     * {@code POST  /ops-metadata} : Create a new opsMetadata.
     *
     * @param opsMetadata the opsMetadata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opsMetadata, or with status {@code 400 (Bad Request)} if the opsMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ops-metadata")
    public ResponseEntity<OpsMetadata> createOpsMetadata(@Valid @RequestBody OpsMetadata opsMetadata) throws URISyntaxException {
        log.debug("REST request to save OpsMetadata : {}", opsMetadata);
        if (opsMetadata.getId() != null) {
            throw new BadRequestAlertException("A new opsMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpsMetadata result = opsMetadataService.save(opsMetadata);
        return ResponseEntity
            .created(new URI("/api/ops-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ops-metadata/:id} : Updates an existing opsMetadata.
     *
     * @param id the id of the opsMetadata to save.
     * @param opsMetadata the opsMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opsMetadata,
     * or with status {@code 400 (Bad Request)} if the opsMetadata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opsMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ops-metadata/{id}")
    public ResponseEntity<OpsMetadata> updateOpsMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OpsMetadata opsMetadata
    ) throws URISyntaxException {
        log.debug("REST request to update OpsMetadata : {}, {}", id, opsMetadata);
        if (opsMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opsMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opsMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpsMetadata result = opsMetadataService.update(opsMetadata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opsMetadata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ops-metadata/:id} : Partial updates given fields of an existing opsMetadata, field will ignore if it is null
     *
     * @param id the id of the opsMetadata to save.
     * @param opsMetadata the opsMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opsMetadata,
     * or with status {@code 400 (Bad Request)} if the opsMetadata is not valid,
     * or with status {@code 404 (Not Found)} if the opsMetadata is not found,
     * or with status {@code 500 (Internal Server Error)} if the opsMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ops-metadata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpsMetadata> partialUpdateOpsMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OpsMetadata opsMetadata
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpsMetadata partially : {}, {}", id, opsMetadata);
        if (opsMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opsMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opsMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpsMetadata> result = opsMetadataService.partialUpdate(opsMetadata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opsMetadata.getId().toString())
        );
    }

    /**
     * {@code GET  /ops-metadata} : get all the opsMetadata.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opsMetadata in body.
     */
    @GetMapping("/ops-metadata")
    public List<OpsMetadata> getAllOpsMetadata() {
        log.debug("REST request to get all OpsMetadata");
        return opsMetadataService.findAll();
    }

    /**
     * {@code GET  /ops-metadata/:id} : get the "id" opsMetadata.
     *
     * @param id the id of the opsMetadata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opsMetadata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ops-metadata/{id}")
    public ResponseEntity<OpsMetadata> getOpsMetadata(@PathVariable Long id) {
        log.debug("REST request to get OpsMetadata : {}", id);
        Optional<OpsMetadata> opsMetadata = opsMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opsMetadata);
    }

    /**
     * {@code DELETE  /ops-metadata/:id} : delete the "id" opsMetadata.
     *
     * @param id the id of the opsMetadata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ops-metadata/{id}")
    public ResponseEntity<Void> deleteOpsMetadata(@PathVariable Long id) {
        log.debug("REST request to delete OpsMetadata : {}", id);
        opsMetadataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
