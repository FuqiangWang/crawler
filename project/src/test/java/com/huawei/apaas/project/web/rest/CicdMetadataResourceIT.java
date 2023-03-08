package com.huawei.apaas.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huawei.apaas.project.IntegrationTest;
import com.huawei.apaas.project.domain.CicdMetadata;
import com.huawei.apaas.project.domain.enumeration.CdType;
import com.huawei.apaas.project.repository.CicdMetadataRepository;
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
 * Integration tests for the {@link CicdMetadataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CicdMetadataResourceIT {

    private static final String DEFAULT_REPOSITORY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REPOSITORY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REPOSITORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPOSITORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CI_URL = "AAAAAAAAAA";
    private static final String UPDATED_CI_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BUILD_PKG = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_PKG = "BBBBBBBBBB";

    private static final String DEFAULT_MIRROR_REPOSITORY = "AAAAAAAAAA";
    private static final String UPDATED_MIRROR_REPOSITORY = "BBBBBBBBBB";

    private static final CdType DEFAULT_CD_TYPE = CdType.AUTO;
    private static final CdType UPDATED_CD_TYPE = CdType.MANUAL;

    private static final String DEFAULT_AUTO_IP = "AAAAAAAAAA";
    private static final String UPDATED_AUTO_IP = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTO_PORT = 1;
    private static final Integer UPDATED_AUTO_PORT = 2;

    private static final String DEFAULT_AUTO_USER = "AAAAAAAAAA";
    private static final String UPDATED_AUTO_USER = "BBBBBBBBBB";

    private static final String DEFAULT_AUTO_PWD = "AAAAAAAAAA";
    private static final String UPDATED_AUTO_PWD = "BBBBBBBBBB";

    private static final String DEFAULT_AUTO_KEY = "AAAAAAAAAA";
    private static final String UPDATED_AUTO_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPLOY = "AAAAAAAAAA";
    private static final String UPDATED_DEPLOY = "BBBBBBBBBB";

    private static final String DEFAULT_RENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cicd-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CicdMetadataRepository cicdMetadataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCicdMetadataMockMvc;

    private CicdMetadata cicdMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CicdMetadata createEntity(EntityManager em) {
        CicdMetadata cicdMetadata = new CicdMetadata()
            .repositoryType(DEFAULT_REPOSITORY_TYPE)
            .repositoryName(DEFAULT_REPOSITORY_NAME)
            .ciName(DEFAULT_CI_NAME)
            .ciUrl(DEFAULT_CI_URL)
            .buildPkg(DEFAULT_BUILD_PKG)
            .mirrorRepository(DEFAULT_MIRROR_REPOSITORY)
            .cdType(DEFAULT_CD_TYPE)
            .autoIp(DEFAULT_AUTO_IP)
            .autoPort(DEFAULT_AUTO_PORT)
            .autoUser(DEFAULT_AUTO_USER)
            .autoPwd(DEFAULT_AUTO_PWD)
            .autoKey(DEFAULT_AUTO_KEY)
            .deploy(DEFAULT_DEPLOY)
            .rentId(DEFAULT_RENT_ID)
            .updateTime(DEFAULT_UPDATE_TIME)
            .createTime(DEFAULT_CREATE_TIME);
        return cicdMetadata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CicdMetadata createUpdatedEntity(EntityManager em) {
        CicdMetadata cicdMetadata = new CicdMetadata()
            .repositoryType(UPDATED_REPOSITORY_TYPE)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .ciName(UPDATED_CI_NAME)
            .ciUrl(UPDATED_CI_URL)
            .buildPkg(UPDATED_BUILD_PKG)
            .mirrorRepository(UPDATED_MIRROR_REPOSITORY)
            .cdType(UPDATED_CD_TYPE)
            .autoIp(UPDATED_AUTO_IP)
            .autoPort(UPDATED_AUTO_PORT)
            .autoUser(UPDATED_AUTO_USER)
            .autoPwd(UPDATED_AUTO_PWD)
            .autoKey(UPDATED_AUTO_KEY)
            .deploy(UPDATED_DEPLOY)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);
        return cicdMetadata;
    }

    @BeforeEach
    public void initTest() {
        cicdMetadata = createEntity(em);
    }

    @Test
    @Transactional
    void createCicdMetadata() throws Exception {
        int databaseSizeBeforeCreate = cicdMetadataRepository.findAll().size();
        // Create the CicdMetadata
        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isCreated());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        CicdMetadata testCicdMetadata = cicdMetadataList.get(cicdMetadataList.size() - 1);
        assertThat(testCicdMetadata.getRepositoryType()).isEqualTo(DEFAULT_REPOSITORY_TYPE);
        assertThat(testCicdMetadata.getRepositoryName()).isEqualTo(DEFAULT_REPOSITORY_NAME);
        assertThat(testCicdMetadata.getCiName()).isEqualTo(DEFAULT_CI_NAME);
        assertThat(testCicdMetadata.getCiUrl()).isEqualTo(DEFAULT_CI_URL);
        assertThat(testCicdMetadata.getBuildPkg()).isEqualTo(DEFAULT_BUILD_PKG);
        assertThat(testCicdMetadata.getMirrorRepository()).isEqualTo(DEFAULT_MIRROR_REPOSITORY);
        assertThat(testCicdMetadata.getCdType()).isEqualTo(DEFAULT_CD_TYPE);
        assertThat(testCicdMetadata.getAutoIp()).isEqualTo(DEFAULT_AUTO_IP);
        assertThat(testCicdMetadata.getAutoPort()).isEqualTo(DEFAULT_AUTO_PORT);
        assertThat(testCicdMetadata.getAutoUser()).isEqualTo(DEFAULT_AUTO_USER);
        assertThat(testCicdMetadata.getAutoPwd()).isEqualTo(DEFAULT_AUTO_PWD);
        assertThat(testCicdMetadata.getAutoKey()).isEqualTo(DEFAULT_AUTO_KEY);
        assertThat(testCicdMetadata.getDeploy()).isEqualTo(DEFAULT_DEPLOY);
        assertThat(testCicdMetadata.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testCicdMetadata.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testCicdMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void createCicdMetadataWithExistingId() throws Exception {
        // Create the CicdMetadata with an existing ID
        cicdMetadata.setId(1L);

        int databaseSizeBeforeCreate = cicdMetadataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRepositoryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setRepositoryType(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepositoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setRepositoryName(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCiNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setCiName(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCiUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setCiUrl(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBuildPkgIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setBuildPkg(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCdTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setCdType(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setRentId(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setUpdateTime(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicdMetadataRepository.findAll().size();
        // set the field null
        cicdMetadata.setCreateTime(null);

        // Create the CicdMetadata, which fails.

        restCicdMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCicdMetadata() throws Exception {
        // Initialize the database
        cicdMetadataRepository.saveAndFlush(cicdMetadata);

        // Get all the cicdMetadataList
        restCicdMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cicdMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].repositoryType").value(hasItem(DEFAULT_REPOSITORY_TYPE)))
            .andExpect(jsonPath("$.[*].repositoryName").value(hasItem(DEFAULT_REPOSITORY_NAME)))
            .andExpect(jsonPath("$.[*].ciName").value(hasItem(DEFAULT_CI_NAME)))
            .andExpect(jsonPath("$.[*].ciUrl").value(hasItem(DEFAULT_CI_URL)))
            .andExpect(jsonPath("$.[*].buildPkg").value(hasItem(DEFAULT_BUILD_PKG)))
            .andExpect(jsonPath("$.[*].mirrorRepository").value(hasItem(DEFAULT_MIRROR_REPOSITORY)))
            .andExpect(jsonPath("$.[*].cdType").value(hasItem(DEFAULT_CD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].autoIp").value(hasItem(DEFAULT_AUTO_IP)))
            .andExpect(jsonPath("$.[*].autoPort").value(hasItem(DEFAULT_AUTO_PORT)))
            .andExpect(jsonPath("$.[*].autoUser").value(hasItem(DEFAULT_AUTO_USER)))
            .andExpect(jsonPath("$.[*].autoPwd").value(hasItem(DEFAULT_AUTO_PWD)))
            .andExpect(jsonPath("$.[*].autoKey").value(hasItem(DEFAULT_AUTO_KEY.toString())))
            .andExpect(jsonPath("$.[*].deploy").value(hasItem(DEFAULT_DEPLOY)))
            .andExpect(jsonPath("$.[*].rentId").value(hasItem(DEFAULT_RENT_ID)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    void getCicdMetadata() throws Exception {
        // Initialize the database
        cicdMetadataRepository.saveAndFlush(cicdMetadata);

        // Get the cicdMetadata
        restCicdMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, cicdMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cicdMetadata.getId().intValue()))
            .andExpect(jsonPath("$.repositoryType").value(DEFAULT_REPOSITORY_TYPE))
            .andExpect(jsonPath("$.repositoryName").value(DEFAULT_REPOSITORY_NAME))
            .andExpect(jsonPath("$.ciName").value(DEFAULT_CI_NAME))
            .andExpect(jsonPath("$.ciUrl").value(DEFAULT_CI_URL))
            .andExpect(jsonPath("$.buildPkg").value(DEFAULT_BUILD_PKG))
            .andExpect(jsonPath("$.mirrorRepository").value(DEFAULT_MIRROR_REPOSITORY))
            .andExpect(jsonPath("$.cdType").value(DEFAULT_CD_TYPE.toString()))
            .andExpect(jsonPath("$.autoIp").value(DEFAULT_AUTO_IP))
            .andExpect(jsonPath("$.autoPort").value(DEFAULT_AUTO_PORT))
            .andExpect(jsonPath("$.autoUser").value(DEFAULT_AUTO_USER))
            .andExpect(jsonPath("$.autoPwd").value(DEFAULT_AUTO_PWD))
            .andExpect(jsonPath("$.autoKey").value(DEFAULT_AUTO_KEY.toString()))
            .andExpect(jsonPath("$.deploy").value(DEFAULT_DEPLOY))
            .andExpect(jsonPath("$.rentId").value(DEFAULT_RENT_ID))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME));
    }

    @Test
    @Transactional
    void getNonExistingCicdMetadata() throws Exception {
        // Get the cicdMetadata
        restCicdMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCicdMetadata() throws Exception {
        // Initialize the database
        cicdMetadataRepository.saveAndFlush(cicdMetadata);

        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();

        // Update the cicdMetadata
        CicdMetadata updatedCicdMetadata = cicdMetadataRepository.findById(cicdMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedCicdMetadata are not directly saved in db
        em.detach(updatedCicdMetadata);
        updatedCicdMetadata
            .repositoryType(UPDATED_REPOSITORY_TYPE)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .ciName(UPDATED_CI_NAME)
            .ciUrl(UPDATED_CI_URL)
            .buildPkg(UPDATED_BUILD_PKG)
            .mirrorRepository(UPDATED_MIRROR_REPOSITORY)
            .cdType(UPDATED_CD_TYPE)
            .autoIp(UPDATED_AUTO_IP)
            .autoPort(UPDATED_AUTO_PORT)
            .autoUser(UPDATED_AUTO_USER)
            .autoPwd(UPDATED_AUTO_PWD)
            .autoKey(UPDATED_AUTO_KEY)
            .deploy(UPDATED_DEPLOY)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restCicdMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCicdMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCicdMetadata))
            )
            .andExpect(status().isOk());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
        CicdMetadata testCicdMetadata = cicdMetadataList.get(cicdMetadataList.size() - 1);
        assertThat(testCicdMetadata.getRepositoryType()).isEqualTo(UPDATED_REPOSITORY_TYPE);
        assertThat(testCicdMetadata.getRepositoryName()).isEqualTo(UPDATED_REPOSITORY_NAME);
        assertThat(testCicdMetadata.getCiName()).isEqualTo(UPDATED_CI_NAME);
        assertThat(testCicdMetadata.getCiUrl()).isEqualTo(UPDATED_CI_URL);
        assertThat(testCicdMetadata.getBuildPkg()).isEqualTo(UPDATED_BUILD_PKG);
        assertThat(testCicdMetadata.getMirrorRepository()).isEqualTo(UPDATED_MIRROR_REPOSITORY);
        assertThat(testCicdMetadata.getCdType()).isEqualTo(UPDATED_CD_TYPE);
        assertThat(testCicdMetadata.getAutoIp()).isEqualTo(UPDATED_AUTO_IP);
        assertThat(testCicdMetadata.getAutoPort()).isEqualTo(UPDATED_AUTO_PORT);
        assertThat(testCicdMetadata.getAutoUser()).isEqualTo(UPDATED_AUTO_USER);
        assertThat(testCicdMetadata.getAutoPwd()).isEqualTo(UPDATED_AUTO_PWD);
        assertThat(testCicdMetadata.getAutoKey()).isEqualTo(UPDATED_AUTO_KEY);
        assertThat(testCicdMetadata.getDeploy()).isEqualTo(UPDATED_DEPLOY);
        assertThat(testCicdMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testCicdMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCicdMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCicdMetadata() throws Exception {
        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();
        cicdMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCicdMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cicdMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCicdMetadata() throws Exception {
        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();
        cicdMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCicdMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCicdMetadata() throws Exception {
        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();
        cicdMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCicdMetadataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCicdMetadataWithPatch() throws Exception {
        // Initialize the database
        cicdMetadataRepository.saveAndFlush(cicdMetadata);

        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();

        // Update the cicdMetadata using partial update
        CicdMetadata partialUpdatedCicdMetadata = new CicdMetadata();
        partialUpdatedCicdMetadata.setId(cicdMetadata.getId());

        partialUpdatedCicdMetadata
            .repositoryType(UPDATED_REPOSITORY_TYPE)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .ciUrl(UPDATED_CI_URL)
            .mirrorRepository(UPDATED_MIRROR_REPOSITORY)
            .autoUser(UPDATED_AUTO_USER)
            .autoKey(UPDATED_AUTO_KEY)
            .deploy(UPDATED_DEPLOY)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restCicdMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCicdMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCicdMetadata))
            )
            .andExpect(status().isOk());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
        CicdMetadata testCicdMetadata = cicdMetadataList.get(cicdMetadataList.size() - 1);
        assertThat(testCicdMetadata.getRepositoryType()).isEqualTo(UPDATED_REPOSITORY_TYPE);
        assertThat(testCicdMetadata.getRepositoryName()).isEqualTo(UPDATED_REPOSITORY_NAME);
        assertThat(testCicdMetadata.getCiName()).isEqualTo(DEFAULT_CI_NAME);
        assertThat(testCicdMetadata.getCiUrl()).isEqualTo(UPDATED_CI_URL);
        assertThat(testCicdMetadata.getBuildPkg()).isEqualTo(DEFAULT_BUILD_PKG);
        assertThat(testCicdMetadata.getMirrorRepository()).isEqualTo(UPDATED_MIRROR_REPOSITORY);
        assertThat(testCicdMetadata.getCdType()).isEqualTo(DEFAULT_CD_TYPE);
        assertThat(testCicdMetadata.getAutoIp()).isEqualTo(DEFAULT_AUTO_IP);
        assertThat(testCicdMetadata.getAutoPort()).isEqualTo(DEFAULT_AUTO_PORT);
        assertThat(testCicdMetadata.getAutoUser()).isEqualTo(UPDATED_AUTO_USER);
        assertThat(testCicdMetadata.getAutoPwd()).isEqualTo(DEFAULT_AUTO_PWD);
        assertThat(testCicdMetadata.getAutoKey()).isEqualTo(UPDATED_AUTO_KEY);
        assertThat(testCicdMetadata.getDeploy()).isEqualTo(UPDATED_DEPLOY);
        assertThat(testCicdMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testCicdMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCicdMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCicdMetadataWithPatch() throws Exception {
        // Initialize the database
        cicdMetadataRepository.saveAndFlush(cicdMetadata);

        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();

        // Update the cicdMetadata using partial update
        CicdMetadata partialUpdatedCicdMetadata = new CicdMetadata();
        partialUpdatedCicdMetadata.setId(cicdMetadata.getId());

        partialUpdatedCicdMetadata
            .repositoryType(UPDATED_REPOSITORY_TYPE)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .ciName(UPDATED_CI_NAME)
            .ciUrl(UPDATED_CI_URL)
            .buildPkg(UPDATED_BUILD_PKG)
            .mirrorRepository(UPDATED_MIRROR_REPOSITORY)
            .cdType(UPDATED_CD_TYPE)
            .autoIp(UPDATED_AUTO_IP)
            .autoPort(UPDATED_AUTO_PORT)
            .autoUser(UPDATED_AUTO_USER)
            .autoPwd(UPDATED_AUTO_PWD)
            .autoKey(UPDATED_AUTO_KEY)
            .deploy(UPDATED_DEPLOY)
            .rentId(UPDATED_RENT_ID)
            .updateTime(UPDATED_UPDATE_TIME)
            .createTime(UPDATED_CREATE_TIME);

        restCicdMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCicdMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCicdMetadata))
            )
            .andExpect(status().isOk());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
        CicdMetadata testCicdMetadata = cicdMetadataList.get(cicdMetadataList.size() - 1);
        assertThat(testCicdMetadata.getRepositoryType()).isEqualTo(UPDATED_REPOSITORY_TYPE);
        assertThat(testCicdMetadata.getRepositoryName()).isEqualTo(UPDATED_REPOSITORY_NAME);
        assertThat(testCicdMetadata.getCiName()).isEqualTo(UPDATED_CI_NAME);
        assertThat(testCicdMetadata.getCiUrl()).isEqualTo(UPDATED_CI_URL);
        assertThat(testCicdMetadata.getBuildPkg()).isEqualTo(UPDATED_BUILD_PKG);
        assertThat(testCicdMetadata.getMirrorRepository()).isEqualTo(UPDATED_MIRROR_REPOSITORY);
        assertThat(testCicdMetadata.getCdType()).isEqualTo(UPDATED_CD_TYPE);
        assertThat(testCicdMetadata.getAutoIp()).isEqualTo(UPDATED_AUTO_IP);
        assertThat(testCicdMetadata.getAutoPort()).isEqualTo(UPDATED_AUTO_PORT);
        assertThat(testCicdMetadata.getAutoUser()).isEqualTo(UPDATED_AUTO_USER);
        assertThat(testCicdMetadata.getAutoPwd()).isEqualTo(UPDATED_AUTO_PWD);
        assertThat(testCicdMetadata.getAutoKey()).isEqualTo(UPDATED_AUTO_KEY);
        assertThat(testCicdMetadata.getDeploy()).isEqualTo(UPDATED_DEPLOY);
        assertThat(testCicdMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testCicdMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCicdMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCicdMetadata() throws Exception {
        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();
        cicdMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCicdMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cicdMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCicdMetadata() throws Exception {
        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();
        cicdMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCicdMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCicdMetadata() throws Exception {
        int databaseSizeBeforeUpdate = cicdMetadataRepository.findAll().size();
        cicdMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCicdMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cicdMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CicdMetadata in the database
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCicdMetadata() throws Exception {
        // Initialize the database
        cicdMetadataRepository.saveAndFlush(cicdMetadata);

        int databaseSizeBeforeDelete = cicdMetadataRepository.findAll().size();

        // Delete the cicdMetadata
        restCicdMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, cicdMetadata.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CicdMetadata> cicdMetadataList = cicdMetadataRepository.findAll();
        assertThat(cicdMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
