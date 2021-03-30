package com.emoby.mph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class SectorsubsectorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectorsubsectorDTO.class);
        SectorsubsectorDTO sectorsubsectorDTO1 = new SectorsubsectorDTO();
        sectorsubsectorDTO1.setId(1L);
        SectorsubsectorDTO sectorsubsectorDTO2 = new SectorsubsectorDTO();
        assertThat(sectorsubsectorDTO1).isNotEqualTo(sectorsubsectorDTO2);
        sectorsubsectorDTO2.setId(sectorsubsectorDTO1.getId());
        assertThat(sectorsubsectorDTO1).isEqualTo(sectorsubsectorDTO2);
        sectorsubsectorDTO2.setId(2L);
        assertThat(sectorsubsectorDTO1).isNotEqualTo(sectorsubsectorDTO2);
        sectorsubsectorDTO1.setId(null);
        assertThat(sectorsubsectorDTO1).isNotEqualTo(sectorsubsectorDTO2);
    }
}
