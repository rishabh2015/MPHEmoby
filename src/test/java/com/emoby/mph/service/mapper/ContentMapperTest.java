package com.emoby.mph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentMapperTest {

    private ContentMapper contentMapper;

    @BeforeEach
    public void setUp() {
        contentMapper = new ContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contentMapper.fromId(null)).isNull();
    }
}
