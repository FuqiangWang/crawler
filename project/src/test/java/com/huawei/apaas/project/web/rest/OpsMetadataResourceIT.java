package com.huawei.apaas.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huawei.apaas.project.IntegrationTest;
import com.huawei.apaas.project.domain.OpsMetadata;
import com.huawei.apaas.project.repository.OpsMetadataRepository;
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
 * Integration tests for the {@link OpsMetadataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpsMetadataResourceIT {

    private static final String DEFAULT_OPS_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_OPS_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_RENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ops-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpsMetadataRepository opsMetadataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpsMetadataMockMvc;

    private OpsMetadata opsMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpsMetadata createEntity(EntityManager em) {
        OpsMetadata opsMetadata = new OpsMetadata()
            .opsSystem(DEFAULT_OPS_SYSTEM)
            .rentId(DEFAULT_RENT_ID)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return opsMetadata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpsMetadata createUpdatedEntity(EntityManager em) {
        OpsMetadata opsMetadata = new OpsMetadata()
            .opsSystem(UPDATED_OPS_SYSTEM)
            .rentId(UPDATED_RENT_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return opsMetadata;
    }

    @BeforeEach
    public void initTest() {
        opsMetadata = createEntity(em);
    }

    @Test
    @Transactional
    void createOpsMetadata() throws Exception {
        int databaseSizeBeforeCreate = opsMetadataRepository.findAll().size();
        // Create the OpsMetadata
        restOpsMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isCreated());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        OpsMetadata testOpsMetadata = opsMetadataList.get(opsMetadataList.size() - 1);
        assertThat(testOpsMetadata.getOpsSystem()).isEqualTo(DEFAULT_OPS_SYSTEM);
        assertThat(testOpsMetadata.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testOpsMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testOpsMetadata.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void createOpsMetadataWithExistingId() throws Exception {
        // Create the OpsMetadata with an existing ID
        opsMetadata.setId(1L);

        int databaseSizeBeforeCreate = opsMetadataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpsMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOpsSystemIsRequired() throws Exception {
        int databaseSizeBeforeTest = opsMetadataRepository.findAll().size();
        // set the field null
        opsMetadata.setOpsSystem(null);

        // Create the OpsMetadata, which fails.

        restOpsMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = opsMetadataRepository.findAll().size();
        // set the field null
        opsMetadata.setCreateTime(null);

        // Create the OpsMetadata, which fails.

        restOpsMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = opsMetadataRepository.findAll().size();
        // set the field null
        opsMetadata.setUpdateTime(null);

        // Create the OpsMetadata, which fails.

        restOpsMetadataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOpsMetadata() throws Exception {
        // Initialize the database
        opsMetadataRepository.saveAndFlush(opsMetadata);

        // Get all the opsMetadataList
        restOpsMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opsMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].opsSystem").value(hasItem(DEFAULT_OPS_SYSTEM)))
            .andExpect(jsonPath("$.[*].rentId").value(hasItem(DEFAULT_RENT_ID)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    void getOpsMetadata() throws Exception {
        // Initialize the database
        opsMetadataRepository.saveAndFlush(opsMetadata);

        // Get the opsMetadata
        restOpsMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, opsMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opsMetadata.getId().intValue()))
            .andExpect(jsonPath("$.opsSystem").value(DEFAULT_OPS_SYSTEM))
            .andExpect(jsonPath("$.rentId").value(DEFAULT_RENT_ID))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME));
    }

    @Test
    @Transactional
    void getNonExistingOpsMetadata() throws Exception {
        // Get the opsMetadata
        restOpsMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpsMetadata() throws Exception {
        // Initialize the database
        opsMetadataRepository.saveAndFlush(opsMetadata);

        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();

        // Update the opsMetadata
        OpsMetadata updatedOpsMetadata = opsMetadataRepository.findById(opsMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedOpsMetadata are not directly saved in db
        em.detach(updatedOpsMetadata);
        updatedOpsMetadata
            .opsSystem(UPDATED_OPS_SYSTEM)
            .rentId(UPDATED_RENT_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restOpsMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpsMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOpsMetadata))
            )
            .andExpect(status().isOk());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
        OpsMetadata testOpsMetadata = opsMetadataList.get(opsMetadataList.size() - 1);
        assertThat(testOpsMetadata.getOpsSystem()).isEqualTo(UPDATED_OPS_SYSTEM);
        assertThat(testOpsMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testOpsMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testOpsMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingOpsMetadata() throws Exception {
        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();
        opsMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpsMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opsMetadata.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpsMetadata() throws Exception {
        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();
        opsMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpsMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpsMetadata() throws Exception {
        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();
        opsMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpsMetadataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpsMetadataWithPatch() throws Exception {
        // Initialize the database
        opsMetadataRepository.saveAndFlush(opsMetadata);

        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();

        // Update the opsMetadata using partial update
        OpsMetadata partialUpdatedOpsMetadata = new OpsMetadata();
        partialUpdatedOpsMetadata.setId(opsMetadata.getId());

        partialUpdatedOpsMetadata.updateTime(UPDATED_UPDATE_TIME);

        restOpsMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpsMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpsMetadata))
            )
            .andExpect(status().isOk());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
        OpsMetadata testOpsMetadata = opsMetadataList.get(opsMetadataList.size() - 1);
        assertThat(testOpsMetadata.getOpsSystem()).isEqualTo(DEFAULT_OPS_SYSTEM);
        assertThat(testOpsMetadata.getRentId()).isEqualTo(DEFAULT_RENT_ID);
        assertThat(testOpsMetadata.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testOpsMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateOpsMetadataWithPatch() throws Exception {
        // Initialize the database
        opsMetadataRepository.saveAndFlush(opsMetadata);

        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();

        // Update the opsMetadata using partial update
        OpsMetadata partialUpdatedOpsMetadata = new OpsMetadata();
        partialUpdatedOpsMetadata.setId(opsMetadata.getId());

        partialUpdatedOpsMetadata
            .opsSystem(UPDATED_OPS_SYSTEM)
            .rentId(UPDATED_RENT_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restOpsMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpsMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpsMetadata))
            )
            .andExpect(status().isOk());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
        OpsMetadata testOpsMetadata = opsMetadataList.get(opsMetadataList.size() - 1);
        assertThat(testOpsMetadata.getOpsSystem()).isEqualTo(UPDATED_OPS_SYSTEM);
        assertThat(testOpsMetadata.getRentId()).isEqualTo(UPDATED_RENT_ID);
        assertThat(testOpsMetadata.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testOpsMetadata.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingOpsMetadata() throws Exception {
        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();
        opsMetadata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpsMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opsMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpsMetadata() throws Exception {
        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();
        opsMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpsMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpsMetadata() throws Exception {
        int databaseSizeBeforeUpdate = opsMetadataRepository.findAll().size();
        opsMetadata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpsMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opsMetadata))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpsMetadata in the database
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpsMetadata() throws Exception {
        // Initialize the database
        opsMetadataRepository.saveAndFlush(opsMetadata);

        int databaseSizeBeforeDelete = opsMetadataRepository.findAll().size();

        // Delete the opsMetadata
        restOpsMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, opsMetadata.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpsMetadata> opsMetadataList = opsMetadataRepository.findAll();
        assertThat(opsMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
