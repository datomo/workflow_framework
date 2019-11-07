package ch.gangoffour.workflow;

import java.util.Arrays;
import java.util.List;

public class FixedSender<T> extends Sender<T> {
    private final List<T> ts;

    @SafeVarargs
    public FixedSender(T... values) {
        this.ts = Arrays.asList(values);
    }

    public void start(){
        ts.forEach(this::send);
    }
}
