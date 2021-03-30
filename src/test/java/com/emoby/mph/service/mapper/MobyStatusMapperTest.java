package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MobyStatusMapperTest {

    private MobyStatusMapper mobyStatusMapper;

    @BeforeEach
    public void setUp() {
        mobyStatusMapper = new MobyStatusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mobyStatusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mobyStatusMapper.fromId(null)).isNull();
    }
}
