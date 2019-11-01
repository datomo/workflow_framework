package ch.gangoffour.workflow.doubles;

import ch.gangoffour.workflow.Output;
import ch.gangoffour.workflow.Transformer;

public class Adder extends Transformer<Double> {

    private final double addend;

    public Adder(Output<Double> input, double addend){
        super(input);
        this.addend = addend;
    }

    @Override
    protected Double transform(Double value) {
        return value + addend;
    }
}