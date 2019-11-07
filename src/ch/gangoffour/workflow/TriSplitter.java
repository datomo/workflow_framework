package ch.gangoffour.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TriSplitter<T> extends WorkflowNode<T> {
    public static class Result<T> {
        final T o1;
        final T o2;
        final T o3;

        public Result(T o1, T o2, T o3) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
        }
    }

    final private Output<T> output1;
    final private Output<T> output2;
    final private Output<T> output3;

    TriSplitter(Output<T> input) {
        super(NodeType.SPLITTER);
        List<Output<T>> outputs = input.split(3, val -> {
            Result<T> res = splitAux(val);
            return Stream.of(res.o1, res.o2, res.o3).collect(Collectors.toList());
        });

        output1 = outputs.get(0);
        output2 = outputs.get(1);
        output3 = outputs.get(2);
    }

    public Output<T> getOutput1() {
        return output1;
    }

    public Output<T> getOutput2() {
        return output2;
    }

    public Output<T> getOutput3() {
        return output3;
    }

    private Result<T> splitAux(T val) {
        Result<T> res = split(val);
        HashMap<String, T> outputs = new HashMap<>();
        outputs.put("output 1", res.o1);
        outputs.put("output 2", res.o2);
        outputs.put("output 3", res.o3);
        log(val, outputs);
        return res;
    }

    protected abstract Result<T> split(T input);
}
