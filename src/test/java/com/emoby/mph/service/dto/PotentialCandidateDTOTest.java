package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class PotentialCandidateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PotentialCandidateDTO.class);
        PotentialCandidateDTO potentialCandidateDTO1 = new PotentialCandidateDTO();
        potentialCandidateDTO1.setId(1L);
        PotentialCandidateDTO potentialCandidateDTO2 = new PotentialCandidateDTO();
        assertThat(potentialCandidateDTO1).isNotEqualTo(potentialCandidateDTO2);
        potentialCandidateDTO2.setId(potentialCandidateDTO1.getId());
        assertThat(potentialCandidateDTO1).isEqualTo(potentialCandidateDTO2);
        potentialCandidateDTO2.setId(2L);
        assertThat(potentialCandidateDTO1).isNotEqualTo(potentialCandidateDTO2);
        potentialCandidateDTO1.setId(null);
        assertThat(potentialCandidateDTO1).isNotEqualTo(potentialCandidateDTO2);
    }
}
