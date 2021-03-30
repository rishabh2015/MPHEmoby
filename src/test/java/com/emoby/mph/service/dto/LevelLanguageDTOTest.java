package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class LevelLanguageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelLanguageDTO.class);
        LevelLanguageDTO levelLanguageDTO1 = new LevelLanguageDTO();
        levelLanguageDTO1.setId(1L);
        LevelLanguageDTO levelLanguageDTO2 = new LevelLanguageDTO();
        assertThat(levelLanguageDTO1).isNotEqualTo(levelLanguageDTO2);
        levelLanguageDTO2.setId(levelLanguageDTO1.getId());
        assertThat(levelLanguageDTO1).isEqualTo(levelLanguageDTO2);
        levelLanguageDTO2.setId(2L);
        assertThat(levelLanguageDTO1).isNotEqualTo(levelLanguageDTO2);
        levelLanguageDTO1.setId(null);
        assertThat(levelLanguageDTO1).isNotEqualTo(levelLanguageDTO2);
    }
}
