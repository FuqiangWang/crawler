package com.huawei.apaas.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.huawei.apaas.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CicdMetadataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicdMetadata.class);
        CicdMetadata cicdMetadata1 = new CicdMetadata();
        cicdMetadata1.setId(1L);
        CicdMetadata cicdMetadata2 = new CicdMetadata();
        cicdMetadata2.setId(cicdMetadata1.getId());
        assertThat(cicdMetadata1).isEqualTo(cicdMetadata2);
        cicdMetadata2.setId(2L);
        assertThat(cicdMetadata1).isNotEqualTo(cicdMetadata2);
        cicdMetadata1.setId(null);
        assertThat(cicdMetadata1).isNotEqualTo(cicdMetadata2);
    }
}
