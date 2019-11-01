package ch.gangoffour.workflow;

import java.util.Collections;

public abstract class Receiver<T> {

    public Receiver(Output<T> output){
        Output.waitFor(Collections.singletonList(output), l -> {
            receive(l.get(0));
        });
    }

    abstract protected void receive(T val);

}
