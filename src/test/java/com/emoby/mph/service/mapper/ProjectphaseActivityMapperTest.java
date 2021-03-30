package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectphaseActivityMapperTest {

    private ProjectphaseActivityMapper projectphaseActivityMapper;

    @BeforeEach
    public void setUp() {
        projectphaseActivityMapper = new ProjectphaseActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projectphaseActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectphaseActivityMapper.fromId(null)).isNull();
    }
}
