package ch.gangoffour.workflow;

import java.util.List;
import java.util.function.Function;
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
    final private Output<T> output1;
    final private Output<T> output2;

    BiConditional(Output<T> input, Function decider) {
        List<Output<T>>outputs = input.conditionalSplit(2, val -> {
            return conditionalSplit(val, decider);
        });

        output1 = outputs.get(0);
        output2 = outputs.get(1);

    }


    abstract Integer conditionalSplit(T input, Function f);
}
