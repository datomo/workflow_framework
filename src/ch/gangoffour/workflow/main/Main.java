package ch.gangoffour.workflow.main;

import ch.gangoffour.workflow.*;
import ch.gangoffour.workflow.doubles.Adder;
import ch.gangoffour.workflow.doubles.DividingSplitter;
import ch.gangoffour.workflow.doubles.Multiplier;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Workflow<Double> wf = new Workflow<>(input -> {
            Multiplier times2 = new Multiplier(input,2);
            PredicateConditional<Double> bigger0 = new PredicateConditional<>(times2.getOutput(), d -> d > 0.0);
            Adder sub42 = new Adder(bigger0.getOutputFalse(), -42);
            DividingSplitter split2 = new DividingSplitter(sub42.getOutput(), 2);
            Multiplier div2 = new Multiplier(split2.getOutput2(), 0.5);

            return new ConditionalMerger<>(
                    new OperaterReducer<>(Arrays.asList(split2.getOutput1(), div2.getOutput()),
                            Double::sum, 0.0).getOutput(),
                    bigger0.getOutputTrue()).getOutput();
        });

        FixedSender<Double> sender = new FixedSender<>(2.00, 3.00, 4.00, -3.5);
        wf.instantiate(sender, PrintReceiver::new);
        sender.start();
    }
}