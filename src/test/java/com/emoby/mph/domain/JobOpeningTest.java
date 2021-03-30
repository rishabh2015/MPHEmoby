package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class JobOpeningTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOpening.class);
        JobOpening jobOpening1 = new JobOpening();
        jobOpening1.setId(1L);
        JobOpening jobOpening2 = new JobOpening();
        jobOpening2.setId(jobOpening1.getId());
        assertThat(jobOpening1).isEqualTo(jobOpening2);
        jobOpening2.setId(2L);
        assertThat(jobOpening1).isNotEqualTo(jobOpening2);
        jobOpening1.setId(null);
        assertThat(jobOpening1).isNotEqualTo(jobOpening2);
    }
}
