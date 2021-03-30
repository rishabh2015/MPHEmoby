package com.emoby.mph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emoby.mph.web.rest.TestUtil;

public class PotentialCandidateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PotentialCandidate.class);
        PotentialCandidate potentialCandidate1 = new PotentialCandidate();
        potentialCandidate1.setId(1L);
        PotentialCandidate potentialCandidate2 = new PotentialCandidate();
        potentialCandidate2.setId(potentialCandidate1.getId());
        assertThat(potentialCandidate1).isEqualTo(potentialCandidate2);
        potentialCandidate2.setId(2L);
        assertThat(potentialCandidate1).isNotEqualTo(potentialCandidate2);
        potentialCandidate1.setId(null);
        assertThat(potentialCandidate1).isNotEqualTo(potentialCandidate2);
    }
}
