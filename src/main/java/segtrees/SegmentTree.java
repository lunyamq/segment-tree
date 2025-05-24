package segtrees;

import java.lang.reflect.Array;

public class SegmentTree<T, U> {
    private final int size;
    private final T[] data;
    private final U[] lazy;
    private final Combiner<T> combiner;
    private final Updater<T, U> updater;
    private final T neutralElement;
    private final U noUpdate;

    public SegmentTree(T[] input, Combiner<T> combiner, Updater<T, U> updater, T neutral, U noUpdate) {
        this.size = input.length;
        this.combiner = combiner;
        this.updater = updater;
        this.neutralElement = neutral;
        this.noUpdate = noUpdate;
        this.data = (T[]) Array.newInstance(input.getClass().getComponentType(), 4 * size);
        this.lazy = (U[]) Array.newInstance(noUpdate.getClass(), 4 * size);

        constructTree(input, 1, 0, size - 1);
    }

    private void constructTree(T[] input, int node, int nodeLeft, int nodeRight) {
        if (nodeLeft == nodeRight)
            data[node] = input[nodeLeft];
        else {
            int middle = (nodeLeft + nodeRight) / 2;
            constructTree(input, 2 * node, nodeLeft, middle);
            constructTree(input, 2 * node + 1, middle + 1, nodeRight);
            data[node] = combiner.combine(data[2 * node], data[2 * node + 1]);
        }

        lazy[node] = noUpdate;
    }

    public T query(int left, int right) {
        return rangeOperation(1, 0, size - 1, left, right, null, false);
    }

    public void update(int left, int right, U update) {
        rangeOperation(1, 0, size - 1, left, right, update, true);
    }

    private T rangeOperation(int node, int nodeLeft, int nodeRight, int left, int right, U update, boolean isUpdate) {
        pushLazy(node, nodeLeft, nodeRight);

        if (left > right)
            return neutralElement;

        if (left == nodeLeft && right == nodeRight) {
            if (isUpdate) {
                lazy[node] = update;
                pushLazy(node, nodeLeft, nodeRight);
            }

            return data[node];
        }

        int middle = (nodeLeft + nodeRight) / 2;
        T leftResult = rangeOperation(2 * node, nodeLeft, middle, left, Math.min(right, middle), update, isUpdate);
        T rightResult = rangeOperation(2 * node + 1, middle + 1, nodeRight, Math.max(left, middle + 1), right, update, isUpdate);

        if (isUpdate)
            data[node] = combiner.combine(data[2 * node], data[2 * node + 1]);

        return combiner.combine(leftResult, rightResult);
    }

    private void pushLazy(int node, int nodeLeft, int nodeRight) {
        if (lazy[node] == noUpdate) return;

        data[node] = updater.apply(data[node], lazy[node], nodeRight - nodeLeft + 1);

        if (nodeLeft != nodeRight) {
            lazy[2 * node] = lazy[node];
            lazy[2 * node + 1] = lazy[node];
        }

        lazy[node] = noUpdate;
    }
}