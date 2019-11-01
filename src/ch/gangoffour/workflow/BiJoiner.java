package ch.gangoffour.workflow;

public abstract class BiJoiner<T> {

    private final Output<T> output;

    public BiJoiner<T>(Output<T> input0, Output<T> input1) {
        output = input0.combine(input1, this::combine);
    }

    public Output<T> getOutput() {
        return output;
    }

    protected abstract T combine(T val0, T val1);

}