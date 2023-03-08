package com.huawei.apaas.project.web.rest;

import com.huawei.apaas.project.domain.ArcMetadata;
import com.huawei.apaas.project.repository.ArcMetadataRepository;
import com.huawei.apaas.project.service.ArcMetadataService;
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
 * REST controller for managing {@link com.huawei.apaas.project.domain.ArcMetadata}.
 */
@RestController
@RequestMapping("/api")
public class ArcMetadataResource {

    private final Logger log = LoggerFactory.getLogger(ArcMetadataResource.class);

    private static final String ENTITY_NAME = "projectArcMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArcMetadataService arcMetadataService;

    private final ArcMetadataRepository arcMetadataRepository;

    public ArcMetadataResource(ArcMetadataService arcMetadataService, ArcMetadataRepository arcMetadataRepository) {
        this.arcMetadataService = arcMetadataService;
        this.arcMetadataRepository = arcMetadataRepository;
    }

    /**
     * {@code POST  /arc-metadata} : Create a new arcMetadata.
     *
     * @param arcMetadata the arcMetadata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arcMetadata, or with status {@code 400 (Bad Request)} if the arcMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arc-metadata")
    public ResponseEntity<ArcMetadata> createArcMetadata(@Valid @RequestBody ArcMetadata arcMetadata) throws URISyntaxException {
        log.debug("REST request to save ArcMetadata : {}", arcMetadata);
        if (arcMetadata.getId() != null) {
            throw new BadRequestAlertException("A new arcMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArcMetadata result = arcMetadataService.save(arcMetadata);
        return ResponseEntity
            .created(new URI("/api/arc-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arc-metadata/:id} : Updates an existing arcMetadata.
     *
     * @param id the id of the arcMetadata to save.
     * @param arcMetadata the arcMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arcMetadata,
     * or with status {@code 400 (Bad Request)} if the arcMetadata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arcMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arc-metadata/{id}")
    public ResponseEntity<ArcMetadata> updateArcMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArcMetadata arcMetadata
    ) throws URISyntaxException {
        log.debug("REST request to update ArcMetadata : {}, {}", id, arcMetadata);
        if (arcMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arcMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arcMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArcMetadata result = arcMetadataService.update(arcMetadata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arcMetadata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arc-metadata/:id} : Partial updates given fields of an existing arcMetadata, field will ignore if it is null
     *
     * @param id the id of the arcMetadata to save.
     * @param arcMetadata the arcMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arcMetadata,
     * or with status {@code 400 (Bad Request)} if the arcMetadata is not valid,
     * or with status {@code 404 (Not Found)} if the arcMetadata is not found,
     * or with status {@code 500 (Internal Server Error)} if the arcMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arc-metadata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArcMetadata> partialUpdateArcMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArcMetadata arcMetadata
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArcMetadata partially : {}, {}", id, arcMetadata);
        if (arcMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arcMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arcMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArcMetadata> result = arcMetadataService.partialUpdate(arcMetadata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arcMetadata.getId().toString())
        );
    }

    /**
     * {@code GET  /arc-metadata} : get all the arcMetadata.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arcMetadata in body.
     */
    @GetMapping("/arc-metadata")
    public List<ArcMetadata> getAllArcMetadata(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ArcMetadata");
        return arcMetadataService.findAll();
    }

    /**
     * {@code GET  /arc-metadata/:id} : get the "id" arcMetadata.
     *
     * @param id the id of the arcMetadata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arcMetadata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arc-metadata/{id}")
    public ResponseEntity<ArcMetadata> getArcMetadata(@PathVariable Long id) {
        log.debug("REST request to get ArcMetadata : {}", id);
        Optional<ArcMetadata> arcMetadata = arcMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arcMetadata);
    }

    /**
     * {@code DELETE  /arc-metadata/:id} : delete the "id" arcMetadata.
     *
     * @param id the id of the arcMetadata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arc-metadata/{id}")
    public ResponseEntity<Void> deleteArcMetadata(@PathVariable Long id) {
        log.debug("REST request to delete ArcMetadata : {}", id);
        arcMetadataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
