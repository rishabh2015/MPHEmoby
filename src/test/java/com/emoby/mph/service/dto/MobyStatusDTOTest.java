package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class MobyStatusDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobyStatusDTO.class);
        MobyStatusDTO mobyStatusDTO1 = new MobyStatusDTO();
        mobyStatusDTO1.setId(1L);
        MobyStatusDTO mobyStatusDTO2 = new MobyStatusDTO();
        assertThat(mobyStatusDTO1).isNotEqualTo(mobyStatusDTO2);
        mobyStatusDTO2.setId(mobyStatusDTO1.getId());
        assertThat(mobyStatusDTO1).isEqualTo(mobyStatusDTO2);
        mobyStatusDTO2.setId(2L);
        assertThat(mobyStatusDTO1).isNotEqualTo(mobyStatusDTO2);
        mobyStatusDTO1.setId(null);
        assertThat(mobyStatusDTO1).isNotEqualTo(mobyStatusDTO2);
    }
}
