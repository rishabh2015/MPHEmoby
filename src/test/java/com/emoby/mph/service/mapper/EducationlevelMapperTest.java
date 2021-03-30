package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EducationlevelMapperTest {

    private EducationlevelMapper educationlevelMapper;

    @BeforeEach
    public void setUp() {
        educationlevelMapper = new EducationlevelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(educationlevelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(educationlevelMapper.fromId(null)).isNull();
    }
}
