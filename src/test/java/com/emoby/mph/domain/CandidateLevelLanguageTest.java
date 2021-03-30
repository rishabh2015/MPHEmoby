package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class CandidateLevelLanguageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateLevelLanguage.class);
        CandidateLevelLanguage candidateLevelLanguage1 = new CandidateLevelLanguage();
        candidateLevelLanguage1.setId(1L);
        CandidateLevelLanguage candidateLevelLanguage2 = new CandidateLevelLanguage();
        candidateLevelLanguage2.setId(candidateLevelLanguage1.getId());
        assertThat(candidateLevelLanguage1).isEqualTo(candidateLevelLanguage2);
        candidateLevelLanguage2.setId(2L);
        assertThat(candidateLevelLanguage1).isNotEqualTo(candidateLevelLanguage2);
        candidateLevelLanguage1.setId(null);
        assertThat(candidateLevelLanguage1).isNotEqualTo(candidateLevelLanguage2);
    }
}
