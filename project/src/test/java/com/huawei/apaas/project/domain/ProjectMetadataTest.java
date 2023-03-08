package com.huawei.apaas.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.huawei.apaas.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectMetadataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectMetadata.class);
        ProjectMetadata projectMetadata1 = new ProjectMetadata();
        projectMetadata1.setId(1L);
        ProjectMetadata projectMetadata2 = new ProjectMetadata();
        projectMetadata2.setId(projectMetadata1.getId());
        assertThat(projectMetadata1).isEqualTo(projectMetadata2);
        projectMetadata2.setId(2L);
        assertThat(projectMetadata1).isNotEqualTo(projectMetadata2);
        projectMetadata1.setId(null);
        assertThat(projectMetadata1).isNotEqualTo(projectMetadata2);
    }
}
