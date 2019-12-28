import lifechoice.PolicyType;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolicyTypeTest {

    @Test
    public void getYouAndFamilyRatePrefix() {
        String prefix = PolicyType.YOU_AND_FAMILY.getRatePrefix(false);
        assertEquals("11", prefix);
    }

    @Test
    public void getYouAndFamilyIncreasingRatePrefix() {
        String prefix = PolicyType.YOU_AND_FAMILY.getRatePrefix(true);
        assertEquals("12", prefix);
    }

    @Test
    public void getHomeRatePrefix() {
        String prefix = PolicyType.HOME.getRatePrefix(false);
        assertEquals("13", prefix);
    }

    @Test
    public void getHomeIncreasingRatePrefix() {
        String prefix = PolicyType.HOME.getRatePrefix(true);
        assertEquals("13", prefix);
    }

    @Test
    public void getAssetsRatePrefix() {
        String prefix = PolicyType.ASSETS.getRatePrefix(false);
        assertEquals("11", prefix);
    }

    @Test
    public void getAssetsIncreasingRatePrefix() {
        String prefix = PolicyType.ASSETS.getRatePrefix(true);
        assertEquals("12", prefix);
    }

    @Test
    public void getYouAndFamilyAgeGapPrefix() {
        String prefix = PolicyType.YOU_AND_FAMILY.getAgeGapPrefix();
        assertEquals("21", prefix);
    }

    @Test
    public void getHomeAgeGapPrefix() {
        String prefix = PolicyType.HOME.getAgeGapPrefix();
        assertEquals("22", prefix);
    }

    @Test
    public void getAssetsAgeGapPrefix() {
        String prefix = PolicyType.ASSETS.getAgeGapPrefix();
        assertEquals("21", prefix);
    }

    @Test
    public void getYouAndFamilySinglePrefix() {
        String prefix = PolicyType.YOU_AND_FAMILY.getSinglePrefix(false);
        assertEquals("31", prefix);
        prefix = PolicyType.YOU_AND_FAMILY.getSinglePrefix(true);
        assertEquals("31", prefix);
    }

    @Test
    public void getHomeSinglePrefix() {
        String prefix = PolicyType.HOME.getSinglePrefix(false);
        assertEquals("31", prefix);
        prefix = PolicyType.HOME.getSinglePrefix(true);
        assertEquals("31", prefix);
    }

    @Test
    public void getAssetsSinglePrefix() {
        String prefix = PolicyType.ASSETS.getSinglePrefix(false);
        assertEquals("31", prefix);
        prefix = PolicyType.ASSETS.getSinglePrefix(true);
        assertEquals("32", prefix);
    }
}