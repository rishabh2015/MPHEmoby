package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class EducationlevelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationlevelDTO.class);
        EducationlevelDTO educationlevelDTO1 = new EducationlevelDTO();
        educationlevelDTO1.setId(1L);
        EducationlevelDTO educationlevelDTO2 = new EducationlevelDTO();
        assertThat(educationlevelDTO1).isNotEqualTo(educationlevelDTO2);
        educationlevelDTO2.setId(educationlevelDTO1.getId());
        assertThat(educationlevelDTO1).isEqualTo(educationlevelDTO2);
        educationlevelDTO2.setId(2L);
        assertThat(educationlevelDTO1).isNotEqualTo(educationlevelDTO2);
        educationlevelDTO1.setId(null);
        assertThat(educationlevelDTO1).isNotEqualTo(educationlevelDTO2);
    }
}
