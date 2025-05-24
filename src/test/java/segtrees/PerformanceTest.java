package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static segtrees.Constants.*;

class PerformanceTest {
    @Test
    void test() {
        int n = PERF_RAND.nextInt(PERF_ARR_SIZE) + 1;
        Long[] arr = new Long[n];
        for (int i = 0; i < n; i++)
            arr[i] = PERF_RAND.nextLong(1_000_000L) - 500_000L;
        SegmentTree<Long, Long> tree = MinSegmentTree.create(arr);

        long t0 = System.nanoTime();
        for (int i = 0; i < PERF_TESTS; i++) {
            int l = PERF_RAND.nextInt(n);
            int r = PERF_RAND.nextInt(n);
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            int pos = l + PERF_RAND.nextInt(r - l + 1);
            long val = PERF_RAND.nextLong(1_000_000L) - 500_000L;
            arr[pos] = val;
            tree.update(pos, pos, val);
            assertEquals(findMin(arr, l, r), tree.query(l, r));
        }

        long ms = (System.nanoTime() - t0) / 1_000_000;
        assertTrue(ms < 20_000, "slow: " + ms);
    }

    long findMin(Long[] arr, int l, int r) {
        long min = arr[l];
        for (int i = l + 1; i <= r; i++) {
            if (arr[i] < min)
                min = arr[i];
        }

        return min;
    }
}