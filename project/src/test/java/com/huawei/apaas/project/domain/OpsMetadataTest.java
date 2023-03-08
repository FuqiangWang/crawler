package com.huawei.apaas.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.huawei.apaas.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpsMetadataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpsMetadata.class);
        OpsMetadata opsMetadata1 = new OpsMetadata();
        opsMetadata1.setId(1L);
        OpsMetadata opsMetadata2 = new OpsMetadata();
        opsMetadata2.setId(opsMetadata1.getId());
        assertThat(opsMetadata1).isEqualTo(opsMetadata2);
        opsMetadata2.setId(2L);
        assertThat(opsMetadata1).isNotEqualTo(opsMetadata2);
        opsMetadata1.setId(null);
        assertThat(opsMetadata1).isNotEqualTo(opsMetadata2);
    }
}
