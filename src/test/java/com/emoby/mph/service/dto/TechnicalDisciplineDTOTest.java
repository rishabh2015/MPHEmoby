package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class TechnicalDisciplineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnicalDisciplineDTO.class);
        TechnicalDisciplineDTO technicalDisciplineDTO1 = new TechnicalDisciplineDTO();
        technicalDisciplineDTO1.setId(1L);
        TechnicalDisciplineDTO technicalDisciplineDTO2 = new TechnicalDisciplineDTO();
        assertThat(technicalDisciplineDTO1).isNotEqualTo(technicalDisciplineDTO2);
        technicalDisciplineDTO2.setId(technicalDisciplineDTO1.getId());
        assertThat(technicalDisciplineDTO1).isEqualTo(technicalDisciplineDTO2);
        technicalDisciplineDTO2.setId(2L);
        assertThat(technicalDisciplineDTO1).isNotEqualTo(technicalDisciplineDTO2);
        technicalDisciplineDTO1.setId(null);
        assertThat(technicalDisciplineDTO1).isNotEqualTo(technicalDisciplineDTO2);
    }
}
