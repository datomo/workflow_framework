package ch.gangoffour.workflow;

public abstract class Reducer<T> {

    private final Output<T> output;

    public Reducer(List<Output<T>> inputs) {
        output = Output.reduce(inputs, getInitial(), this::combine);
    }

    public Output<T> getOutput() {
        return output;
    }

    protected abstract T combine(T val0, T val1);
    
    protected abstract T getInitial();
}