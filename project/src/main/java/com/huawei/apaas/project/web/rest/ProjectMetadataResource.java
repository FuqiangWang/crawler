package com.huawei.apaas.project.web.rest;

import com.huawei.apaas.project.domain.ProjectMetadata;
import com.huawei.apaas.project.repository.ProjectMetadataRepository;
import com.huawei.apaas.project.service.ProjectMetadataService;
import com.huawei.apaas.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.huawei.apaas.project.domain.ProjectMetadata}.
 */
@RestController
@RequestMapping("/api")
public class ProjectMetadataResource {

    private final Logger log = LoggerFactory.getLogger(ProjectMetadataResource.class);

    private static final String ENTITY_NAME = "projectProjectMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectMetadataService projectMetadataService;

    private final ProjectMetadataRepository projectMetadataRepository;

    public ProjectMetadataResource(ProjectMetadataService projectMetadataService, ProjectMetadataRepository projectMetadataRepository) {
        this.projectMetadataService = projectMetadataService;
        this.projectMetadataRepository = projectMetadataRepository;
    }

    /**
     * {@code POST  /project-metadata} : Create a new projectMetadata.
     *
     * @param projectMetadata the projectMetadata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectMetadata, or with status {@code 400 (Bad Request)} if the projectMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-metadata")
    public ResponseEntity<ProjectMetadata> createProjectMetadata(@Valid @RequestBody ProjectMetadata projectMetadata)
        throws URISyntaxException {
        log.debug("REST request to save ProjectMetadata : {}", projectMetadata);
        if (projectMetadata.getId() != null) {
            throw new BadRequestAlertException("A new projectMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectMetadata result = projectMetadataService.save(projectMetadata);
        return ResponseEntity
            .created(new URI("/api/project-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-metadata/:id} : Updates an existing projectMetadata.
     *
     * @param id the id of the projectMetadata to save.
     * @param projectMetadata the projectMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectMetadata,
     * or with status {@code 400 (Bad Request)} if the projectMetadata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-metadata/{id}")
    public ResponseEntity<ProjectMetadata> updateProjectMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectMetadata projectMetadata
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectMetadata : {}, {}", id, projectMetadata);
        if (projectMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectMetadata result = projectMetadataService.update(projectMetadata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectMetadata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-metadata/:id} : Partial updates given fields of an existing projectMetadata, field will ignore if it is null
     *
     * @param id the id of the projectMetadata to save.
     * @param projectMetadata the projectMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectMetadata,
     * or with status {@code 400 (Bad Request)} if the projectMetadata is not valid,
     * or with status {@code 404 (Not Found)} if the projectMetadata is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-metadata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectMetadata> partialUpdateProjectMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectMetadata projectMetadata
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectMetadata partially : {}, {}", id, projectMetadata);
        if (projectMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectMetadata> result = projectMetadataService.partialUpdate(projectMetadata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectMetadata.getId().toString())
        );
    }

    /**
     * {@code GET  /project-metadata} : get all the projectMetadata.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectMetadata in body.
     */
    @GetMapping("/project-metadata")
    public ResponseEntity<List<ProjectMetadata>> getAllProjectMetadata(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("arcmetadata-is-null".equals(filter)) {
            log.debug("REST request to get all ProjectMetadatas where arcMetadata is null");
            return new ResponseEntity<>(projectMetadataService.findAllWhereArcMetadataIsNull(), HttpStatus.OK);
        }

        if ("cicdmetadata-is-null".equals(filter)) {
            log.debug("REST request to get all ProjectMetadatas where cicdMetadata is null");
            return new ResponseEntity<>(projectMetadataService.findAllWhereCicdMetadataIsNull(), HttpStatus.OK);
        }

        if ("opsmetadata-is-null".equals(filter)) {
            log.debug("REST request to get all ProjectMetadatas where opsMetadata is null");
            return new ResponseEntity<>(projectMetadataService.findAllWhereOpsMetadataIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ProjectMetadata");
        Page<ProjectMetadata> page = projectMetadataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-metadata/:id} : get the "id" projectMetadata.
     *
     * @param id the id of the projectMetadata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectMetadata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-metadata/{id}")
    public ResponseEntity<ProjectMetadata> getProjectMetadata(@PathVariable Long id) {
        log.debug("REST request to get ProjectMetadata : {}", id);
        Optional<ProjectMetadata> projectMetadata = projectMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectMetadata);
    }

    /**
     * {@code DELETE  /project-metadata/:id} : delete the "id" projectMetadata.
     *
     * @param id the id of the projectMetadata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-metadata/{id}")
    public ResponseEntity<Void> deleteProjectMetadata(@PathVariable Long id) {
        log.debug("REST request to delete ProjectMetadata : {}", id);
        projectMetadataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
