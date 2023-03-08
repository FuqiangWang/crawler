package com.huawei.apaas.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.huawei.apaas.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArcTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArcTemplate.class);
        ArcTemplate arcTemplate1 = new ArcTemplate();
        arcTemplate1.setId(1L);
        ArcTemplate arcTemplate2 = new ArcTemplate();
        arcTemplate2.setId(arcTemplate1.getId());
        assertThat(arcTemplate1).isEqualTo(arcTemplate2);
        arcTemplate2.setId(2L);
        assertThat(arcTemplate1).isNotEqualTo(arcTemplate2);
        arcTemplate1.setId(null);
        assertThat(arcTemplate1).isNotEqualTo(arcTemplate2);
    }
}
