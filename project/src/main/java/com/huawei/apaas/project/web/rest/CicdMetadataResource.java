package com.huawei.apaas.project.web.rest;

import com.huawei.apaas.project.domain.CicdMetadata;
import com.huawei.apaas.project.repository.CicdMetadataRepository;
import com.huawei.apaas.project.service.CicdMetadataService;
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
 * REST controller for managing {@link com.huawei.apaas.project.domain.CicdMetadata}.
 */
@RestController
@RequestMapping("/api")
public class CicdMetadataResource {

    private final Logger log = LoggerFactory.getLogger(CicdMetadataResource.class);

    private static final String ENTITY_NAME = "projectCicdMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CicdMetadataService cicdMetadataService;

    private final CicdMetadataRepository cicdMetadataRepository;

    public CicdMetadataResource(CicdMetadataService cicdMetadataService, CicdMetadataRepository cicdMetadataRepository) {
        this.cicdMetadataService = cicdMetadataService;
        this.cicdMetadataRepository = cicdMetadataRepository;
    }

    /**
     * {@code POST  /cicd-metadata} : Create a new cicdMetadata.
     *
     * @param cicdMetadata the cicdMetadata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cicdMetadata, or with status {@code 400 (Bad Request)} if the cicdMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cicd-metadata")
    public ResponseEntity<CicdMetadata> createCicdMetadata(@Valid @RequestBody CicdMetadata cicdMetadata) throws URISyntaxException {
        log.debug("REST request to save CicdMetadata : {}", cicdMetadata);
        if (cicdMetadata.getId() != null) {
            throw new BadRequestAlertException("A new cicdMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CicdMetadata result = cicdMetadataService.save(cicdMetadata);
        return ResponseEntity
            .created(new URI("/api/cicd-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cicd-metadata/:id} : Updates an existing cicdMetadata.
     *
     * @param id the id of the cicdMetadata to save.
     * @param cicdMetadata the cicdMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cicdMetadata,
     * or with status {@code 400 (Bad Request)} if the cicdMetadata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cicdMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cicd-metadata/{id}")
    public ResponseEntity<CicdMetadata> updateCicdMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CicdMetadata cicdMetadata
    ) throws URISyntaxException {
        log.debug("REST request to update CicdMetadata : {}, {}", id, cicdMetadata);
        if (cicdMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cicdMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cicdMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CicdMetadata result = cicdMetadataService.update(cicdMetadata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cicdMetadata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cicd-metadata/:id} : Partial updates given fields of an existing cicdMetadata, field will ignore if it is null
     *
     * @param id the id of the cicdMetadata to save.
     * @param cicdMetadata the cicdMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cicdMetadata,
     * or with status {@code 400 (Bad Request)} if the cicdMetadata is not valid,
     * or with status {@code 404 (Not Found)} if the cicdMetadata is not found,
     * or with status {@code 500 (Internal Server Error)} if the cicdMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cicd-metadata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CicdMetadata> partialUpdateCicdMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CicdMetadata cicdMetadata
    ) throws URISyntaxException {
        log.debug("REST request to partial update CicdMetadata partially : {}, {}", id, cicdMetadata);
        if (cicdMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cicdMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cicdMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CicdMetadata> result = cicdMetadataService.partialUpdate(cicdMetadata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cicdMetadata.getId().toString())
        );
    }

    /**
     * {@code GET  /cicd-metadata} : get all the cicdMetadata.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cicdMetadata in body.
     */
    @GetMapping("/cicd-metadata")
    public List<CicdMetadata> getAllCicdMetadata() {
        log.debug("REST request to get all CicdMetadata");
        return cicdMetadataService.findAll();
    }

    /**
     * {@code GET  /cicd-metadata/:id} : get the "id" cicdMetadata.
     *
     * @param id the id of the cicdMetadata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cicdMetadata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cicd-metadata/{id}")
    public ResponseEntity<CicdMetadata> getCicdMetadata(@PathVariable Long id) {
        log.debug("REST request to get CicdMetadata : {}", id);
        Optional<CicdMetadata> cicdMetadata = cicdMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cicdMetadata);
    }

    /**
     * {@code DELETE  /cicd-metadata/:id} : delete the "id" cicdMetadata.
     *
     * @param id the id of the cicdMetadata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cicd-metadata/{id}")
    public ResponseEntity<Void> deleteCicdMetadata(@PathVariable Long id) {
        log.debug("REST request to delete CicdMetadata : {}", id);
        cicdMetadataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
