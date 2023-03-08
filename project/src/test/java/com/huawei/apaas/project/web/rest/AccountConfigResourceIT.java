package com.huawei.apaas.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huawei.apaas.project.IntegrationTest;
import com.huawei.apaas.project.domain.AccountConfig;
import com.huawei.apaas.project.repository.AccountConfigRepository;
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
 * Integration tests for the {@link AccountConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountConfigResourceIT {

    private static final String DEFAULT_GIT_HUB_USER = "AAAAAAAAAA";
    private static final String UPDATED_GIT_HUB_USER = "BBBBBBBBBB";

    private static final String DEFAULT_GIT_HUB_PWD = "AAAAAAAAAA";
    private static final String UPDATED_GIT_HUB_PWD = "BBBBBBBBBB";

    private static final String DEFAULT_GIT_LAB_USER = "AAAAAAAAAA";
    private static final String UPDATED_GIT_LAB_USER = "BBBBBBBBBB";

    private static final String DEFAULT_GIT_LAB_PWD = "AAAAAAAAAA";
    private static final String UPDATED_GIT_LAB_PWD = "BBBBBBBBBB";

    private static final String DEFAULT_GITEE_USER = "AAAAAAAAAA";
    private static final String UPDATED_GITEE_USER = "BBBBBBBBBB";

    private static final String DEFAULT_GITEE_PWD = "AAAAAAAAAA";
    private static final String UPDATED_GITEE_PWD = "BBBBBBBBBB";

    private static final String DEFAULT_DOCKER_HUB_USER = "AAAAAAAAAA";
    private static final String UPDATED_DOCKER_HUB_USER = "BBBBBBBBBB";

    private static final String DEFAULT_DOCK_HUB_PWD = "AAAAAAAAAA";
    private static final String UPDATED_DOCK_HUB_PWD = "BBBBBBBBBB";

    private static final String DEFAULT_REND_ID = "AAAAAAAAAA";
    private static final String UPDATED_REND_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/account-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountConfigRepository accountConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountConfigMockMvc;

    private AccountConfig accountConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountConfig createEntity(EntityManager em) {
        AccountConfig accountConfig = new AccountConfig()
            .gitHubUser(DEFAULT_GIT_HUB_USER)
            .gitHubPwd(DEFAULT_GIT_HUB_PWD)
            .gitLabUser(DEFAULT_GIT_LAB_USER)
            .gitLabPwd(DEFAULT_GIT_LAB_PWD)
            .giteeUser(DEFAULT_GITEE_USER)
            .giteePwd(DEFAULT_GITEE_PWD)
            .dockerHubUser(DEFAULT_DOCKER_HUB_USER)
            .dockHubPwd(DEFAULT_DOCK_HUB_PWD)
            .rendId(DEFAULT_REND_ID);
        return accountConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountConfig createUpdatedEntity(EntityManager em) {
        AccountConfig accountConfig = new AccountConfig()
            .gitHubUser(UPDATED_GIT_HUB_USER)
            .gitHubPwd(UPDATED_GIT_HUB_PWD)
            .gitLabUser(UPDATED_GIT_LAB_USER)
            .gitLabPwd(UPDATED_GIT_LAB_PWD)
            .giteeUser(UPDATED_GITEE_USER)
            .giteePwd(UPDATED_GITEE_PWD)
            .dockerHubUser(UPDATED_DOCKER_HUB_USER)
            .dockHubPwd(UPDATED_DOCK_HUB_PWD)
            .rendId(UPDATED_REND_ID);
        return accountConfig;
    }

    @BeforeEach
    public void initTest() {
        accountConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountConfig() throws Exception {
        int databaseSizeBeforeCreate = accountConfigRepository.findAll().size();
        // Create the AccountConfig
        restAccountConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isCreated());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeCreate + 1);
        AccountConfig testAccountConfig = accountConfigList.get(accountConfigList.size() - 1);
        assertThat(testAccountConfig.getGitHubUser()).isEqualTo(DEFAULT_GIT_HUB_USER);
        assertThat(testAccountConfig.getGitHubPwd()).isEqualTo(DEFAULT_GIT_HUB_PWD);
        assertThat(testAccountConfig.getGitLabUser()).isEqualTo(DEFAULT_GIT_LAB_USER);
        assertThat(testAccountConfig.getGitLabPwd()).isEqualTo(DEFAULT_GIT_LAB_PWD);
        assertThat(testAccountConfig.getGiteeUser()).isEqualTo(DEFAULT_GITEE_USER);
        assertThat(testAccountConfig.getGiteePwd()).isEqualTo(DEFAULT_GITEE_PWD);
        assertThat(testAccountConfig.getDockerHubUser()).isEqualTo(DEFAULT_DOCKER_HUB_USER);
        assertThat(testAccountConfig.getDockHubPwd()).isEqualTo(DEFAULT_DOCK_HUB_PWD);
        assertThat(testAccountConfig.getRendId()).isEqualTo(DEFAULT_REND_ID);
    }

    @Test
    @Transactional
    void createAccountConfigWithExistingId() throws Exception {
        // Create the AccountConfig with an existing ID
        accountConfig.setId(1L);

        int databaseSizeBeforeCreate = accountConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRendIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountConfigRepository.findAll().size();
        // set the field null
        accountConfig.setRendId(null);

        // Create the AccountConfig, which fails.

        restAccountConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isBadRequest());

        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccountConfigs() throws Exception {
        // Initialize the database
        accountConfigRepository.saveAndFlush(accountConfig);

        // Get all the accountConfigList
        restAccountConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].gitHubUser").value(hasItem(DEFAULT_GIT_HUB_USER)))
            .andExpect(jsonPath("$.[*].gitHubPwd").value(hasItem(DEFAULT_GIT_HUB_PWD)))
            .andExpect(jsonPath("$.[*].gitLabUser").value(hasItem(DEFAULT_GIT_LAB_USER)))
            .andExpect(jsonPath("$.[*].gitLabPwd").value(hasItem(DEFAULT_GIT_LAB_PWD)))
            .andExpect(jsonPath("$.[*].giteeUser").value(hasItem(DEFAULT_GITEE_USER)))
            .andExpect(jsonPath("$.[*].giteePwd").value(hasItem(DEFAULT_GITEE_PWD)))
            .andExpect(jsonPath("$.[*].dockerHubUser").value(hasItem(DEFAULT_DOCKER_HUB_USER)))
            .andExpect(jsonPath("$.[*].dockHubPwd").value(hasItem(DEFAULT_DOCK_HUB_PWD)))
            .andExpect(jsonPath("$.[*].rendId").value(hasItem(DEFAULT_REND_ID)));
    }

    @Test
    @Transactional
    void getAccountConfig() throws Exception {
        // Initialize the database
        accountConfigRepository.saveAndFlush(accountConfig);

        // Get the accountConfig
        restAccountConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, accountConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountConfig.getId().intValue()))
            .andExpect(jsonPath("$.gitHubUser").value(DEFAULT_GIT_HUB_USER))
            .andExpect(jsonPath("$.gitHubPwd").value(DEFAULT_GIT_HUB_PWD))
            .andExpect(jsonPath("$.gitLabUser").value(DEFAULT_GIT_LAB_USER))
            .andExpect(jsonPath("$.gitLabPwd").value(DEFAULT_GIT_LAB_PWD))
            .andExpect(jsonPath("$.giteeUser").value(DEFAULT_GITEE_USER))
            .andExpect(jsonPath("$.giteePwd").value(DEFAULT_GITEE_PWD))
            .andExpect(jsonPath("$.dockerHubUser").value(DEFAULT_DOCKER_HUB_USER))
            .andExpect(jsonPath("$.dockHubPwd").value(DEFAULT_DOCK_HUB_PWD))
            .andExpect(jsonPath("$.rendId").value(DEFAULT_REND_ID));
    }

    @Test
    @Transactional
    void getNonExistingAccountConfig() throws Exception {
        // Get the accountConfig
        restAccountConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountConfig() throws Exception {
        // Initialize the database
        accountConfigRepository.saveAndFlush(accountConfig);

        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();

        // Update the accountConfig
        AccountConfig updatedAccountConfig = accountConfigRepository.findById(accountConfig.getId()).get();
        // Disconnect from session so that the updates on updatedAccountConfig are not directly saved in db
        em.detach(updatedAccountConfig);
        updatedAccountConfig
            .gitHubUser(UPDATED_GIT_HUB_USER)
            .gitHubPwd(UPDATED_GIT_HUB_PWD)
            .gitLabUser(UPDATED_GIT_LAB_USER)
            .gitLabPwd(UPDATED_GIT_LAB_PWD)
            .giteeUser(UPDATED_GITEE_USER)
            .giteePwd(UPDATED_GITEE_PWD)
            .dockerHubUser(UPDATED_DOCKER_HUB_USER)
            .dockHubPwd(UPDATED_DOCK_HUB_PWD)
            .rendId(UPDATED_REND_ID);

        restAccountConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccountConfig.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccountConfig))
            )
            .andExpect(status().isOk());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
        AccountConfig testAccountConfig = accountConfigList.get(accountConfigList.size() - 1);
        assertThat(testAccountConfig.getGitHubUser()).isEqualTo(UPDATED_GIT_HUB_USER);
        assertThat(testAccountConfig.getGitHubPwd()).isEqualTo(UPDATED_GIT_HUB_PWD);
        assertThat(testAccountConfig.getGitLabUser()).isEqualTo(UPDATED_GIT_LAB_USER);
        assertThat(testAccountConfig.getGitLabPwd()).isEqualTo(UPDATED_GIT_LAB_PWD);
        assertThat(testAccountConfig.getGiteeUser()).isEqualTo(UPDATED_GITEE_USER);
        assertThat(testAccountConfig.getGiteePwd()).isEqualTo(UPDATED_GITEE_PWD);
        assertThat(testAccountConfig.getDockerHubUser()).isEqualTo(UPDATED_DOCKER_HUB_USER);
        assertThat(testAccountConfig.getDockHubPwd()).isEqualTo(UPDATED_DOCK_HUB_PWD);
        assertThat(testAccountConfig.getRendId()).isEqualTo(UPDATED_REND_ID);
    }

    @Test
    @Transactional
    void putNonExistingAccountConfig() throws Exception {
        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();
        accountConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountConfig.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountConfig() throws Exception {
        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();
        accountConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountConfig() throws Exception {
        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();
        accountConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountConfigMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountConfigWithPatch() throws Exception {
        // Initialize the database
        accountConfigRepository.saveAndFlush(accountConfig);

        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();

        // Update the accountConfig using partial update
        AccountConfig partialUpdatedAccountConfig = new AccountConfig();
        partialUpdatedAccountConfig.setId(accountConfig.getId());

        partialUpdatedAccountConfig
            .gitLabUser(UPDATED_GIT_LAB_USER)
            .giteeUser(UPDATED_GITEE_USER)
            .giteePwd(UPDATED_GITEE_PWD)
            .dockerHubUser(UPDATED_DOCKER_HUB_USER)
            .dockHubPwd(UPDATED_DOCK_HUB_PWD)
            .rendId(UPDATED_REND_ID);

        restAccountConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountConfig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountConfig))
            )
            .andExpect(status().isOk());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
        AccountConfig testAccountConfig = accountConfigList.get(accountConfigList.size() - 1);
        assertThat(testAccountConfig.getGitHubUser()).isEqualTo(DEFAULT_GIT_HUB_USER);
        assertThat(testAccountConfig.getGitHubPwd()).isEqualTo(DEFAULT_GIT_HUB_PWD);
        assertThat(testAccountConfig.getGitLabUser()).isEqualTo(UPDATED_GIT_LAB_USER);
        assertThat(testAccountConfig.getGitLabPwd()).isEqualTo(DEFAULT_GIT_LAB_PWD);
        assertThat(testAccountConfig.getGiteeUser()).isEqualTo(UPDATED_GITEE_USER);
        assertThat(testAccountConfig.getGiteePwd()).isEqualTo(UPDATED_GITEE_PWD);
        assertThat(testAccountConfig.getDockerHubUser()).isEqualTo(UPDATED_DOCKER_HUB_USER);
        assertThat(testAccountConfig.getDockHubPwd()).isEqualTo(UPDATED_DOCK_HUB_PWD);
        assertThat(testAccountConfig.getRendId()).isEqualTo(UPDATED_REND_ID);
    }

    @Test
    @Transactional
    void fullUpdateAccountConfigWithPatch() throws Exception {
        // Initialize the database
        accountConfigRepository.saveAndFlush(accountConfig);

        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();

        // Update the accountConfig using partial update
        AccountConfig partialUpdatedAccountConfig = new AccountConfig();
        partialUpdatedAccountConfig.setId(accountConfig.getId());

        partialUpdatedAccountConfig
            .gitHubUser(UPDATED_GIT_HUB_USER)
            .gitHubPwd(UPDATED_GIT_HUB_PWD)
            .gitLabUser(UPDATED_GIT_LAB_USER)
            .gitLabPwd(UPDATED_GIT_LAB_PWD)
            .giteeUser(UPDATED_GITEE_USER)
            .giteePwd(UPDATED_GITEE_PWD)
            .dockerHubUser(UPDATED_DOCKER_HUB_USER)
            .dockHubPwd(UPDATED_DOCK_HUB_PWD)
            .rendId(UPDATED_REND_ID);

        restAccountConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountConfig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountConfig))
            )
            .andExpect(status().isOk());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
        AccountConfig testAccountConfig = accountConfigList.get(accountConfigList.size() - 1);
        assertThat(testAccountConfig.getGitHubUser()).isEqualTo(UPDATED_GIT_HUB_USER);
        assertThat(testAccountConfig.getGitHubPwd()).isEqualTo(UPDATED_GIT_HUB_PWD);
        assertThat(testAccountConfig.getGitLabUser()).isEqualTo(UPDATED_GIT_LAB_USER);
        assertThat(testAccountConfig.getGitLabPwd()).isEqualTo(UPDATED_GIT_LAB_PWD);
        assertThat(testAccountConfig.getGiteeUser()).isEqualTo(UPDATED_GITEE_USER);
        assertThat(testAccountConfig.getGiteePwd()).isEqualTo(UPDATED_GITEE_PWD);
        assertThat(testAccountConfig.getDockerHubUser()).isEqualTo(UPDATED_DOCKER_HUB_USER);
        assertThat(testAccountConfig.getDockHubPwd()).isEqualTo(UPDATED_DOCK_HUB_PWD);
        assertThat(testAccountConfig.getRendId()).isEqualTo(UPDATED_REND_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAccountConfig() throws Exception {
        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();
        accountConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountConfig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountConfig() throws Exception {
        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();
        accountConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountConfig() throws Exception {
        int databaseSizeBeforeUpdate = accountConfigRepository.findAll().size();
        accountConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountConfig))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountConfig in the database
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountConfig() throws Exception {
        // Initialize the database
        accountConfigRepository.saveAndFlush(accountConfig);

        int databaseSizeBeforeDelete = accountConfigRepository.findAll().size();

        // Delete the accountConfig
        restAccountConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountConfig.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountConfig> accountConfigList = accountConfigRepository.findAll();
        assertThat(accountConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
