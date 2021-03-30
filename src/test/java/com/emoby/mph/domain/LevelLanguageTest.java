package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class LevelLanguageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelLanguage.class);
        LevelLanguage levelLanguage1 = new LevelLanguage();
        levelLanguage1.setId(1L);
        LevelLanguage levelLanguage2 = new LevelLanguage();
        levelLanguage2.setId(levelLanguage1.getId());
        assertThat(levelLanguage1).isEqualTo(levelLanguage2);
        levelLanguage2.setId(2L);
        assertThat(levelLanguage1).isNotEqualTo(levelLanguage2);
        levelLanguage1.setId(null);
        assertThat(levelLanguage1).isNotEqualTo(levelLanguage2);
    }
}
