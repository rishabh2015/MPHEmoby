package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelLanguageMapperTest {

    private LevelLanguageMapper levelLanguageMapper;

    @BeforeEach
    public void setUp() {
        levelLanguageMapper = new LevelLanguageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(levelLanguageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(levelLanguageMapper.fromId(null)).isNull();
    }
}
