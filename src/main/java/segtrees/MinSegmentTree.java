package segtrees;

public class MinSegmentTree {
    public static SegmentTree<Long, Long> create(Long[] array) {
        return new SegmentTree<>(
                array,
                Math::min,
                (current, update, len) -> update,
                Long.MAX_VALUE,
                Long.MAX_VALUE
        );
    }
}