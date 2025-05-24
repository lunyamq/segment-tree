package segtrees;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static segtrees.Constants.*;

class StressRandomTest {
    @Test
    void test() {
        int n = DEF_RAND.nextInt(DEF_ARR_SIZE) + 1;
        Long[] arr = new Long[n];
        for (int i = 0; i < n; i++)
            arr[i] = DEF_RAND.nextLong();
        SegmentTree<Long, Long> tree1 = SumSegmentTree.create(arr);
        SegmentTree<Long, Long> tree2 = MinSegmentTree.create(arr);

        for (int i = 0; i < STRESS_TESTS; i++) {
            int pos = DEF_RAND.nextInt(n);
            long val = DEF_RAND.nextLong();
            arr[pos] = val;
            tree1.update(pos, pos, val);
            tree2.update(pos, pos, val);
            assertEquals(Arrays.stream(arr).mapToLong(Long::longValue).sum(), tree1.query(0, n-1));
            assertEquals(Arrays.stream(arr).mapToLong(Long::longValue).min().getAsLong(), tree2.query(0, n-1));
        }
    }
}