package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class ProjectphaseActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectphaseActivity.class);
        ProjectphaseActivity projectphaseActivity1 = new ProjectphaseActivity();
        projectphaseActivity1.setId(1L);
        ProjectphaseActivity projectphaseActivity2 = new ProjectphaseActivity();
        projectphaseActivity2.setId(projectphaseActivity1.getId());
        assertThat(projectphaseActivity1).isEqualTo(projectphaseActivity2);
        projectphaseActivity2.setId(2L);
        assertThat(projectphaseActivity1).isNotEqualTo(projectphaseActivity2);
        projectphaseActivity1.setId(null);
        assertThat(projectphaseActivity1).isNotEqualTo(projectphaseActivity2);
    }
}
