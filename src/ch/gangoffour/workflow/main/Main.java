package ch.gangoffour.workflow.main;

import ch.gangoffour.workflow.*;
import ch.gangoffour.workflow.doubles.Adder;
import ch.gangoffour.workflow.doubles.DividingSplitter;
import ch.gangoffour.workflow.doubles.Multiplier;
import ch.gangoffour.workflow.log.PrintLogger;
import ch.gangoffour.workflow.message.ExecutableBody;
import ch.gangoffour.workflow.message.ExecutingReceiver;
import ch.gangoffour.workflow.message.Message;

import java.util.Arrays;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        //example use of Double Workflow
        Workflow<Double> wf = new Workflow<>((input, logger) -> {
            Multiplier times2 = new Multiplier(input, 2);
            PredicateConditional<Double> bigger0 = new PredicateConditional<>(times2.getOutput(), d -> d > 0.0);
            Adder sub42 = new Adder(bigger0.getOutputFalse(), -42);
            DividingSplitter split2 = new DividingSplitter(sub42.getOutput(), 2);
            Multiplier div2 = new Multiplier(split2.getOutput2(), 0.5);

            logger.log(times2, split2, div2);

            return new ConditionalMerger<>(
                    new OperaterReducer<>(Arrays.asList(split2.getOutput1(), div2.getOutput()),
                            Double::sum, 0.0).getOutput(),
                    bigger0.getOutputTrue()).getOutput();
        });

        FixedSender<Double> sender = new FixedSender<>(2.00, 3.00, 4.00, -3.5);
        wf.instantiate(sender, PrintReceiver::new, new PrintLogger<>(Object::toString, System.out));
        sender.start();

        // Example use of our executable message in a simple workflow
        FixedSender<Message<Integer, ExecutableBody<String, String>>> messageSender = new FixedSender<>(
                new Message<>(0, new ExecutableBody<>(m -> {
                        System.out.println(m);
                        sender.start();
                    }, "this is the body")));
        new ExecutingReceiver<>(messageSender.getOutput(), "Hello, World!");
        messageSender.start();
    }
}