package com.huawei.apaas.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huawei.apaas.project.IntegrationTest;
import com.huawei.apaas.project.domain.ArcMetadata;
import com.huawei.apaas.project.domain.enumeration.ArcModelType;
import com.huawei.apaas.project.repository.ArcMetadataRepository;
import com.huawei.apaas.project.service.ArcMetadataService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArcMetadataResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArcMetadataResourceIT {

    private static final ArcModelType DEFAULT_TYPE = ArcModelType.TEMPLATE;
    private static final ArcModelType UPDATED_TYPE = ArcModelType.MANUAL;

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

    private static final Long DEFAULT_SERVER_PORT = 1L;
    private static final Long UPDATED_SERVER_PORT = 2L;

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

    private static final String ENTITY_API_URL = "/api/arc-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArcMetadataRepository arcMetadataRepository;

    @Mock
    private ArcMetadataRepository arcMetadataRepositoryMock;

    @Mock
    private ArcMetadataService arcMetadataServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArcMetadataMockMvc;

    private ArcMetadata arcMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArcMetadata createEntity(EntityManager em) {
        ArcMetadata arcMetadata = new ArcMetadata()
            .type(DEFAULT_TYPE)
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
        return arcMetadata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArcMetadata createUpdatedEntity(EntityManager em) {
        ArcMetadata arcMetadata = new ArcMetadata()
            .type(UPDATED_TYPE)
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
        return arcMetadata;
    }

    @BeforeEach
    public void initTest() {
        arcMetadata = createEntity(em);
    }

    @Test
    @Transactional
    void createArcMetadata() throws Exception {
        int databaseSizeBeforeCreate = arcMetadataRepository.findAll().size();
        // Create the ArcMetadata
        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isCreated());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        ArcMetadata testArcMetadata = arcMetadataList.get(arcMetadataList.size() - 1);
        assertThat(testArcMetadata.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testArcMetadata.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testArcMetadata.getServiceDiscoveryType()).isEqualTo(DEFAULT_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcMetadata.getDevDatabaseType()).isEqualTo(DEFAULT_DEV_DATABASE_TYPE);
        assertThat(testArcMetadata.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(testArcMetadata.getCacheProvider()).isEqualTo(DEFAULT_CACHE_PROVIDER);
        assertThat(testArcMetadata.getMessageBroker()).isEqualTo(DEFAULT_MESSAGE_BROKER);
        assertThat(testArcMetadata.getServerPort()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(testArcMetadata.getFrontMessage()).isEqualTo(DEFAULT_FRONT_MESSAGE);
        assertThat(testArcMetadata.getClientFramework()).isEqualTo(DEFAULT_CLIENT_FRAMEWORK);
        assertThat(testArcMetadata.getLanguages()).isEqualTo(DEFAULT_LANGUAGES);
        assertThat(testArcMetadata.getTestFramework()).isEqualTo(DEFAULT_TEST_FRAMEWORK);
        assertThat(testArcMetadata.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testArcMetadata.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArcMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void createArcMetadataWithExistingId() throws Exception {
        // Create the ArcMetadata with an existing ID
        arcMetadata.setId(1L);

        int databaseSizeBeforeCreate = arcMetadataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcMetadataRepository.findAll().size();
        // set the field null
        arcMetadata.setType(null);

        // Create the ArcMetadata, which fails.

        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPackageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcMetadataRepository.findAll().size();
        // set the field null
        arcMetadata.setPackageName(null);

        // Create the ArcMetadata, which fails.

        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServerPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcMetadataRepository.findAll().size();
        // set the field null
        arcMetadata.setServerPort(null);

        // Create the ArcMetadata, which fails.

        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcMetadataRepository.findAll().size();
        // set the field null
        arcMetadata.setRentId(null);

        // Create the ArcMetadata, which fails.

        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcMetadataRepository.findAll().size();
        // set the field null
        arcMetadata.setUpdateTime(null);

        // Create the ArcMetadata, which fails.

        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arcMetadataRepository.findAll().size();
        // set the field null
        arcMetadata.setCreateTime(null);

        // Create the ArcMetadata, which fails.

        restArcMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArcMetadata() throws Exception {
        // Initialize the database
        arcMetadataRepository.saveAndFlush(arcMetadata);

        // Get all the arcMetadataList
        restArcMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arcMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].packageName").value(hasItem(DEFAULT_PACKAGE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDiscoveryType").value(hasItem(DEFAULT_SERVICE_DISCOVERY_TYPE)))
            .andExpect(jsonPath("$.[*].devDatabaseType").value(hasItem(DEFAULT_DEV_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].prodDatabaseType").value(hasItem(DEFAULT_PROD_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].cacheProvider").value(hasItem(DEFAULT_CACHE_PROVIDER)))
            .andExpect(jsonPath("$.[*].messageBroker").value(hasItem(DEFAULT_MESSAGE_BROKER)))
            .andExpect(jsonPath("$.[*].serverPort").value(hasItem(DEFAULT_SERVER_PORT.intValue())))
            .andExpect(jsonPath("$.[*].frontMessage").value(hasItem(DEFAULT_FRONT_MESSAGE)))
            .andExpect(jsonPath("$.[*].clientFramework").value(hasItem(DEFAULT_CLIENT_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].languages").value(hasItem(DEFAULT_LANGUAGES)))
            .andExpect(jsonPath("$.[*].testFramework").value(hasItem(DEFAULT_TEST_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].rentId").value(hasItem(DEFAULT_RENT_ID)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArcMetadataWithEagerRelationshipsIsEnabled() throws Exception {
        when(arcMetadataServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArcMetadataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(arcMetadataServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArcMetadataWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(arcMetadataServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArcMetadataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(arcMetadataRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArcMetadata() throws Exception {
        // Initialize the database
        arcMetadataRepository.saveAndFlush(arcMetadata);

        // Get the arcMetadata
        restArcMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, arcMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arcMetadata.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.packageName").value(DEFAULT_PACKAGE_NAME))
            .andExpect(jsonPath("$.serviceDiscoveryType").value(DEFAULT_SERVICE_DISCOVERY_TYPE))
            .andExpect(jsonPath("$.devDatabaseType").value(DEFAULT_DEV_DATABASE_TYPE))
            .andExpect(jsonPath("$.prodDatabaseType").value(DEFAULT_PROD_DATABASE_TYPE))
            .andExpect(jsonPath("$.cacheProvider").value(DEFAULT_CACHE_PROVIDER))
            .andExpect(jsonPath("$.messageBroker").value(DEFAULT_MESSAGE_BROKER))
            .andExpect(jsonPath("$.serverPort").value(DEFAULT_SERVER_PORT.intValue()))
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
    void getNonExistingArcMetadata() throws Exception {
        // Get the arcMetadata
        restArcMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArcMetadata() throws Exception {
        // Initialize the database
        arcMetadataRepository.saveAndFlush(arcMetadata);

        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();

        // Update the arcMetadata
        ArcMetadata updatedArcMetadata = arcMetadataRepository.findById(arcMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedArcMetadata are not directly saved in db
        em.detach(updatedArcMetadata);
        updatedArcMetadata
            .type(UPDATED_TYPE)
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

        restArcMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArcMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArcMetadata))
            )
            .andExpect(status().isOk());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
        ArcMetadata testArcMetadata = arcMetadataList.get(arcMetadataList.size() - 1);
        assertThat(testArcMetadata.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArcMetadata.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testArcMetadata.getServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcMetadata.getDevDatabaseType()).isEqualTo(UPDATED_DEV_DATABASE_TYPE);
        assertThat(testArcMetadata.getProdDatabaseType()).isEqualTo(UPDATED_PROD_DATABASE_TYPE);
        assertThat(testArcMetadata.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testArcMetadata.getMessageBroker()).isEqualTo(UPDATED_MESSAGE_BROKER);
        assertThat(testArcMetadata.getServerPort()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testArcMetadata.getFrontMessage()).isEqualTo(UPDATED_FRONT_MESSAGE);
        assertThat(testArcMetadata.getClientFramework()).isEqualTo(UPDATED_CLIENT_FRAMEWORK);
        assertThat(testArcMetadata.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testArcMetadata.getTestFramework()).isEqualTo(UPDATED_TEST_FRAMEWORK);
        assertThat(testArcMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testArcMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArcMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingArcMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();
        arcMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArcMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arcMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArcMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();
        arcMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArcMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();
        arcMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcMetadataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArcMetadataWithPatch() throws Exception {
        // Initialize the database
        arcMetadataRepository.saveAndFlush(arcMetadata);

        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();

        // Update the arcMetadata using partial update
        ArcMetadata partialUpdatedArcMetadata = new ArcMetadata();
        partialUpdatedArcMetadata.setId(arcMetadata.getId());

        partialUpdatedArcMetadata
            .type(UPDATED_TYPE)
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .messageBroker(UPDATED_MESSAGE_BROKER)
            .frontMessage(UPDATED_FRONT_MESSAGE)
            .clientFramework(UPDATED_CLIENT_FRAMEWORK)
            .languages(UPDATED_LANGUAGES)
            .testFramework(UPDATED_TEST_FRAMEWORK);

        restArcMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArcMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArcMetadata))
            )
            .andExpect(status().isOk());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
        ArcMetadata testArcMetadata = arcMetadataList.get(arcMetadataList.size() - 1);
        assertThat(testArcMetadata.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArcMetadata.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testArcMetadata.getServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcMetadata.getDevDatabaseType()).isEqualTo(DEFAULT_DEV_DATABASE_TYPE);
        assertThat(testArcMetadata.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(testArcMetadata.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testArcMetadata.getMessageBroker()).isEqualTo(UPDATED_MESSAGE_BROKER);
        assertThat(testArcMetadata.getServerPort()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(testArcMetadata.getFrontMessage()).isEqualTo(UPDATED_FRONT_MESSAGE);
        assertThat(testArcMetadata.getClientFramework()).isEqualTo(UPDATED_CLIENT_FRAMEWORK);
        assertThat(testArcMetadata.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testArcMetadata.getTestFramework()).isEqualTo(UPDATED_TEST_FRAMEWORK);
        assertThat(testArcMetadata.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testArcMetadata.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArcMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateArcMetadataWithPatch() throws Exception {
        // Initialize the database
        arcMetadataRepository.saveAndFlush(arcMetadata);

        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();

        // Update the arcMetadata using partial update
        ArcMetadata partialUpdatedArcMetadata = new ArcMetadata();
        partialUpdatedArcMetadata.setId(arcMetadata.getId());

        partialUpdatedArcMetadata
            .type(UPDATED_TYPE)
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

        restArcMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArcMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArcMetadata))
            )
            .andExpect(status().isOk());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
        ArcMetadata testArcMetadata = arcMetadataList.get(arcMetadataList.size() - 1);
        assertThat(testArcMetadata.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArcMetadata.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testArcMetadata.getServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testArcMetadata.getDevDatabaseType()).isEqualTo(UPDATED_DEV_DATABASE_TYPE);
        assertThat(testArcMetadata.getProdDatabaseType()).isEqualTo(UPDATED_PROD_DATABASE_TYPE);
        assertThat(testArcMetadata.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testArcMetadata.getMessageBroker()).isEqualTo(UPDATED_MESSAGE_BROKER);
        assertThat(testArcMetadata.getServerPort()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testArcMetadata.getFrontMessage()).isEqualTo(UPDATED_FRONT_MESSAGE);
        assertThat(testArcMetadata.getClientFramework()).isEqualTo(UPDATED_CLIENT_FRAMEWORK);
        assertThat(testArcMetadata.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testArcMetadata.getTestFramework()).isEqualTo(UPDATED_TEST_FRAMEWORK);
        assertThat(testArcMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testArcMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArcMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingArcMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();
        arcMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArcMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arcMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArcMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();
        arcMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArcMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arcMetadataRepository.findAll().size();
        arcMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arcMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArcMetadata in the database
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArcMetadata() throws Exception {
        // Initialize the database
        arcMetadataRepository.saveAndFlush(arcMetadata);

        int databaseSizeBeforeDelete = arcMetadataRepository.findAll().size();

        // Delete the arcMetadata
        restArcMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, arcMetadata.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArcMetadata> arcMetadataList = arcMetadataRepository.findAll();
        assertThat(arcMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
