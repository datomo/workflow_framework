package ch.gangoffour.workflow;

public abstract class Transformer<T> {
    final private Output<T> output;

    public Transformer(Output<T> input) {
      output = input.map(this::transform);
    }

    public Output<T> getOutput() {
        return output;
    }

    protected abstract T transform(T value);
}