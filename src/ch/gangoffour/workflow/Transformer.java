package ch.gangoffour.workflow;

public abstract class Transformer<T> extends WorkflowNode<T> {
    final private Output<T> output;

    public Transformer(Output<T> input) {
        super(NodeType.TRANSFORMER);
        output = input.map(this::transformAux);
    }

    public Output<T> getOutput() {
        return output;
    }

    private T transformAux(T value) {
        T res = transform(value);
        log(value, res);
        return res;
    }

    protected abstract T transform(T value);
}