package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class EducationlevelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Educationlevel.class);
        Educationlevel educationlevel1 = new Educationlevel();
        educationlevel1.setId(1L);
        Educationlevel educationlevel2 = new Educationlevel();
        educationlevel2.setId(educationlevel1.getId());
        assertThat(educationlevel1).isEqualTo(educationlevel2);
        educationlevel2.setId(2L);
        assertThat(educationlevel1).isNotEqualTo(educationlevel2);
        educationlevel1.setId(null);
        assertThat(educationlevel1).isNotEqualTo(educationlevel2);
    }
}
