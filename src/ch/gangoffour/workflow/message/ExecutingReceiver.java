package ch.gangoffour.workflow.message;

import ch.gangoffour.workflow.Output;
import ch.gangoffour.workflow.Receiver;

public class ExecutingReceiver<H, T, D> extends Receiver<Message<H, ExecutableBody<T,D>>> {

    private T input;

    public ExecutingReceiver(Output<Message<H, ExecutableBody<T, D>>> input, T val){
        super(input);
        this.input = val;
    }

    @Override
    protected void receive(Message<H, ExecutableBody<T, D>> message) {
        message.body.execute(input);
    }
}
