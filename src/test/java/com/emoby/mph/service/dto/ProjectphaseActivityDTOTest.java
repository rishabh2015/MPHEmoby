package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class ProjectphaseActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectphaseActivityDTO.class);
        ProjectphaseActivityDTO projectphaseActivityDTO1 = new ProjectphaseActivityDTO();
        projectphaseActivityDTO1.setId(1L);
        ProjectphaseActivityDTO projectphaseActivityDTO2 = new ProjectphaseActivityDTO();
        assertThat(projectphaseActivityDTO1).isNotEqualTo(projectphaseActivityDTO2);
        projectphaseActivityDTO2.setId(projectphaseActivityDTO1.getId());
        assertThat(projectphaseActivityDTO1).isEqualTo(projectphaseActivityDTO2);
        projectphaseActivityDTO2.setId(2L);
        assertThat(projectphaseActivityDTO1).isNotEqualTo(projectphaseActivityDTO2);
        projectphaseActivityDTO1.setId(null);
        assertThat(projectphaseActivityDTO1).isNotEqualTo(projectphaseActivityDTO2);
    }
}
