package segtrees;

public class SumSegmentTree {
    public static SegmentTree<Long, Long> create(Long[] array) {
        return new SegmentTree<>(
                array,
                Long::sum,
                (current, update, len) -> update * len,
                0L,
                0L
        );
    }
}