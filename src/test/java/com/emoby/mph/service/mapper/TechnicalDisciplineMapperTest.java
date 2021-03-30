package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TechnicalDisciplineMapperTest {

    private TechnicalDisciplineMapper technicalDisciplineMapper;

    @BeforeEach
    public void setUp() {
        technicalDisciplineMapper = new TechnicalDisciplineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(technicalDisciplineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(technicalDisciplineMapper.fromId(null)).isNull();
    }
}
