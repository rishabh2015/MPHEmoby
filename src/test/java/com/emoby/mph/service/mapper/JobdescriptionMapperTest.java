package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JobdescriptionMapperTest {

    private JobdescriptionMapper jobdescriptionMapper;

    @BeforeEach
    public void setUp() {
        jobdescriptionMapper = new JobdescriptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobdescriptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobdescriptionMapper.fromId(null)).isNull();
    }
}
