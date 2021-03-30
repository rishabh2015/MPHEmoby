package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class CandidateLevelLanguageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateLevelLanguageDTO.class);
        CandidateLevelLanguageDTO candidateLevelLanguageDTO1 = new CandidateLevelLanguageDTO();
        candidateLevelLanguageDTO1.setId(1L);
        CandidateLevelLanguageDTO candidateLevelLanguageDTO2 = new CandidateLevelLanguageDTO();
        assertThat(candidateLevelLanguageDTO1).isNotEqualTo(candidateLevelLanguageDTO2);
        candidateLevelLanguageDTO2.setId(candidateLevelLanguageDTO1.getId());
        assertThat(candidateLevelLanguageDTO1).isEqualTo(candidateLevelLanguageDTO2);
        candidateLevelLanguageDTO2.setId(2L);
        assertThat(candidateLevelLanguageDTO1).isNotEqualTo(candidateLevelLanguageDTO2);
        candidateLevelLanguageDTO1.setId(null);
        assertThat(candidateLevelLanguageDTO1).isNotEqualTo(candidateLevelLanguageDTO2);
    }
}
