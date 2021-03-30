package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class ProjectphaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectphase.class);
        Projectphase projectphase1 = new Projectphase();
        projectphase1.setId(1L);
        Projectphase projectphase2 = new Projectphase();
        projectphase2.setId(projectphase1.getId());
        assertThat(projectphase1).isEqualTo(projectphase2);
        projectphase2.setId(2L);
        assertThat(projectphase1).isNotEqualTo(projectphase2);
        projectphase1.setId(null);
        assertThat(projectphase1).isNotEqualTo(projectphase2);
    }
}
