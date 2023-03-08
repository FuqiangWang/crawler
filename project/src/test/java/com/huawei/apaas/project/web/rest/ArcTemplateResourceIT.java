package com.huawei.apaas.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huawei.apaas.project.IntegrationTest;
import com.huawei.apaas.project.domain.ArcTemplate;
import com.huawei.apaas.project.repository.ArcTemplateRepository;
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

/**
 * Integration tests for the {@link ArcTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArcTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_DISCOVERY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DISCOVERY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEV_DATABASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEV_DATABASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PROD_DATABASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROD_DATABASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CACHE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_CACHE_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_BROKER = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_BROKER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SERVER_PORT = 1;
    private static final Integer UPDATED_SERVER_PORT = 2;

    private static final String DEFAULT_FRONT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_FRONT_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_FRAMEWORK = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_FRAMEWORK = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGES = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_FRAMEWORK = "AAAAAAAAAA";
    private static final String UPDATED_TEST_FRAMEWORK = "BBBBBBBBBB";

    private static final String DEFAULT_RENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/arc-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArcTemplateRepository arcTemplateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArcTemplateMockMvc;

    private ArcTemplate arcTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArcTemplate createEntity(EntityManager em) {
        ArcTemplate arcTemplate = new ArcTemplate()
            .name(DEFAULT_NAME)
            .packageName(DEFAULT_PACKAGE_NAME)
            .serviceDiscoveryType(DEFAULT_SERVICE_DISCOVERY_TYPE)
            .devDatabaseType(DEFAULT_DEV_DATABASE_TYPE)
            .prodDatabaseType(DEFAULT_PROD_DATABASE_TYPE)
            .cacheProvider(DEFAULT_CACHE_PROVIDER)
            .messageBroker(DEFAULT_MESSAGE_BROKER)
            .serverPort(DEFAULT_SERVER_PORT)
            .frontMessage(DEFAULT_FRONT_MESSAGE)
            .clientFramework(DEFAULT_CLIENT_FRAMEWORK)
            .languages(DEFAULT_LANGUAGES)
            .testFramework(DEFAULT_TEST_FRAMEWORK)
            .rentId(DEFAULT_RENT_ID)
            .updateTime(DEFAULT_UPDATE_TIME)
            .createTime(DEFAULT_CREATE_TIME);
        return arcTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArcTemplate createUpdatedEntity(EntityManager em) {
        ArcTemplate arcTemplate = new ArcTemplate()
            .name(UPDATED_NAME)
            .packageName(UPDATED_PACKAGE_NAME)
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .devDatabaseType(UPDATED_DEV_DATABASE_TYPE)
            .prodDatabaseType(UPDATED_PROD_DATABASE_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .messageBroker(UPDATED_MESSAGE_BROKER)
            .serverPort(UPDATED_SERVER_PORT)
            .frontMessage(UPDATED_FRONT_MESSAGE)
            .clientFramework(UPDATED_CLIENT_FRAMEWORK)
            .languages(UPDATED_LANGUAGES)
            .testFramework(UPDATED_TEST_FRAMEWORK)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);
        return arcTemplate;
    }

    @BeforeEach
    public void initTest() {
        arcTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createArcTemplate() throws Exception {
        int databaseSizeBeforeCreate = arcTemplateRepository.findAll().size();
        // Create the ArcTemplate
        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isCreated());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ArcTemplate testArcTemplate = arcTemplateList.get(arcTemplateList.size() - 1);
        assertThat(testArcTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArcTemplate.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testArcTemplate.getServiceDiscoveryType()).isEqualTo(DEFAULT_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcTemplate.getDevDatabaseType()).isEqualTo(DEFAULT_DEV_DATABASE_TYPE);
        assertThat(testArcTemplate.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(testArcTemplate.getCacheProvider()).isEqualTo(DEFAULT_CACHE_PROVIDER);
        assertThat(testArcTemplate.getMessageBroker()).isEqualTo(DEFAULT_MESSAGE_BROKER);
        assertThat(testArcTemplate.getServerPort()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(testArcTemplate.getFrontMessage()).isEqualTo(DEFAULT_FRONT_MESSAGE);
        assertThat(testArcTemplate.getClientFramework()).isEqualTo(DEFAULT_CLIENT_FRAMEWORK);
        assertThat(testArcTemplate.getLanguages()).isEqualTo(DEFAULT_LANGUAGES);
        assertThat(testArcTemplate.getTestFramework()).isEqualTo(DEFAULT_TEST_FRAMEWORK);
        assertThat(testArcTemplate.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testArcTemplate.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArcTemplate.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void createArcTemplateWithExistingId() throws Exception {
        // Create the ArcTemplate with an existing ID
        arcTemplate.setId(1L);

        int databaseSizeBeforeCreate = arcTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcTemplateRepository.findAll().size();
        // set the field null
        arcTemplate.setName(null);

        // Create the ArcTemplate, which fails.

        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPackageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcTemplateRepository.findAll().size();
        // set the field null
        arcTemplate.setPackageName(null);

        // Create the ArcTemplate, which fails.

        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServerPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcTemplateRepository.findAll().size();
        // set the field null
        arcTemplate.setServerPort(null);

        // Create the ArcTemplate, which fails.

        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcTemplateRepository.findAll().size();
        // set the field null
        arcTemplate.setRentId(null);

        // Create the ArcTemplate, which fails.

        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcTemplateRepository.findAll().size();
        // set the field null
        arcTemplate.setUpdateTime(null);

        // Create the ArcTemplate, which fails.

        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcTemplateRepository.findAll().size();
        // set the field null
        arcTemplate.setCreateTime(null);

        // Create the ArcTemplate, which fails.

        restArcTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArcTemplates() throws Exception {
        // Initialize the database
        arcTemplateRepository.saveAndFlush(arcTemplate);

        // Get all the arcTemplateList
        restArcTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arcTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].packageName").value(hasItem(DEFAULT_PACKAGE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDiscoveryType").value(hasItem(DEFAULT_SERVICE_DISCOVERY_TYPE)))
            .andExpect(jsonPath("$.[*].devDatabaseType").value(hasItem(DEFAULT_DEV_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].prodDatabaseType").value(hasItem(DEFAULT_PROD_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].cacheProvider").value(hasItem(DEFAULT_CACHE_PROVIDER)))
            .andExpect(jsonPath("$.[*].messageBroker").value(hasItem(DEFAULT_MESSAGE_BROKER)))
            .andExpect(jsonPath("$.[*].serverPort").value(hasItem(DEFAULT_SERVER_PORT)))
            .andExpect(jsonPath("$.[*].frontMessage").value(hasItem(DEFAULT_FRONT_MESSAGE)))
            .andExpect(jsonPath("$.[*].clientFramework").value(hasItem(DEFAULT_CLIENT_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].languages").value(hasItem(DEFAULT_LANGUAGES)))
            .andExpect(jsonPath("$.[*].testFramework").value(hasItem(DEFAULT_TEST_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].rentId").value(hasItem(DEFAULT_RENT_ID)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    void getArcTemplate() throws Exception {
        // Initialize the database
        arcTemplateRepository.saveAndFlush(arcTemplate);

        // Get the arcTemplate
        restArcTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, arcTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arcTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.packageName").value(DEFAULT_PACKAGE_NAME))
            .andExpect(jsonPath("$.serviceDiscoveryType").value(DEFAULT_SERVICE_DISCOVERY_TYPE))
            .andExpect(jsonPath("$.devDatabaseType").value(DEFAULT_DEV_DATABASE_TYPE))
            .andExpect(jsonPath("$.prodDatabaseType").value(DEFAULT_PROD_DATABASE_TYPE))
            .andExpect(jsonPath("$.cacheProvider").value(DEFAULT_CACHE_PROVIDER))
            .andExpect(jsonPath("$.messageBroker").value(DEFAULT_MESSAGE_BROKER))
            .andExpect(jsonPath("$.serverPort").value(DEFAULT_SERVER_PORT))
            .andExpect(jsonPath("$.frontMessage").value(DEFAULT_FRONT_MESSAGE))
            .andExpect(jsonPath("$.clientFramework").value(DEFAULT_CLIENT_FRAMEWORK))
            .andExpect(jsonPath("$.languages").value(DEFAULT_LANGUAGES))
            .andExpect(jsonPath("$.testFramework").value(DEFAULT_TEST_FRAMEWORK))
            .andExpect(jsonPath("$.rentId").value(DEFAULT_RENT_ID))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME));
    }

    @Test
    @Transactional
    void getNonExistingArcTemplate() throws Exception {
        // Get the arcTemplate
        restArcTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArcTemplate() throws Exception {
        // Initialize the database
        arcTemplateRepository.saveAndFlush(arcTemplate);

        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();

        // Update the arcTemplate
        ArcTemplate updatedArcTemplate = arcTemplateRepository.findById(arcTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedArcTemplate are not directly saved in db
        em.detach(updatedArcTemplate);
        updatedArcTemplate
            .name(UPDATED_NAME)
            .packageName(UPDATED_PACKAGE_NAME)
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .devDatabaseType(UPDATED_DEV_DATABASE_TYPE)
            .prodDatabaseType(UPDATED_PROD_DATABASE_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .messageBroker(UPDATED_MESSAGE_BROKER)
            .serverPort(UPDATED_SERVER_PORT)
            .frontMessage(UPDATED_FRONT_MESSAGE)
            .clientFramework(UPDATED_CLIENT_FRAMEWORK)
            .languages(UPDATED_LANGUAGES)
            .testFramework(UPDATED_TEST_FRAMEWORK)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restArcTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArcTemplate.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArcTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
        ArcTemplate testArcTemplate = arcTemplateList.get(arcTemplateList.size() - 1);
        assertThat(testArcTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArcTemplate.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testArcTemplate.getServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcTemplate.getDevDatabaseType()).isEqualTo(UPDATED_DEV_DATABASE_TYPE);
        assertThat(testArcTemplate.getProdDatabaseType()).isEqualTo(UPDATED_PROD_DATABASE_TYPE);
        assertThat(testArcTemplate.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testArcTemplate.getMessageBroker()).isEqualTo(UPDATED_MESSAGE_BROKER);
        assertThat(testArcTemplate.getServerPort()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testArcTemplate.getFrontMessage()).isEqualTo(UPDATED_FRONT_MESSAGE);
        assertThat(testArcTemplate.getClientFramework()).isEqualTo(UPDATED_CLIENT_FRAMEWORK);
        assertThat(testArcTemplate.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testArcTemplate.getTestFramework()).isEqualTo(UPDATED_TEST_FRAMEWORK);
        assertThat(testArcTemplate.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testArcTemplate.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArcTemplate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingArcTemplate() throws Exception {
        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();
        arcTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArcTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arcTemplate.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArcTemplate() throws Exception {
        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();
        arcTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArcTemplate() throws Exception {
        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();
        arcTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArcTemplateWithPatch() throws Exception {
        // Initialize the database
        arcTemplateRepository.saveAndFlush(arcTemplate);

        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();

        // Update the arcTemplate using partial update
        ArcTemplate partialUpdatedArcTemplate = new ArcTemplate();
        partialUpdatedArcTemplate.setId(arcTemplate.getId());

        partialUpdatedArcTemplate
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .devDatabaseType(UPDATED_DEV_DATABASE_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .serverPort(UPDATED_SERVER_PORT)
            .frontMessage(UPDATED_FRONT_MESSAGE)
            .testFramework(UPDATED_TEST_FRAMEWORK)
            .rentId(UPDATED_RENT_ID)
            .createTime(UPDATED_CREATE_TIME);

        restArcTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArcTemplate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArcTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
        ArcTemplate testArcTemplate = arcTemplateList.get(arcTemplateList.size() - 1);
        assertThat(testArcTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArcTemplate.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testArcTemplate.getServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcTemplate.getDevDatabaseType()).isEqualTo(UPDATED_DEV_DATABASE_TYPE);
        assertThat(testArcTemplate.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(testArcTemplate.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testArcTemplate.getMessageBroker()).isEqualTo(DEFAULT_MESSAGE_BROKER);
        assertThat(testArcTemplate.getServerPort()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testArcTemplate.getFrontMessage()).isEqualTo(UPDATED_FRONT_MESSAGE);
        assertThat(testArcTemplate.getClientFramework()).isEqualTo(DEFAULT_CLIENT_FRAMEWORK);
        assertThat(testArcTemplate.getLanguages()).isEqualTo(DEFAULT_LANGUAGES);
        assertThat(testArcTemplate.getTestFramework()).isEqualTo(UPDATED_TEST_FRAMEWORK);
        assertThat(testArcTemplate.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testArcTemplate.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArcTemplate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateArcTemplateWithPatch() throws Exception {
        // Initialize the database
        arcTemplateRepository.saveAndFlush(arcTemplate);

        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();

        // Update the arcTemplate using partial update
        ArcTemplate partialUpdatedArcTemplate = new ArcTemplate();
        partialUpdatedArcTemplate.setId(arcTemplate.getId());

        partialUpdatedArcTemplate
            .name(UPDATED_NAME)
            .packageName(UPDATED_PACKAGE_NAME)
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .devDatabaseType(UPDATED_DEV_DATABASE_TYPE)
            .prodDatabaseType(UPDATED_PROD_DATABASE_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .messageBroker(UPDATED_MESSAGE_BROKER)
            .serverPort(UPDATED_SERVER_PORT)
            .frontMessage(UPDATED_FRONT_MESSAGE)
            .clientFramework(UPDATED_CLIENT_FRAMEWORK)
            .languages(UPDATED_LANGUAGES)
            .testFramework(UPDATED_TEST_FRAMEWORK)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restArcTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArcTemplate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArcTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
        ArcTemplate testArcTemplate = arcTemplateList.get(arcTemplateList.size() - 1);
        assertThat(testArcTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArcTemplate.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testArcTemplate.getServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcTemplate.getDevDatabaseType()).isEqualTo(UPDATED_DEV_DATABASE_TYPE);
        assertThat(testArcTemplate.getProdDatabaseType()).isEqualTo(UPDATED_PROD_DATABASE_TYPE);
        assertThat(testArcTemplate.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testArcTemplate.getMessageBroker()).isEqualTo(UPDATED_MESSAGE_BROKER);
        assertThat(testArcTemplate.getServerPort()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testArcTemplate.getFrontMessage()).isEqualTo(UPDATED_FRONT_MESSAGE);
        assertThat(testArcTemplate.getClientFramework()).isEqualTo(UPDATED_CLIENT_FRAMEWORK);
        assertThat(testArcTemplate.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testArcTemplate.getTestFramework()).isEqualTo(UPDATED_TEST_FRAMEWORK);
        assertThat(testArcTemplate.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testArcTemplate.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArcTemplate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingArcTemplate() throws Exception {
        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();
        arcTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArcTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arcTemplate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArcTemplate() throws Exception {
        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();
        arcTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArcTemplate() throws Exception {
        int databaseSizeBeforeUpdate = arcTemplateRepository.findAll().size();
        arcTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arcTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArcTemplate in the database
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArcTemplate() throws Exception {
        // Initialize the database
        arcTemplateRepository.saveAndFlush(arcTemplate);

        int databaseSizeBeforeDelete = arcTemplateRepository.findAll().size();

        // Delete the arcTemplate
        restArcTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, arcTemplate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArcTemplate> arcTemplateList = arcTemplateRepository.findAll();
        assertThat(arcTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
