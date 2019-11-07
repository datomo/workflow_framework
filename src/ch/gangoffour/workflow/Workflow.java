package ch.gangoffour.workflow;

import java.util.function.Function;

public class Workflow<T> {

    private final Function<Output<T>, Output<T>> creator;

    public Workflow(Function<Output<T>, Output<T>> creator) {
        this.creator = creator;
    }

    public Receiver<T> instantiate(Sender<T> sender, Function<Output<T>, Receiver<T>> receiverCreator) {
        return receiverCreator.apply(creator.apply(sender.getOutput()));
    }

    public Output<T> instantiate(Output<T> input) {
        return creator.apply(input);
    }

    public Workflow<T> concatenate(Workflow<T> other) {
        return new Workflow<>(this.creator.compose(other.creator));
    }
}
