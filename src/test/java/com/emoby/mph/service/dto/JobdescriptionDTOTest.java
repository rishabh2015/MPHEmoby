package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class JobdescriptionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobdescriptionDTO.class);
        JobdescriptionDTO jobdescriptionDTO1 = new JobdescriptionDTO();
        jobdescriptionDTO1.setId(1L);
        JobdescriptionDTO jobdescriptionDTO2 = new JobdescriptionDTO();
        assertThat(jobdescriptionDTO1).isNotEqualTo(jobdescriptionDTO2);
        jobdescriptionDTO2.setId(jobdescriptionDTO1.getId());
        assertThat(jobdescriptionDTO1).isEqualTo(jobdescriptionDTO2);
        jobdescriptionDTO2.setId(2L);
        assertThat(jobdescriptionDTO1).isNotEqualTo(jobdescriptionDTO2);
        jobdescriptionDTO1.setId(null);
        assertThat(jobdescriptionDTO1).isNotEqualTo(jobdescriptionDTO2);
    }
}
