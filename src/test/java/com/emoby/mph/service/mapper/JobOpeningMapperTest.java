package com.emoby.mph.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobOpeningMapperTest {

    private JobOpeningMapper jobOpeningMapper;

    @BeforeEach
    public void setUp() {
        jobOpeningMapper = new JobOpeningMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobOpeningMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobOpeningMapper.fromId(null)).isNull();
    }
}
