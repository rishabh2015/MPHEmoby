package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CandidateLevelLanguageMapperTest {

    private CandidateLevelLanguageMapper candidateLevelLanguageMapper;

    @BeforeEach
    public void setUp() {
        candidateLevelLanguageMapper = new CandidateLevelLanguageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(candidateLevelLanguageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(candidateLevelLanguageMapper.fromId(null)).isNull();
    }
}
