package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class JobdescriptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jobdescription.class);
        Jobdescription jobdescription1 = new Jobdescription();
        jobdescription1.setId(1L);
        Jobdescription jobdescription2 = new Jobdescription();
        jobdescription2.setId(jobdescription1.getId());
        assertThat(jobdescription1).isEqualTo(jobdescription2);
        jobdescription2.setId(2L);
        assertThat(jobdescription1).isNotEqualTo(jobdescription2);
        jobdescription1.setId(null);
        assertThat(jobdescription1).isNotEqualTo(jobdescription2);
    }
}
