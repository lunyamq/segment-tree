package segtrees;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static segtrees.Constants.*;

class MinSegmentTreeTest {
    @Test
    void testMin() {
        Long[] arr = {5L, 3L, 7L, 2L, 8L};
        SegmentTree<Long, Long> tree = MinSegmentTree.create(arr);

        assertEquals(2L, tree.query(0, 4)); // min(2, 3, 5, 7, 8)
        tree.update(3, 3,10L); // Изменяем 2 на 10
        assertEquals(3L, tree.query(0, 4)); // min(3, 5, 7, 8, 10)
    }

    @Test
    void randomTest() {
        int n = DEF_RAND.nextInt(DEF_ARR_SIZE) + 1;
        Long[] arr = new Long[n];
        for (int i = 0; i < n; i++)
            arr[i] = DEF_RAND.nextLong();
        SegmentTree<Long, Long> tree = MinSegmentTree.create(arr);

        assertEquals(Arrays.stream(arr).mapToLong(Long::longValue).min().getAsLong(), tree.query(0, n-1));
        for (int i = 0; i < DEF_TESTS; i++) {
            int pos = DEF_RAND.nextInt(n);
            long val = DEF_RAND.nextLong();
            arr[pos] = val;
            tree.update(pos, pos, val);
            assertEquals(Arrays.stream(arr).mapToLong(Long::longValue).min().getAsLong(), tree.query(0, n-1));
        }
    }
}