package com.huawei.apaas.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.huawei.apaas.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArcMetadataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArcMetadata.class);
        ArcMetadata arcMetadata1 = new ArcMetadata();
        arcMetadata1.setId(1L);
        ArcMetadata arcMetadata2 = new ArcMetadata();
        arcMetadata2.setId(arcMetadata1.getId());
        assertThat(arcMetadata1).isEqualTo(arcMetadata2);
        arcMetadata2.setId(2L);
        assertThat(arcMetadata1).isNotEqualTo(arcMetadata2);
        arcMetadata1.setId(null);
        assertThat(arcMetadata1).isNotEqualTo(arcMetadata2);
    }
}
