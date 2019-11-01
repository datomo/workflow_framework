package ch.gangoffour.workflow;

import java.util.function.Consumer;

public abstract class Sender<T> {
    private final Output<T> output;
    private Consumer<T> sender;

    public Sender() {
        output = new Output<>(sender -> {
            this.sender = sender;
        });
    }

    protected void send(T val){
        sender.accept(val);
    }

    public Output<T> getOutput() {
        return output;
    }
}
