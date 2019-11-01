package ch.gangoffour.workflow.doubles;

import ch.gangoffour.workflow.BiSplitter;
import ch.gangoffour.workflow.Output;

public class DividingSplitter extends BiSplitter<Double> {

    private final int denominator;

    public DividingSplitter(Output<Double> input, int denominator){
        super(input);
        this.denominator = denominator;
    }

    @Override
    protected Result<Double> split(Double input) {
        return new Result<>(input/denominator, input - input/denominator);
    }
}
