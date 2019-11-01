package ch.gangoffour.workflow;

import java.util.List;

public abstract class Reducer<T> {

    private final Output<T> output;

    public Reducer(List<Output<T>> inputs, T initial) {
        output = Output.reduce(inputs, initial, this::combine);
    }

    public Output<T> getOutput() {
        return output;
    }

    protected abstract T combine(T val0, T val1);
}