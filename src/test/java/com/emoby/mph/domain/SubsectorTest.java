package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class SubsectorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subsector.class);
        Subsector subsector1 = new Subsector();
        subsector1.setId(1L);
        Subsector subsector2 = new Subsector();
        subsector2.setId(subsector1.getId());
        assertThat(subsector1).isEqualTo(subsector2);
        subsector2.setId(2L);
        assertThat(subsector1).isNotEqualTo(subsector2);
        subsector1.setId(null);
        assertThat(subsector1).isNotEqualTo(subsector2);
    }
}
