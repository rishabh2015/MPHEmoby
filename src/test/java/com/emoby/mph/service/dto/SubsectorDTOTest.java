package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class SubsectorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubsectorDTO.class);
        SubsectorDTO subsectorDTO1 = new SubsectorDTO();
        subsectorDTO1.setId(1L);
        SubsectorDTO subsectorDTO2 = new SubsectorDTO();
        assertThat(subsectorDTO1).isNotEqualTo(subsectorDTO2);
        subsectorDTO2.setId(subsectorDTO1.getId());
        assertThat(subsectorDTO1).isEqualTo(subsectorDTO2);
        subsectorDTO2.setId(2L);
        assertThat(subsectorDTO1).isNotEqualTo(subsectorDTO2);
        subsectorDTO1.setId(null);
        assertThat(subsectorDTO1).isNotEqualTo(subsectorDTO2);
    }
}
