package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class SectorsubsectorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectorSubsector.class);
        SectorSubsector sectorsubsector1 = new SectorSubsector();
        sectorsubsector1.setId(1L);
        SectorSubsector sectorsubsector2 = new SectorSubsector();
        sectorsubsector2.setId(sectorsubsector1.getId());
        assertThat(sectorsubsector1).isEqualTo(sectorsubsector2);
        sectorsubsector2.setId(2L);
        assertThat(sectorsubsector1).isNotEqualTo(sectorsubsector2);
        sectorsubsector1.setId(null);
        assertThat(sectorsubsector1).isNotEqualTo(sectorsubsector2);
    }
}
