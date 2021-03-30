package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SubsectorMapperTest {

    private SubsectorMapper subsectorMapper;

    @BeforeEach
    public void setUp() {
        subsectorMapper = new SubsectorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(subsectorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(subsectorMapper.fromId(null)).isNull();
    }
}
