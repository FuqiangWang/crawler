package com.huawei.apaas.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.huawei.apaas.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountConfig.class);
        AccountConfig accountConfig1 = new AccountConfig();
        accountConfig1.setId(1L);
        AccountConfig accountConfig2 = new AccountConfig();
        accountConfig2.setId(accountConfig1.getId());
        assertThat(accountConfig1).isEqualTo(accountConfig2);
        accountConfig2.setId(2L);
        assertThat(accountConfig1).isNotEqualTo(accountConfig2);
        accountConfig1.setId(null);
        assertThat(accountConfig1).isNotEqualTo(accountConfig2);
    }
}
