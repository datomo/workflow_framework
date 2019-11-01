package ch.gangoffour.workflow.doubles;

import ch.gangoffour.workflow.Output;
import ch.gangoffour.workflow.Transformer;

public class Multiplier extends Transformer<Double> {

    private final double factor;

    public Multiplier(Output<Double> input, double factor){
        super(input);
        this.factor = factor;
    }

    @Override
    protected Double transform(Double value) {
        return value*factor;
    }
}
