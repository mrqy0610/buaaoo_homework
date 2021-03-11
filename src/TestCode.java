package olddervation;

import org.junit.Assert;
import org.junit.Test;

public class TestCode {
    @Test
    public void testSame() {
        String ret = Derivation.runCode("x + 1");
        Assert.assertEquals("1", ret);
    }
}
