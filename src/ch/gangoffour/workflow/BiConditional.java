package ch.gangoffour.workflow;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BiConditional<T> {
    public static class Result<T> {
        final T o1;
        final T o2;

        public Result(T o1, T o2) {
            this.o1 = o1;
            this.o2 = o2;
        }
    }
    final private Output<T> outputTrue;
    final private Output<T> outputFalse;

    BiConditional(Output<T> input) {
        List<Output<T>>outputs = input.conditionalSplit(2, val -> {
            return takeFirstBranch(val) ? 0 : 1;
        });

        outputTrue = outputs.get(0);
        outputFalse = outputs.get(1);

    }

    public Output<T> getOutputTrue() {
        return outputTrue;
    }

    public Output<T> getOutputFalse() {
        return outputFalse;
    }

    abstract boolean takeFirstBranch(T input);
}
