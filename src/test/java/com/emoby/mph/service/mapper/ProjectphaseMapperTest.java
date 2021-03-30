package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectphaseMapperTest {

    private ProjectphaseMapper projectphaseMapper;

    @BeforeEach
    public void setUp() {
        projectphaseMapper = new ProjectphaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projectphaseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectphaseMapper.fromId(null)).isNull();
    }
}
