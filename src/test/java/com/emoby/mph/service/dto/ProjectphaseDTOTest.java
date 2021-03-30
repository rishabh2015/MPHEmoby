package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class ProjectphaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectphaseDTO.class);
        ProjectphaseDTO projectphaseDTO1 = new ProjectphaseDTO();
        projectphaseDTO1.setId(1L);
        ProjectphaseDTO projectphaseDTO2 = new ProjectphaseDTO();
        assertThat(projectphaseDTO1).isNotEqualTo(projectphaseDTO2);
        projectphaseDTO2.setId(projectphaseDTO1.getId());
        assertThat(projectphaseDTO1).isEqualTo(projectphaseDTO2);
        projectphaseDTO2.setId(2L);
        assertThat(projectphaseDTO1).isNotEqualTo(projectphaseDTO2);
        projectphaseDTO1.setId(null);
        assertThat(projectphaseDTO1).isNotEqualTo(projectphaseDTO2);
    }
}
