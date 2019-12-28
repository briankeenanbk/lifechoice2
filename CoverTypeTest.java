import lifechoice.CoverType;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoverTypeTest {

    @Test
    public void getSingleFactor() {
        assertEquals(1.0, CoverType.SINGLE.getFactor(), 0.00);
    }

    @Test
    public void getDualFactor() {
        assertEquals(1.0, CoverType.DUAL.getFactor(), 0.00);
    }

    @Test
    public void getJointFactor() {
        assertEquals(0.945, CoverType.JOINT.getFactor(), 0.00);
    }
}