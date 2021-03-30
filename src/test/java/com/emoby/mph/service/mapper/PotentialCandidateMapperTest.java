package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PotentialCandidateMapperTest {

    private PotentialCandidateMapper potentialCandidateMapper;

    @BeforeEach
    public void setUp() {
        potentialCandidateMapper = new PotentialCandidateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(potentialCandidateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(potentialCandidateMapper.fromId(null)).isNull();
    }
}
