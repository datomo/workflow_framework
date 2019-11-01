package ch.gangoffour.workflow;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BiSplitter<T> {
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

    protected BiSplitter(Output<T> input) {
        List<Output<T>> outputs = input.split(2, val -> {
            Result<T> res = split(val);
            return Stream.of(res.o1, res.o2).collect(Collectors.toList());
        });

        output1 = outputs.get(0);
        output2 = outputs.get(1);
    }

    public Output<T> getOutput1() {
        return output1;
    }

    public Output<T> getOutput2() {
        return output2;
    }

    protected abstract Result<T> split(T input);
}
