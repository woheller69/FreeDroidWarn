package org.woheller69.freeDroidWarn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FreeDroidWarnTest {

    @Test
    public void containsFreeOsIndicator_detectsKnownIndicators() {
        assertTrue(FreeDroidWarn.containsFreeOsIndicator("grapheneos"));
        assertTrue(FreeDroidWarn.containsFreeOsIndicator("LineageOS-21"));
        assertTrue(FreeDroidWarn.containsFreeOsIndicator("running /e/os build"));
        assertTrue(FreeDroidWarn.containsFreeOsIndicator("CALYX release"));
        assertTrue(FreeDroidWarn.containsFreeOsIndicator("iodéOS"));
    }

    @Test
    public void containsFreeOsIndicator_handlesNullAndUnknownValues() {
        assertFalse(FreeDroidWarn.containsFreeOsIndicator(null));
        assertFalse(FreeDroidWarn.containsFreeOsIndicator(""));
        assertFalse(FreeDroidWarn.containsFreeOsIndicator("stock android"));
    }

    @Test
    public void isLikelyFreeOs_trueWhenFingerprintMatches() {
        assertTrue(FreeDroidWarn.isLikelyFreeOs("google/panther/graphene-user", "", "", "", ""));
    }

    @Test
    public void isLikelyFreeOs_trueWhenBrandMatches() {
        assertTrue(FreeDroidWarn.isLikelyFreeOs("", "", "", "Lineage", ""));
    }

    @Test
    public void isLikelyFreeOs_falseWhenNoFieldMatches() {
        assertFalse(FreeDroidWarn.isLikelyFreeOs(
                "google/panther/panther:15/release-keys",
                "AP4A.250205.002",
                "panther",
                "google",
                "Google"
        ));
    }

    @Test
    public void shouldShowWarning_trueWhenNewVersionAndNotFreeOs() {
        assertTrue(FreeDroidWarn.shouldShowWarning(2, 1, false));
    }

    @Test
    public void shouldShowWarning_falseWhenVersionAlreadyAcknowledged() {
        assertFalse(FreeDroidWarn.shouldShowWarning(2, 2, false));
        assertFalse(FreeDroidWarn.shouldShowWarning(1, 2, false));
    }

    @Test
    public void shouldShowWarning_falseWhenFreeOsDetected() {
        assertFalse(FreeDroidWarn.shouldShowWarning(2, 1, true));
    }
}
