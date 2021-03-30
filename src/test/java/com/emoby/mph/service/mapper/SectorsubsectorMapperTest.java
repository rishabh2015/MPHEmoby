package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SectorsubsectorMapperTest {

    private SectorsubsectorMapper sectorsubsectorMapper;

    @BeforeEach
    public void setUp() {
        sectorsubsectorMapper = new SectorsubsectorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sectorsubsectorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sectorsubsectorMapper.fromId(null)).isNull();
    }
}
