package ch.gangoffour.workflow;

import java.util.Arrays;

public class ConditionalMerger<T> {

    private final Output<T> output;

    public ConditionalMerger<T>(Output<T>... outputs) {
        output = Output.merge(Arrays.asList(outputs));
    }

    public Output<T> getOutput() {
        return output;
    }
}