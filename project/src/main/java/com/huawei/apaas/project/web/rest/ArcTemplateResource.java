package com.huawei.apaas.project.web.rest;

import com.huawei.apaas.project.domain.ArcTemplate;
import com.huawei.apaas.project.repository.ArcTemplateRepository;
import com.huawei.apaas.project.service.ArcTemplateService;
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
 * REST controller for managing {@link com.huawei.apaas.project.domain.ArcTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ArcTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ArcTemplateResource.class);

    private static final String ENTITY_NAME = "projectArcTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArcTemplateService arcTemplateService;

    private final ArcTemplateRepository arcTemplateRepository;

    public ArcTemplateResource(ArcTemplateService arcTemplateService, ArcTemplateRepository arcTemplateRepository) {
        this.arcTemplateService = arcTemplateService;
        this.arcTemplateRepository = arcTemplateRepository;
    }

    /**
     * {@code POST  /arc-templates} : Create a new arcTemplate.
     *
     * @param arcTemplate the arcTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arcTemplate, or with status {@code 400 (Bad Request)} if the arcTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arc-templates")
    public ResponseEntity<ArcTemplate> createArcTemplate(@Valid @RequestBody ArcTemplate arcTemplate) throws URISyntaxException {
        log.debug("REST request to save ArcTemplate : {}", arcTemplate);
        if (arcTemplate.getId() != null) {
            throw new BadRequestAlertException("A new arcTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArcTemplate result = arcTemplateService.save(arcTemplate);
        return ResponseEntity
            .created(new URI("/api/arc-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arc-templates/:id} : Updates an existing arcTemplate.
     *
     * @param id the id of the arcTemplate to save.
     * @param arcTemplate the arcTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arcTemplate,
     * or with status {@code 400 (Bad Request)} if the arcTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arcTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arc-templates/{id}")
    public ResponseEntity<ArcTemplate> updateArcTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArcTemplate arcTemplate
    ) throws URISyntaxException {
        log.debug("REST request to update ArcTemplate : {}, {}", id, arcTemplate);
        if (arcTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arcTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arcTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArcTemplate result = arcTemplateService.update(arcTemplate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arcTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arc-templates/:id} : Partial updates given fields of an existing arcTemplate, field will ignore if it is null
     *
     * @param id the id of the arcTemplate to save.
     * @param arcTemplate the arcTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arcTemplate,
     * or with status {@code 400 (Bad Request)} if the arcTemplate is not valid,
     * or with status {@code 404 (Not Found)} if the arcTemplate is not found,
     * or with status {@code 500 (Internal Server Error)} if the arcTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arc-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArcTemplate> partialUpdateArcTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArcTemplate arcTemplate
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArcTemplate partially : {}, {}", id, arcTemplate);
        if (arcTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arcTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arcTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArcTemplate> result = arcTemplateService.partialUpdate(arcTemplate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arcTemplate.getId().toString())
        );
    }

    /**
     * {@code GET  /arc-templates} : get all the arcTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arcTemplates in body.
     */
    @GetMapping("/arc-templates")
    public List<ArcTemplate> getAllArcTemplates() {
        log.debug("REST request to get all ArcTemplates");
        return arcTemplateService.findAll();
    }

    /**
     * {@code GET  /arc-templates/:id} : get the "id" arcTemplate.
     *
     * @param id the id of the arcTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arcTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arc-templates/{id}")
    public ResponseEntity<ArcTemplate> getArcTemplate(@PathVariable Long id) {
        log.debug("REST request to get ArcTemplate : {}", id);
        Optional<ArcTemplate> arcTemplate = arcTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arcTemplate);
    }

    /**
     * {@code DELETE  /arc-templates/:id} : delete the "id" arcTemplate.
     *
     * @param id the id of the arcTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arc-templates/{id}")
    public ResponseEntity<Void> deleteArcTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ArcTemplate : {}", id);
        arcTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
