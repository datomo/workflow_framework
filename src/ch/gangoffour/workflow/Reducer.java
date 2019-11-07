package ch.gangoffour.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Reducer<T> extends WorkflowNode<T> {

    private final Output<T> output;

    public Reducer(List<Output<T>> inputs, T initial) {
        super(NodeType.MERGER);
        output = Output.reduce(inputs, initial, this::combine);
        for (int i = 0; i < inputs.size(); i++) {
            final int ii = i;
            inputs.get(i).onOutput(q -> this.logAux(ii, q.remove()));
        }
    }

    private void logAux(int input, T value) {
        Map<String, T> inputs = new HashMap<>();
        inputs.put("input " + input, value);
        log(inputs, value);
    }

    public Output<T> getOutput() {
        return output;
    }

    protected abstract T combine(T val0, T val1);
}