package segtrees;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static segtrees.Constants.*;

class SumSegmentTreeTest {
    @Test
    void testSum() {
        Long[] arr = {1L, 2L, 3L, 4L, 5L};
        SegmentTree<Long, Long> tree = SumSegmentTree.create(arr);

        assertEquals(9L, tree.query(1, 3)); // 2+3+4
        tree.update(2, 2, 10L); // Изменяем 3 на 10
        assertEquals(16L, tree.query(1, 3)); // 2+10+4
    }

    @Test
    void randomTest() {
        int n = DEF_RAND.nextInt(DEF_ARR_SIZE) + 1;
        Long[] arr = new Long[n];
        for (int i = 0; i < n; i++)
            arr[i] = DEF_RAND.nextLong();
        SegmentTree<Long, Long> tree = SumSegmentTree.create(arr);

        assertEquals(Arrays.stream(arr).mapToLong(Long::longValue).sum(), tree.query(0, n-1));
        for (int i = 0; i < DEF_TESTS; i++) {
            int pos = DEF_RAND.nextInt(n);
            long val = DEF_RAND.nextLong();
            arr[pos] = val;
            tree.update(pos, pos, val);
            assertEquals(Arrays.stream(arr).mapToLong(Long::longValue).sum(), tree.query(0, n-1));
        }
    }
}