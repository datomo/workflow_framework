package ch.gangoffour.workflow;

public class PrintReceiver<T> extends Receiver<T> {
    public PrintReceiver(Output<T> input){
        super(input);
    }

    @Override
    protected void receive(T val) {
        System.out.println(val);
    }
}
