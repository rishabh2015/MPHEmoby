package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class MobyStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobyStatus.class);
        MobyStatus mobyStatus1 = new MobyStatus();
        mobyStatus1.setId(1L);
        MobyStatus mobyStatus2 = new MobyStatus();
        mobyStatus2.setId(mobyStatus1.getId());
        assertThat(mobyStatus1).isEqualTo(mobyStatus2);
        mobyStatus2.setId(2L);
        assertThat(mobyStatus1).isNotEqualTo(mobyStatus2);
        mobyStatus1.setId(null);
        assertThat(mobyStatus1).isNotEqualTo(mobyStatus2);
    }
}
