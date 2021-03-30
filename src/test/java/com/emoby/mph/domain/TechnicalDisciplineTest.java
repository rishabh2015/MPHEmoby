package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class TechnicalDisciplineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnicalDiscipline.class);
        TechnicalDiscipline technicalDiscipline1 = new TechnicalDiscipline();
        technicalDiscipline1.setId(1L);
        TechnicalDiscipline technicalDiscipline2 = new TechnicalDiscipline();
        technicalDiscipline2.setId(technicalDiscipline1.getId());
        assertThat(technicalDiscipline1).isEqualTo(technicalDiscipline2);
        technicalDiscipline2.setId(2L);
        assertThat(technicalDiscipline1).isNotEqualTo(technicalDiscipline2);
        technicalDiscipline1.setId(null);
        assertThat(technicalDiscipline1).isNotEqualTo(technicalDiscipline2);
    }
}
