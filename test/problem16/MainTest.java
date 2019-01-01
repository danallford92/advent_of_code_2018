package problem16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static problem16.Instruction.*;

public class MainTest {

    @Test
    public void addr() {
        assertEquals(ADDR.newValue(new int[4], 0, 1), 0);
        assertEquals(ADDR.newValue(new int[]{1, 2, 3, 4}, 0, 1), 3);
        assertEquals(ADDR.newValue(new int[]{1, 2, 3, 4}, 0, 1), 3);
        assertEquals(ADDR.newValue(new int[]{1, 2, 3, 4}, 0, 1), 3);
        assertEquals(ADDR.newValue(new int[]{1, 2, 3, 4}, 0, 0), 2);
        assertEquals(ADDR.newValue(new int[]{1, 2, 3, 4}, 3, 3), 8);
    }

    @Test
    public void addi() {
        assertEquals(ADDI.newValue(new int[4], 0, 1), 1);
        assertEquals(ADDI.newValue(new int[]{1, 2, 3, 4}, 0, 1), 2);
        assertEquals(ADDI.newValue(new int[]{1, 2, 3, 4}, 0, 4), 5);
        assertEquals(ADDI.newValue(new int[]{1, 2, 3, 4}, 0, 7), 8);
    }

    @Test
    public void mulr() {
        assertEquals(MULR.newValue(new int[4], 0, 1), 0);
        assertEquals(MULR.newValue(new int[]{1, 2, 3, 4}, 0, 1), 2);
        assertEquals(MULR.newValue(new int[]{1, 2, 3, 4}, 0, 0), 1);
    }

    @Test
    public void muli() {
        assertEquals(MULI.newValue(new int[4], 0, 1), 0);
        assertEquals(MULI.newValue(new int[]{1, 2, 3, 4}, 3, 5), 20);
        assertEquals(MULI.newValue(new int[]{1, 2, 3, 4}, 2, 2), 6);
    }

}
