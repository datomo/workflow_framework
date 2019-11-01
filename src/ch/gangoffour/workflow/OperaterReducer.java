package ch.gangoffour.workflow;

import java.util.List;
import java.util.function.BinaryOperator;

public class OperaterReducer<T> extends Reducer<T>{

    private final BinaryOperator<T> combiner;

    public OperaterReducer(List<Output<T>> inputs, BinaryOperator<T> combiner, T initalValue){
        super(inputs, initalValue);
        this.combiner = combiner;
    }

    @Override
    protected T combine(T val0, T val1) {
        return combiner.apply(val0, val1);
    }
}
