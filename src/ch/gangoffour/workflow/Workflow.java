package ch.gangoffour.workflow;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Workflow<T> {

    private final BiFunction<Output<T>, Logger<T>, Output<T>>  creator;

    public Workflow(Function<Output<T>, Output<T>> creator) {
        this.creator = (input, logger) -> creator.apply(input);
    }
    public Workflow(BiFunction<Output<T>, Logger<T>, Output<T>> creator) {
        this.creator = creator;
    }

    public Receiver<T> instantiate(Sender<T> sender, Function<Output<T>, Receiver<T>> receiverCreator) {
        return receiverCreator.apply(instantiate(sender.getOutput()).getOutput());
    }

    public Receiver<T> instantiate(Sender<T> sender, Function<Output<T>, Receiver<T>> receiverCreator, Logger<T> logger) {
        ComplexNode<T> cNode = instantiate(sender.getOutput());
        logger.log(cNode);
        return receiverCreator.apply(cNode.getOutput());
    }

    public ComplexNode<T> instantiate(Output<T> input) {
        return new ComplexNode<>(creator, input);
    }

    public Workflow<T> concatenate(Workflow<T> other) {
        return new Workflow<>((sender, logger) -> {
            Output<T> output = this.creator.apply(sender, logger);
            return other.creator.apply(output, logger);
        });
    }
}
