package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class JobOpeningDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOpeningDTO.class);
        JobOpeningDTO jobOpeningDTO1 = new JobOpeningDTO();
        jobOpeningDTO1.setId(1L);
        JobOpeningDTO jobOpeningDTO2 = new JobOpeningDTO();
        assertThat(jobOpeningDTO1).isNotEqualTo(jobOpeningDTO2);
        jobOpeningDTO2.setId(jobOpeningDTO1.getId());
        assertThat(jobOpeningDTO1).isEqualTo(jobOpeningDTO2);
        jobOpeningDTO2.setId(2L);
        assertThat(jobOpeningDTO1).isNotEqualTo(jobOpeningDTO2);
        jobOpeningDTO1.setId(null);
        assertThat(jobOpeningDTO1).isNotEqualTo(jobOpeningDTO2);
    }
}
