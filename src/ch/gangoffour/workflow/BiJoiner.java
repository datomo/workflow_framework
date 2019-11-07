package ch.gangoffour.workflow;

import java.util.HashMap;

public abstract class BiJoiner<T> extends WorkflowNode<T> {

    private final Output<T> output;

    public BiJoiner(Output<T> input0, Output<T> input1) {
        super(NodeType.JOINER);
        output = input0.combine(input1, this::combineAux);
    }

    private T combineAux(T val0, T val1) {
        T res = combine(val0, val1);
        HashMap<String, T> inputs = new HashMap<>();
        inputs.put("input 1", val0);
        inputs.put("input 2", val1);
        log(inputs, res);
        return res;
    }

    public Output<T> getOutput() {
        return output;
    }

    protected abstract T combine(T val0, T val1);

}