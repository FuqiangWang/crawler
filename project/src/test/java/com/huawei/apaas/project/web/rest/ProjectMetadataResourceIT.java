package com.huawei.apaas.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huawei.apaas.project.IntegrationTest;
import com.huawei.apaas.project.domain.ProjectMetadata;
import com.huawei.apaas.project.domain.enumeration.ProjectType;
import com.huawei.apaas.project.repository.ProjectMetadataRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProjectMetadataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectMetadataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ProjectType DEFAULT_TYPE = ProjectType.MONOLITH;
    private static final ProjectType UPDATED_TYPE = ProjectType.MICROSERVICE;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LANG_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_LANG_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_BUILD_TOOL = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_TOOL = "BBBBBBBBBB";

    private static final String DEFAULT_BUILD_TOOL_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_TOOL_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER = "AAAAAAAAAA";
    private static final String UPDATED_BANNER = "BBBBBBBBBB";

    private static final String DEFAULT_FAVICON = "AAAAAAAAAA";
    private static final String UPDATED_FAVICON = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_RENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectMetadataRepository projectMetadataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMetadataMockMvc;

    private ProjectMetadata projectMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectMetadata createEntity(EntityManager em) {
        ProjectMetadata projectMetadata = new ProjectMetadata()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .language(DEFAULT_LANGUAGE)
            .langVersion(DEFAULT_LANG_VERSION)
            .buildTool(DEFAULT_BUILD_TOOL)
            .buildToolVersion(DEFAULT_BUILD_TOOL_VERSION)
            .banner(DEFAULT_BANNER)
            .favicon(DEFAULT_FAVICON)
            .version(DEFAULT_VERSION)
            .rentId(DEFAULT_RENT_ID)
            .updateTime(DEFAULT_UPDATE_TIME)
            .createTime(DEFAULT_CREATE_TIME);
        return projectMetadata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectMetadata createUpdatedEntity(EntityManager em) {
        ProjectMetadata projectMetadata = new ProjectMetadata()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .language(UPDATED_LANGUAGE)
            .langVersion(UPDATED_LANG_VERSION)
            .buildTool(UPDATED_BUILD_TOOL)
            .buildToolVersion(UPDATED_BUILD_TOOL_VERSION)
            .banner(UPDATED_BANNER)
            .favicon(UPDATED_FAVICON)
            .version(UPDATED_VERSION)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);
        return projectMetadata;
    }

    @BeforeEach
    public void initTest() {
        projectMetadata = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectMetadata() throws Exception {
        int databaseSizeBeforeCreate = projectMetadataRepository.findAll().size();
        // Create the ProjectMetadata
        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectMetadata testProjectMetadata = projectMetadataList.get(projectMetadataList.size() - 1);
        assertThat(testProjectMetadata.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectMetadata.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProjectMetadata.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testProjectMetadata.getLangVersion()).isEqualTo(DEFAULT_LANG_VERSION);
        assertThat(testProjectMetadata.getBuildTool()).isEqualTo(DEFAULT_BUILD_TOOL);
        assertThat(testProjectMetadata.getBuildToolVersion()).isEqualTo(DEFAULT_BUILD_TOOL_VERSION);
        assertThat(testProjectMetadata.getBanner()).isEqualTo(DEFAULT_BANNER);
        assertThat(testProjectMetadata.getFavicon()).isEqualTo(DEFAULT_FAVICON);
        assertThat(testProjectMetadata.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProjectMetadata.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testProjectMetadata.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testProjectMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void createProjectMetadataWithExistingId() throws Exception {
        // Create the ProjectMetadata with an existing ID
        projectMetadata.setId(1L);

        int databaseSizeBeforeCreate = projectMetadataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setName(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setType(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setLanguage(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBannerIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setBanner(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setVersion(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setRentId(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setUpdateTime(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMetadataRepository.findAll().size();
        // set the field null
        projectMetadata.setCreateTime(null);

        // Create the ProjectMetadata, which fails.

        restProjectMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectMetadata() throws Exception {
        // Initialize the database
        projectMetadataRepository.saveAndFlush(projectMetadata);

        // Get all the projectMetadataList
        restProjectMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].langVersion").value(hasItem(DEFAULT_LANG_VERSION)))
            .andExpect(jsonPath("$.[*].buildTool").value(hasItem(DEFAULT_BUILD_TOOL)))
            .andExpect(jsonPath("$.[*].buildToolVersion").value(hasItem(DEFAULT_BUILD_TOOL_VERSION)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(DEFAULT_BANNER)))
            .andExpect(jsonPath("$.[*].favicon").value(hasItem(DEFAULT_FAVICON.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].rentId").value(hasItem(DEFAULT_RENT_ID)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    void getProjectMetadata() throws Exception {
        // Initialize the database
        projectMetadataRepository.saveAndFlush(projectMetadata);

        // Get the projectMetadata
        restProjectMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, projectMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectMetadata.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.langVersion").value(DEFAULT_LANG_VERSION))
            .andExpect(jsonPath("$.buildTool").value(DEFAULT_BUILD_TOOL))
            .andExpect(jsonPath("$.buildToolVersion").value(DEFAULT_BUILD_TOOL_VERSION))
            .andExpect(jsonPath("$.banner").value(DEFAULT_BANNER))
            .andExpect(jsonPath("$.favicon").value(DEFAULT_FAVICON.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.rentId").value(DEFAULT_RENT_ID))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME));
    }

    @Test
    @Transactional
    void getNonExistingProjectMetadata() throws Exception {
        // Get the projectMetadata
        restProjectMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectMetadata() throws Exception {
        // Initialize the database
        projectMetadataRepository.saveAndFlush(projectMetadata);

        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();

        // Update the projectMetadata
        ProjectMetadata updatedProjectMetadata = projectMetadataRepository.findById(projectMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedProjectMetadata are not directly saved in db
        em.detach(updatedProjectMetadata);
        updatedProjectMetadata
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .language(UPDATED_LANGUAGE)
            .langVersion(UPDATED_LANG_VERSION)
            .buildTool(UPDATED_BUILD_TOOL)
            .buildToolVersion(UPDATED_BUILD_TOOL_VERSION)
            .banner(UPDATED_BANNER)
            .favicon(UPDATED_FAVICON)
            .version(UPDATED_VERSION)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restProjectMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectMetadata))
            )
            .andExpect(status().isOk());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
        ProjectMetadata testProjectMetadata = projectMetadataList.get(projectMetadataList.size() - 1);
        assertThat(testProjectMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectMetadata.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProjectMetadata.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testProjectMetadata.getLangVersion()).isEqualTo(UPDATED_LANG_VERSION);
        assertThat(testProjectMetadata.getBuildTool()).isEqualTo(UPDATED_BUILD_TOOL);
        assertThat(testProjectMetadata.getBuildToolVersion()).isEqualTo(UPDATED_BUILD_TOOL_VERSION);
        assertThat(testProjectMetadata.getBanner()).isEqualTo(UPDATED_BANNER);
        assertThat(testProjectMetadata.getFavicon()).isEqualTo(UPDATED_FAVICON);
        assertThat(testProjectMetadata.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProjectMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testProjectMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testProjectMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingProjectMetadata() throws Exception {
        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();
        projectMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectMetadata() throws Exception {
        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();
        projectMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectMetadata() throws Exception {
        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();
        projectMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMetadataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectMetadataWithPatch() throws Exception {
        // Initialize the database
        projectMetadataRepository.saveAndFlush(projectMetadata);

        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();

        // Update the projectMetadata using partial update
        ProjectMetadata partialUpdatedProjectMetadata = new ProjectMetadata();
        partialUpdatedProjectMetadata.setId(projectMetadata.getId());

        partialUpdatedProjectMetadata
            .language(UPDATED_LANGUAGE)
            .langVersion(UPDATED_LANG_VERSION)
            .buildTool(UPDATED_BUILD_TOOL)
            .rentId(UPDATED_RENT_ID);

        restProjectMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectMetadata))
            )
            .andExpect(status().isOk());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
        ProjectMetadata testProjectMetadata = projectMetadataList.get(projectMetadataList.size() - 1);
        assertThat(testProjectMetadata.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectMetadata.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProjectMetadata.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testProjectMetadata.getLangVersion()).isEqualTo(UPDATED_LANG_VERSION);
        assertThat(testProjectMetadata.getBuildTool()).isEqualTo(UPDATED_BUILD_TOOL);
        assertThat(testProjectMetadata.getBuildToolVersion()).isEqualTo(DEFAULT_BUILD_TOOL_VERSION);
        assertThat(testProjectMetadata.getBanner()).isEqualTo(DEFAULT_BANNER);
        assertThat(testProjectMetadata.getFavicon()).isEqualTo(DEFAULT_FAVICON);
        assertThat(testProjectMetadata.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProjectMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testProjectMetadata.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testProjectMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateProjectMetadataWithPatch() throws Exception {
        // Initialize the database
        projectMetadataRepository.saveAndFlush(projectMetadata);

        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();

        // Update the projectMetadata using partial update
        ProjectMetadata partialUpdatedProjectMetadata = new ProjectMetadata();
        partialUpdatedProjectMetadata.setId(projectMetadata.getId());

        partialUpdatedProjectMetadata
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .language(UPDATED_LANGUAGE)
            .langVersion(UPDATED_LANG_VERSION)
            .buildTool(UPDATED_BUILD_TOOL)
            .buildToolVersion(UPDATED_BUILD_TOOL_VERSION)
            .banner(UPDATED_BANNER)
            .favicon(UPDATED_FAVICON)
            .version(UPDATED_VERSION)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restProjectMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectMetadata))
            )
            .andExpect(status().isOk());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
        ProjectMetadata testProjectMetadata = projectMetadataList.get(projectMetadataList.size() - 1);
        assertThat(testProjectMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectMetadata.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProjectMetadata.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testProjectMetadata.getLangVersion()).isEqualTo(UPDATED_LANG_VERSION);
        assertThat(testProjectMetadata.getBuildTool()).isEqualTo(UPDATED_BUILD_TOOL);
        assertThat(testProjectMetadata.getBuildToolVersion()).isEqualTo(UPDATED_BUILD_TOOL_VERSION);
        assertThat(testProjectMetadata.getBanner()).isEqualTo(UPDATED_BANNER);
        assertThat(testProjectMetadata.getFavicon()).isEqualTo(UPDATED_FAVICON);
        assertThat(testProjectMetadata.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProjectMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testProjectMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testProjectMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingProjectMetadata() throws Exception {
        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();
        projectMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectMetadata() throws Exception {
        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();
        projectMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectMetadata() throws Exception {
        int databaseSizeBeforeUpdate = projectMetadataRepository.findAll().size();
        projectMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectMetadata in the database
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectMetadata() throws Exception {
        // Initialize the database
        projectMetadataRepository.saveAndFlush(projectMetadata);

        int databaseSizeBeforeDelete = projectMetadataRepository.findAll().size();

        // Delete the projectMetadata
        restProjectMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectMetadata.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectMetadata> projectMetadataList = projectMetadataRepository.findAll();
        assertThat(projectMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
